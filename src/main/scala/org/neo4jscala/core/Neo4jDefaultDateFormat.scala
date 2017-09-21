package org.neo4jscala.core

import java.text.SimpleDateFormat

/**
  * Created by nico on 19/09/17.
  */
object Neo4jDefaultDateFormat {
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
}
