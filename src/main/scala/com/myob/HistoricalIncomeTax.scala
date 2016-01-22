package com.myob

case class HistoricalIncomeTax(taxTables: Map[String, IncomeTax]) {
  def yearOf(year: Long): Option[IncomeTax] = {
    val financialYear = s"${year}-07-01"
    taxTables.get(financialYear)
  }

}
