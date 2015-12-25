
case class TaxRate() {
  implicit def doubleToRoundingDollar(number: Double): RoundingDollar = RoundingDollar(number)

  def of(salary: Double): RoundingDollar = {
    if (salary > 80000) {
      (17547 + (salary - 80000) * 0.37) / 12
    } else {
      (3572 + (salary - 37000) * 0.325) / 12
    }
  }

  def of(salary: Double, superRate: Double) = PersonalTax(salary, superRate, this)
}

