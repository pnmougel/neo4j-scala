package org.neo4jscala.dsl

import org.neo4jscala.dsl.constraints.{ExistsFieldConstraint, IsUniqueFieldConstraint}
import org.neo4jscala.dsl.where.WhereTerm

/**
  * Created by nico on 13/10/17.
  */

trait Field {
  val name: String

  def isUnique() = IsUniqueFieldConstraint(this)
  def exist() = ExistsFieldConstraint(this)
}

trait ComparableFieldOperation[T] extends Field {
  val beforeValue = ""
  val afterValue = ""

//  def ==(value: T): WhereExpressionBinary[T] = WhereExpressionBinary[T]("=", this, value)
  def :==(value: T): WhereExpressionBinary[T] = WhereExpressionBinary[T]("=", this, value)
//  def eq(value: T) = ==(value)
  def :<>(value: T): WhereExpressionBinary[T] = WhereExpressionBinary[T]("<>", this, value)
//  def neq(value: T) = <>(value)
  def :>(value: T): WhereExpressionBinary[T] = WhereExpressionBinary[T](">", this, value)
//  def gt(value: T) = >(value)
  def :>=(value: T): WhereExpressionBinary[T] = WhereExpressionBinary[T](">=", this, value)
  def :<(value: T): WhereExpressionBinary[T] = WhereExpressionBinary[T]("<", this, value)
  def :<=(value: T): WhereExpressionBinary[T] = WhereExpressionBinary[T]("<=", this, value)
  def isNull: WhereExpressionUnary = WhereExpressionUnary("IS NULL", this)
  def isNotNull: WhereExpressionUnary = WhereExpressionUnary("IS NOT NULL", this)
}

trait StringFieldOperation extends ComparableFieldOperation[String] {
  def startsWith(value: String): WhereExpressionBinary[String] = {
    WhereExpressionBinary[String]("STARTS WITH", this, value)
  }
  def endsWith(value: String) = {
    WhereExpressionBinary[String]("ENDS WITH", this, value)
  }
  def contains(value: String) = {
    WhereExpressionBinary[String]("CONTAINS", this, value)
  }
  def matchRegExp(value: String) = {
    WhereExpressionBinary[String]("=~", this, value)
  }
}

case class WhereExpressionBinary[T](term: String, field: ComparableFieldOperation[T], value: T) extends WhereTerm {
  def build = s"${field.name} $term ${field.beforeValue}$value${field.afterValue}"
}

case class WhereExpressionUnary(term: String, field: Field) extends WhereTerm {
  def build = s"${field.name} $term"
}

case class StringField(name: String) extends ComparableFieldOperation[String] with StringFieldOperation {
  override val beforeValue: String = "'"
  override val afterValue: String = "'"
}

case class IntField(name: String) extends ComparableFieldOperation[Int]

case class AnyField(name: String) extends ComparableFieldOperation[Any]
