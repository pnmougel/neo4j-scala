package org.neo4jscala.core

import org.neo4jscala.Neo4jClient

/**
  * Created by nico on 10/10/17.
  */
trait Neo4jRunnable {
  def run[R](f: Neo4jStatement[R])(implicit client: Neo4jClient): R = {
    client.run(f)
  }
}
