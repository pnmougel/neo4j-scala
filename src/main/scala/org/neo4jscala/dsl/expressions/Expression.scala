package org.neo4jscala.dsl.expressions

import org.neo4jscala.dsl.{ComparableFieldOperation, StringFieldOperation}
import org.neo4jscala.dsl.returns.returnable.{IntReturnable, Returnable, StringReturnable}

/**
  * Created by nico on 20/10/17.
  */
trait Expression[T] extends Returnable[T] {
  def build: String
}

trait Expressions {
  case class StringExpression(name: String) extends ComparableFieldOperation[String] with StringFieldOperation {
    override val beforeValue: String = "'"
    override val afterValue: String = "'"
  }

  case class IntExpression(name: String) extends ComparableFieldOperation[Int] with IntReturnable {
    def buildReturn = name
  }
}
