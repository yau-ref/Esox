import scala.collection.Traversable

package object esox {

  implicit class Remotable[A](t: Traversable[A]) {
    def rem: RemoteCollection[A] = new BaseRemoteCollection[A](t)
  }

}
