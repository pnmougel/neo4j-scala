package org.neo4jscala.dsl.orderby

import org.neo4jscala.dsl.{CypherTerm, Field}
import org.neo4jscala.dsl.orderby.SortOrder.SortOrder

/**
  * Created by nico on 14/10/17.
  */
object SortOrder extends Enumeration {
  type SortOrder = Value
  val Desc, Asc = Value
}

trait OrderByTerm extends CypherTerm {
}
//
case class OrderBy(fields: Field*) extends OrderByTerm {
  def desc(): OrderByDesc = {
    new OrderByDesc(fields:_*)
  }

  def build() = {
    s"ORDER BY ${fields.map(_.name).mkString(", ")}"
  }
}

class OrderByDesc(descFields: Field*) extends OrderBy(descFields :_*) {
  override def build() = {
    s"${super.build()} DESC"
  }
}

