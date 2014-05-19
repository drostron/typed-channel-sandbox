# Akka Typed Channels

[Dave Rostron](http://github.com/drostron) -- [`@yastero`](http://twitter.com/yastero)

May 14, 2014

-----

Actors you say, why bother...

<aside class="notes">
a note, is this thing on?, it doesn't update?

- hmm, notes, could be useful, the div tag seems a bit verbose but perhaps ok
- audience reaction?...
</aside>

-----

brief actor intro, what they are and why we care

> The Actor Model provides a higher level of abstraction for writing concurrent and distributed systems. It alleviates the developer from having to deal with explicit locking and thread management, making it easier to write correct concurrent and parallel systems. 
>
> -- Akka Docs

hmm, distribution sounds useful, tell me more

-----

wat!? Any ⇒ Unit

-----

-!->

or how I learned to calm my Any ⇒ Unit woes

-----

old way

show runtime errors

-----

typed channel way

show compiler error on invalid inputs

-----

some explanation of channel within actor, channel refs, channelref wrapped actor, parent in type sig, channel lists

-----

stretch goal : implementation overview, a fun way to demo the power of macros and reflection

-----

careats:

type safety. I ran into it. you will too.
there is hope: keep tabs on SI-6240

fixed in 2.11, yay!

just use:
scala > 2.11
akka > ???

Typed Channels have been removed from Akka, boo!

-----