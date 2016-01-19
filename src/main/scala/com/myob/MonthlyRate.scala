package com.myob

import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeConstants, Days, Months}

case class MonthlyRate(period: String, year: Long) {
  def inMonthRate: Double = period match {
    case MonthlyRate.startEndDate(start, end) =>
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


object MonthlyRate {
  //15 March – 31 March
  val startEndDate =
    """^([^\–]+?)\s*–\s*([^\–]+?)$""".r

  def split(period: String, year: Long) = period match {
    case startEndDate(start, end) =>
      val startDate = DateTime.parse(s"$start $year", DateTimeFormat.forPattern("dd MMMM yyyy"))
      val endDate = DateTime.parse(s"$end $year", DateTimeFormat.forPattern("dd MMMM yyyy"))
      Months.monthsBetween(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1)).getMonths match {
        case 0 =>
          List(MonthlyRate(period, year))
        case monthsInDifference if monthsInDifference > 0 =>
          val startPeriodToMonthEnd = s"${startDate.toString("dd MMMM")} – ${startDate.dayOfMonth().withMaximumValue().toString("dd MMMM")}"
          val endPeriodFromMonthStart = s"${endDate.withDayOfMonth(1).toString("dd MMMM")} – ${endDate.toString("dd MMMM")}"
          Stream.from(1).take(monthsInDifference - 1).foldLeft(List(MonthlyRate(startPeriodToMonthEnd, year))) {
            (acc, m) =>
              val start = startDate.plusMonths(m).withDayOfMonth(1)
              val end = startDate.plusMonths(m).dayOfMonth().withMaximumValue()
              acc ++ List(MonthlyRate(s"${start.toString("dd MMMM")} – ${end.toString("dd MMMM")}", year))
          } ++ List(MonthlyRate(endPeriodFromMonthStart, year))
      }

    case _ => List()
  }

}