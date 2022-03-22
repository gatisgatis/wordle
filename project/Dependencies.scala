import sbt._

object Dependencies {
  val CatsVersion = "2.7.0"
  val CatsEffectVersion = "2.5.4"

  val ScalaTestVersion = "3.2.11"

  object Cats {
    val Effect = "org.typelevel" %% "cats-effect" % CatsEffectVersion
    val Core = "org.typelevel" %% "cats-core" % CatsVersion
  }

  val ScalaTest = "org.scalatest" %% "scalatest" % ScalaTestVersion % "test"

}