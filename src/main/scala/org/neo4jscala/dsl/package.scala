package org.neo4jscala

import org.neo4jscala.dsl.`match`.MatchTerm
import org.neo4jscala.dsl.constraints.ConstraintTerm
import org.neo4jscala.dsl.returns.returnable.ToReturnable
import org.neo4jscala.dsl.where.WhereTerm

/**
  * Created by nico on 13/10/17.
  */
package object dsl extends ToReturnable with QueryGenerator {
  abstract class CypherExpression(q: CypherQuery, term: CypherTerm) {
    val prefix: String
    q.terms :+= this
    def build = s"$prefix ${term.build}"
  }

  case class Where(expr: WhereTerm)(implicit q: CypherQuery) extends CypherExpression(q, expr) {
    val prefix = "WHERE"
  }
  case class Match(expr: MatchTerm)(implicit q: CypherQuery) extends CypherExpression(q, expr) {
    val prefix = "MATCH"
  }

  case class CreateConstraint(expr: ConstraintTerm)(implicit q: CypherQuery) extends CypherExpression(q, expr) {
    val prefix = "CREATE CONSTRAINT ON"
  }

  case class DropConstraint(expr: ConstraintTerm)(implicit q: CypherQuery) extends CypherExpression(q, expr)  {
    val prefix = "DROP CONSTRAINT ON"
  }
}
