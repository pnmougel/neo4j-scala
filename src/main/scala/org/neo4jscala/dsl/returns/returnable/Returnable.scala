package org.neo4jscala.dsl.returns.returnable

import org.neo4j.driver.v1.Value
import org.neo4jscala.core.{Neo4jNode, NodeMapper}

/**
  * Created by nico on 14/10/17.
  */

trait Returnable[T] {
  def get(v: Value): T
  def buildReturn: String
}
trait IntReturnable extends Returnable[Int] {
  def get(v: Value) = v.asInt()
}
trait FloatReturnable extends Returnable[Float] {
  def get(v: Value) = v.asFloat()
}
trait StringReturnable extends Returnable[String] {
  def get(v: Value) = v.asString()
}
trait BooleanReturnable extends Returnable[Boolean] {
  def get(v: Value) = v.asBoolean()
}
//trait NodeReturnable[T <: Neo4jNode[T]] extends Returnable[T] {
//  def get(v: Value): T = {
//    val node = v.asNode()
//    NodeMapper.as[T](node)
//  }
//
//  def build = ""
//}

//class NodeReturnable[T <: Neo4jNode[T]](implicit mf1: Manifest[T]) extends Returnable[T] {
//  def get(v: Value): T = {
//    val node = v.asNode()
//    NodeMapper.as[T](node)
//  }
//
//  def build = ""
//}