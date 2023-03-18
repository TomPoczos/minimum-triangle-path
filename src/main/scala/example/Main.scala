package example

import scala.annotation.tailrec
import scala.io.Source
import scala.util.chaining.scalaUtilChainingOps

import cats._
import cats.data._
import cats.syntax.all._

object Main extends App {
  Source.stdin.pipe(preprocessSource).pipe(processGraph).pipe {
    case Left(error) => println(error)
    case Right(minimumPath) => println(s"Minimal path is: ${minimumPath.mkString(" + ")} = ${minimumPath.sum}")
  }

  def preprocessSource(source: Source): List[List[Int]] =
    source.getLines().toList.map(
      _.split(" ").toList.map(_.toInt))

  def processGraph(graph: List[List[Int]]): Either[String, List[Int]] =
    graph match {
      case Nil => "there are no paths in an empty triangle".asLeft
      case bottom :: rest => rest.foldLeft(bottom.map(List(_))) {
        case (previousRow, row) => processRow(List.empty, previousRow, row)
      }.flatMap(_.reverse).asRight
    }

  @tailrec
  def processRow(accumulator: List[List[Int]], previousRow: List[List[Int]], row: List[Int]): List[List[Int]] = {
    (previousRow, row) match {
      case (fstPathPrevRow :: sndPathPrevRow :: restOfPrevRow, fstValueRow :: restOfRow) =>
        val appended = if (fstPathPrevRow.sum < sndPathPrevRow.sum) fstPathPrevRow else sndPathPrevRow
        processRow(accumulator :+ (appended :+ fstValueRow), sndPathPrevRow :: restOfPrevRow, restOfRow)
      case (_, Nil) => accumulator
    }
  }
}

