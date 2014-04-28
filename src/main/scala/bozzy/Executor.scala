package bozzy

import akka.actor.Actor
import akka.channels._
import scala.reflect.runtime.universe.TypeTag

class Executor[T:TypeTag,U:TypeTag] extends Actor with Channels[TNil, ((T,T⇒U),U) :+: TNil] {
  channel[(T,T⇒U)] {
    case ((t,f),sender) ⇒
      println(Thread.currentThread().getName, self.path.name)
      f(t) -!-> sender
  }
}