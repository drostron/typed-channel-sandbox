package bozzy

import akka.actor._
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy.Restart
import akka.channels._
import scala.concurrent.duration._
import scala.concurrent.{Future, Await}
import scala.language.postfixOps
import akka.routing.{RoundRobinRouter, SmallestMailboxRouter}
import akka.util.Timeout

// TODO : consolidate timeouts, clean, ...

class Vaka extends Actor {
  val woe = context.actorOf(Props[WoeCalculator])

  val adder = ChannelExt(context.system).actorOf(new Adder, "adder")
  val doubler = ChannelExt(context.system).actorOf(new Doubler, "doubler")
  val calculator = ChannelExt(context.system).actorOf(new Calculator, "calculator")

  context.watch(woe)
  context.watch(adder.actorRef)
  context.watch(doubler.actorRef)
  context.watch(calculator.actorRef)

  override val supervisorStrategy = OneForOneStrategy() {
    case _ ⇒ Restart
  }

  def receive = {
    case "stop" ⇒ context stop self
  }
}

object VakaApp extends App {
  akka.Main.main(Array("bozzy.Vaka"))
}

object Vaka {
  def run(f: ActorSystem⇒Unit) {
    val system = ActorSystem("typewalking-system")
    try {
      f(system)
    }
    finally {
      system.shutdown()
    }
  }

  def runParOps() = run { system ⇒
    import system.dispatcher
    implicit val timeout = Timeout(3 seconds)

    val executor = ChannelExt(system).actorOf(new Executor[Int,Int], "executor")
    val result = Future.sequence(Par.map((0 to 100).toList, (_:Int)*2, executor))
    println(Await.result(result, 3 seconds))

    val folder = ChannelExt(system).actorOf(new Folder[Int], "folder")
    val foldResult = Par.fold[Int](0, (0 to 50).toList, _+_, folder, true)
    println(Await.result(foldResult, 3 seconds))
  }

  // appears that the thread safety issue rears its head

  // TODO : pass collection and function

  implicit

  def runParOpsˆ(i:Int, j:Int) = run { system ⇒
    import system.dispatcher
    implicit val timeout = Timeout(5 seconds)

    // TODO : dead letters? something to do with "an ask request that is done by the "endpoint glue"".

    val routees = (0 to j).map { i ⇒
      Thread.sleep(25) // Thread safety hack
      ChannelExt(system).actorOf(new Executor[Int,Int], s"executor-$i").actorRef
    }.toList
    val executorRouter = system.actorOf(Props.empty.withRouter(RoundRobinRouter(routees = routees)))
    val executorRouterChannel = new ChannelRef[((Int,Int⇒Int),Int) :+: TNil](executorRouter)
    val result = Future.sequence((0 to i).map(i ⇒ (i,(_:Int)*2) -?-> executorRouterChannel))
//    import Operators._
//    val result = Future.sequence((0 to i).map(i ⇒ (i,(_:Int)*2) ?▷ executorRouterChannel))
    println(Await.result(result, 10 seconds))
  }

  def runParOpsˆˆ(i:Int, j:Int) = run { system ⇒
    import system.dispatcher
    implicit val timeout = Timeout(5 seconds)

    val executorRouter = ChannelExt(system).actorOf(new ExecutorRouter[Int,Int](j), "executor-router")
    val result = Future.sequence((0 to i).map(i ⇒ (i,(_:Int)*2) -?-> executorRouter))
    println(Await.result(result, 10 seconds))
  }
}