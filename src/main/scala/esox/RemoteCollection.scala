package esox

import esox.modops.{Filtered, Mapped, Sliced}
import main.scala.esox.Performer

import scala.collection.Traversable

abstract class RemoteCollection[A] {

  def performer: Performer

  def filter(f: A => Boolean): Filtered[A] = Filtered(this, f)

  def slice(from: Int, to: Int): Sliced[A] = Sliced(this, from, to)

  def take(n: Int): Sliced[A] = slice(0, n)

  def drop(n: Int): Sliced[A] = slice(n, -1)

  def map[B](f: A => B): Mapped[A, B] = Mapped(this, f)

  /* TODO: implements this methods:
  flatMap
  groupBy
  distinct

  # Extraction:
  length num
  reduce
  find
  exists bool
  count  num
  isEmpty  bool
  foreach
  get - returns full collection
  */
}

class BaseRemoteCollection[A](val localCollection: Traversable[A])
                             (override implicit val performer: Performer) extends RemoteCollection[A] {

}

package modops {

sealed trait ModifiedRemoteCollection[A] extends RemoteCollection[A] {

  val inrRC: RemoteCollection[A]

  override val performer = inrRC.performer

}

case class Filtered[A](inrRC: RemoteCollection[A], f: A => Boolean) extends ModifiedRemoteCollection[A]

case class Sliced[A](inrRC: RemoteCollection[A], from: Int, to: Int) extends ModifiedRemoteCollection[A]

case class Mapped[A, B](inrRC: RemoteCollection[A], f: A => B) extends ModifiedRemoteCollection[A]

}

package termops {

sealed trait RCTerminalOperation[A] {
  val inrRC: RemoteCollection[A]
}

case class GetLength[A](inrRC: RemoteCollection[A]) extends RCTerminalOperation

case class Count[A](inrRC: RemoteCollection[A], p: A => Boolean) extends RCTerminalOperation

case class Exists[A](inrRC: RemoteCollection[A], p: A => Boolean) extends RCTerminalOperation

case class isEmpty[A](inrRC: RemoteCollection[A]) extends RCTerminalOperation

case class Reduce[A, B >: A](inrRC: RemoteCollection[A], f: (A, B) => B) extends RCTerminalOperation

case class Find[A](inrRC: RemoteCollection[A], p: A => Boolean) extends RCTerminalOperation

case class GetBack[A](inrRC: RemoteCollection[A]) extends RCTerminalOperation

}