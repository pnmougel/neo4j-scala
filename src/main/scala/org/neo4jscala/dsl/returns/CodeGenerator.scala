package org.neo4jscala.dsl.returns

/**
  * Created by nico on 14/10/17.
  */
object CodeGenerator {
  def main(args: Array[String]): Unit = {
    val caseClasses = for(i <- 2 to nbGenerate) yield generateN(i)
    println(generateObj(nbGenerate))

    println(
      """
        |case class Return1[T1](t1: Returnable[T1]) {
        |  def toTuple(rec: Record): Tuple1[T1] = {
        |    val it = rec.values().iterator()
        |    Tuple1(t1.get(it.next))
        |  }
        |  def asList(res: StatementResult) = asScalaIterator(res).map(toTuple).toList
        |  def single(res: StatementResult) = toTuple(res.single())
        |}
      """.stripMargin)
    println(caseClasses.mkString("\n"))
  }

  val nbGenerate = 10

  def generateObj(n: Int) = {
s"""object Return {
${applies(n)}
}"""
  }

  def applies(n: Int): String = (1 to n).map(applyOne).mkString("\n")
  def applyOne(n: Int): String = s"    def apply[${types(n)}](${params(n)}) = Return$n(${vars(n)})"
  def types(n: Int) = (1 to n).map(e => s"T$e").mkString(", ")
  def asTuple(n: Int) = (1 to n).map(e => s"t$e.get(rec.get(${e - 1}))").mkString(", ")
  def params(n: Int) = (1 to n).map(e => s"t$e: Returnable[T$e]").mkString(", ")
  def vars(n: Int) = (1 to n).map(e => s"t$e").mkString(", ")

  def generateN(n: Int): String = {
    s"""
case class Return$n[${types(n)}](${params(n)}) {
  def toTuple(rec: Record): (${types(n)}) = (${asTuple(n)})
  def asList(res: StatementResult) = asScalaIterator(res).map(toTuple).toList
  def single(res: StatementResult) = toTuple(res.single())
}""".stripMargin
  }
}
