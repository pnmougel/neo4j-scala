package org.neo4jscala.dsl.functions

import org.neo4jscala.dsl._

/**
  * Created by nico on 20/10/17.
  */
trait FunctionsBuilder {
  def stDev(fields: IntExpression*) = StDevFunction(fields:_*)
}
