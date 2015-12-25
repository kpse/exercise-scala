package com.myob

import scala.math.BigDecimal.RoundingMode

case class RoundingDollar(number: Double) {
  def *(dollar: RoundingDollar) = RoundingDollar(number * dollar.number)

  def -(dollar: RoundingDollar) = RoundingDollar(number - dollar.number)

  override def toString: String = BigDecimal.double2bigDecimal(number).setScale(0, RoundingMode.HALF_UP).toString()
}
