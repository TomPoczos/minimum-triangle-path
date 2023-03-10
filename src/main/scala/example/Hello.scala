package example

import cats._
import cats.data._
import cats.syntax.all._

import scala.annotation.tailrec
import scala.collection.:+
import scala.io.Source
import scala.util.chaining.scalaUtilChainingOps

// todo change IO to read from standard input at the end
object Hello extends Greeting with App {
  val lines = Source.fromResource("data_small.txt")
  val preprocessed = preprocessSource(lines)
  println(preprocessed)

  def preprocessSource(source: Source): List[List[List[Int]]] =
    source
      .getLines()
      .toList
      .reverse
      .map(
        _.split(" ")
          .toList
          .map(value => List(value.toInt)))

  def again(graph: List[List[List[Int]]]) = {

    val zeroes = List.fill(0)(graph.head.size + 1).map(zero => List(zero))

    graph.foldLeft((List.empty, zeroes)) {
      case ((accumulator, previousRow), row) => ???
    }
  }

  @tailrec
  def processRow(accumulator: List[List[Int]], previousRow: List[List[Int]], row: List[List[Int]]): List[List[Int]] = {
    (previousRow, row) match {
      case (fstPathPrevRow :: sndPathPrevRow :: restOfprevRow, fstPathRow :: restOfRow) =>
        if (fstPathPrevRow.sum < sndPathPrevRow.sum)
          processRow(
            accumulator :+ (fstPathPrevRow ::: fstPathRow),
            sndPathPrevRow :: restOfprevRow,
            restOfRow
          )
        else
          processRow(
            accumulator :+ (sndPathPrevRow ::: fstPathRow),
            sndPathPrevRow :: restOfprevRow,
            restOfRow
          )
      case (_, Nil) => accumulator
    }
  }

  def messingAround(graph: List[List[Int]]) =
    graph match {
      case (fstValfstRow :: sndValFstRow) :: (fstValSndRow :: _) :: _ => List(fstValfstRow)
    }

}


trait Greeting {
  lazy val greeting: String = "hello"
}
