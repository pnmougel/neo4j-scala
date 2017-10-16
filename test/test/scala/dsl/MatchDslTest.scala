package dsl

import matchers.CypherQueryMatchers
import models.Foo2
import org.neo4jscala.core.AnyNode
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

/**
  * Created by nico on 13/10/17.
  */
class MatchDslTest extends WordSpec with CypherQueryMatchers with BeforeAndAfterAll {
  import org.neo4jscala.dsl._
  import org.neo4jscala.macros.Macros._

  "A match query with a single node" should {
    "produce a match term with class name as node label" in {
      val q = query[Foo2] { (n) => implicit q =>
        Match(n)
      }
      assertQuery(q, "MATCH (n:Foo2)")
    }

    "produce a match term with no label" in {
      val q = query[AnyNode] { (n) => implicit q =>
        Match(n)
      }
      assertQuery(q, "MATCH (n)")
    }

    "produce a match term with a new label" in {
      val q = query[Foo2] { (n) => implicit q =>
        Match(n :+ "OtherLabel")
      }
      assertQuery(q, "MATCH (n:OtherLabel:Foo2)")
    }
  }

  "A match query with two nodes" should {
    "produce a match term with a directed left to right relation" in {
      val q = query[AnyNode, AnyNode] { (n1, n2) => implicit q =>
        Match(n1 :--> n2)
      }
      assertQuery(q, "MATCH (n1) --> (n2)")
    }

    "produce a match term with a directed right to left relation" in {
      val q = query[AnyNode, AnyNode] { (n1, n2) => implicit q =>
        Match(n1 :<-- n2)
      }
      assertQuery(q, "MATCH (n1) <-- (n2)")
    }

    "produce a match term with an undirected relation" in {
      val q = query[AnyNode, AnyNode] { (n1, n2) => implicit q =>
        Match(n1 :-- n2)
      }
      assertQuery(q, "MATCH (n1) -- (n2)")
    }
  }
}
