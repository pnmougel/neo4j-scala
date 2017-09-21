import java.io.File

import org.neo4jscala.Neo4jClientFactory
import org.neo4jscala.core.Neo4jNode
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import org.neo4j.graphdb.factory.GraphDatabaseSettings._
import org.neo4j.kernel.configuration.BoltConnector.EncryptionLevel

/**
  * Created by nico on 21/09/17.
  */

case class StringModel(str: String) extends Neo4jNode

class Test extends FlatSpec with Matchers with BeforeAndAfterAll {


  import org.neo4j.graphdb.factory.GraphDatabaseFactory

  val graphDbTestPort = 8888
  val bolt = new org.neo4j.kernel.configuration.BoltConnector("0")

  val graphDb = new GraphDatabaseFactory()
    .newEmbeddedDatabaseBuilder(new File(".graphDb"))
    .setConfig(auth_enabled, "false")
    .setConfig(bolt.`type`, "BOLT")
    .setConfig(bolt.enabled, "true")
    .setConfig(bolt.listen_address, s"localhost:$graphDbTestPort")
    .setConfig(bolt.encryption_level, EncryptionLevel.OPTIONAL.name())
    .newGraphDatabase()

  val client = Neo4jClientFactory.noAuth(port = graphDbTestPort)

  override def beforeAll(): Unit = {
  }

  it should "Create sthg" in {
    val foo = StringModel("foo")
    client.create(foo)
    println(foo.id)
    val foo2 = client.byId[StringModel](foo.id)
    assert(foo2.id === foo.id)
    assert(foo2.str === foo.str)
  }
}
