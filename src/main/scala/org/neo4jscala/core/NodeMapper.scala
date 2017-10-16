package org.neo4jscala.core

import java.util.Date

import org.neo4j.driver.v1.Value
import org.neo4j.driver.v1.types.Node
import org.neo4j.driver.v1.util.Function
import org.neo4jscala.utils.ReflectionUtilities

import scala.collection.JavaConverters._
import scala.reflect.ClassTag
import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe._


/**
  * Created by nico on 19/09/17.
  */

object NodeMapper extends ReflectionUtilities {
  private def asIterator[T](v: Value, f: Value => T): Iterable[T] = {
    val list = v.asList[T](new Function[Value, T] {
      def apply(v: Value): T = f(v)
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

  private def handleType[T: TypeTag](typeToCheck: Type, value: Value, f: Value => T)(implicit m: Manifest[T]): AnyRef = {
    val mappedValue = typeToCheck match {
      case t if t <:< typeOf[T] => f(value)
      case t if t <:< typeOf[Option[T]] => Some(f(value))
      case t if t <:< typeOf[List[T]] => asList(value, f)
      case t if t <:< typeOf[Set[T]] => asSet(value, f)
      case t if t <:< typeOf[Vector[T]] => asVector(value, f)
      case t if t <:< typeOf[Seq[T]] => asSeq(value, f)
      case t if t <:< typeOf[Array[T]] => asArray[T](value, f)
      case _ => None
    }
    mappedValue.asInstanceOf[AnyRef]
  }

  def as[T <: Neo4jNode](node: Node)(implicit tag: ClassTag[T], tt: TypeTag[T]): T = {
    val cl2 = symbolOf[T].asClass
    val defaultParams = getFieldsDefaultParameters[T]
    val constructor = cl2.primaryConstructor.asMethod

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
          case t if isEnumeration(t) => getEnumerationValues(t)(v.asString())
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

    val params = optParams
    val inst = currentMirror.reflectClass(cl2).reflectConstructor(constructor)(params: _*).asInstanceOf[T]

    inst.id = node.get("id").asString()
    inst.nodeLabels = iterableAsScalaIterableConverter(node.labels()).asScala.toVector
    inst
  }
}
