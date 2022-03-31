import sbt._

object Dependencies {
  val CatsVersion = "2.7.0"
  val CatsEffectVersion = "2.5.4"

  val MUnitVersion = "0.7.29"

  val CirceVersion = "0.14.1"
  object Circe {
    val Generic = "io.circe" %% "circe-generic" % CirceVersion
    val GenericExtras = "io.circe" %% "circe-generic-extras" % CirceVersion
  }

  object Cats {
    val Effect = "org.typelevel" %% "cats-effect" % CatsEffectVersion
    val Core = "org.typelevel" %% "cats-core" % CatsVersion
  }
  val MUnit = "org.scalameta" %% "munit" % "0.7.29" % Test
}