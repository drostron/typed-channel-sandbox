package bozzy

import akka.actor.{ActorRef,ActorSystem}
import akka.channels._
import akka.pattern.ask
import akka.util.Timeout
import bozzy.Adder.AdderChannelRef
import bozzy.Doubler.DoublerChannelRef
import bozzy.Calculator.CalculatorChannelRef
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.language.postfixOps
import scala.reflect.runtime.universe.TypeTag
import spire.math.Number

/*
  TODO : pondering
  List(
    (T,T) ⇒ T,
    T ⇒ T,
    T ⇒ (T, T),
    (T, T) ⇒ T
 */

class TypeWalking(
    woe: ActorRef,
    adder: AdderChannelRef,
    doubler: DoublerChannelRef,
    calculator: CalculatorChannelRef,
    implicit val context: ExecutionContext) {
  implicit val timeout = Timeout(1 seconds)

  def typelessWoes(m: Any) {
    (woe ? m).flatMap { case i:Number ⇒ woe ? Double(i) }.onComplete { i ⇒
      println(s"$i")
    }
  }

  def typedCalculation(a: Number, b: Number) {
    val results = Future.sequence(List(
      (a,b) -?-> adder -?-> doubler,
      Add(a,b) -?-> calculator,
      Double(a) -?-> calculator,
      (Add(a,b) -?-> calculator).flatMap(Double(_) -?-> calculator)
    ))

    results.onComplete { i ⇒
      println(s"$i")
    }
  }
}