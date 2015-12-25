package com.myob

case class PaymentCalculator(tax: IncomeTax) {
  val employee = """^(\w+),(\w+),(\d+),(\d+)%,(.+)$""".r

  def payslip(input: String) = input match {
    case employee(firstName, lastName, salary, superRate, period) =>
      PaymentDetail(s"$firstName $lastName", period, PersonalTax(salary.toDouble, superRate.toDouble, tax)).report()
    case _ => ""
  }


}
