case class PaymentDetail(name: String, period: String, grossIncome: RoundingDollar, incomeTax: RoundingDollar, netIncome: RoundingDollar, superTax: RoundingDollar) {
  def report() = s"$name,$period,$grossIncome,$incomeTax,$netIncome,$superTax"
}
