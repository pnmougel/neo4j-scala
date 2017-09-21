package org.neo4jscala

import org.neo4j.driver.v1.{AuthTokens, Driver, GraphDatabase, StatementResult}
import org.neo4jscala.core.{Neo4jNode, NodeMapper}
/**
  * Created by nico on 17/09/17.
  */
class Neo4jClient(driver: Driver) {
  def run(statement: String) : StatementResult = {
    val session = driver.session()
    val res = session.run(statement)
    session.close()
    res
  }

  def create(node: Neo4jNode) = {
    run(node.buildCreateQuery)
  }

  def search[T <: Neo4jNode](implicit manifest: scala.reflect.Manifest[T]): Iterator[T] = {
    val className = manifest.runtimeClass.getSimpleName
    val result = run(s"MATCH (a:${className}) RETURN a")
    import scala.collection.JavaConverters._
    asScalaIterator(result).map(record => {
      val node = record.get("a").asNode()
      NodeMapper.as[T](node)
    })
  }

  def byId[T <: Neo4jNode](id: String)(implicit manifest: scala.reflect.Manifest[T]): T = {
    val className = manifest.runtimeClass.getSimpleName
    val result = run(s"MATCH (a:${className}) WHERE a.id = '${id}' RETURN a")
    val node = result.single().get("a").asNode()
    NodeMapper.as[T](node)
  }
}

object Neo4jClientFactory {
  def usingBolt(url: String, user: String, password: String): Neo4jClient = {
    val driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic(user, password))
    new Neo4jClient(driver)
  }

  def usingBolt(server: String = "localhost", port: Int = 7687, user: String = "neo4j", password: String): Neo4jClient = {
    usingBolt(s"bolt://$server:$port", user, password)
  }
}