package bozzy

import akka.actor.Actor
import akka.channels._
import akka.util.Timeout
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.reflect.runtime.universe.TypeTag
//import shapeless._
//import shapeless.syntax.std.tuple._

class ExecutorRouter[T:TypeTag,U:TypeTag](nrOfInstances:Int)
    extends Actor with Channels[TNil, ((T,T⇒U),U) :+: ((T,T⇒U),U) :+: TNil] {

  implicit val timeout = Timeout(5 seconds)

  private var ptr = -1

//  val executor1 = ChannelExt(context.system).actorOf(new Executor[T,U], "executor-1")
//  val executor2 = ChannelExt(system).actorOf(new Executor[Int,Int], "executor-2")

  val channels:List[ChannelRef[((T,T⇒U),U) :+: TNil]] =
    (0 to nrOfInstances).map { i ⇒
      Thread.sleep(25) // Thread safety hack
      ChannelExt(context.system).actorOf(new Executor[T,U], s"executor-$i")
    }.toList

  def getChannel(i:Int):ChannelRef[((T,T⇒U),U) :+: TNil] = channels(i)
//  var channels =
//    ChannelExt(context.system).actorOf(new Executor[T,U], "executor-1") ::
//    ChannelExt(context.system).actorOf(new Executor[T,U], "executor-2") ::
//    HNil

  channel[(T,T⇒U)] {
    case ((t,f),sender) ⇒
      ptr+=1
      (t,f) -?-> getChannel(ptr%nrOfInstances) -!-> sender
  }
}
