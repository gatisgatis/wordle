ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

scalacOptions := Seq(
  "-Xsource:3",
)

import Dependencies._

libraryDependencies ++= Seq(
  Cats.Effect,
  Cats.Core,
  MUnit,
  Circe.Generic,
  Circe.GenericExtras
)

lazy val root = (project in file("."))
  .settings(name := "wordle-engine")
  .settings(testCoverageReport)

val testCoverageReport = addCommandAlias(
  "test-coverage-report",
  """
    | coverage;
    | test;
    | coverageReport;
    | coverageAggregate;
    | coverageOff;
    | """.stripMargin,
)