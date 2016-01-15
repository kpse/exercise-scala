package com.myob

import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeConstants, Days}

case class PaymentRate(period: String, year: Long) {

  //15 March – 31 March
  val startEndDate =
    """^([^\–]+?)\s*–\s*([^\–]+?)$""".r

  def inMonthRate: Double = period match {
    case startEndDate(start, end) =>
      val startDate = DateTime.parse(s"$start $year", DateTimeFormat.forPattern("dd MMMM yyyy"))
      val endDate = DateTime.parse(s"$end $year", DateTimeFormat.forPattern("dd MMMM yyyy"))
      totalWorkingDays(startDate, endDate) * 1.0 / totalWorkingDays(endDate.dayOfMonth().withMinimumValue(), endDate.dayOfMonth().withMaximumValue())
    case _ => 1.0
  }


  def totalWorkingDays(firstDay: DateTime, lastDay: DateTime) = {
    Stream.from(0).takeWhile(p => Days.daysBetween(firstDay.plusDays(p), lastDay).getDays >= 0)
      .count(p => firstDay.plusDays(p).dayOfWeek().get() < DateTimeConstants.SATURDAY)
  }
}
