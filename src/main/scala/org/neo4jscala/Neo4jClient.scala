package org.neo4jscala

import java.io.File

import org.neo4j.driver.v1._
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import org.neo4j.graphdb.factory.GraphDatabaseSettings.BoltConnector.EncryptionLevel
import org.neo4j.graphdb.factory.GraphDatabaseSettings.auth_enabled
import org.neo4j.test.TestGraphDatabaseFactory
//import org.neo4j.kernel.configuration.BoltConnector.EncryptionLevel
import org.neo4jscala.core.{Neo4jNode, Neo4jRunnable, Neo4jStatement, NodeMapper}

import scala.collection.JavaConversions._
/**
  * Created by nico on 17/09/17.
  */
class Neo4jClient(driver: Driver) {
//  def run(statement: String) : StatementResult = {
//    val session = driver.session()
//    val res = session.run(statement)
//    session.close()
//    res
//  }

  def run[R](statement: Neo4jStatement[R]): R = {
    val session = driver.session()
//    println(statement.stmt)
    val res = session.run(statement.stmt)
    session.close()
    statement.after(res)
//    statement.result
  }

  def exec(statement: String): StatementResult = {
    val session = driver.session()
    val res = session.run(statement)
    session.close()
    res
  }

//  def search[T <: Neo4jNode](implicit manifest: scala.reflect.Manifest[T]): Iterator[T] = {
//    val className = manifest.runtimeClass.getSimpleName
//    val result = run(s"MATCH (a:${className}) RETURN a")
//    asScalaIterator(result).map(record => {
//      val node = record.get("a").asNode()
//      NodeMapper.as[T](node)
//    })
//  }
//
}

object Neo4jClientFactory {
  def usingBolt(url: String, user: String, password: String): Neo4jClient = {
    val driver = GraphDatabase.driver(url, AuthTokens.basic(user, password))
    new Neo4jClient(driver)
  }

  def usingBolt(server: String = "localhost", port: Int = 7687, user: String = "neo4j", password: String): Neo4jClient = {
    usingBolt(s"bolt://$server:$port", user, password)
  }

  def noAuth(server: String = "localhost", port: Int = 7687) = {
    val driver = GraphDatabase.driver(s"bolt://${server}:${port}")
    new Neo4jClient(driver)
  }

  def embeded(file: File, port: Int = 7688) = {
    val bolt = new org.neo4j.kernel.configuration.BoltConnector("0")
    new GraphDatabaseFactory()
      .newEmbeddedDatabaseBuilder(new File(".graphDb"))
      .setConfig(auth_enabled, "false")
      .setConfig(bolt.`type`, "BOLT")
      .setConfig(bolt.enabled, "true")
      .setConfig(bolt.listen_address, s"localhost:$port")
      .setConfig(bolt.encryption_level, EncryptionLevel.OPTIONAL.name())
      .newGraphDatabase()

    Neo4jClientFactory.noAuth(port = port)
  }

  def inMemory() = {
    new TestGraphDatabaseFactory()
      .newImpermanentDatabase()
  }
}