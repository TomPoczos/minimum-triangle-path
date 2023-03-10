package example

import cats._
import cats.data._
import cats.syntax.all._

// todo change IO to read from standard input at the end
object Hello extends Greeting with App {
  println(greeting)
}

trait Greeting {
  lazy val greeting: String = "hello"
}
