package org.neo4jscala.dsl.returns.returnable

import org.neo4j.driver.v1.Value
import org.neo4jscala.core.{Neo4jNode, NodeMapper}

/**
  * Created by nico on 14/10/17.
  */

trait Returnable[T] {
  def get(v: Value): T
  def build: String
}
trait IntReturnable extends Returnable[Int] {
  def get(v: Value) = v.asInt()
}
trait StringReturnable extends Returnable[String] {
  def get(v: Value) = v.asString()
}
trait BooleanReturnable extends Returnable[Boolean] {
  def get(v: Value) = v.asBoolean()
}
trait NodeReturnable[T <: Neo4jNode] extends Returnable[T] {
  def get(v: Value)(implicit mf1: Manifest[T]) = {
    val node = v.asNode()
    NodeMapper.as[T](node)
  }
}

