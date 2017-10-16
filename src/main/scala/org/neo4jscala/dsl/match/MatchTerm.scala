package org.neo4jscala.dsl.`match`

import org.neo4jscala.dsl.CypherTerm

/**
  * Created by nico on 13/10/17.
  */
trait MatchTerm extends CypherTerm {
  def :-(e: MatchTerm): MatchTerm = {
    MatchPatternBinaryTerm(this, e, "-")
  }
  def :->(e: MatchTerm): MatchTerm = {
    MatchPatternBinaryTerm(this, e, "->")
  }
  def :<-(e: MatchTerm): MatchTerm = {
    MatchPatternBinaryTerm(this, e, "<-")
  }
  def :--(e: MatchTerm): MatchTerm = {
    MatchPatternBinaryTerm(this, e, "--")
  }
  def :-->(e: MatchTerm): MatchTerm = {
    MatchPatternBinaryTerm(this, e, "-->")
  }
  def :<--(e: MatchTerm): MatchTerm = {
    MatchPatternBinaryTerm(this, e, "<--")
  }
  def :<->(e: MatchTerm): MatchTerm = {
    MatchPatternBinaryTerm(this, e, "<->")
  }
  def :<-->(e: MatchTerm): MatchTerm = {
    MatchPatternBinaryTerm(this, e, "<-->")
  }
}