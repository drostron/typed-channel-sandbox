package bozzy

import akka.channels._
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.language.postfixOps
import scala.reflect.runtime.universe.TypeTag

object Par {
  implicit val timeout = Timeout(1 seconds)

  def fold[T:TypeTag](z:T, l:List[T], f:(T,T) ⇒ T,
                      folder: ChannelRef[(FolderStep[T], T) :+: TNil],
                      debug:Boolean = false)
                      :Future[T] = {
    FolderStep[T](l, f, z, debug) -?-> folder
  }

  def map[T:TypeTag,U:TypeTag](l:List[T], f:T⇒U, executor:ChannelRef[((T,T⇒U),U) :+: TNil]):List[Future[U]] =
    l.map(i ⇒ (i,f) -?-> executor)
}
