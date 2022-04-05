package gatis.scaladev.engine

import scala.io.Source
import scala.util.{Failure, Random, Success, Try, Using}

object Utils {

  def getDataFromTxt(name: String): List[String] = {
    Using(Source.fromResource(name))(_.getLines().toList).getOrElse(Nil)
  }

  final case class ErrorMsg(value: String) extends AnyVal

  final case class Answers(value: List[String]) extends AnyVal

  final case class AllWords(value: List[String]) extends AnyVal

  def validateWord(word: String)(implicit allWords: AllWords): Either[ErrorMsg, String] = {
    if (word.length != 5) Left(ErrorMsg("Provided word is not a 5 letter word"))
    else if (allWords.value.contains(word)) Right(word)
    else Left(ErrorMsg("Provided word is not in the legitimate word list"))
  }

  def selectAnswer(answers: Answers): Either[ErrorMsg, String] = {
    val random = new Random
    Try(random.nextInt(answers.value.length)).map(rnd => answers.value(rnd)).toEither match {
      case Left(_) => Left(ErrorMsg("Error selecting answer"))
      case Right(x) => Right(x)
    }

  }
}
