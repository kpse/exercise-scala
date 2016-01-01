package com.myob

import scala.math.BigDecimal.RoundingMode

case class RoundingDollar(number: Double) {
  def toInt: Integer = BigDecimal.double2bigDecimal(number).setScale(0, RoundingMode.HALF_UP).toInt

  def *(dollar: RoundingDollar) = RoundingDollar(number * dollar.number)

  def -(dollar: RoundingDollar) = RoundingDollar(number - dollar.number)

}
