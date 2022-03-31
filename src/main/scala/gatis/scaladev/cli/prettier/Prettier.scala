package gatis.scaladev.cli.prettier

trait Prettier[T] {
  def toFancyString(entity: T): String
}

object Prettier {
  def apply[T](implicit instance: Prettier[T]): Prettier[T] = instance
}

object PrettierSyntax {
  implicit class PrettierOps[T](x: T) {
    def toFancyString(implicit parser: Prettier[T]): String = parser.toFancyString(x)
  }
}

