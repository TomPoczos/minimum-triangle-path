package example

import example.Main.{preprocessSource, processGraph}
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.matchers.must.Matchers
import org.scalatest.time.Span
import org.scalatest.time.SpanSugar._
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source
import scala.util.chaining.scalaUtilChainingOps

import cats._
import cats.data._
import cats.syntax.all._

class MainSpec extends AnyWordSpec with Matchers with TimeLimitedTests {

  // reliably passes on my PC but this might be dependent on hardware (I'm currently on an 8 years old Intel i7)
  // feel free to increase to what you would expect on your PC
  override def timeLimit: Span = 1.minute

  "preprocessSource" must {
    "return a relevant error" when {
      "the graph contains characters other than integers" in {
        val source = Source.fromString(
          """|1
             |2 X
             |4 5 6
             |""".stripMargin
        )
        val expected = "graphs can only contain integers".asLeft

        Main.preprocessSource(source) mustBe expected
      }
    }

    "create a nested list of integers from a data source" in {
      val source = Source.fromString(
        """|1
           |2 3
           |4 5 6
           |""".stripMargin
      )
      val expected = List(
        List(1),
        List(2, 3),
        List(4, 5, 6),
      ).asRight[String]

      Main.preprocessSource(source) mustBe expected
    }
  }

  "processRow" when {
    "provided with a left value instead of valid previous row" must {
      "return the provided left value" in {
        val error = "test error".asLeft
        Main.processRow(List.empty, error, List.empty) mustBe error
      }
    }
    "provided with paths in the previous row" must {
      "return a list of minimum paths for the given row's elements" in {
        val row = List(100, 200, 300)
        val previousRow = List(
          List(1, 2, 3),
          List(10, 11, 12),
          List(7, 8, 9),
          List(4, 5, 6),
        ).asRight[String]

        val expected = List(
          List(1, 2, 3, 100),
          List(7, 8, 9, 200),
          List(4, 5, 6, 300),
        ).asRight[String]

        Main.processRow(List.empty, previousRow, row) mustBe expected
      }
    }
  }

  "processGraph" must {
    "be able to handle single row graphs" in {
      Main.processGraph(List(List(1))) mustBe List(1).asRight[String]
    }

    "replace all values by the minimum path up to their nodes" in {
      val graph = List(
        List(1),
        List(2, 3).reverse,
        List(4, 5, 6),
        List(7, 8, 9, 10).reverse,
      )

      Main.processGraph(graph) mustBe List(1, 2, 6, 7).asRight[String]
    }

    "not choke on large graphs" in {
      Source.fromResource("data_big.txt").pipe(preprocessSource).flatMap(processGraph).pipe(println)
    }
  }
}
