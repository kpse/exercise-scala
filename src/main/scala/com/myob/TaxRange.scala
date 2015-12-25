package com.myob

case class TaxRange(lowerBound: Int, upperBound: Int, base: Int, rate: Double, previousRange: Int) {
  def isInRange(salary: Double): Boolean = salary > lowerBound && salary <= upperBound

  def amount(salary: Double): Double = (base + (salary - previousRange) * rate) / 12
}
