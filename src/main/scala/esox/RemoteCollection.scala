package esox

import esox.modops.{Mapped, Sliced, Filtered}

import scala.collection.generic.CanBuildFrom

class RemoteCollection[A](protected val collection: Traversable[A]){

  def rem = this

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

package modops{

  case class Filtered[A](rc: RemoteCollection[A], f: A => Boolean)

  case class Sliced[A](rc: RemoteCollection[A], from: Int, to: Int)

  case class Mapped[A, B](rc: RemoteCollection[A], f: A => B)

}