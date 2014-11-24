package tests

import org.scalatest.FunSuite
import scala.util.Try
import esox._
import esox.termops.{GetLength, TerminalOperation}

class Tests extends FunSuite{

  val simpleList = List(1,2,3,4,5)
  implicit val performer = new Performer {
    override def performSafe[A, B](op: TerminalOperation[A, B]): Try[B] = Try(op.result)
  }

  test("Not empty with 2 elements"){
    val collection = simpleList.rem.filter(_ > 2).map(_ + "!")
    assert(collection.isEmpty === false)
    assert(collection.length === 3)
  }

}
