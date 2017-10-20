package org.neo4jscala.dsl

import org.neo4j.cypher.internal.frontend.v3_2.parser.CypherParser
import org.neo4j.driver.v1.StatementResult
import org.neo4jscala.Neo4jClient
import org.neo4jscala.dsl.returns.ReturnableExpression
import org.parboiled.scala.parserunners.ReportingParseRunner

/**
  * Created by nico on 13/10/17.
  */
class CypherQuery[T] {
  var terms : Vector[CypherExpression] = Vector[CypherExpression]()
  var res: Option[StatementResult] = None

  var returnableExpression: ReturnableExpression[T] = _

  override def toString = {
    terms.map(_.build).mkString("\n")
  }

  def query = this.toString

  def isValid : Boolean = {
    ReportingParseRunner(CypherParser.Statement).run(query).matched
  }

  def asList(implicit client: Neo4jClient): Seq[T] = {
    val res = client.exec(this.toString)
    returnableExpression.asList(res)
//    terms.find(_.isInstanceOf[ReturnExpression[T]]).map { returnExpr =>
//      returnExpr.asInstanceOf[ReturnExpression[T]].asList(res)
//    }
//    None
  }

  def all[U](f: T => U)(implicit client: Neo4jClient) = {
    val res = client.exec(this.toString)
    val l = returnableExpression.asList(res)
    l.foreach { e => f(e) }
  }

//  def all2(f: (T, T2) => Unit)(implicit client: Neo4jClient) = {
//    val res = client.exec(this.toString)
//    val l = returnableExpression.asList(res)
//    l.foreach { case (a: T1, b: T2) => f(a, b) }
//  }
}
