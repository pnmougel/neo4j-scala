package org.neo4jscala.core

import java.util.UUID

import org.neo4jscala.jsonmappers.Neo4jJsonMapper
/**
  * Created by nico on 17/09/17.
  */
class Neo4jNode {
  var id: String = _

  var labels: Vector[String] = Vector(this.getClass.getSimpleName)

  def buildCreateQuery: String = {
    id = UUID.randomUUID().toString
    val properties = Neo4jJsonMapper.mapper.writeValueAsString(this)
    s"CREATE (${labels.map(label => s":$label").mkString} $properties)"
  }
}
