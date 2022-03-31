package gatis.scaladev.cli.prettier

import gatis.scaladev.cli.prettier.PrettierSyntax.PrettierOps
import gatis.scaladev.engine.{GameRound, Green, Grey, Guess, Letter, White, Yellow}

object Instances {

  implicit val letterPrettier: Prettier[Letter] = {
    case Green(value) => Console.GREEN_B + Console.BLACK + s" ${value.toUpperCase} " + Console.RESET
    case Yellow(value) => Console.YELLOW_B + Console.BLACK + s" ${value.toUpperCase} " + Console.RESET
    case Grey(value) => Console.RED_B + Console.BLACK + s" ${value.toUpperCase} " + Console.RESET
    case White(value) => Console.WHITE_B + Console.BLACK + s" ${value.toUpperCase} " + Console.RESET
  }

  implicit val guessPrettier: Prettier[Guess] = { guess =>
    guess.value.foldLeft("")((result, l) => result + " " + l.toFancyString)
  }

  implicit val gameRoundPrettier: Prettier[GameRound] = (gr: GameRound) => {
    val filledBoardString = gr.guesses.foldLeft("")((result, g) => result + g.toFancyString + "\n\n")

    val emptyBoard = List(Guess.empty, Guess.empty, Guess.empty, Guess.empty, Guess.empty, Guess.empty)
    val restOfBoard = emptyBoard.slice(0, 6 - gr.guesses.size)
    val restOfBoardString = restOfBoard.foldLeft("")((result, g) => result + g.toFancyString + "\n\n")

    val k1 = gr.keyboard.slice(0, 9)
    val k2 = gr.keyboard.slice(9, 18)
    val k3 = gr.keyboard.slice(18, gr.keyboard.size)
    val keyboardString = k1.foldLeft("")((result, l) => result + " " + l.toFancyString) + "\n\n" +
      k2.foldLeft("")((result, l) => result + " " + l.toFancyString) +
      "\n\n" + k3.foldLeft("")((result, l) => result + " " + l.toFancyString) + "\n"

    val separator = "-"*40+"\n\n"

    filledBoardString + restOfBoardString + separator + keyboardString
  }

}
