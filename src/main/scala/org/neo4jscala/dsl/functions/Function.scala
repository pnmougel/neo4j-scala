package org.neo4jscala.dsl.functions

import org.neo4jscala.dsl._
import org.neo4jscala.dsl.returns.returnable.FloatReturnable

/**
  * Created by nico on 20/10/17.
  */
trait Function {
  def buildFunction: String
}

case class StDevFunction(fields: IntExpression*) extends FloatReturnable with Function {
  def buildFunction = s"stDev(${fields.map(_.name).mkString(", ")})"

  override def buildReturn: String = buildFunction
}