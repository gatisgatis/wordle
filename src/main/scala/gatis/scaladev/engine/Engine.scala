package gatis.scaladev.engine

import scala.io.Source


object Engine extends App {

  def getFromTxt(name: String): List[String] = {
    // TODO this should not be hardcoded
    val path = "/Users/gatismeikulis/IdeaProjects/wordle-engine/src/main/resources/" + name
    val bufferedSource = Source.fromFile(path)
    val lines = bufferedSource.getLines().toList
    bufferedSource.close
    lines
  }

}
