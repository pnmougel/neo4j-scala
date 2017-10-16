package org.neo4jscala.dsl

import org.neo4jscala.core.{AnyNode, Neo4jNode}
import org.neo4jscala.dsl.`match`.MatchTerm
import org.neo4jscala.dsl.constraints._
import org.neo4jscala.dsl.delete.DeleteTerm
import org.neo4jscala.dsl.returns.ReturnTerm
import org.neo4jscala.dsl.returns.returnable.{NodeReturnable, Returnable}

/**
  * Created by nico on 13/10/17.
  */
case class NodeDescriptor(name: String, nodeLabels: Seq[String] = List()) extends MatchTerm with DeleteTerm {
  var nodeLabelsMut: Seq[String] = nodeLabels

  def build = this.toString

  /**
    * Add labels to the node
    * @param labels
    */
  def labels(labels: String*): NodeDescriptor = {
    nodeLabelsMut ++= labels.toList
    this
  }

  def :::(label: String): NodeDescriptor = {
    nodeLabelsMut = label :: nodeLabelsMut.toList
    this
  }

//  def +:(label: String): NodeDescriptor = {
//    nodeLabelsMut = label :: nodeLabelsMut.toList
//    this
//  }

  def :+(label: String): NodeDescriptor = {
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
  def withLabels(labels: String*): NodeDescriptor = {
    nodeLabelsMut = labels
    this
  }

  override def toString: String = {
    val labelsStr = nodeLabelsMut.map(label => s":$label").mkString
     s"($name$labelsStr)"
  }
}
