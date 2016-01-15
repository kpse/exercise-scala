package com.myob

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

case class PaymentCalculator(tax: IncomeTax, year: Long = 2016) {
  val employee = """^(\w+)\s*,\s*(\w+)\s*,\s*(\d+)\s*,\s*(\d+)%\s*,\s*(.+)\s*$""".r
  //15 March – 31 March
  val startEndDate = """^([^\–]+?)\s*–\s*([^\–]+?)$""".r

  def isWholeMonth(period: String) = period match {
    case startEndDate(start, end) =>
      val startDate = DateTime.parse(s"$start $year", DateTimeFormat.forPattern("dd MMMM yyyy"))
      val endDate = DateTime.parse(s"$end $year", DateTimeFormat.forPattern("dd MMMM yyyy"))
      startDate.dayOfMonth().get == 1 &&
        endDate.dayOfMonth() == endDate.dayOfMonth().withMaximumValue().dayOfMonth()
    case _ => true
  }

  def payslip(input: String) = input match {
    case employee(firstName, lastName, salary, superRate, period) if isWholeMonth(period) =>
      PaymentDetail(s"$firstName $lastName", period, PersonalTax(salary.toDouble, superRate.toDouble, tax)).report()
    case employee(firstName, lastName, salary, superRate, period) =>
      PaymentDetail(s"$firstName $lastName", period, PersonalTax(salary.toDouble, superRate.toDouble, tax, 6.0/11)).report()
    case _ =>
      "The employee information consists of first name, last name, annual salary, super rate (%) and payment period, separated by comma."
  }
}
