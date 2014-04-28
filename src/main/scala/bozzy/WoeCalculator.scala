package bozzy

import akka.actor.Actor

class WoeCalculator extends Actor {
  def receive = {
    case Add(a,b) ⇒
      sender ! a + b
    case Addˆ(a,b) ⇒
      sender ! (a + b).toString
    case Double(a) ⇒
      sender ! a * 2
    case i ⇒
      println(s"WoeCalculator received unsupported message: $i")
  }
}