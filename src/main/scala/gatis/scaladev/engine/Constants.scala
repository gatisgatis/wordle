package gatis.scaladev.engine

import gatis.scaladev.engine.Utils.{AllWords, Answers, getDataFromTxt}

object Constants {
  // ASK. Būtu labi šos padarīt kā implicit, lai nebūtu tie vienmēr jāpadod funkcijās.. kaut kas īsti nesanāca...
  val answers: Answers = Answers(getDataFromTxt("answers.txt"))
  val allWords: AllWords = AllWords(getDataFromTxt("all_words.txt"))
}
