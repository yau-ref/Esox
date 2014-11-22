package main.scala.esox

import esox.RemoteCollection
import esox.termops.RCTerminalOperation

import scala.util.Try

trait Performer {

  def perform[A, B](op: RCTerminalOperation[A]): Try[B]

}