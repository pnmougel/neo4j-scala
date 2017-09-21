//package org.neo4j
//
//import scala.reflect.ClassTag
//
//-scala.utils
//
///**
//  * Created by nico on 06/10/16.
//  */
//object ReflectionUtilities {
//  val reflections = new Reflections("io.nuata")
//
//  def getFieldsDefaultParameters[T: TypeTag]: Map[String, AnyRef] = {
//    val classTag = ClassTag[T](typeTag[T].mirror.runtimeClass(typeTag[T].tpe))
//    val mod = currentMirror.classSymbol(classTag.runtimeClass).companion.asModule
//    val im = currentMirror.reflect(currentMirror.reflectModule(mod).instance)
//    val ts = im.symbol.typeSignature
//    val mApply = ts.member(universe.TermName("apply")).asMethod
//    Map(mApply.paramLists.flatten.zipWithIndex.map { case (p, i) =>
//      (p.name.toString, ts.member(universe.TermName(s"apply$$default$$${i + 1}")))
//    }.filter(_._2.isMethod).map(m => {
//      (m._1, im.reflectMethod(m._2.asMethod)().asInstanceOf[AnyRef])
//    }): _*)
//  }
//
//  def newInstanceOf[T](classOf: Class[_ <: T]): Option[T] = {
//    (for (constructor <- classOf.getConstructors
//          if constructor.getParameterCount == 0) yield {
//      constructor.setAccessible(true)
//      constructor.newInstance().asInstanceOf[T]
//    }).headOption
//  }
//
//  def newInstanceOfUntyped(classOf: Class[_]): Option[_] = {
//    (for (constructor <- classOf.getConstructors
//          if constructor.getParameterCount == 0) yield {
//      constructor.setAccessible(true)
//      constructor.newInstance()
//    }).headOption
//  }
//
//  /**
//    * Retrieve all the instances of type T
//    *
//    * @param tag
//    * @tparam T a class
//    * @return
//    */
//  def getInstancesOf[T](implicit tag: ClassTag[T]): Array[T] = {
//    val subTypes = reflections.getSubTypesOf(tag.runtimeClass)
//    (for (subType <- subTypes.toList;
//          constructor <- subType.getDeclaredConstructors
//          if constructor.getParameterCount == 0
//    ) yield {
//      constructor.setAccessible(true)
//      allCatch.opt {
//        constructor.newInstance().asInstanceOf[T]
//      }
//    }).flatten.toArray
//  }
//
//  def getSubTypesOf[T](implicit tag: ClassTag[T]): List[Type] = {
//    val subTypes = reflections.getSubTypesOf(tag.runtimeClass)
//    for (subType <- subTypes.toList) yield {
//      runtimeMirror(subType.getClassLoader).classSymbol(subType).toType
//    }
//  }
//
//  def getSubClassesOf[T: TypeTag](implicit tag: ClassTag[T]): List[Class[_]] = {
//    reflections.getSubTypesOf(tag.runtimeClass).toList
//  }
//
//  def instanceOf[T: TypeTag](args: AnyRef*) = {
//    val cl = symbolOf[T].asClass
//    currentMirror.reflectClass(cl).reflectConstructor(cl.primaryConstructor.asMethod)(args: _*).asInstanceOf[T]
//  }
//
//  def classToClassSymbol(cl: Class[_]): ClassSymbol = {
//    val mirror = runtimeMirror(cl.getClassLoader)
//    mirror.classSymbol(cl)
//  }
//
//  def isOptionType(t: Type): Boolean = t <:< typeOf[Option[_]]
//
//  def isEnumerationType(t: Type): Boolean = t.asInstanceOf[TypeRef].pre <:< typeOf[Enumeration]
//
//  def isCollectionType(t: Type): Boolean = t <:< typeOf[Array[_]] || t <:< typeOf[Seq[_]] || t <:< typeOf[Traversable[_]]
//
//  def getEnumerationValues(t: Type): Array[String] = {
//    val parent = t.asInstanceOf[TypeRef].pre
//    val module = currentMirror.staticModule(parent.typeSymbol.fullName)
//    val obj = currentMirror.reflectModule(module)
//    val enum = obj.instance.asInstanceOf[Enumeration]
//    enum.values.toArray.map(_.toString)
//  }
//
//
//  /**
//    * Check if typeToCheck is either T or has type args of T
//    * @param typeToCheck
//    * @tparam T
//    * @return
//    */
//  def isOfType[T : TypeTag](typeToCheck: Type): Boolean = {
//    val t = typeOf[T]
//    t =:= typeToCheck || typeToCheck.typeArgs.exists(_ =:= t)
//  }
//}
