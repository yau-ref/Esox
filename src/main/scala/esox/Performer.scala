package esox

import esox.termops.TerminalOperation

import scala.reflect.ClassTag
import scala.util.{Failure, Success, Try}

trait Performer {

  def performSafe[A: ClassTag, B: ClassTag](op: TerminalOperation[A, B]): Try[B]

  def perform[A: ClassTag, B: ClassTag](op: TerminalOperation[A, B]) = performSafe(op) match {
    case Success(res) => res
    case Failure(e) => throw e
  }

}