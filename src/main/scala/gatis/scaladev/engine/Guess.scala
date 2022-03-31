package gatis.scaladev.engine

import gatis.scaladev.engine.Utils.{AllWords, validateWord, ErrorMsg}

final case class Guess(value: List[Letter])

object Guess {
  val empty: Guess = Guess(value = List(White(" "), White(" "), White(" "), White(" "), White(" ")))

  def fromString(input: String, answer: String)(allWords: AllWords): Either[ErrorMsg, Guess] = {
    for {
      validatedInput <- validateWord(input.toLowerCase)(allWords)
      validatedAnswer <- validateWord(answer.toLowerCase)(allWords)
      answerSymbols: Array[String] = validatedAnswer.split("")
      tempResult: List[Letter] = validatedInput
        .split("")
        .zipWithIndex
        .map {
          case (symbol, index) =>
            if (answerSymbols(index) == symbol) Green(symbol)
            else if (answerSymbols.contains(symbol)) Yellow(symbol)
            else Grey(symbol)
        }.toList
      result: List[Letter] = tempResult.map {
        case Yellow(symbol) =>
          if (tempResult.contains(Green(symbol))) Grey(symbol)
          else Yellow(symbol)
        case notYellow => notYellow
      }
    } yield Guess(result)
  }
}