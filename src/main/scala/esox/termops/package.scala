package esox

package object termops {

  sealed trait TerminalOperation[A, B] {
    val collection: RemoteCollection[A]
    def result: B
  }

  case class GetLength[A](collection: RemoteCollection[A]) extends TerminalOperation[A, Int] {
    def result = collection.data.size
  }

  case class Count[A](collection: RemoteCollection[A], p: A => Boolean) extends TerminalOperation[A, Int] {
    def result = collection.data.count(p)
  }

  case class Exists[A](collection: RemoteCollection[A], p: A => Boolean) extends TerminalOperation[A, Boolean] {
    def result = collection.data.exists(p)
  }

  case class IsEmpty[A](collection: RemoteCollection[A]) extends TerminalOperation[A, Boolean] {
    def result = collection.data.isEmpty
  }

  case class Reduce[A, B >: A](collection: RemoteCollection[A], f: (B, A) => B) extends TerminalOperation[A, B] {
    def result = collection.data.reduceLeft(f)
  }

  case class Find[A](collection: RemoteCollection[A], p: A => Boolean) extends TerminalOperation[A, Option[A]] {
    def result = collection.data.find(p)
  }

  case class GetBack[A](collection: RemoteCollection[A]) extends TerminalOperation[A, Traversable[A]] {
    def result = collection.data
  }

}
