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
  val lines = Source.fromResource("data_big.txt")
  val preprocessed = preprocessSource(lines)
    println(preprocessed.pipe(processGraph))

  def preprocessSource(source: Source): List[List[Int]] =
    source
      .getLines()
      .toList
      .reverse
      .map(
        _.split(" ")
          .toList
          //          .map(value => List(value.toInt))
          .map(_.toInt)
      )

  def processGraph(graph: List[List[Int]]) = {

    val zeroes = List.fill(graph.head.size + 1)(0).map(zero => List(zero))

    val allPaths = graph.foldLeft(zeroes) {
      case ((previousRow, row)) =>
        processRow(List.empty, previousRow, row.map(x => x :: Nil))
    }

    allPaths.flatMap(_.tail.reverse)

  }

  @tailrec
  def processRow(accumulator: List[List[Int]], previousRow: List[List[Int]], row: List[List[Int]]): List[List[Int]] = {
    (previousRow, row) match {
      case (fstPathPrevRow :: sndPathPrevRow :: restOfPrevRow, fstPathRow :: restOfRow) =>
        if (fstPathPrevRow.sum < sndPathPrevRow.sum)
          processRow(
            accumulator :+ (fstPathPrevRow ::: fstPathRow),
            sndPathPrevRow :: restOfPrevRow,
            restOfRow
          )
        else
          processRow(
            accumulator :+ (sndPathPrevRow ::: fstPathRow),
            sndPathPrevRow :: restOfPrevRow,
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
