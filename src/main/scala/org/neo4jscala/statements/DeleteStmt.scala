package org.neo4jscala.statements

import org.neo4jscala.core.UnitNeo4jStatement2

/**
  * Created by nico on 10/10/17.
  */
case class DeleteAllStmt() extends UnitNeo4jStatement2("MATCH (n) DETACH DELETE n")
