package example

import scala.annotation.tailrec
import scala.collection.:+
import scala.io.Source
import scala.util.chaining.scalaUtilChainingOps

object Main extends App {

  val minimumPath = Source.fromFile(args(0)).pipe(preprocessSource).pipe(processGraph)
  println(s"Minimal path is: ${minimumPath.mkString(" + ")} = ${minimumPath.sum}")

  def preprocessSource(source: Source): List[List[Int]] =
    source
      .getLines()
      .toList
      .reverse
      .map(
        _.split(" ")
          .toList
          .map(_.toInt)
      )

  def processGraph(graph: List[List[Int]]) = {

    val zeroes = List.fill(graph.head.size + 1)(0).map(zero => List(zero))

    graph.foldLeft(zeroes) {
      case (previousRow, row) => processRow(List.empty, previousRow, row)
    }.flatMap(_.tail.reverse) // this line removes the "artificial" zero

  }

  @tailrec
  def processRow(accumulator: List[List[Int]], previousRow: List[List[Int]], row: List[Int]): List[List[Int]] = {
    (previousRow, row) match {
      case (fstPathPrevRow :: sndPathPrevRow :: restOfPrevRow, fstValueRow :: restOfRow) =>
        if (fstPathPrevRow.sum < sndPathPrevRow.sum)
          processRow(
            accumulator :+ (fstPathPrevRow :+ fstValueRow),
            sndPathPrevRow :: restOfPrevRow,
            restOfRow
          )
        else
          processRow(
            accumulator :+ (sndPathPrevRow :+ fstValueRow),
            sndPathPrevRow :: restOfPrevRow,
            restOfRow
          )
      case (_, Nil) => accumulator
    }
  }
}

