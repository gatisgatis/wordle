package gatis.scaladev.engine

import gatis.scaladev.engine.Constants.allWords
import gatis.scaladev.engine.Utils.{AllWords, ErrorMsg, validateWord}

final case class GameRound(guesses: List[Guess], status: GameStatus, private val answer: Either[ErrorMsg, String], allWords: AllWords = allWords) {
  private val alphabet: Array[String] = "abcdefghijklmnopqrstuvwxyz".split("")

  private val maxGuesses = 6

  private def isWon(guess: Guess) = guess.value.forall(_.isInstanceOf[Green])

  private def isLost = this.guesses.length >= maxGuesses - 1

  private def getStatus(guess: Guess): GameStatus = {
    if (isWon(guess)) Won
    else if (isLost) Lost
    else InProgress
  }

  def playTurn(input: String): Either[ErrorMsg, GameRound] = {
    for {
      unvalidatedAnswer <- answer
      validatedAnswer <- validateWord(unvalidatedAnswer)(allWords)
      guess <- Guess.fromString(input, validatedAnswer)(allWords)
    } yield this.copy(guesses = this.guesses :+ guess, status = getStatus(guess))
  }

  def keyboard: List[Letter] = {
    val map: Map[String, Letter] = guesses.foldLeft(Map.empty[String, Letter]) { (map, guess) =>
      guess.value.foldLeft(map) { (m, letter) =>
        map.get(letter.value) match {
          case Some(Yellow(_)) | None => m.updated(letter.value, letter) // updates yellow to green or adds new record to the map
          case _ => m // Green or Grey -> do not change the map
        }
      }
    }
    alphabet.map(symbol => map.getOrElse(symbol, White(symbol))).toList
  }
}
