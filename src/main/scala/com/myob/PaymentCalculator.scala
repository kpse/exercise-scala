package com.myob

import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, Months}

case class PaymentCalculator(tax: IncomeTax, year: Long = 2016) {
  val employee = """^(\w+)\s*,\s*(\w+)\s*,\s*(\d+)\s*,\s*(\d+)%\s*,\s*(.+)\s*$""".r

  //15 March – 31 March
  val startEndDate =
    """^([^\–]+?)\s*–\s*([^\–]+?)$""".r

  def monthSplitter(period: String, year: Long) = period match {
    case startEndDate(start, end) =>
      val startDate = DateTime.parse(s"$start $year", DateTimeFormat.forPattern("dd MMMM yyyy"))
      val endDate = DateTime.parse(s"$end $year", DateTimeFormat.forPattern("dd MMMM yyyy"))
      Months.monthsBetween(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1)).getMonths match {
        case 0 =>
          List(PaymentRate(period, year))
        case oneMore if oneMore > 0 =>
          val startPeriodToMonthEnd = s"${startDate.toString("dd MMMM")} – ${startDate.dayOfMonth().withMaximumValue().toString("dd MMMM")}"
          val endPeriodFromMonthStart = s"${endDate.withDayOfMonth(1).toString("dd MMMM")} – ${endDate.toString("dd MMMM")}"
          Stream.from(1).take(oneMore - 1).foldLeft(List(PaymentRate(startPeriodToMonthEnd, year))) {
            (acc, m) =>
              val start = startDate.plusMonths(m).withDayOfMonth(1)
              val end = startDate.plusMonths(m).dayOfMonth().withMaximumValue()
              acc ++ List(PaymentRate(s"${start.toString("dd MMMM")} – ${end.toString("dd MMMM")}", year))
          } ++ List(PaymentRate(endPeriodFromMonthStart, year))
      }

    case _ => List()
  }

  def payslip(input: String) = input match {
    case employee(firstName, lastName, salary, superRate, period) =>
      monthSplitter(period, year).map {
        p =>
          println(s"rate = ${p}")
          PaymentDetail(s"$firstName $lastName", p.period, PersonalTax(salary.toDouble, superRate.toDouble, tax, p.inMonthRate)).report()
      }.mkString("\n")
    case _ =>
      "The employee information consists of first name, last name, annual salary, super rate (%) and payment period, separated by comma."
  }
}
