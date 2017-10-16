package org.neo4jscala.utils

import scala.reflect.ClassTag
import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe._

trait ReflectionUtilities {
  /**
    * Check if a type corresponds to an enumeration
    *
    * @param t
    * @return true if t extends Enumeration, false otherwise
    */
  def isEnumeration(t: Type): Boolean = {
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