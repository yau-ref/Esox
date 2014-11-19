package object esox {
  implicit def traversable2RemoteCollection[T](t: Traversable[T]) = new BaseRemoteCollection[T](t)
}
