package com.myob

case class PersonalTax(grossIncome: RoundingDollar, incomeTax: RoundingDollar, netIncome: RoundingDollar, superTax: RoundingDollar) {
  def report = List(grossIncome, incomeTax, netIncome, superTax).map(_.toInt).mkString(",")
}

case class PersonalTaxPresenter(salary: Double, superRate: Double, incomeTax: Option[RoundingDollar], totalRate: Double = 1.0) {

  def report = tax.map(_.report).getOrElse(s"Error in calculating tax: $errorMessage")

  def tax = for {
    tax <- incomeTax.map(_ * totalRate)
    net <- netIncome
    sup <- superTax
  } yield PersonalTax(grossIncome, tax, net, sup)

  def superTax: Option[RoundingDollar] = superRate match {
    case rate if rate >= 0 && rate <= 50 =>
      Some(grossIncome * rate * 0.01)
    case _ => None
  }

  def netIncome: Option[RoundingDollar] = incomeTax.map(_ * totalRate).map(grossIncome - _)

  def grossIncome: RoundingDollar = salary / 12 * totalRate

  private def errorMessage = List(incomeTax, superTax)
    .zip(List("No match range in Tax Table", "Invalid Super Tax Rate(0 ~ 50% inclusive)"))
    .find(_._1.isEmpty)
    .map(_._2)
    .getOrElse("General Error.")
}
