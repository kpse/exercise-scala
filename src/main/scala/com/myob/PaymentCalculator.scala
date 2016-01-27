package com.myob

import java.time.Year

case class PaymentCalculator(tax: IncomeTax, year: Long = 2015) {
  val employee = """^(\w+)\s*,\s*(\w+)\s*,\s*(\d+)\s*,\s*([\d.]+)%\s*,\s*(.+)\s*$""".r

  def payslip(input: String) = input match {
    case employee(firstName, lastName, salary, superRate, period) =>
      MonthlyRate.split(period, year).map {
        p =>
          PaymentDetail(s"$firstName $lastName", p.period, PersonalTaxPresenter(salary.toDouble, superRate.toDouble, tax.of(salary.toDouble), p.inMonthRate)).report()
      }.mkString("\n")
    case _ =>
      "The employee information consists of first name, last name, annual salary, super rate (%) and payment period, separated by comma."
  }
}

object PaymentCalculator {
  def apply(tax: IncomeTax) = new PaymentCalculator(tax)
  def apply(history: HistoricalIncomeTax, year: Long) = for(tax <- history.yearOf(year)) yield new PaymentCalculator(tax, year)
}