package esox

import scala.collection.Traversable
import scala.reflect.ClassTag

class BaseRemoteCollection[A: ClassTag](val localCollection: Traversable[A])
                                       (override implicit val performer: Performer) extends RemoteCollection[A] {

  override def data: Traversable[A] = localCollection

}
