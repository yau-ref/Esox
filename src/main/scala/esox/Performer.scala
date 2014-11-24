package main.scala.esox

import esox.RemoteCollection
import esox.termops.TerminalOperation

import scala.util.Try

trait Performer {

  def perform[A, B](op: TerminalOperation[A]): Try[B]

}