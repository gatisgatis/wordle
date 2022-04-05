package gatis.scaladev.engine

import gatis.scaladev.engine.Utils.{AllWords, Answers, getDataFromTxt, validateWord}
import munit.FunSuite

class EngineSpec extends FunSuite {

  implicit val allWords: AllWords = AllWords(getDataFromTxt("all_words.txt"))

  test("check if testing works fine (fake test)") {
    val output = true
    assertEquals(output, true)
  }

  test("can get all legitimate words from txt") {
    val expectedFirstWordInAllWords = "aahed"
    val totalWordsInAllWords = 12972
    val w = getDataFromTxt("all_words.txt")
    assertEquals(expectedFirstWordInAllWords, w.head)
    assertEquals(totalWordsInAllWords, w.size)
  }

  test("can get all answers from txt") {
    val expectedFirstWordInAnswers = "aback"
    val totalWordsInAnswers = 2315
    val a = getDataFromTxt("answers.txt")
    assertEquals(expectedFirstWordInAnswers, a.head)
    assertEquals(totalWordsInAnswers, a.size)
  }

  test("validates player's guess") {
    // should be exactly 5 letters
    // should be in all words list
    val tooLongWord = "zebraa"
    assert(validateWord(tooLongWord).isLeft)
    val tooShortWord = "z"
    assert(validateWord(tooShortWord).isLeft)
    val fiveLetterWordNotInList = "zebrr"
    assert(validateWord(fiveLetterWordNotInList).isLeft)
    val legitWord = "zebra"
    assertEquals(validateWord(legitWord), Right("zebra"))
  }

  test("constructs guess (standard case)") {
    // input - "zebra", answer - "rumba"
    val expectedGuess: Guess = Guess(List(Grey("z"), Grey("e"), Yellow("b"), Yellow("r"), Green("a")))
    val result = Guess.fromString(input = "zebra", answer = "rumba")
    assertEquals(expectedGuess, result.getOrElse(Guess.empty))
  }

  test("constructs guess (if same letter in green and yellow position)") {
    // should not show yellow if green is found
    // input - "anima", answer - "rumba"
    val expectedGuess: Guess = Guess(List(Grey("a"), Grey("n"), Grey("i"), Yellow("m"), Green("a")))
    val result = Guess.fromString(input = "anima", answer = "rumba")
    assertEquals(expectedGuess, result.getOrElse(Guess.empty))
  }

  test("provides error msg if constructing guess with invalid input") {
    val guessInvalidInput = Guess.fromString(input = "sdas", answer = "rumba")
    val guessInvalidAnswer = Guess.fromString(input = "zebra", answer = "sdaf")
    assert(guessInvalidInput.isLeft)
    assert(guessInvalidAnswer.isLeft)
  }

  // TODO. More keyboard tests
  test("creates keyboard") {
    // input - "zebra", input2 - "abear", answer - "rumba"
    val expectedKeyboard: List[Letter] = List(
      Green("a"), Yellow("b"), White("c"), White("d"), Grey("e"),
      White("f"), White("g"), White("h"), White("i"), White("j"),
      White("k"), White("l"), White("m"), White("n"), White("o"),
      White("p"), White("q"), Yellow("r"), White("s"), White("t"),
      White("u"), White("v"), White("w"), White("x"), White("y"),
      Grey("z"))
    val gameRound = GameRound(List(
      Guess.fromString("zebra", "rumba").getOrElse(Guess.empty),
      Guess.fromString("abear", "rumba").getOrElse(Guess.empty)
    ), InProgress, Right("rumba"))
    val result = gameRound.updateKeyboard

    assertEquals(expectedKeyboard, result)
  }

  test("plays game and wins") {

    val initialGameRound = GameRound(Nil, InProgress, Right("super"))

    val finished = (for {
      gr1 <- initialGameRound.playTurn("suing")
      gr2 <- gr1.playTurn("super")
    } yield gr2).getOrElse(initialGameRound)

    assert(finished.status == Won)
    assert(finished.guesses.size == 2)

  }

  test("creates correct guess if same symbol in green and yellow position") {

    val initialGameRound = GameRound(Nil, InProgress, Right("adapt"))

    val finished = (for {
      gr1 <- initialGameRound.playTurn("suing")
      gr2 <- gr1.playTurn("agora")
    } yield gr2).getOrElse(initialGameRound)

    assert(finished.status == InProgress)
    assert(finished.guesses.size == 2)
    assertEquals(finished.guesses(1), Guess(List(Green("a"), Grey("g"), Grey("o"), Grey("r"), Yellow("a"))))

  }

  // TODO plays game and looses
  // TODO plays game with error inputs
  //
}


