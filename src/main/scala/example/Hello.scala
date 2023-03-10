package example

import cats._
import cats.data._
import cats.syntax.all._

import scala.io.Source
import scala.util.chaining.scalaUtilChainingOps

// todo change IO to read from standard input at the end
object Hello extends Greeting with App {
  val lines = Source.fromResource("data_small.txt")
  val preprocessed = preprocessSource(lines)
  println(preprocessed)

  def preprocessSource(source: Source): List[List[Int]] =
    source
      .getLines()
      .toList
      .map(
        _.split(" ").toList.map(_.toInt))

}


trait Greeting {
  lazy val greeting: String = "hello"
}
