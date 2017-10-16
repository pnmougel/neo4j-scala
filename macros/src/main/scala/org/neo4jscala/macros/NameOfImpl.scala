package org.neo4jscala.macros

/**
  * Created by nico on 19/09/17.
  */

import org.neo4jscala.core.Neo4jNode
import org.neo4jscala.dsl.{IntField, NodeDescriptor, StringField}

import scala.annotation.tailrec
import scala.reflect.macros.blackbox.Context

object NameOfImpl {
  @tailrec def extract(c: Context)(tree: c.Tree, expr: c.Expr[Any]): String = {
    import c.universe._
    tree match {
      case Ident(n) => {
        n.decodedName.toString
      }
      case Select(a, n) => {
        a.symbol.name.decodedName.toString + "." + n.decodedName.toString
      }
      case Function(_, body) => extract(c)(body, expr)
      case Block(_, block) => extract(c)(block, expr)
      case Apply(func, _) => extract(c)(func, expr)
      case TypeApply(func, _) => extract(c)(func, expr)
      case _ => c.abort(c.enclosingPosition, s"Unsupported expression: $expr")
    }
  }

  def nodeDescriptorBuilder(c: Context)(expr: c.Expr[Neo4jNode]): c.Expr[NodeDescriptor] = {
    import c.universe._

    val name = extract(c)(expr.tree, expr)
    val label = List(expr.tree.tpe.typeSymbol.name.decodedName.toString).filter(_ != "AnyNode")
    reify {
      c.Expr[NodeDescriptor] {
        q"NodeDescriptor($name, $label)"
      }.splice
    }
  }

  def stringFieldDescriptorBuilder(c: Context)(expr: c.Expr[String]): c.Expr[StringField] = {
    import c.universe._
    val name = extract(c)(expr.tree, expr)
    reify(c.Expr[StringField](q"StringField($name)").splice)
  }

  def intFieldDescriptorBuilder(c: Context)(expr: c.Expr[Int]): c.Expr[IntField] = {
    import c.universe._
    val name = extract(c)(expr.tree, expr)
    reify(c.Expr[IntField](q"IntField($name)").splice)
  }

//  def nameOf2(c: Context)(expr: c.Expr[Any]): c.Expr[String] = {
//    import c.universe._
//
//    @tailrec def extract(tree: c.Tree): String = tree match {
//      case Ident(n) => {
//        n.decodedName.toString
//      }
//      case Select(a, n) => {
//        a.symbol.name.decodedName.toString + "." + n.decodedName.toString
//      }
//      case Function(_, body) => extract(body)
//      case Block(_, expr) => extract(expr)
//      case Apply(func, _) => extract(func)
//      case TypeApply(func, _) => extract(func)
//      case _ => c.abort(c.enclosingPosition, s"Unsupported expression: $expr")
//    }
//
//    val name = extract(expr.tree)
//    reify {
//      c.Expr[String] {
//        Literal(Constant(name))
//      }.splice
//    }
//  }
//
//  def nameOfType[T](c: Context)(implicit tag: c.WeakTypeTag[T]): c.Expr[String] = {
//    import c.universe._
//    val name = showRaw(tag.tpe.typeSymbol.name)
//    reify {
//      c.Expr[String] {
//        Literal(Constant(name))
//      }.splice
//    }
//  }

  //  def qualifiedNameOfType(c: Context)(number: c.Tree): c.Tree = {
  //    import c.universe._
  //    val name = showRaw(tag.tpe.typeSymbol.fullName)
  //
  ////    q"""object M { val a = 42} """
  //
  //    q"""
  //      if ($number%2==0){
  //        println($number.toString + " is even")
  //      }else {
  //        println($number.toString + " is odd")
  //      }
  //    """
  //  }
  //
  //  def toObj(c: Context): c.Expr[String] = {
  //    import c.universe._
  //
  //    reify {
  //      c.Expr[String] { q"""
  //       object A {
  //        val foo = "fsdfsd"
  //       }
  //       A.foo
  //     """ }.splice
  //    }
  //  }

  def qualifiedNameOfType[T](c: Context)(implicit tag: c.WeakTypeTag[T]): c.Expr[String] = {
    import c.universe._
    val name = showRaw(tag.tpe.typeSymbol.fullName)

    reify {
      c.Expr[String] {
        Literal(Constant(name))
      }.splice
    }
  }
}