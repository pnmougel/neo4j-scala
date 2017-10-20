package dsl

import matchers.CypherQueryMatchers
import models.Foo2
import org.neo4jscala.core.AnyNode
import org.scalatest.{BeforeAndAfterAll, WordSpec}

/**
  * Created by nico on 13/10/17.
  */
class ConstraintDslTest extends WordSpec with CypherQueryMatchers with BeforeAndAfterAll {
  import org.neo4jscala.dsl._
  import org.neo4jscala.macros.Macros._

//  "A create constraint query" should {
//    "generate a unique constraint query" in {
//      val q = query(use[Foo2]) { (n) => implicit q =>
//        CreateConstraint(n assertUnique n.strField)
//      }
//      assertQuery(q, "CREATE CONSTRAINT ON (n:Foo2) ASSERT n.strField IS UNIQUE")
//    }
//
//    "generate an exist constraint query" in {
//      val q = query(use[Foo2]) { (n) => implicit q =>
//        CreateConstraint(n assertExists n.strField)
//      }
//      assertQuery(q, "CREATE CONSTRAINT ON (n:Foo2) ASSERT exists(n.strField)")
//    }
//
//    "generate a node key constraint query" in {
//      val q = query(use[Foo2]) { (n) => implicit q =>
//        CreateConstraint(n assertNodeKey(n.strField, n.intField))
//      }
//      assertQuery(q, "CREATE CONSTRAINT ON (n:Foo2) ASSERT (n.strField, n.intField) IS NODE KEY")
//    }
//  }
//
//  "A drop constraint query" should {
//    "generate a unique constraint query" in {
//      val q = query(use[Foo2]) { (n) => implicit q =>
//        DropConstraint(n assertUnique n.strField)
//      }
//      assertQuery(q, "DROP CONSTRAINT ON (n:Foo2) ASSERT n.strField IS UNIQUE")
//    }
//
//    "generate an exist constraint query" in {
//      val q = query(use[Foo2]) { (n) => implicit q =>
//        DropConstraint(n assertExists n.strField)
//      }
//      assertQuery(q, "DROP CONSTRAINT ON (n:Foo2) ASSERT exists(n.strField)")
//    }
//
//    "generate a node key constraint query" in {
//      val q = query(use[Foo2]) { (n) => implicit q =>
//        DropConstraint(n assertNodeKey(n.strField, n.intField))
//      }
//      assertQuery(q, "DROP CONSTRAINT ON (n:Foo2) ASSERT (n.strField, n.intField) IS NODE KEY")
//    }
//  }

}
