package gatis.scaladev.engine

import gatis.scaladev.engine.Utils.{AllWords, validateWord, ErrorMsg}

final case class Guess(value: List[Letter])

object Guess {
  val empty: Guess = Guess(value = List(White(" "), White(" "), White(" "), White(" "), White(" ")))

  def fromString(input: String, answer: String)(implicit allWords: AllWords): Either[ErrorMsg, Guess] = {
    for {
      validatedInput <- validateWord(input.toLowerCase)(allWords)
      validatedAnswer <- validateWord(answer.toLowerCase)(allWords)
      answerSymbols: Array[String] = validatedAnswer.split("")
      inputSymbols: Array[String] = validatedInput.split("")
      tempResult: List[Letter] = inputSymbols
        .zipWithIndex
        .map {
          case (symbol, index) =>
            if (answerSymbols(index) == symbol) Green(symbol)
            else if (answerSymbols.contains(symbol)) Yellow(symbol)
            else Grey(symbol)
        }.toList
      result: List[Letter] = tempResult.map {
        case Yellow(symbol) =>
          val thisSymbolInInputCount = inputSymbols.count(_ == symbol)
          val thisSymbolInAnswerCount = answerSymbols.count(_ == symbol)
          if (tempResult.contains(Green(symbol)) && thisSymbolInAnswerCount < thisSymbolInInputCount) Grey(symbol)
          else Yellow(symbol)
        case notYellow => notYellow
      }
    } yield Guess(result)
  }
}