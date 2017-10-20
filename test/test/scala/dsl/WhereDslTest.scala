package dsl

import java.io.File

import matchers.CypherQueryMatchers
import models.Foo2
import org.neo4j.cypher.internal.compiler.v2_3.commands.QueryBuilder
import org.neo4j.test.TestGraphDatabaseFactory
import org.neo4jscala.Neo4jClientFactory
import org.neo4jscala.core.AnyNode
import org.neo4jscala.dsl.returns.{Return, ReturnExpression}
import org.scalatest.{BeforeAndAfterAll, WordSpec}

/**
  * Created by nico on 13/10/17.
  */
class WhereDslTest extends WordSpec with CypherQueryMatchers with BeforeAndAfterAll {

  import org.neo4jscala.dsl._
  import org.neo4jscala.macros.Macros._

//  "A where term" should {
//    "allow int comparison with Int field" in {
//      val q = query(use[Foo2]) { (n) =>
//        implicit q =>
//          Where(n.intField :== 1)
//      }
//      assertQuery(q, "WHERE n.intField = 1")
//    }
//
//    "allow string comparison with String field" in {
//      val q = query(use[Foo2]) { (n) =>
//        implicit q =>
//          Where(n.strField :== "foo")
//      }
//      assertQuery(q, "WHERE n.strField = 'foo'")
//    }
//  }
//
//  "foo" should {
//    "fooppp" in {
////      val q = query(use[Foo2]) { a =>
////        implicit q =>
////          a.strField
////          Return(42, "FEFFF")
////      }.returnResult
////
////      query(use[(Foo2, Foo2)]) { case (a, b) => implicit q =>
////        a.strField
////        Return(42, "FEFFF")
////      }.returnResult
////
////      query() { _ => implicit q =>
////        Return("fsdfdsf")
////      }.returnResult.asList(null).foreach { case (b) =>
////        b.toCharArray
////      }
//    }
//  }
}
