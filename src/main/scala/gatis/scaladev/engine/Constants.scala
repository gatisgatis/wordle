package gatis.scaladev.engine

import gatis.scaladev.engine.Utils.{AllWords, Answers, getDataFromTxt}

object Constants {
  val answers: Answers = Answers(getDataFromTxt("answers.txt"))
  val allWords: AllWords = AllWords(getDataFromTxt("all_words.txt"))
}
