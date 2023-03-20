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
    graph.reverse match {
      case Nil => "there are no paths in an empty graph".asLeft
      case bottom :: rest => rest.foldLeft(bottom.map(List(_)).asRight[String]) {
        case (previousRow, row) => processRow(List.empty, previousRow, row)
      }.map(_.flatMap(_.reverse))
    }

  @tailrec
  def processRow(accumulator: List[List[Int]], previousRow: Either[String, List[List[Int]]], row: List[Int]): Either[String, List[List[Int]]] = {
    (previousRow, row) match {
      case (error@Left(_), _) => error
      case (_, Nil) => accumulator.asRight
      case (Right(fstPathPrevRow :: sndPathPrevRow :: restOfPrevRow), fstValueRow :: restOfRow) =>
        val appended = if (fstPathPrevRow.sum < sndPathPrevRow.sum) fstPathPrevRow else sndPathPrevRow
        processRow(accumulator :+ (appended :+ fstValueRow), (sndPathPrevRow :: restOfPrevRow).asRight[String], restOfRow)
      case _ => "Malformed graph".asLeft
    }
  }
}

