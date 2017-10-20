package org.neo4jscala.dsl.constraints

import org.neo4jscala.dsl.{CypherTerm, Field, NodeDescriptor}

/**
  * Created by nico on 14/10/17.
  */
trait FieldConstraint {
  def build(): String
}

case class IsUniqueFieldConstraint(field: Field) extends FieldConstraint {
  def build = s"${field.name} IS UNIQUE"
}

case class ExistsFieldConstraint(field: Field) extends FieldConstraint {
  def build = s"exists(${field.name})"
}

case class NodeKeyFieldConstraint(fields: Field*) extends FieldConstraint {
  def build = s"(${fields.map(_.name).mkString(", ")}) IS NODE KEY"
}

case class ConstraintTerm(node: NodeDescriptor[_], fieldConstraint: FieldConstraint) extends CypherTerm {
  def build = s"$node ASSERT ${fieldConstraint.build()}"
}

