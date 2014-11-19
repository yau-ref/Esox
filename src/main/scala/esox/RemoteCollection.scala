package esox

import esox.modops.{Sliced, Filtered}

import scala.collection.generic.CanBuildFrom

class RemoteCollection[T](protected val collection: Traversable[T]){

  def rem = this

  def filter(f: T => Boolean) = Filtered(this, f)

  def slice(from: Int, to: Int) = Sliced(this, from, to)

  def take(n: Int) = slice(0, n)

  def drop(n: Int) = slice(n, -1)

  /* TODO: implements this methods:

  # Modification:
  filter
  slice
  take
  drop

  groupBy
  distinct
  map
  flatMap

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

  case class Filtered[T](rc: RemoteCollection[T], f: T => Boolean)

  case class Sliced[T](rc: RemoteCollection[T], from: Int, to: Int)

}