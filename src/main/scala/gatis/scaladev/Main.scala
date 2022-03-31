package gatis.scaladev

import cats.effect.{ExitCode, IO, IOApp}
import gatis.scaladev.cli.{Console, Processor}
import gatis.scaladev.engine.Constants.answers
import gatis.scaladev.engine.Utils.selectAnswer
import gatis.scaladev.engine.{GameRound, InProgress}

object Main extends IOApp {
  private val initialGameRound = GameRound(Nil, InProgress, selectAnswer(answers))
  private val welcomeMessage = "Welcome to Worlde! You have 6 chances to guess the 5-letter word! Good Luck!"

  override def run(args: List[String]): IO[ExitCode] =
    Processor.process(Console.create[IO], initialGameRound, welcomeMessage).as(ExitCode.Success)
}
