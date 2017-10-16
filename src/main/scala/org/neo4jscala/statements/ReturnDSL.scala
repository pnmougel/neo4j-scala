package org.neo4jscala.statements

//import org.neo4jscala.dsl.where.WhereStatement

/**
  * Created by nico on 10/10/17.
  */
object ReturnDSL {
  def orderBy(field: String, order: String = "ASC") = {
    s"ORDER BY ${field} ${order}"
  }

  def skip(nbSkip: Int) = {
    s"SKIP ${nbSkip}"
  }

  def limit(limit: Int) = {
    s"LIMIT ${limit}"
  }
}

//class SearchQuery() {
//  def where(where: WhereStatement) = {
//
//  }
//
//  def build(): String = {
//    ""
//  }
//}
