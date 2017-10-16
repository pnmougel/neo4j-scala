package matchers

import org.neo4jscala.dsl.CypherQuery
import org.scalatest.Matchers

/**
  * Created by nico on 14/10/17.
  */
trait CypherQueryMatchers extends Matchers {
  def assertQuery(q: CypherQuery, res: String): Unit = {
    q.toString.replaceAllLiterally("\n", " ") should be(res)
  }
}
