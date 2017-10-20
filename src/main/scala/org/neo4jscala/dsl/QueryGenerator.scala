package org.neo4jscala.dsl

import org.neo4jscala.dsl.returns.ReturnableExpression

/**
  * Created by nico on 14/10/17.
  */
trait QueryGenerator {

  def query[T, U](params: (T) => Unit)(expr: (T) => CypherQuery[_] => ReturnableExpression[U])(implicit mf: Manifest[T]): CypherQuery[U] = {
    val q = new CypherQuery[U]()
    val returnExpr: ReturnableExpression[U] = expr(null.asInstanceOf[T])(q)
    q.returnableExpression = returnExpr
    q
  }

  def query2[T1, T2, U](params: (T1, T2) => Unit)(expr: (T1, T2) => CypherQuery[_] => ReturnableExpression[U])(implicit mf: Manifest[T1],  mf2: Manifest[T2]): CypherQuery[U] = {
    val q = new CypherQuery[U]()
    val returnExpr: ReturnableExpression[U] = expr(null.asInstanceOf[T1], null.asInstanceOf[T2])(q)
    q.returnableExpression = returnExpr
    q
  }

  def use[T](t: T): T = t
  def use[T1, T2](t1: T1, t2: T2): (T1, T2) = (t1, t2)
}
