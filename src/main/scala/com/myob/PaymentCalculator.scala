package com.myob

case class PaymentCalculator(tax: IncomeTax, year: Long = 2016) {
  val employee = """^(\w+)\s*,\s*(\w+)\s*,\s*(\d+)\s*,\s*([\d.]+)%\s*,\s*(.+)\s*$""".r

  def payslip(input: String) = input match {
    case employee(firstName, lastName, salary, superRate, period) =>
      MonthlyRate.split(period, year).map {
        p =>
          PaymentDetail(s"$firstName $lastName", p.period, PersonalTax(salary.toDouble, superRate.toDouble, tax, p.inMonthRate)).report()
      }.mkString("\n")
    case _ =>
      "The employee information consists of first name, last name, annual salary, super rate (%) and payment period, separated by comma."
  }
}
