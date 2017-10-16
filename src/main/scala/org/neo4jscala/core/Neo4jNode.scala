package org.neo4jscala.core

import java.util.UUID

import org.neo4jscala.Neo4jClient
import org.neo4jscala.jsonmappers.Neo4jJsonMapper
/**
  * Created by nico on 17/09/17.
  */
trait Neo4jNode extends Neo4jRunnable {

  private var _id: Option[String] = None

  def id = {
    _id.getOrElse {
      _id = Some(UUID.randomUUID().toString)
      _id.get
    }
  }

  def id_=(value: String): Unit = {
    _id = Some(value)
  }

  var nodeLabels: Vector[String] = Vector(this.getClass.getSimpleName)

  @transient implicit val source: Neo4jNode = this

  def create[T <: Neo4jNode]() : Neo4jStatement[T] = {
    val properties = Neo4jJsonMapper.mapper.writeValueAsString(this)
    new BaseNeo4jStatement[T](s"CREATE (${nodeLabels.map(label => s":$label").mkString} $properties)", (_) => {
      this.asInstanceOf[T]
    })
  }


  def doCreate[T <: Neo4jNode]()(implicit client: Neo4jClient) = {
    run(create[T]())
  }
}
