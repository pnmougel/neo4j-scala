package org.neo4jscala.dsl.where

import org.neo4jscala.dsl.CypherTerm

/**
  * Created by nico on 13/10/17.
  */
trait WhereTerm extends CypherTerm {
  def build: String

  def and(right: WhereTerm) = WhereTermBinary("AND", this, right)
  def or(right: WhereTerm) = WhereTermBinary("OR", this, right)
  def xor(right: WhereTerm) = WhereTermBinary("XOR", this, right)
}

case class WhereTermBinary(joinExpr: String, firstExpr: WhereTerm, remains: WhereTerm*) extends WhereTerm {
  def build: String = {
    s"${(firstExpr :: remains.toList).map(e => s"(${e.build})").mkString(s" ${joinExpr} ")}"
  }
}