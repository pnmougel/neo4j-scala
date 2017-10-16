package org.neo4jscala.dsl.returns.returnable

/**
  * Created by nico on 14/10/17.
  */
trait ToReturnable {
  case class AStringReturnable(value: String) extends StringReturnable {
    def build = s"'$value'"
  }
  case class AIntReturnable(value: Int) extends IntReturnable {
    def build = s"$value"
  }

  implicit def IntToIntReturnable(v: Int): AIntReturnable = AIntReturnable(v)
  implicit def StringToStringReturnable(v: String): AStringReturnable = AStringReturnable(v)
}
