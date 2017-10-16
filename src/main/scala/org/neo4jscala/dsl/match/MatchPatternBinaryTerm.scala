package org.neo4jscala.dsl.`match`

/**
  * Created by nico on 13/10/17.
  */
case class MatchPatternBinaryTerm(left: MatchTerm, right: MatchTerm, sep: String) extends MatchTerm {
  override def toString = s"$left $sep $right"
  def build = this.toString
}