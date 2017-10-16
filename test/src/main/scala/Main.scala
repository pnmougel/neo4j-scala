//import models.{Foo2, Foo7, MyEnum}
//import org.neo4jscala.Neo4jClientFactory
//import org.neo4jscala.core.AnyNode
//import org.neo4jscala.statements.{DeleteAllStmt, StatementDsl}
//
///**
//  * Created by nico on 21/09/17.
//  */
//object Main {
//  def main(args: Array[String]): Unit = {
//    import org.neo4jscala.macros.Macros._
//    import org.neo4jscala.dsl._
//
//    val q = query[Foo2, AnyNode]((a, b) => {
//      Match($(a) -- $(b))
//      Where($(a.strField) == "a")
//      Where(
//        ($(a.strField) == "test" and $(a.intField) > 4).or($(a.strField) contains "foo")
//      )
//      Return($(a))
//    })
//    println(q)
//
//    implicit val client = Neo4jClientFactory.usingBolt(password = "fwRise")
//    val foo = Foo7("Pierre-Nicolas", MyEnum.AsNew)
//    val foo2 = Foo2("a", 1)
//    val foo3 = Foo2("b", 2)
//    foo.id = "xxxxx"
////
////
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
//  }
//}
