package esox

import scala.collection.Traversable

package object modops {

  trait ModifiedRemoteCollection[A] extends RemoteCollection[A] {
    val originalCollection: RemoteCollection[_]
    override def performer = originalCollection.performer
  }

  case class Filtered[A](originalCollection: RemoteCollection[A], f: A => Boolean) extends ModifiedRemoteCollection[A] {
    override def data: Traversable[A] = originalCollection.data.filter(f)
  }

  case class Sliced[A](originalCollection: RemoteCollection[A], from: Int, to: Int) extends ModifiedRemoteCollection[A] {
    override def data: Traversable[A] = originalCollection.data.slice(from, to)
  }

  case class Mapped[A, B](originalCollection: RemoteCollection[A], f: A => B) extends ModifiedRemoteCollection[B] {
    override def data: Traversable[B] = originalCollection.data.map(f)
  }

}
