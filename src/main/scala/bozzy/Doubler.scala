package bozzy

import akka.actor.Actor
import akka.channels._
import spire.math.Number

class Doubler extends Actor with Channels[TNil, (Number,Number) :+: TNil] {
  channel[Number] {
    case (i, sender) ⇒ i * 2 -!-> sender
  }
}

object Doubler {
  type DoublerChannelRef = ChannelRef[(Number,Number) :+: TNil]
}