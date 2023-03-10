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
        List(1),
        List(2, 3),
        List(4, 5, 6)
      )
      Hello.preprocessSource(source) mustBe expected
    }
  }

}
