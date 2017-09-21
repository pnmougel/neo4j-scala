package org.neo4jscala.core

import java.util.Date

import org.neo4j.driver.v1.Value
import org.neo4j.driver.v1.types.Node
import org.neo4j.driver.v1.util.Function

import scala.reflect.ClassTag
import scala.reflect.runtime.universe._
import scala.reflect.runtime.currentMirror

import scala.collection.JavaConverters._

/**
  * Created by nico on 19/09/17.
  */

object NodeMapper {
  private def asIterator[T](v: Value, f: Value => T): Iterable[T] = {
    val list = v.asList[T](new Function[Value, T] {
      def apply(v: Value): T = {
        f(v)
      }
    })
    collectionAsScalaIterableConverter[T](list).asScala
  }
  private def asSet[T](v: Value, f: Value => T) = asIterator(v, f).toSet
  private def asList[T](v: Value, f: Value => T) = asIterator(v, f).toList
  private def asVector[T](v: Value, f: Value => T) = asIterator(v, f).toVector
  private def asArray[T](v: Value, f: Value => T)(implicit m: Manifest[T]) = asIterator(v, f).toArray[T]
  private def asSeq[T](v: Value, f: Value => T) = asIterator(v, f).toSeq

  private def isOfType[T : TypeTag](typeToCheck: Type): Boolean = {
    val t = typeOf[T]
    t =:= typeToCheck || typeToCheck.typeArgs.exists(_ =:= t)
  }

  def handleType[T: TypeTag](typeToCheck: Type, value: Value, f: Value => T): AnyRef = {
    val mappedValue = typeToCheck match {
      case t if t <:< typeOf[T] => f(value)
      case t if t <:< typeOf[Option[T]] => Some(f(value))
      case t if t <:< typeOf[List[T]] => asList(value, f)
      case t if t <:< typeOf[Set[T]] => asSet(value, f)
      case t if t <:< typeOf[Vector[T]] => asVector(value, f)
      case t if t <:< typeOf[Seq[T]] => asSeq(value, f)
      case t if t <:< typeOf[Array[T]] => asArray(value, f)
      case _ => None
    }
    mappedValue.asInstanceOf[AnyRef]
  }

  def as[T <: Neo4jNode](node: Node)(implicit tag: ClassTag[T], tt: TypeTag[T]): T = {
    val cl2 = symbolOf[T].asClass
    val defaultParams = getFieldsDefaultParameters[T]
    val constructor = cl2.asClass.primaryConstructor.asMethod

    val optParams : List[AnyRef] = for(p <- constructor.paramLists.head) yield {
      val paramName = p.name.decodedName.toString
      if(node.containsKey(paramName)) {
        val v = node.get(paramName)
        p.typeSignature match {
          // Handle primitive types
          case t if isOfType[String](t) => handleType[String](t, v, _.asString())
          case t if isOfType[Int](t) => handleType[Int](t, v, _.asInt())
          case t if isOfType[Boolean](t) => handleType[Boolean](t, v, _.asBoolean())
          case t if isOfType[Double](t) => handleType[Double](t, v, _.asDouble())
          case t if isOfType[Float](t) => handleType[Float](t, v, _.asFloat())
          case t if isOfType[Long](t) => handleType[Long](t, v, _.asLong())

          // Handle date
          case t if isOfType[Date](t) => handleType[Date](t, v, e => Neo4jDefaultDateFormat.dateFormat.parse(e.asString()))

            // Handle enumeration
          case t if isEnumeration(t) =>
            val enumerationAsString = v.asString()
            getEnumerationValues(t)(enumerationAsString)
        }
      } else if (defaultParams.contains(paramName)) {
        defaultParams(paramName)
      } else {
        p.typeSignature match {
          case t if t <:< typeOf[Option[_]] => None
          case t if t <:< typeOf[List[_]] => List()
          case _ =>
            println("Unable to map field")
            None
        }
      }
    }

    val cl = tag.runtimeClass
    val params = optParams
    val inst = (for (constructor <- cl.getConstructors
          if constructor.getParameterCount == params.length) yield {
      constructor.setAccessible(true)
      constructor.newInstance(params: _*).asInstanceOf[T]
    }).head

    inst.id = node.get("id").asString()
    inst.labels = iterableAsScalaIterableConverter(node.labels()).asScala.toVector
    inst
  }


  /**
    * Check if a type corresponds to an enumeration
    *
    * @param t
    * @return true if t extends Enumeration, false otherwise
    */
  private def isEnumeration(t: Type): Boolean = {
    val parent = t.asInstanceOf[TypeRef].pre
    parent <:< typeOf[Enumeration]
  }

  /**
    * Check if the type correspond to a List of enumeration by checking the type args
    *
    * @param t
    * @return
    */
  def isEnumerationList(t: Type): Boolean = {
    t <:< typeOf[List[Any]] && t.asInstanceOf[TypeRef].typeArgs.exists(tpe => isEnumeration(tpe))
  }

  /**
    * Check if the type correspond to a Option of enumeration by checking the type args
    *
    * @param t
    * @return
    */
  def isEnumerationOption(t: Type): Boolean = {
    t <:< typeOf[Option[Any]] && t.asInstanceOf[TypeRef].typeArgs.exists(tpe => isEnumeration(tpe))
  }

  /**
    * Given a type corresponding to a list of enumeration, provide the values
    *
    * @param listType
    * @return
    */
  def getEnumerationListValues(listType: Type): Map[String, AnyRef] = {
    val t = listType.asInstanceOf[TypeRef].typeArgs.find(tpe => isEnumeration(tpe)).get
    getEnumerationValues(t)
  }

  /**
    * Return all the values of an enumeration type
    *
    * @param t
    * @return
    */
  def getEnumerationValues(t: Type): Map[String, AnyRef] = {
    val parent = t.asInstanceOf[TypeRef].pre

    val module = currentMirror.staticModule(parent.typeSymbol.fullName)
    val obj = currentMirror.reflectModule(module)
    val enum = obj.instance.asInstanceOf[Enumeration]
    Map(enum.values.toList.map(value => {
      value.toString -> value
    }): _*)
  }

  def getFieldsDefaultParameters[T: TypeTag]: Map[String, AnyRef] = {
    import scala.reflect.runtime.universe

    val classTag = ClassTag[T](typeTag[T].mirror.runtimeClass(typeTag[T].tpe))
      val mod = currentMirror.classSymbol(classTag.runtimeClass).companion.asModule
      val im = currentMirror.reflect(currentMirror.reflectModule(mod).instance)
      val ts = im.symbol.typeSignature
      val mApply = ts.member(universe.TermName("apply")).asMethod
      Map(mApply.paramLists.flatten.zipWithIndex.map { case (p, i) =>
        (p.name.toString, ts.member(universe.TermName(s"apply$$default$$${i + 1}")))
      }.filter(_._2.isMethod).map(m => {
        (m._1, im.reflectMethod(m._2.asMethod)().asInstanceOf[AnyRef])
      }): _*)
    }
}
