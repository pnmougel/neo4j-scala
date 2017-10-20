package org.neo4jscala.dsl

import org.neo4j.driver.v1.Value
import org.neo4jscala.core.NodeMapper
import org.neo4jscala.dsl.`match`.MatchTerm
import org.neo4jscala.dsl.constraints._
import org.neo4jscala.dsl.delete.DeleteTerm
import org.neo4jscala.dsl.returns.returnable.Returnable

/**
  * Created by nico on 13/10/17.
  */
case class NodeDescriptor[T](name: String, nodeLabels: Seq[String] = List())(implicit mf: Manifest[T]) extends MatchTerm with DeleteTerm with Returnable[T] {
  var nodeLabelsMut: Seq[String] = nodeLabels

  def get(v: Value):T = {
    val node = v.asNode()
    NodeMapper.as[T](node)
  }
//  def build: String

  def build = this.toString

  def buildReturn = name

  /**
    * Add labels to the node
    * @param labels
    */
  def labels(labels: String*): NodeDescriptor[T] = {
    nodeLabelsMut ++= labels.toList
    this
  }

  def :::(label: String): NodeDescriptor[T] = {
    nodeLabelsMut = label :: nodeLabelsMut.toList
    this
  }

//  def +:(label: String): NodeDescriptor = {
//    nodeLabelsMut = label :: nodeLabelsMut.toList
//    this
//  }

  def :+(label: String): NodeDescriptor[T] = {
    nodeLabelsMut = label :: nodeLabelsMut.toList
    this
  }

//  def assert(constraint: FieldConstraint) : ConstraintTerm = {
//    ConstraintTerm(this, constraint)
//  }

  def assertUnique(field: Field) : ConstraintTerm = {
    ConstraintTerm(this, IsUniqueFieldConstraint(field))
  }

  def assertExists(field: Field) : ConstraintTerm = {
    ConstraintTerm(this, ExistsFieldConstraint(field))
  }

  def assertNodeKey(fields: Field*) : ConstraintTerm = {
    ConstraintTerm(this, NodeKeyFieldConstraint(fields :_*))
  }

  /**
    * Replace the labels of the node
    * @param labels
    */
  def withLabels(labels: String*): NodeDescriptor[T] = {
    nodeLabelsMut = labels
    this
  }

  override def toString: String = {
    val labelsStr = nodeLabelsMut.map(label => s":$label").mkString
     s"($name$labelsStr)"
  }
}
