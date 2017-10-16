package org.neo4jscala.dsl

import org.neo4jscala.dsl.returns.ReturnExpression

/**
  * Created by nico on 14/10/17.
  */
trait QueryGenerator {

  class QueryWithResult[K](val returnResult: ReturnExpression[K])

  def query3[T, U](expr: T => CypherQuery => ReturnExpression[U])(implicit mf: Manifest[T]): QueryWithResult[U] = {
    val q = new CypherQuery()
    val returnExpr = expr(null.asInstanceOf[T])(q)
    new QueryWithResult[U](returnExpr)
  }

  def query4[T, U](params: T => Unit = {_: Unit => })(expr: T => CypherQuery => ReturnExpression[U])(implicit mf: Manifest[T]): QueryWithResult[U] = {
    val q = new CypherQuery()
    val returnExpr = expr(null.asInstanceOf[T])(q)
    new QueryWithResult[U](returnExpr)
  }

  def use[T](t: T) = t
  def params2[T1, T2](t1: T1, t2: T2) = {}
  def params3[T1, T2, T3](t1: T1, t2: T2, t3: T3) = {}

  case class P[T]() {}
  case class P2[T1, T2]() {}

  def query7[T1, U](params: (T1) => Unit = {_: (Unit) => })(expr: (T1) => CypherQuery => ReturnExpression[U]): QueryWithResult[U] = {
    val q = new CypherQuery()
    val returnExpr = expr(null.asInstanceOf[T1])(q)
    new QueryWithResult[U](returnExpr)
  }

//  def query9[T1, U](params: P[T1])(expr: (T1) => CypherQuery => ReturnExpression[U]): QueryWithResult[U] = {
//    val q = new CypherQuery()
//    val returnExpr = expr(null.asInstanceOf[T1])(q)
//    new QueryWithResult[U](returnExpr)
//  }
  def query9[T1, T2, U](params: P2[T1, T2])(expr: (T1, T2) => CypherQuery => ReturnExpression[U]): QueryWithResult[U] = {
    val q = new CypherQuery()
    val returnExpr = expr(null.asInstanceOf[T1], null.asInstanceOf[T2])(q)
    new QueryWithResult[U](returnExpr)
  }

//  def query7[T1, T2, U](params: (T1, T2) => Unit = {_: (Unit) => })(expr: (T1, T2) => CypherQuery => ReturnExpression[U]): QueryWithResult[U] = {
//    val q = new CypherQuery()
//    val returnExpr = expr(null.asInstanceOf[T1], null.asInstanceOf[T2])(q)
//    new QueryWithResult[U](returnExpr)
//  }

//  def query7[T1, T2, T3, U](params: (T1, T2, T3) => Unit = {_: Unit => })(expr: (T1, T2, T3) => CypherQuery => ReturnExpression[U]): QueryWithResult[U] = {
//    val q = new CypherQuery()
//    val returnExpr = expr(null.asInstanceOf[T1], null.asInstanceOf[T2], null.asInstanceOf[T3])(q)
//    new QueryWithResult[U](returnExpr)
//  }

  def query5[T, U](params: => T)(expr: T => CypherQuery => ReturnExpression[U])(implicit mf: Manifest[T]): QueryWithResult[U] = {
    val q = new CypherQuery()
    val returnExpr = expr(null.asInstanceOf[T])(q)
    new QueryWithResult[U](returnExpr)
  }

  def query6[T, U](params: => T)(expr: T => CypherQuery => ReturnExpression[U])(implicit mf: Manifest[T]): QueryWithResult[U] = {
    val q = new CypherQuery()
    val returnExpr = expr(null.asInstanceOf[T])(q)
    new QueryWithResult[U](returnExpr)
  }

  def query[T](expr: T => CypherQuery => Unit)(implicit mf: Manifest[T]): CypherQuery = {
    val q = new CypherQuery()
    expr(null.asInstanceOf[T])(q)
    q
  }

  def query2[T, U](expr: T => CypherQuery => ReturnExpression[U])(implicit mf: Manifest[T]): QueryWithResult[U] = {
    val q = new CypherQuery()
    val returnExpr = expr(null.asInstanceOf[T])(q)
    new QueryWithResult[U](returnExpr)
  }

  def query[T1, T2](expr: (T1, T2) => CypherQuery => Unit)(implicit mf1: Manifest[T1], mf2: Manifest[T2]): CypherQuery = {
    val q = new CypherQuery()
    expr(null.asInstanceOf[T1], null.asInstanceOf[T2])(q)
    q
  }

  def query[T1, T2, T3](expr: (T1, T2, T3) => CypherQuery => Unit)(implicit mf1: Manifest[T1], mf2: Manifest[T2], mf3: Manifest[T3]): CypherQuery = {
    val q = new CypherQuery()
    expr(null.asInstanceOf[T1], null.asInstanceOf[T2], null.asInstanceOf[T3])(q)
    q
  }


  //  def query[T](expr: (T, CypherQuery) => Unit)(implicit mf: Manifest[T]): CypherQuery = {
  //    val q = new CypherQuery()
  //    expr(null.asInstanceOf[T], q)
  //    q
  //  }
  //  def query[T1, T2](expr: (CypherQuery, T1, T2) => Unit)(implicit mf1: Manifest[T1], mf2: Manifest[T2]): CypherQuery = {
  //    val q = new CypherQuery()
  //    expr(q, null.asInstanceOf[T1], null.asInstanceOf[T2])
  //    q
  //  }
}
