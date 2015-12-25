case class PersonalTax(salary: Double, superRate: Double, incomeTaxRate: IncomeTax) {
  implicit def doubleToRoundingDollar(number: Double): RoundingDollar = RoundingDollar(number)

  implicit def IntToRoundingDollar(number: Int): RoundingDollar = RoundingDollar(number)

  def superTax: RoundingDollar = grossIncome * superRate * 0.01

  def netIncome: RoundingDollar = grossIncome - incomeTax

  def incomeTax: RoundingDollar = incomeTaxRate.of(salary)

  def grossIncome: RoundingDollar = salary / 12

  def report = List(grossIncome, incomeTax, netIncome, superTax).mkString(",")
}
