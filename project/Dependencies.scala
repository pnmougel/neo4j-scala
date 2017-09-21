import sbt._

object Dependencies {
  // Versions
  lazy val jacksonVersion = "2.8.8"
  lazy val log4jVersion = "2.7"
  lazy val neo4jDriverVersion = "1.4.3"
  lazy val neo4jVersion = "3.2.3"

  // lazy val scalaCurVersion = "2.12.3"
  lazy val scalaCurVersion = "2.11.8"

  lazy val scalaTestVersion = "3.0.1"

  // Libraries
  val dependencies = Seq(
    // Reflection
    "org.reflections" % "reflections" % "0.9.10",

    // Neo4j
    "org.neo4j.driver" % "neo4j-java-driver" % neo4jDriverVersion,

    // Log4j
    "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,

    // Jackson
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion
  )

  val testDependencies = Seq(
    "org.scalactic" %% "scalactic" % scalaTestVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,

    // Embeded neo4j server
    "org.neo4j" % "neo4j" % neo4jVersion % Test,
    "org.neo4j" % "neo4j-bolt" % neo4jVersion,
    "org.neo4j" % "neo4j-cypher" % neo4jVersion,

    // Test html output
    "com.vladsch.flexmark" % "flexmark-all" % "0.27.0" % Test
  )


  val macrosDependencies = Seq(
    // Macros :)
    "org.scala-lang" % "scala-reflect" % scalaCurVersion
  )
}