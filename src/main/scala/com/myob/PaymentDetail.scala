package com.myob

case class PaymentDetail(name: String, period: String, personalTax: PersonalTaxPresenter) {
  def report() = s"$name,$period,${personalTax.report}"
}
