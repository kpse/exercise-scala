package com.myob

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

case class HistoricalIncomeTax(taxTables: Map[String, IncomeTax]) {
  def yearOf(year: Long): Option[IncomeTax] = {
    val financialYear = s"${year}-07-01"
    taxTables.get(financialYear) match {
      case Some(data) => Some(data)
      case None => taxTables.get("default")
    }
  }

}

object HistoricalIncomeTax {
  val YearDeclaration = """The following rates for 2012-13 apply from (.+)$""".r

  def parse(inputs: List[String]): HistoricalIncomeTax = {
    val sameYear: (String) => Boolean = {
      case YearDeclaration(t) => false
      case _ => true
    }
    def extract(info: List[String]): List[(String, IncomeTax)] = {
      info match {
        case YearDeclaration(time) :: xs =>
          val startDate = DateTime.parse(time, DateTimeFormat.forPattern("dd MMMM yyyy"))
          List(startDate.toString("yyyy-MM-dd") -> IncomeTax(xs.takeWhile(sameYear))) ++ extract(xs.dropWhile(sameYear))
        case _ =>
          List()
      }
    }

    inputs match {
      case YearDeclaration(time) :: xs =>
        HistoricalIncomeTax(extract(inputs).toMap)
      case x :: xs =>
        HistoricalIncomeTax(List("default" -> IncomeTax(xs)).toMap)
    }

  }

}
