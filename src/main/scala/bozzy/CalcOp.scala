package bozzy

import spire.math.Number

sealed trait CalcOp
case class Add(a:Number, b:Number) extends CalcOp
case class Addˆ(a:Number, b:Number) extends CalcOp
case class Double(a:Number) extends CalcOp
