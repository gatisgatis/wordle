package gatis.scaladev.cli

import cats.Monad
import cats.syntax.all.*
import gatis.scaladev.cli.prettier.Instances.gameRoundPrettier
import gatis.scaladev.cli.prettier.PrettierSyntax.PrettierOps
import gatis.scaladev.engine.{GameRound, Lost, Won}

object Processor {
  def process[F[_] : Monad](console: Console[F], gameRound: GameRound, message: String = ""): F[Unit] = {

    def winningMessage(count: Int) = s"Congrats. You WON using only $count guesses!"
    def loosingMessage(answer: String) = s"Meh. You LOST. The answer is '${answer.toUpperCase}'!"


    for {
      _ <- ().pure[F]
      defaultMsg = s"Enter your guess number ${gameRound.guesses.size + 1}"
      msg = if (message.isBlank) defaultMsg else message + "\n" + defaultMsg
      _ <- console.putStrLn(msg + "\n")
      _ <- console.putStrLn(gameRound.toFancyString)
      input <- console.readStrLn
      _ <- gameRound.playTurn(input) match {
        case Right(gr) =>
          if (gr.status == Won) console.putStrLn(gr.toFancyString) *> console.putStrLn(winningMessage(gr.guesses.size))
          else if (gr.status == Lost) console.putStrLn(gr.toFancyString) *> console.putStrLn(loosingMessage(gr.answer.getOrElse("")))
          else process(console, gr)
        case Left(err) => process(console, gameRound, "Error! " + err.value + "!")
      }
    } yield ()
  }
}
