package bozzy

import akka.actor.Actor
import akka.channels._
import scala.reflect.runtime.universe.TypeTag

class Writer[T:TypeTag] extends Actor with Channels[TNil, (T, T) :+: TNil] {
  channel[T] {
    case (a, sender) ⇒
      println(s"Writer: $a")
      a -!-> sender
  }
}