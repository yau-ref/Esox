package esox

import esox.modops.{Mapped, Sliced, Filtered}
import esox.termops.{IsEmpty, GetLength}

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

package termops {

sealed trait TerminalOperation[A] {
  val collection: RemoteCollection[A]
  def result: _
}

case class GetLength[A](collection: RemoteCollection[A]) extends TerminalOperation[A] {
  def result = collection.data.size
}

case class Count[A](collection: RemoteCollection[A], p: A => Boolean) extends TerminalOperation[A] {
  def result = collection.data.count(p)
}

case class Exists[A](collection: RemoteCollection[A], p: A => Boolean) extends TerminalOperation[A] {
  def result = collection.data.exists(p)
}

case class IsEmpty[A](collection: RemoteCollection[A]) extends TerminalOperation[A] {
  def result = collection.data.isEmpty
}

case class Reduce[A, B >: A](collection: RemoteCollection[A], f: (A, B) => B) extends TerminalOperation[A] {
  def result = collection.data.reduce(f)
}

case class Find[A](collection: RemoteCollection[A], p: A => Boolean) extends TerminalOperation[A] {
  def result = collection.data.find(p)
}

case class GetBack[A](collection: RemoteCollection[A]) extends TerminalOperation[A] {
  def result = collection.data
}

}