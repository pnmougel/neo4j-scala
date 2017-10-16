package org.neo4jscala.core

import org.neo4j.driver.v1.StatementResult

/**
  * Created by nico on 10/10/17.
  */
trait Neo4jStatement[R] {
  val stmt: String
  def after: StatementResult => R
}

class BaseNeo4jStatement[R](val stmt: String, val after: StatementResult => R) extends Neo4jStatement[R] {
}

trait UnitNeo4jStatement extends Neo4jStatement[Unit] {
  val after: StatementResult => Unit = (_: StatementResult) => {}
}

class UnitNeo4jStatement2(val stmt: String) extends UnitNeo4jStatement {

}
