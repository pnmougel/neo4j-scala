package org.neo4jscala.dsl

import org.neo4jscala.dsl.returns.{ReturnExpression, ReturnableExpression}

/**
  * Created by nico on 16/10/17.
  */
class QueryWithResult[K](val q: CypherQuery[_], val returnResult: ReturnableExpression[K]) {

}
