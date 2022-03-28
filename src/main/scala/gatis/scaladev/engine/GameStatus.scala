package gatis.scaladev.engine

sealed trait GameStatus

case object InProgress extends GameStatus

case object Lost extends GameStatus

case object Won extends GameStatus
