package gatis.scaladev.cliapp

import cats.effect.Sync

import scala.io.StdIn

trait Console[F[_]] {
  def putStrLn(value: String): F[Unit]
  def readStrLn: F[String]
}

object Console {
  def create[F[_] : Sync]: Console[F] = new Console[F] {
    def putStrLn(value: String): F[Unit] = Sync[F].delay(println(value))
    def readStrLn: F[String] = Sync[F].delay(StdIn.readLine())
  }
}



