package com.myob

case class PaymentCalculator(tax: IncomeTax, year: Long = 2016) {
  val employee = """^(\w+)\s*,\s*(\w+)\s*,\s*(\d+)\s*,\s*(\d+)%\s*,\s*(.+)\s*$""".r

  def payslip(input: String) = input match {
    case employee(firstName, lastName, salary, superRate, period) =>
      PaymentDetail(s"$firstName $lastName", period, PersonalTax(salary.toDouble, superRate.toDouble, tax, PaymentRate(period, year).inMonthRate)).report()
    case _ =>
      "The employee information consists of first name, last name, annual salary, super rate (%) and payment period, separated by comma."
  }
}
