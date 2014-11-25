package esox

import esox.modops.{Filtered, Mapped, Sliced}
import esox.termops._

import scala.collection.Traversable
import scala.reflect.ClassTag

abstract class RemoteCollection[A: ClassTag] {

  // apply all modifications
  private[esox] def data: Traversable[A]

  def performer: Performer

  def filter(f: A => Boolean): Filtered[A] = Filtered(this, f)

  def slice(from: Int, to: Int): Sliced[A] = Sliced(this, from, to)

  def take(n: Int): Sliced[A] = slice(0, n)

  def drop(n: Int): Sliced[A] = slice(n, -1)

  def map[B: ClassTag](f: A => B): Mapped[A, B] = Mapped(this, f)

  def length: Int = performer.perform(GetLength(this))

  def isEmpty: Boolean = performer.perform(IsEmpty(this))

  def exists(p: A => Boolean): Boolean = performer.perform(Exists(this, p))

  def find(p: A => Boolean): Option[A] = performer.perform(Find(this, p))

  def count(p: A => Boolean): Int = performer.perform(Count(this, p))

  def reduce[B >: A : ClassTag](f: (B, A) => B): B = performer.perform(Reduce(this, f))

  // returns full collection
  def get: Traversable[A] = performer.perform(GetBack(this))

  def toArray: Array[A] = get.toArray

  def toList: List[A] = get.toList

  def foreach(f: A => Unit): Unit = get.foreach(f)


  /* TODO: implements this methods:
  flatMap
  groupBy
  distinct
  */
}

class BaseRemoteCollection[A: ClassTag](val localCollection: Traversable[A])
                                       (override implicit val performer: Performer) extends RemoteCollection[A] {

  override def data: Traversable[A] = localCollection

}