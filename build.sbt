import sbt._
import Keys._
import Dependencies._

lazy val commonSettings = Seq(
  version := "0.1.0",
  scalaVersion := "2.12.3"
)


lazy val macros = (project in file("macros"))
  .settings(name := "api-macros")
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= macrosDeps)

lazy val root = (project in file("."))
  .settings(name := "neo4j-scala")
  .settings(commonSettings)
  .settings(libraryDependencies ++= appDeps)
  .dependsOn(macros)


