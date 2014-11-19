package esox

import esox.modops.Filtered

import scala.collection.generic.CanBuildFrom

class RemoteCollection[T](protected val collection: Traversable[T]){

  def rem = this

  def filter(f: T => Boolean) = Filtered(this, f)


  /* TODO: implements this methods:

  # Modification:
  filter
  groupBy
  distinct
  take
  drop
  map
  length
  flatMap

  # Extraction:
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

  case class Filtered[T](rc: RemoteCollection[T], f: T => Boolean){
  }

}

package extops{

}