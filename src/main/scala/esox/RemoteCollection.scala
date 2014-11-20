package esox

import esox.modops.{Filtered, Mapped, Sliced}
import scala.collection.Traversable

class RemoteCollection[A]{

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
  length
  reduce
  find
  exists
  count
  isEmpty
  foreach
  get - returns full collection
  */
}

class BaseRemoteCollection[A](protected val localCollection: Traversable[A]) extends RemoteCollection[A]{
  def rem = this
}

package modops{

  sealed trait ModifiedRemoteCollection[A] extends RemoteCollection[A]{
    val inrRC: RemoteCollection[A]
  }

  case class Filtered[A](inrRC: RemoteCollection[A], f: A => Boolean) extends ModifiedRemoteCollection[A]

  case class Sliced[A](inrRC: RemoteCollection[A], from: Int, to: Int) extends ModifiedRemoteCollection[A]

  case class Mapped[A, B](inrRC: RemoteCollection[A], f: A => B) extends ModifiedRemoteCollection[A]

}