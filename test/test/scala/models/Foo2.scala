package models

import org.neo4jscala.core.Neo4jNode


/**
  * Created by nico on 13/10/17.
  */
case class Foo2(strField: String, intField: Int) extends Neo4jNode[Foo2]