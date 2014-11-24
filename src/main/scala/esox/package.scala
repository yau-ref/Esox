import scala.collection.Traversable

package object esox {

  implicit class Remotable[A](t: Traversable[A])(implicit val performer: Performer) {
    def rem: RemoteCollection[A] = new BaseRemoteCollection[A](t)
  }

}
