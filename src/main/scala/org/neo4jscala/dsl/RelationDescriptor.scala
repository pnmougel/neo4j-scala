package org.neo4jscala.dsl

import org.neo4jscala.dsl.`match`.MatchTerm

/**
  * Created by nico on 13/10/17.
  */
case class RelationDescriptor(name: String, labels: Seq[String] = List()) extends MatchTerm {
  def build = this.toString
}
