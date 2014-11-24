package esox

package object termops {

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
