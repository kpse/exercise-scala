package com.myob

case class PersonalTax(salary: Double, superRate: Double, incomeTaxRate: IncomeTax) {
  implicit def doubleToRoundingDollar(number: Double): RoundingDollar = RoundingDollar(number)

  implicit def IntToRoundingDollar(number: Int): RoundingDollar = RoundingDollar(number)

  def superTax: RoundingDollar = grossIncome * superRate * 0.01

  def netIncome: Option[RoundingDollar] = incomeTax.map(grossIncome - _)

  def incomeTax: Option[RoundingDollar] = incomeTaxRate.of(salary)

  def grossIncome: RoundingDollar = salary / 12

  def report = {
    val result = for (income <- incomeTax; net <- netIncome)
      yield List(grossIncome, income, net, superTax).mkString(",")
    result.getOrElse("error in calculating tax")
  }
}
