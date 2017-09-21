package macros

/**
  * Created by nico on 19/09/17.
  */
import scala.annotation.tailrec
//import scala.reflect.macros.whitebox.Context
import scala.reflect.macros.blackbox.Context

object NameOfImpl {
  def nameOf(c: Context)(expr: c.Expr[Any]): c.Expr[String] = {
    import c.universe._

    @tailrec def extract(tree: c.Tree): c.Name = tree match {
      case Ident(n) => n
      case Select(_, n) => n
      case Function(_, body) => extract(body)
      case Block(_, expr) => extract(expr)
      case Apply(func, _) => extract(func)
      case TypeApply(func, _) => extract(func)
      case _ => c.abort(c.enclosingPosition, s"Unsupported expression: $expr")
    }

    val name = extract(expr.tree).decoded
    reify {
      c.Expr[String] { Literal(Constant(name)) }.splice
    }
  }

  def nameOfType[T](c: Context)(implicit tag: c.WeakTypeTag[T]): c.Expr[String] = {
    import c.universe._
    val name = showRaw(tag.tpe.typeSymbol.name)
    reify {
      c.Expr[String] { Literal(Constant(name)) }.splice
    }
  }

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
      c.Expr[String] { Literal(Constant(name)) }.splice
    }
  }
}