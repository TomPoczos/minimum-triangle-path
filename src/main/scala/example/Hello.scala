package example

import cats._
import cats.data._
import cats.syntax.all._
object Hello extends Greeting with App {
  println(greeting)
}

trait Greeting {
  lazy val greeting: String = "hello"
}
