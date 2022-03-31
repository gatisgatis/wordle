package gatis.scaladev.codecs

import gatis.scaladev.engine.{GameRound, Guess, Letter}
import io.circe.Encoder
import io.circe.syntax.EncoderOps

// Not used for cli app
object Codecs {

  implicit val letterE: Encoder[Letter] =
    Encoder.forProduct2("letter", "color")(l => (l.value, l.toString))

  // TODO Decoder!?

  implicit val guessE: Encoder[Guess] = (a: Guess) => a.value.asJson

  implicit val gameRoundE: Encoder[GameRound] =
    Encoder.forProduct3("guesses", "status", "keyboard")(gr => (gr.guesses, gr.status.toString, gr.keyboard))
}
