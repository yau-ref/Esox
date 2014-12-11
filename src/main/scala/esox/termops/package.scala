package esox

import scala.reflect.ClassTag

package object termops {

  abstract class TerminalOperation[A: ClassTag, B: ClassTag] extends Serializable {

    val collection: RemoteCollection[A]

    def result: B

  }

  case class GetLength[A: ClassTag](collection: RemoteCollection[A]) extends TerminalOperation[A, Int] {
    def result = collection.data.size
  }

  case class Count[A: ClassTag](collection: RemoteCollection[A], p: A => Boolean) extends TerminalOperation[A, Int] {
    def result = collection.data.count(p)
  }

  case class Exists[A: ClassTag](collection: RemoteCollection[A], p: A => Boolean) extends TerminalOperation[A, Boolean] {
    def result = collection.data.exists(p)
  }

  case class IsEmpty[A: ClassTag](collection: RemoteCollection[A]) extends TerminalOperation[A, Boolean] {
    def result = collection.data.isEmpty
  }

  case class Reduce[A: ClassTag, B >: A : ClassTag](collection: RemoteCollection[A], f: (B, A) => B) extends TerminalOperation[A, B] {
    def result = collection.data.reduceLeft(f)
  }

  case class Find[A: ClassTag](collection: RemoteCollection[A], p: A => Boolean) extends TerminalOperation[A, Option[A]] {
    def result = collection.data.find(p)
  }

  case class GetBack[A: ClassTag](collection: RemoteCollection[A]) extends TerminalOperation[A, Traversable[A]] {
    def result = collection.data
  }

}