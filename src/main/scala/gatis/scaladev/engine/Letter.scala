package gatis.scaladev.engine

sealed trait Letter {
  val value: String
}

final case class Green(value: String) extends Letter

final case class Yellow(value: String) extends Letter

final case class Grey(value: String) extends Letter

final case class White(value: String) extends Letter

