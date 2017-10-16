package org.neo4jscala.dsl

/**
  * Created by nico on 13/10/17.
  */
trait CypherTerm {
  def build: String
}
