case class PaymentDetail(name: String, period: String, grossIncome: Int, incomeTax: Int, netIncome: Int, superTax: Int) {
  def report() = s"$name,$period,$grossIncome,$incomeTax,$netIncome,$superTax"
}
