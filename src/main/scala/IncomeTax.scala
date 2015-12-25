

case class IncomeTax(taxTable: List[String]) {
  implicit def doubleToRoundingDollar(number: Double): RoundingDollar = RoundingDollar(number)

  def of(salary: Double): RoundingDollar = {
    val amount = taxRangeList.find(_.isInRange(salary)).map(_.amount(salary)).getOrElse(0.0)
    RoundingDollar(amount)
  }

  def taxRangeList = List(TaxRange(37001, 80000, 3572, 0.325), TaxRange(80000, Int.MaxValue, 17547, 0.37))
}

