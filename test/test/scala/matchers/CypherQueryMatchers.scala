package matchers

import org.neo4jscala.dsl.{CypherQuery, QueryWithResult}
import org.scalatest.Matchers

/**
  * Created by nico on 14/10/17.
  */
trait CypherQueryMatchers extends Matchers {
  def assertQuery(q: CypherQuery[_], res: String): Unit = {
    q.toString.replaceAllLiterally("\n", " ") should be(res)
  }
}
