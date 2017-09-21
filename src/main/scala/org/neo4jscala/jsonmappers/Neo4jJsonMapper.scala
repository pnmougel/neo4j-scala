package org.neo4jscala.jsonmappers

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.neo4jscala.core.Neo4jDefaultDateFormat

/**
  * Created by nico on 16/09/17.
  */

object Neo4jJsonMapper {
  val mapper: ObjectMapper with ScalaObjectMapper = new ObjectMapper with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.setDateFormat(Neo4jDefaultDateFormat.dateFormat)
  mapper.configure (JsonGenerator.Feature.QUOTE_FIELD_NAMES, false)
}
