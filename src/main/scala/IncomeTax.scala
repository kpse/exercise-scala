

case class IncomeTax(taxTable: List[String]) {
  implicit def doubleToRoundingDollar(number: Double): RoundingDollar = RoundingDollar(number)

  def of(salary: Double): RoundingDollar = {
    val amount = taxRangeList.find(_.isInRange(salary)).map(_.amount(salary)).getOrElse(0.0)
    RoundingDollar(amount)
  }

  def taxRangeList = {
    //"$18,201 - $37,000       19c for each $1 over $18,200"
    val range = """^\$(\d+)\s*-\s*\$(\d+)\s+(\d*\.?\d*)c for each \$1 over \$(\d+)$""".r
    //"$37,001 - $80,000       $3,572 plus 32.5c for each $1 over $37,000"
    val rangeWithBasic = """^\$(\d+)\s*-\s*\$(\d+)\s+\$(\d+) plus (\d*\.?\d*)c for each \$1 over \$(\d+)$""".r
    taxTable.map(_.replace(",", "")).flatMap {
      case range(lower, upper, centsInDollar, previousRange) => Some(TaxRange(lower.toInt, upper.toInt, 0, centsInDollar.toDouble * 0.01, previousRange.toInt))
      case rangeWithBasic(lower, upper, basic, centsInDollar, previousRange) => Some(TaxRange(lower.toInt, upper.toInt, basic.toInt, centsInDollar.toDouble * 0.01, previousRange.toInt))
      case _ => None
    }
  }
}

