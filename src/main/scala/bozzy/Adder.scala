package bozzy

import akka.actor.Actor
import akka.channels._
import spire.math.Number

class Adder extends Actor with Channels[TNil, ((Number,Number),Number) :+: TNil] {
  channel[(Number, Number)] {
    case ((a,b), sender) ⇒ a + b -!-> sender
  }
}

object Adder {
  type AdderChannelRef = ChannelRef[((Number,Number),Number) :+: TNil]
}
