package org.neo4jscala

import org.neo4jscala.dsl.`match`.MatchTerm
import org.neo4jscala.dsl.constraints.ConstraintTerm
import org.neo4jscala.dsl.delete.DeleteTerm
import org.neo4jscala.dsl.expressions.Expressions
import org.neo4jscala.dsl.functions.FunctionsBuilder
import org.neo4jscala.dsl.returns.UnitReturnExpression
import org.neo4jscala.dsl.returns.returnable.ToReturnable
import org.neo4jscala.dsl.where.WhereTerm

/**
  * Created by nico on 13/10/17.
  */
package object dsl extends ToReturnable with QueryGenerator with FunctionsBuilder with Expressions {
  abstract class CypherExpression(q: CypherQuery[_], term: CypherTerm) {
    val prefix: String
    q.terms :+= this
    def build = s"$prefix ${term.build}"
  }

  case class Delete(expr: DeleteTerm)(implicit q: CypherQuery[_]) extends CypherExpression(q, expr) with UnitReturnExpression {
    override val prefix = "DELETE"
  }

  case class Where(expr: WhereTerm)(implicit q: CypherQuery[_]) extends CypherExpression(q, expr) with UnitReturnExpression {
    override val prefix = "WHERE"
  }

  case class Match(expr: MatchTerm)(implicit q: CypherQuery[_]) extends CypherExpression(q, expr) with UnitReturnExpression {
    override val prefix = "MATCH"
  }

  case class CreateConstraint(expr: ConstraintTerm)(implicit q: CypherQuery[_]) extends CypherExpression(q, expr) with UnitReturnExpression {
    override val prefix = "CREATE CONSTRAINT ON"
  }

  case class DropConstraint(expr: ConstraintTerm)(implicit q: CypherQuery[_]) extends CypherExpression(q, expr) with UnitReturnExpression {
    override val prefix = "DROP CONSTRAINT ON"
  }
}
