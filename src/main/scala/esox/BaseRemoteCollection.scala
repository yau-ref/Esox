package esox

import scala.collection.Traversable
import scala.reflect.ClassTag


class BaseRemoteCollection[A: ClassTag](val localCollection: Traversable[A],
                                        @transient override val performer: Performer) extends RemoteCollection[A] with Serializable {

  override def data: Traversable[A] = localCollection

}