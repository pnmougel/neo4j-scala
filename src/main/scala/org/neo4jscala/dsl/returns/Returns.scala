package org.neo4jscala.dsl.returns

import org.neo4j.driver.v1.{Record, StatementResult}
import org.neo4jscala.dsl.returns.returnable.Returnable
import org.neo4jscala.dsl.{CypherExpression, CypherQuery, CypherTerm, NodeDescriptor}

import scala.collection.JavaConversions.asScalaIterator

/**
  * Created by nico on 14/10/17.
  */
object Return {
  def apply[T1](a: Returnable[T1])(implicit q: CypherQuery[_],  mf: Manifest[T1]) = new Return1(a)
  def apply[T1, T2](a: Returnable[T1], b: Returnable[T2])(implicit q: CypherQuery[_]) = new Return2(a, b)
  def apply[T1, T2, T3](a: Returnable[T1], b: Returnable[T2], c: Returnable[T3])(implicit q: CypherQuery[_]) = new Return3(a, b, c)
}

case class ReturnTerm(terms: Returnable[_]*) extends CypherTerm {
  def build() = terms.map(_.buildReturn).mkString(", ")
}

trait UnitReturnExpression extends ReturnableExpression[Tuple1[Unit]] {
  val terms = Seq()
  def toTuple(rec: Record): Tuple1[Unit] = Tuple1()
}

trait ReturnableExpression[T] {
  val terms: Seq[Returnable[_]]

  def toTuple(rec: Record): T
  def asList(res: StatementResult) = asScalaIterator(res).map(toTuple).toList
  def single(res: StatementResult) = toTuple(res.single())
}

abstract class ReturnExpression[T](terms2: Returnable[_]*)(implicit q: CypherQuery[_]) extends CypherExpression(q, ReturnTerm(terms2 :_*)) with ReturnableExpression[T] {
  val terms = terms2
  val prefix = "RETURN"
}

class Return1[T1](t1: Returnable[T1])(implicit q: CypherQuery[_]) extends ReturnExpression[T1](t1) {
  def toTuple(rec: Record): T1 = {
    val it = rec.values().iterator()
    t1.get(it.next)
  }
}

class Return2[T1, T2](t1: Returnable[T1], t2: Returnable[T2])(implicit q: CypherQuery[_]) extends ReturnExpression[(T1, T2)](t1, t2) {
  def toTuple(rec: Record): (T1, T2) = {
    val it = rec.values().iterator()
    (t1.get(it.next), t2.get(it.next))
  }
}

class Return3[T1, T2, T3](t1: Returnable[T1], t2: Returnable[T2], t3: Returnable[T3])(implicit q: CypherQuery[_]) extends ReturnExpression[(T1, T2, T3)](t1, t2, t3) {
  def toTuple(rec: Record): (T1, T2, T3) = {
    val it = rec.values().iterator()
    (t1.get(it.next), t2.get(it.next), t3.get(it.next))
  }
}

