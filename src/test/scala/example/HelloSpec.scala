package example

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source

class HelloSpec extends AnyWordSpec with Matchers {

  "preprocessSource" must {
    "create a nested list of integers from a data source" in {
      val source = Source.fromString(
        """|1
           |2 3
           |4 5 6
           |""".stripMargin
      )
      val expected = List(
        List(List(4), List(5), List(6)),
        List(List(2), List(3)),
        List(List(1)),
      )
      Hello.preprocessSource(source) mustBe expected
    }
  }

  "processRow" when {
    "provided with paths in the previous row" must {

      "return a list of minimum paths for the given row's elements" in {
        val row = List(
          List(100),
          List(200),
          List(300),
        )
        val previousRow = List(
          List(1, 2, 3),
          List(10, 11, 12),
          List(7, 8, 9),
          List(4, 5, 6),
        )

        val expected = List(
          List(1, 2, 3, 100),
          List(7, 8, 9, 200),
          List(4, 5, 6, 300),
        )

        Hello.processRow(List.empty, previousRow, row) mustBe expected
      }
    }
  }

  "processGraph" must {
    "replace all values by the minimum path up to their nodes" in {
      val graph = List(
        List(List(7), List(8), List(9), List(10)).reverse,
        List(List(4), List(5), List(6)),
        List(List(2), List(3)).reverse,
        List(List(1)),
      )

      Hello.processGraph(graph) mustBe List(1, 2, 6, 7)
    }
  }

}
