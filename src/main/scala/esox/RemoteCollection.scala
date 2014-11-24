package esox

import esox.modops.{Filtered, Mapped, Sliced}
import esox.termops.{GetLength, IsEmpty}
import main.scala.esox.Performer

import scala.collection.Traversable

abstract class RemoteCollection[A] {

  //TODO: make it package private
  def data: Traversable[A]

  def performer: Performer

  def filter(f: A => Boolean): Filtered[A] = Filtered(this, f)

  def slice(from: Int, to: Int): Sliced[A] = Sliced(this, from, to)

  def take(n: Int): Sliced[A] = slice(0, n)

  def drop(n: Int): Sliced[A] = slice(n, -1)

  def map[B](f: A => B): Mapped[A, B] = Mapped(this, f)

  def length = performer.perform[A, Int](GetLength(this))

  def isEmpty = performer.perform(IsEmpty(this))

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
  override def data: Traversable[A] = localCollection

}

package modops {

//TODO: rename inrRC
trait ModifiedRemoteCollection[A] extends RemoteCollection[A] {

  val inrRC: RemoteCollection[A]

  override def performer = inrRC.performer

}

case class Filtered[A](inrRC: RemoteCollection[A], f: A => Boolean) extends ModifiedRemoteCollection[A] {

  override def data: Traversable[A] = inrRC.data.filter(f)

}

case class Sliced[A](inrRC: RemoteCollection[A], from: Int, to: Int) extends ModifiedRemoteCollection[A] {

  override def data: Traversable[A] = inrRC.data.slice(from, to)

}

case class Mapped[A, B](inrRC: RemoteCollection[A], f: A => B) extends ModifiedRemoteCollection[B] {

  override def data: Traversable[B] = inrRC.data.map(f)

}

}

package termops {

sealed trait RCTerminalOperation[A] {
  val inrRC: RemoteCollection[A]
}

case class GetLength[A](inrRC: RemoteCollection[A]) extends RCTerminalOperation[A]

case class Count[A](inrRC: RemoteCollection[A], p: A => Boolean) extends RCTerminalOperation[A]

case class Exists[A](inrRC: RemoteCollection[A], p: A => Boolean) extends RCTerminalOperation[A]

case class IsEmpty[A](inrRC: RemoteCollection[A]) extends RCTerminalOperation[A]

case class Reduce[A, B >: A](inrRC: RemoteCollection[A], f: (A, B) => B) extends RCTerminalOperation[A]

case class Find[A](inrRC: RemoteCollection[A], p: A => Boolean) extends RCTerminalOperation[A]

case class GetBack[A](inrRC: RemoteCollection[A]) extends RCTerminalOperation[A]

}