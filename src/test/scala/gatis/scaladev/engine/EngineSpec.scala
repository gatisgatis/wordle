package gatis.scaladev.engine

import gatis.scaladev.engine.Constants.allWords
import gatis.scaladev.engine.Utils.{getDataFromTxt, validateWord}
import munit.FunSuite

class EngineSpec extends FunSuite {

  // ASK. Kā būt drošam, ka testi nosedz visu?

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
    assert(validateWord(tooLongWord)(allWords).isLeft)
    val tooShortWord = "z"
    assert(validateWord(tooShortWord)(allWords).isLeft)
    val fiveLetterWordNotInList = "zebrr"
    assert(validateWord(fiveLetterWordNotInList)(allWords).isLeft)
    val legitWord = "zebra"
    assertEquals(validateWord(legitWord)(allWords), Right("zebra"))
  }

  test("constructs guess (standard case)") {
    // input - "zebra", answer - "rumba"
    val expectedGuess: Guess = Guess(List(Grey("z"), Grey("e"), Yellow("b"), Yellow("r"), Green("a")))
    val result = Guess.fromString(input = "zebra", answer = "rumba")(allWords)
    assertEquals(expectedGuess, result.getOrElse(Guess.empty))
  }

  test("constructs guess (if same letter in green and yellow position)") {
    // should not show yellow if green is found
    // input - "anima", answer - "rumba"
    val expectedGuess: Guess = Guess(List(Grey("a"), Grey("n"), Grey("i"), Yellow("m"), Green("a")))
    val result = Guess.fromString(input = "anima", answer = "rumba")(allWords)
    assertEquals(expectedGuess, result.getOrElse(Guess.empty))
  }

  test("provides error msg if constructing guess with invalid input") {
    val guessInvalidInput = Guess.fromString(input = "sdas", answer = "rumba")(allWords)
    val guessInvalidAnswer = Guess.fromString(input = "zebra", answer = "sdaf")(allWords)
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
      Guess.fromString("zebra", "rumba")(allWords).getOrElse(Guess.empty),
      Guess.fromString("abear", "rumba")(allWords).getOrElse(Guess.empty)
    ), InProgress, Right("rumba"), allWords)
    val result = gameRound.keyboard

    assertEquals(expectedKeyboard, result)
  }

  test("plays full game for known answer") {

    val initialGameRound = GameRound(Nil, InProgress, Right("super"), allWords)

    val finished = (for {
      gr1 <- initialGameRound.playTurn("suing")
      gr2 <- gr1.playTurn("super")
    } yield gr2).getOrElse(initialGameRound)

    assert(finished.status == Won)
    assert(finished.guesses.size == 2)

  }

  // TODO plays game and looses
  // TODO plays game with error inputs
  //
}


