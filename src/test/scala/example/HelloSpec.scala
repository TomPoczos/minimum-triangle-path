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
          List(10),
          List(20),
          List(30),
        )
        val previousRow = List(
          List(1, 2),
          List(7, 8),
          List(5, 6),
          List(3, 4),
        )

        val expected = List(
          List(1, 2, 10),
          List(5, 6, 20),
          List(3, 4, 30),
        )

        Hello.processRow(List.empty, previousRow, row) mustBe expected
      }
    }
  }

}
