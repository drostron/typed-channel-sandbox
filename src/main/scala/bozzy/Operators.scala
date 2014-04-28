package bozzy

import akka.channels._
import scala.concurrent.Future

object Operators {

  implicit class RichAny[T](i:T) {
//    def !▷[C<:ChannelList](channel:ChannelRef[C]):T = i -!-> channel
//    def ?▷[C<:ChannelList](channel:ChannelRef[C]):Future[_] = i -?-> channel
//    def ◁!(j:T) = (j,i)
//    def ◁?(j:T) = (j,i)
  }
}
