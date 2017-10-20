package org.neo4jscala.core

import java.util.UUID

import org.neo4jscala.Neo4jClient
import org.neo4jscala.jsonmappers.Neo4jJsonMapper

import scala.reflect.ClassTag
/**
  * Created by nico on 17/09/17.
  */


class Neo4jNode[T](implicit mf1: Manifest[T], tag: ClassTag[T]) extends Neo4jRunnable {

  def build = ""

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

  var nodeLabels: Vector[String] = Vector(this.getClass.toString)

  @transient implicit val source: Neo4jNode[_] = this

  def create[T <: Neo4jNode[_]]() : Neo4jStatement[T] = {
    val properties = Neo4jJsonMapper.mapper.writeValueAsString(this)
    new BaseNeo4jStatement[T](s"CREATE (${nodeLabels.map(label => s":$label").mkString} $properties)", (_) => {
      this.asInstanceOf[T]
    })
  }


  def doCreate[T <: Neo4jNode[_]]()(implicit client: Neo4jClient) = {
    run(create[T]())
  }
}
