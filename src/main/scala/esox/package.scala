package object esox {
  implicit def traversable2RemTraversable[T](t: Traversable[T]) = new RemoteTraversable[T](t)
}
