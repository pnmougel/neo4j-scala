package org.neo4jscala.dsl

import org.neo4j.cypher.internal.frontend.v3_2.parser.CypherParser
import org.neo4j.driver.v1.StatementResult
import org.neo4jscala.Neo4jClient
import org.neo4jscala.dsl.returns.ReturnExpression
import org.parboiled.scala
import org.parboiled.scala.parserunners.ReportingParseRunner

/**
  * Created by nico on 13/10/17.
  */
class CypherQuery {
  var terms : Vector[CypherExpression] = Vector[CypherExpression]()
  var res: Option[StatementResult] = None

  override def toString = {
    terms.map(_.build).mkString("\n")
  }

  def query = this.toString

  def isValid : Boolean = {
    ReportingParseRunner(CypherParser.Statement).run(query).matched
  }

  def runAsList[T](implicit client: Neo4jClient): Option[Seq[T]] = {
    val res = client.exec(this.toString)
//    terms.find(_.isInstanceOf[ReturnExpression[T]]).map { returnExpr =>
//      returnExpr.asInstanceOf[ReturnExpression[T]].asList(res)
//    }
    None
  }
}
