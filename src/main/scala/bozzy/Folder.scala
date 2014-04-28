package bozzy

import akka.actor.Actor
import akka.channels._
import akka.util.Timeout

import scala.reflect.runtime.universe.TypeTag

class Folder[T:TypeTag](implicit timeout: Timeout)
    extends Actor with Channels[TNil, (FolderStep[T], T) :+: TNil] {

  channel[FolderStep[T]] {
    case (s @ FolderStep(Nil, _, v, d), sender) ⇒
      if (d) println(s"A: $s")
      v -?-> sender
    case (s @ FolderStep(h :: t, f, v, d), sender) ⇒
      if (d) println(s"B: $s")
      s.copy(l = t, v = f(v,h)) -?-> selfChannel -!-> sender
  }
}

case class FolderStep[T](
  l: List[T],
  f: (T,T) ⇒ T,
  v: T,
  debug: Boolean)
