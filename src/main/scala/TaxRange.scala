case class TaxRange(lowerBound: Int, upperBound: Int, base: Int, rate: Double) {
  def isInRange(salary: Double): Boolean = salary > lowerBound && salary <= upperBound

  def amount(salary: Double): Double = (base + (salary - lowerBound) * rate) / 12
}
