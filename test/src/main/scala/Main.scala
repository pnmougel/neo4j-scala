import org.neo4jscala.Neo4jClientFactory
import org.neo4jscala.core.{AnyNode, Neo4jNode}
import org.neo4jscala.dsl.{Where, query, use}
import org.neo4jscala.dsl.returns.Return
import org.neo4jscala.statements.{DeleteAllStmt, StatementDsl}

/**
  * Created by nico on 21/09/17.
  */
case class Foo4(name: Int) extends Neo4jNode[Foo4]

object Main {
  def main(args: Array[String]): Unit = {
    import org.neo4jscala.macros.Macros._
    import org.neo4jscala.dsl._
    import org.neo4jscala.dsl.expressions._

    implicit val client = Neo4jClientFactory.usingBolt(password = "fwRise")
    client.exec("CREATE (n:Foo4 {name: 42})")

    //    query(use[Foo4]) { n => implicit q =>
    //      Match(n)
    ////      Where(n.name :<> 42)
    //      Return(field(n.name), stDev(n.name))
    //    }.all( a => {
    //      println(a)
    //    })



    val q = query(use[Foo4]) { n =>
      implicit q =>
        Match(n)
        Return(field(n.name), 78, stDev(n.name))
    }
    println(q)
    q.all { case (a, b, c) =>
      println("-----")
      println(a)
//      println(b)
      println(c)
    }

    val n = Foo4(18)
    use(n)

    //    query(use[Foo4]) { n => implicit q =>
    //      Match(n)
    //      Return(n, "4578")
    //    }
    //
    //    query(use[Foo4]) { n => implicit q =>
    //      Match(n)
    //      Return(n, "4578", 12)
    //    }.all { case (e, _, _) =>
    //      println(e)
    //    }
    //
    //    query(use[Foo4]) { n => implicit q =>
    //      Match(n)
    //      Return(n, "4578", 12)
    //    }.asList.foreach { case (e, f, g) =>
    //        println(e)
    //    }

    //    val values = res.list().map(_.get(0))
    //    values.map { v =>
    //      println(nd.get(v))
    //    }


    //    query2(use[Foo2, Foo2]) { (a, b) => implicit q =>
    ////      Match(a :-- b)
    ////      Where(a.name :== "a")
    //      Return("hello world!")
    //    }.asList.foreach(e => {
    //      println(e)
    //    })
    //
    //    val q2 = query2(use[Foo2, Foo2]) { (a, b) => implicit q =>
    //      Match(a :--> b)
    //      Where((a.name :== "a").and(b.name :== "bla"))
    //      Return("a")
    //    }
    //    println(q2.q.toString)

    //    implicit val client = Neo4jClientFactory.usingBolt(password = "fwRise")
    //    val foo = Foo7("Pierre-Nicolas", MyEnum.AsNew)
    //    val foo2 = Foo2("a", 1)
    //    val foo3 = Foo2("b", 2)
    //    foo.id = "xxxxx"
    //
    //
    //    client.run(DeleteAllStmt())
    //    foo.doCreate[Foo7]()
    //    client.run(foo2.create[Foo2]())
    //    client.run(foo3.create[Foo2]())
    //    foo.rels.doAdd(foo2)
    //    client.run(foo.rels.add(foo3))
    //
    //    client.run(StatementDsl.byId[Foo2](foo2.id))
    //
    //    foo.rels.runEndNodes().foreach(res => {
    //      println(res)
    //    })
  }
}
