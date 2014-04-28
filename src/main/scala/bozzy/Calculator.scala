package bozzy

import akka.actor.Actor
import akka.channels._
import spire.math.Number

// error on initialization via ChannelExt if CalcOp is not declared
class Calculator extends Actor with Channels[TNil, (CalcOp, Number) :+: TNil] {
  channel[Add] {
    case (Add(a,b), sender) ⇒ a + b -!-> sender
  }
  channel[Double] {
    case (Double(a), sender) ⇒ a * 2 -!-> sender
  }
  channel[CalcOp] {
    case (request, sender) ⇒ Number(42) -!-> sender
  }
}

object Calculator {
  type CalculatorChannelRef = ChannelRef[(CalcOp, Number) :+: TNil]
}