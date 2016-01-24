package com.myob

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

case class HistoricalIncomeTax(taxTables: Map[String, IncomeTax]) {
  def yearOf(year: Long): Option[IncomeTax] = {
    val financialYear = s"${year}-07-01"
    taxTables.get(financialYear)
  }

}

object HistoricalIncomeTax {
  val YearDeclaration = """The following rates for 2012-13 apply from (.+)$""".r

  def parse(inputs: List[String]): HistoricalIncomeTax = {
    inputs.head match {
      case YearDeclaration(time) =>
        val startDate = DateTime.parse(time, DateTimeFormat.forPattern("dd MMMM yyyy"))
        val tail = inputs.tail.takeWhile {
          case YearDeclaration(t) => false
          case _ => true
        }
        HistoricalIncomeTax(Map(startDate.toString("yyyy-MM-dd") -> IncomeTax(tail)))
      case _ =>
        HistoricalIncomeTax(Map("" -> IncomeTax(List())))
    }


  }
}
