package gatis.scaladev.engine

sealed trait Letter {
  val value: String
}

final case class Green(value: String) extends Letter {
  override def toString: String = "green"
}

final case class Yellow(value: String) extends Letter {
  override def toString: String = "yellow"
}

final case class Grey(value: String) extends Letter {
  override def toString: String = "grey"
}

final case class White(value: String) extends Letter {
  override def toString: String = "white"
}

