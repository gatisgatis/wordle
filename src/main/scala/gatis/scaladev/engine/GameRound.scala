package gatis.scaladev.engine

import gatis.scaladev.engine.Utils.{AllWords, Answers, ErrorMsg, getDataFromTxt}

final case class GameRound(guesses: List[Guess], status: GameStatus, answer: Either[ErrorMsg, String]) {
  private val maxGuesses = 6

  private def isWon(guess: Guess) = guess.value.forall(_.isInstanceOf[Green])

  private def isLost = this.guesses.length >= maxGuesses - 1

  private def getStatus(guess: Guess): GameStatus = {
    if (isWon(guess)) Won
    else if (isLost) Lost
    else InProgress
  }

  def playTurn(input: String): Either[ErrorMsg, GameRound] = {
    implicit val allWords: AllWords = AllWords(getDataFromTxt("all_words.txt"))
    for {
      answer <- answer
      guess <- Guess.fromString(input, answer)(allWords)
    } yield this.copy(guesses = this.guesses :+ guess, status = getStatus(guess))
  }

  def updateKeyboard: List[Letter] = {

    val usedLetters: List[Letter] = guesses.flatMap(_.value)

    val usedLettersVerified = usedLetters.foldLeft(Map.empty[String, Letter]) { (map, letter) =>
      map.get(letter.value) match {
        case Some(Yellow(_)) | None => map.updated(letter.value, letter) // updates yellow to green or adds new record to the map
        case _ => map // Green or Grey -> do not change the map
      }
    }

    "abcdefghijklmnopqrstuvwxyz"
      .split("")
      .map(symbol => usedLettersVerified.getOrElse(symbol, White(symbol)))
      .toList
  }
}