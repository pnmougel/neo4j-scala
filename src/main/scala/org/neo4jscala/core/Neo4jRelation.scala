package org.neo4jscala.core

import org.neo4j.driver.v1.Record
import org.neo4jscala.Neo4jClient

import scala.collection.JavaConversions.asScalaIterator
import scala.collection.mutable

/**
  * Created by nico on 21/09/17.
  */
class Neo4jRelation[T <: Neo4jNode[T]](label: String)(implicit source: Neo4jNode[_], mf: Manifest[T]) extends Neo4jRunnable {
  var nodes = mutable.HashSet[T]()

  def add(target: T): Neo4jStatement[Unit] = {
    val stmt = s"MATCH (a${source.nodeLabels.map(label => s":$label").mkString}), (b:${target.nodeLabels.map(label => s"$label").mkString})" +
      s" WHERE a.id = '${source.id}' AND b.id = '${target.id}'" +
      s" CREATE (a)-[r:$label]->(b) RETURN r"
    new BaseNeo4jStatement(stmt, _ => {
      nodes.add(target)
    })
  }

  def doAdd(target: T)(implicit client: Neo4jClient) = run(add(target))

  def endNodes(): Neo4jStatement[Iterator[T]] = {
    val stmt = s"MATCH (a${source.nodeLabels.map(label => s":$label").mkString}) <-[r:$label]-> (b)" +
      s" WHERE a.id = '${source.id}'" +
      s" RETURN b"
    new BaseNeo4jStatement[Iterator[T]](stmt, res => {
      asScalaIterator(res).map(record => {
        val rec: Record = record
        val node = record.get("b").asNode()
        NodeMapper.as[T](node)
      })
    })
  }

  def runEndNodes()(implicit client: Neo4jClient) = run(endNodes())
}
