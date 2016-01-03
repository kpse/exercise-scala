package com.myob

case class PersonalTax(salary: Double, superRate: Double, incomeTaxRate: IncomeTax) {
  def superTax: Option[RoundingDollar] = superRate match {
    case rate if rate >= 0 && rate <= 50 =>
      Some(grossIncome * rate * 0.01)
    case _ => None
  }

  def netIncome: Option[RoundingDollar] = incomeTax.map(grossIncome - _)

  def incomeTax: Option[RoundingDollar] = incomeTaxRate.of(salary)

  def grossIncome: RoundingDollar = salary / 12

  def report = {
    val result = for (income <- incomeTax; net <- netIncome; sup <- superTax)
      yield List(grossIncome, income, net, sup).map(_.toInt).mkString(",")
    result.getOrElse(s"Error in calculating tax: $errorMessage")
  }

  def errorMessage = List(incomeTax, superTax).zip(List("No match range in Tax Table", "Invalid Super Tax Rate(0 ~ 50% inclusive)"))
    .find(_._1.isEmpty).map(_._2).getOrElse("General Error.")
}
