package org.neo4jscala.statements

import org.neo4j.driver.v1.StatementResult
import org.neo4jscala.core.{Neo4jNode, Neo4jStatement, NodeMapper}

import scala.collection.JavaConversions.asScalaIterator

/**
  * Created by nico on 10/10/17.
  */
case class ByIdStmt[T <: Neo4jNode](id: String)(implicit manifest: scala.reflect.Manifest[T]) extends Neo4jStatement[T]() {
  val stmt = {
    val className = manifest.runtimeClass.getSimpleName
    s"MATCH (a:$className) WHERE a.id = '$id' RETURN a"
  }
  val after = (res: StatementResult) => {
    val node = res.single().get("a").asNode()
    NodeMapper.as[T](node)
  }
}

case class SearchAllStmt[T <: Neo4jNode](implicit manifest: scala.reflect.Manifest[T]) extends Neo4jStatement[Iterator[T]]() {
  val stmt = {
    val className = manifest.runtimeClass.getSimpleName
    s"MATCH (a:${className}) RETURN a"
  }
  val after = (res: StatementResult) => {
    asScalaIterator(res).map(record => {
      val node = record.get("a").asNode()
      NodeMapper.as[T](node)
    })
  }
}

trait SearchDsl {
  def byId[T <: Neo4jNode](id: String)(implicit mf: Manifest[T]): Neo4jStatement[T] = ByIdStmt[T](id)
  def all[T <: Neo4jNode](implicit mf: Manifest[T]): Neo4jStatement[Iterator[T]] = SearchAllStmt[T]
}


object StatementDsl extends SearchDsl