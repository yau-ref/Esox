package esox

import esox.termops.TerminalOperation
import scala.util.{Failure, Success, Try}

trait Performer {

  def performSafe[A, B](op: TerminalOperation[A, B]): Try[B]

  def perform[A, B](op: TerminalOperation[A, B]) = performSafe(op) match {
    case Success(res) => res
    case Failure(e) => throw e
  }

}