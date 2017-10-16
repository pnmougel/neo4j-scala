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

  "A where term" should {
    "allow int comparison with Int field" in {
      val q = query[Foo2] { (n) =>
        implicit q =>
          Where(n.intField :== 1)
      }
      assertQuery(q, "WHERE n.intField = 1")
    }

    "allow string comparison with String field" in {
      val q = query[Foo2] { (n) =>
        implicit q =>
          Where(n.strField :== "foo")
      }
      assertQuery(q, "WHERE n.strField = 'foo'")
    }
  }

  "foo" should {
    "fooppp" in {
      query4(use[(Foo2, String)]) { case (a, b) => implicit q =>
        Return(42, "FEFFF")
      }.returnResult.asList(null).foreach { case (l, b) =>
        println(l)
        println(b.toLowerCase)
      }

      query4(use[Foo2]) { a => implicit q =>
        a.strField
        Return(42, "FEFFF")
      }.returnResult.asList(null).foreach { case (l, b) =>
        println(l)
        println(b.toLowerCase)
      }

      val q = query4(use[Foo2]) { a => implicit q =>
        a.strField
        Return(42, "FEFFF")
      }.returnResult

//      query9(P[Foo2]) { a => implicit q =>
//        a.strField
//        Return(42, "FEFFF")
//      }.returnResult

      query9(P2[Foo2, Foo2]) { (a, b) => implicit q =>
        a.strField
        Return(42, "FEFFF")
      }.returnResult


//      def zz[T](f: PartialFunction[T, String]) = {
//        f(null.asInstanceOf[T])
//      }

//      zz { case (a: String, b: Foo2) =>
//        a
//      }

//      query7(params2[Foo2, Foo2]) { (a, b) => implicit q =>
//        a.strField
//        b.intField
//        Return(42, "FEFFF")
//      }.returnResult
//
//      query7(params3[Foo2, Foo2, String]) { (a, b, c) => implicit q =>
//        a.strField
//        b.intField
//        Return(42, "FEFFF")
//      }.returnResult


//      def gg[T](a: T => Unit)(f: T => Unit) = {
//
//      }

//      for((a, b) <- q.asList(null)) {
//        b.toLowerCase()
//      }
//      for((a, b) <- query4(params[Foo2]) { a => implicit q =>
//        a.strField
//        Return(42, "FEFFF")
//      }.returnResult.asList(null)) {
//        a.toLong
//        b.toLowerCase
//      }

      query4(){ _ => implicit q =>
        Return("fsdfdsf")
      }.returnResult.asList(null).foreach { case (b) =>
        b.toCharArray
      }

      //      query2[(Foo2), (String, Int)] { (n) =>
//        implicit q =>
//          Return(n.strField, 45)
//      }.returnResult


//      def f[A, B](x: A => B): B = {
//        x(null.asInstanceOf[A])
//      }

//      query3 { case (a: Foo2, b: String) => implicit q =>
//        Return("sdfdsf")
//      }.returnResult.asList(null).foreach { l =>
//        l.toLowerCase
//      }
//
//      case class MQuery[T]() {
//        implicit val q = new CypherQuery()
//        private[this] val gg = 77
//        val xx = 42
//        def zz = "fsdf"
//        def build[U](f: U => ReturnExpression[T])(implicit q: QueryBuilder): ReturnExpression[T] = {
//          f(null.asInstanceOf[U])
//        }
//
//      }
//
//      MQuery[(String, Int)]().build[Foo2]( a =>
//        Return("fsfds", 24)
//      ).asList(null)
//
//      query3 { e: (Foo2, String) => implicit q =>
//        val (a, b) = e
//        Return("sdfdsf")
//      }.returnResult.asList(null).foreach { l =>
//        l.toLowerCase
//      }
//
//      query6(Foo2("fsd", 21)){ (a) => implicit q =>
//        a.strField
//        Return(a.strField, "fsdfdsf")
//    }.returnResult.asList(null).foreach { case (l, b) =>
//      l.toLowerCase
//      b.toCharArray
//    }

      //      def ff[X](a: X): ReturnExpression[_] = {
      //        Return("foo", 45)
      //      }
      //      val q = query2[Foo2] { (n) => implicit q =>
      //        Return("foo", 45)
      //      }
      //      println(q.returnResult.asList(null))

      //
      //      def bkkk[T](expr: T => Unit) = {
      //        expr(null.asInstanceOf[T])
      //      }

      //      def query[T1, T2](expr: (T1, T2) => CypherQuery => Unit)(implicit mf1: Manifest[T1], mf2: Manifest[T2]): CypherQuery = {
      //        val q = new CypherQuery()
      //        expr(null.asInstanceOf[T1], null.asInstanceOf[T2])(q)
      //        q
      //      }

      //      bkkk[(Foo2, AnyNode)] { case (a, b) =>
      //      }

      //      object GG {
      //        def query[T[U]](expr: T1 => ReturnExpression[T]): Unit = {
      //          expr(null.asInstanceOf[T1])
      //        }
      //      }
      //      def runx[T[U]](f: => ReturnExpression[T]): ReturnExpression[T] = {
      //        f
      //        GG.query[Int] { a =>
      //          Return(42)
      //        }
    }

    //      GG.quer

    //      runx {
    //        query[Foo2] { (n) => implicit q =>
    //          Return("foo", 45)
    //        }
    //        val cq = new CypherQuery()
    //        Return("4", 42)(cq)
    //      }.asList(null).map { a =>
    //      }

    //      val cq = new CypherQuery()
    //      Return("4", 42)(cq).asList(null)


    //      val db = new TestGraphDatabaseFactory()
    //      db.newImpermanentDatabaseBuilder()

    //      val client = Neo4jClientFactory.embeded(new File("foo"))
    //      q.runAsList(client).map { e =>
    //        e.map( a => {
    //          println(a)
    //        })
    //      }
    //      val res = client.exec(q.toString)

    //      for((r1, r2) <- Return("4", 42).asList(res)) {
    //        println(r1)
    //        println(r2)
    //      }
    //    }
  }
}
