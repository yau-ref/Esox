package object esox {
  implicit def traversable2RemoteCollection[T](t: Traversable[T]) = new RemoteCollection[T](t)
}
