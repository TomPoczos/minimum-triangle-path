package example

import scala.annotation.tailrec
import scala.collection.:+
import scala.io.Source
import scala.util.chaining.scalaUtilChainingOps

object Main extends App {


  val minimumPath = Source.stdin.pipe(preprocessSource).pipe(processGraph)
  println(s"Minimal path is: ${minimumPath.mkString(" + ")} = ${minimumPath.sum}")

  def preprocessSource(source: Source): List[List[Int]] =
    source.getLines().toList.map(
      _.split(" ").toList.map(_.toInt))

  def processGraph(graph: List[List[Int]]): List[Int] =
    graph match {
      case bottom :: rest => rest.foldLeft(bottom.map(List(_))) {
        case (previousRow, row) => processRow(List.empty, previousRow, row)
      }.flatMap(_.reverse)
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

