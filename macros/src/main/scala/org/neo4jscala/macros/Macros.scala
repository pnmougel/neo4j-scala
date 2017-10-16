package org.neo4jscala.macros

import org.neo4jscala.core.Neo4jNode
import org.neo4jscala.dsl.{IntField, NodeDescriptor, StringField}

/**
  * Created by nico on 19/09/17.
  */
trait Macros {
  import scala.language.experimental.macros

  /**
    * Obtain an identifier name as a constant string.
    *
    * Example usage:
    * {{{
    *   val amount = 5
    *   nameOf(amount) => "amount"
    * }}}
    */
//  def $(expr: Any): String = macro NameOfImpl.nameOf2

  implicit def node(expr: Neo4jNode): NodeDescriptor = macro NameOfImpl.nodeDescriptorBuilder

  implicit def field(expr: String): StringField = macro NameOfImpl.stringFieldDescriptorBuilder
  implicit def field(expr: Int): IntField = macro NameOfImpl.intFieldDescriptorBuilder

  def $(expr: String): StringField = macro NameOfImpl.stringFieldDescriptorBuilder
  def $(expr: Int): IntField = macro NameOfImpl.intFieldDescriptorBuilder
  def $(expr: Neo4jNode): NodeDescriptor = macro NameOfImpl.nodeDescriptorBuilder


//  /**
//    * Obtain an identifier name as a constant string.
//    *
//    * This overload can be used to access an instance method without having an instance of the type.
//    *
//    * Example usage:
//    * {{{
//    *   class Person(val name: String)
//    *   nameOf[Person](_.name) => "name"
//    * }}}
//    */
//  def $[T](expr: T => Any): String = macro NameOfImpl.nameOf2
//
//  /**
//    * Obtain a type's unqualified name as a constant string.
//    *
//    * Example usage:
//    * {{{
//    *   nameOfType[String] => "String"
//    *   nameOfType[fully.qualified.ClassName] => "ClassName"
//    * }}}
//    */
//  def nameOfType[T]: String = macro NameOfImpl.nameOfType[T]
//
//  /**
//    * Obtain a type's qualified name as a constant string.
//    *
//    * Example usage:
//    * {{{
//    *   nameOfType[String] => "java.lang.String"
//    *   nameOfType[fully.qualified.ClassName] => "fully.qualified.ClassName"
//    * }}}
//    */
//  def qualifiedNameOfType[T]: String = macro NameOfImpl.qualifiedNameOfType[T]
}
object Macros extends Macros
