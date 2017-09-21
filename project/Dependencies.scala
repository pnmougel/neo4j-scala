import sbt._

object Dependencies {
  // Versions
  lazy val jacksonVersion = "2.8.8"
  lazy val log4jVersion = "2.7"
  lazy val neo4jVersion = "1.4.3"
  lazy val scalaCurVersion = "2.12.3"


  // Libraries
  val appDeps = Seq(
    // Reflection
    "org.reflections" % "reflections" % "0.9.10",

    // Neo4j
    "org.neo4j.driver" % "neo4j-java-driver" % neo4jVersion,
//    "com.graphaware.neo4j" % "uuid" % "3.2.1.51.14",
//    "com.graphaware.neo4j" % "runtime" % "3.2.1.51",
//    "com.graphaware.neo4j" % "graphaware-framework-embedded" % "3.2.1.51",

    // Log4j
    "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion,

    // Jackson
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion
  )


  val macrosDeps = Seq(
    // Macros :)
    "org.scala-lang" % "scala-reflect" % scalaCurVersion
  )
}