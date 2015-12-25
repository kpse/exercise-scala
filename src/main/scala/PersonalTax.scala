case class PersonalTax(salary: Double, superRate: Double) {
  implicit def doubleToRoundingDollar(number: Double): RoundingDollar = RoundingDollar(number)
  implicit def IntToRoundingDollar(number: Int): RoundingDollar = RoundingDollar(number)

  def superTax: RoundingDollar = {
    grossIncome * superRate * 0.01
  }

  def netIncome: RoundingDollar = {
    grossIncome - incomeTax
  }

  def incomeTax: RoundingDollar = {
    (3572 + (salary - 37000) * 0.325) / 12
  }

  def grossIncome: RoundingDollar = {
    salary / 12
  }

  def report = List(grossIncome,incomeTax,netIncome,superTax).mkString(",")
}
