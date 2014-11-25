package esox

import esox.modops.{Filtered, Mapped, Sliced}
import esox.termops._

import scala.collection.Traversable
import scala.reflect.ClassTag

abstract class RemoteCollection[A : ClassTag] {

  private[esox] def data: Traversable[A]

  def performer: Performer

  def filter(f: A => Boolean): Filtered[A] = Filtered(this, f)

  def slice(from: Int, to: Int): Sliced[A] = Sliced(this, from, to)

  def take(n: Int): Sliced[A] = slice(0, n)

  def drop(n: Int): Sliced[A] = slice(n, -1)

  def map[B : ClassTag](f: A => B): Mapped[A, B] = Mapped(this, f)

  def length = performer.perform[A, Int](GetLength(this))

  def isEmpty = performer.perform[A, Boolean](IsEmpty(this))

  def exists(p: A => Boolean) = performer.perform[A, Boolean](Exists(this, p))

  def find(p: A => Boolean) = performer.perform[A, Option[A]](Find(this, p))

  def count(p: A => Boolean) = performer.perform[A, Int](Count(this, p))

  def reduce[B >: A : ClassTag](f: (B, A) => B) = performer.perform[A, B](Reduce(this, f))

  // returns full collection
  def get = performer.perform[A, Traversable[A]](GetBack(this))

  def toArray = get.toArray

  def toList = get.toList

  def foreach(f: A => Unit): Unit = get.foreach(f)


  /* TODO: implements this methods:
  flatMap
  groupBy
  distinct
  */
}

class BaseRemoteCollection[A : ClassTag](val localCollection: Traversable[A])
                             (override implicit val performer: Performer) extends RemoteCollection[A] {

  override def data: Traversable[A] = localCollection

}