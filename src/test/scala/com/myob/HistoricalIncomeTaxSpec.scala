package com.myob

import org.specs2.Specification

class HistoricalIncomeTaxSpec extends Specification {
  def is = s2"""

  This is a specification for the 'HistoricalIncomeTax'

  The HistoricalIncomeTax should
    extract from years                                  $extract
    extract from different years                        $extract2
    report base on year                                 $report

                                                      """

  def extract = {
    val inputs = List("The following rates for 2012-13 apply from 1 July 2012",
    "Taxable income   Tax on this income",
    "0 - $18,200     Nil")
    val tax = HistoricalIncomeTax.parse(inputs).yearOf(2012)
    tax must not beNone
    val tax2 = HistoricalIncomeTax.parse(inputs).yearOf(2013)
    tax2 must beNone
  }

  def extract2 = {
    val inputs = List("The following rates for 2012-13 apply from 1 July 2013",
    "Taxable income   Tax on this income",
    "0 - $18,200     Nil")
    val tax = HistoricalIncomeTax.parse(inputs).yearOf(2012)
    tax must beNone
    val tax2 = HistoricalIncomeTax.parse(inputs).yearOf(2013)
    tax2 must not beNone
  }

  def report = {
    val inputs = List("The following rates for 2012-13 apply from 1 July 2012",
      "Taxable income   Tax on this income",
      "0 - $18,200     Nil")
    val tax = HistoricalIncomeTax.parse(inputs).yearOf(2012).get
    tax.of(1).get.toInt must equalTo(0)
  }

}
