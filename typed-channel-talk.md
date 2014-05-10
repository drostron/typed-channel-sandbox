# Akka Typed Channels

[Dave Rostron](http://github.com/drostron) -- [`@yastero`](http://twitter.com/yastero)

May 14, 2014

-----

Actors you say, why bother...

-----

brief actor intro, what they are and why we care

> The Actor Model provides a higher level of abstraction for writing concurrent and distributed systems. It alleviates the developer from having to deal with explicit locking and thread management, making it easier to write correct concurrent and parallel systems. 
>
> -- Akka Docs

hmm, distribution sounds useful, tell me more

-----

quick std akka distributed example here

would be nice to have a virtualized 2-3 machine setup (stretch goal)

-----

wat!? Any ⇒ Unit

-----

-!->

or how I learned to calm my Any ⇒ Unit woes

-----

incrementer old way

~~~{.scala contenteditable=true}
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
~~~

~~~{.scala}
val i = "not editable?"
~~~

-----

channels incrementer
non-generic or generic?

-----

show where compiler error on invalid inputs

-----

some explanation of channel within actor, channel refs, channelref wrapped actor, parent in type sig, channel lists

-----

stretch goal : implementation overview, a fun way to demo the power of macros and reflection

-----

careats:

move this elsewhere

show an example, before upgrading recreate the issue, create a sub-project to show the issue on current versions

~~type safety. I ran into it. you will too.
there is hope: keep tabs on SI-6240~~

fixed in 2.11, yay!

just use:
scala > 2.11
akka > ???

-----

### further investigations
- macro implementation
- …