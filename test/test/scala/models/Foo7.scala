package models

import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import models.enums.MyEnum.MyEnum
import models.enums.MyEnumType
import org.neo4jscala.core.{Neo4jNode, Neo4jRelation}


/**
  * Created by nico on 13/10/17.
  */
case class Foo7(
  name: String,
  @JsonScalaEnumeration(classOf[MyEnumType])
  enum: MyEnum) extends Neo4jNode[Foo7] {
    @transient val rels = new Neo4jRelation[Foo2]("xxx")
  }
