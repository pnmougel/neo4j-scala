import sbt._
import Keys._
import Dependencies._

lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

lazy val test = (project in file("test"))
  .settings(name := "neo4j-scala-dsl")
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= dependencies)
  .settings(libraryDependencies ++= testDependencies)
  .dependsOn(macros)
  .dependsOn(root)

lazy val macros = (project in file("macros"))
  .settings(name := "api-macros")
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= macrosDependencies)
  .dependsOn(root)

lazy val root = (project in file("."))
  .settings(name := "neo4j-scala")
  .settings(commonSettings)
  .settings(libraryDependencies ++= dependencies)
  .settings(libraryDependencies ++= testDependencies)



