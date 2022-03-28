package gatis.scaladev.cliapp

import cats.Monad
import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.flatMap.*
import cats.syntax.functor.*
import gatis.scaladev.engine.Constants.answers
import gatis.scaladev.engine.Utils.selectAnswer
import gatis.scaladev.engine.{GameRound, InProgress, Lost, Won}


object MainApp extends IOApp {

  def process[F[_] : Monad](console: Console[F], gameRound: GameRound): F[ExitCode] =
    for {
      _ <- console.putStrLn("Enter Your Guess")
      input <- console.readStrLn
      exitCode <- gameRound.playTurn(input) match {
        case Right(gr) =>
          if (gr.status == Won) console.putStrLn("Congrats").as(ExitCode.Success)
          else if (gr.status == Lost) console.putStrLn("Looser").as(ExitCode.Success)
          else process(console, gr)
        case Left(err) =>
          // ASK. Kāpēc šis nestrādā?
//          console.putStrLn(err.value)
//          process(console, gameRound)
          console.putStrLn(err.value).flatMap(_=> process(console, gameRound))
      }
    } yield exitCode

  private val initialGameRound = GameRound(Nil, InProgress, selectAnswer(answers))

  override def run(args: List[String]): IO[ExitCode] = process(Console.create[IO], initialGameRound)
}
