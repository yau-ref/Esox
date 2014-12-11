package esox

import scala.collection.Traversable
import scala.reflect.ClassTag

package object modops {

  abstract class ModifiedRemoteCollection[A: ClassTag] extends RemoteCollection[A] with Serializable {

    val originalCollection: RemoteCollection[_]

    override def performer = originalCollection.performer

  }

  case class Filtered[A: ClassTag](originalCollection: RemoteCollection[A], f: A => Boolean) extends ModifiedRemoteCollection[A] {
    override def data: Traversable[A] = originalCollection.data.filter(f)
  }

  case class Sliced[A: ClassTag](originalCollection: RemoteCollection[A], from: Int, to: Int) extends ModifiedRemoteCollection[A] {
    override def data: Traversable[A] = originalCollection.data.slice(from, to)
  }

  case class Mapped[A: ClassTag, B: ClassTag](originalCollection: RemoteCollection[A], f: A => B) extends ModifiedRemoteCollection[B] {
    override def data: Traversable[B] = originalCollection.data.map(f)
  }

  case class Deduplicated[A: ClassTag](originalCollection: RemoteCollection[A]) extends ModifiedRemoteCollection[A] {
    //TODO: Use better way for this:
    override def data: Traversable[A] = originalCollection.data.toSeq.distinct
  }

}
