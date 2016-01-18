package com.myob

case class PaymentDetail(name: String, period: String, personalTax: PersonalTaxPresentation) {
  def report() = s"$name,$period,${personalTax.report}"
}
