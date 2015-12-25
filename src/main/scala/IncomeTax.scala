case class TaxRange(lowerBound: Int, upperBound: Int, base: Int, rate: Double) {
  def isInRange(salary: Double): Boolean = salary > lowerBound && salary <= upperBound

  def amount(salary: Double): Double = (base + (salary - lowerBound) * rate) / 12
}

case class IncomeTax() {
  implicit def doubleToRoundingDollar(number: Double): RoundingDollar = RoundingDollar(number)

  def of(salary: Double): RoundingDollar = {
    val TaxRanges: List[TaxRange] = List(TaxRange(37001, 80000, 3572, 0.325), TaxRange(80000, Int.MaxValue, 17547, 0.37))
    val amount = TaxRanges.find(_.isInRange(salary)).map(_.amount(salary)).getOrElse(0.0)
    RoundingDollar(amount)
  }

  def of(salary: Double, superRate: Double) = PersonalTax(salary, superRate, this)
}

