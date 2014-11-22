package main.scala.esox

import esox.RemoteCollection

import scala.util.Try

trait Performer {

  def perform[T](collection: RemoteCollection): Try[T]

}