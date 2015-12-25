case class PaymentDetail(name: String, period: String, personalTax: PersonalTax) {
  def report() = s"$name,$period,${personalTax.report}"
}
