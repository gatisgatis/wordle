package gatis.scaladev.solver

import gatis.scaladev.engine.{GameRound, Guess}
import gatis.scaladev.engine.Utils.AllWords

import scala.util.{Random, Try}

object Solver {

  def selectRandomFirstWord(all: AllWords): String = {
    val random = new Random
    all.value(random.nextInt(all.value.length))
  }

//  def solve(startingWord: String = selectRandomFirstWord(allWords), gameRound: GameRound): Unit = {
//    ???
//  }

  def chooseNextGuess(gameRound: GameRound, allWords: AllWords): String = {
    // checks keyboard and selects only white letters if it's not a 6th turn
    // goes through all words and trys to find a word which is most suitable for these letters
    // on the 6th guess goes through all the words and finds only those which greens and yellows match...
    ???
  }

}
