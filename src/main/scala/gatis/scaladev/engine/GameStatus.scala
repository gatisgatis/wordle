package gatis.scaladev.engine

sealed trait GameStatus

case object InProgress extends GameStatus {
  override def toString: String = "in-progress"
}

case object Lost extends GameStatus {
  override def toString: String = "lost"
}

case object Won extends GameStatus {
  override def toString: String = "won"
}
