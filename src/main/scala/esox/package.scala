import scala.collection.Traversable
import scala.reflect.ClassTag

package object esox {

  implicit class Remotable[A: ClassTag](val t: Traversable[A]) {
    def rem(implicit performer: Performer): RemoteCollection[A] = new BaseRemoteCollection[A](t)
  }

}
