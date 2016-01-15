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
      println(s"working = ${totalWorkingDays(startDate, endDate) * 1.0}")
      println(s"total = ${totalWorkingDays(endDate.dayOfMonth().withMinimumValue(), endDate.dayOfMonth().withMaximumValue())}")
      totalWorkingDays(startDate, endDate) * 1.0 / totalWorkingDays(endDate.dayOfMonth().withMinimumValue(), endDate.dayOfMonth().withMaximumValue())
    case _ => 1.0
  }


  def totalWorkingDays(firstDay: DateTime, lastDay: DateTime) = {
    Range(0, Days.daysBetween(firstDay, lastDay).getDays + 1).foldLeft(0) {
      (acc, d) => firstDay.plusDays(d).dayOfWeek().get() match {
        case DateTimeConstants.SUNDAY | DateTimeConstants.SATURDAY => acc
        case _ => acc + 1
      }
    }
  }
}
