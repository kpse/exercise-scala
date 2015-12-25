package com.myob

import org.specs2.Specification

class IncomeTaxSpec extends Specification {
  def is = s2"""

  This is a specification for the 'IncomeTax'

  The IncomeTax should
    report tax base on input table                                $report
    report tax with a basic tax value                             $reportWithBasic
    report tax with a non-taxable range                             $reportWithNonTaxableRange
    report tax with a roof-break range                             $reportWithRoofBreak
                                                      """

  def report = {
    val tax = IncomeTax(List("$18,201 - $37,000       19c for each $1 over $18,200")).of(18200 + 32)
    s"$tax" must equalTo("1")
  }

  def reportWithBasic = {
    val tax = IncomeTax(List("$37,001 - $80,000       $3,572 plus 32.5c for each $1 over $37,000")).of(60050)
    s"$tax" must equalTo("922")
  }

  def reportWithNonTaxableRange = {
    val tax = IncomeTax(List("0 - $18,200     Nil")).of(18200)
    s"$tax" must equalTo("0")
  }

  def reportWithRoofBreak = {
    val tax = IncomeTax(List("$180,001 and over       $54,547 plus 45c for each $1 over $180,000")).of(180002)
    s"$tax" must equalTo("4546")
  }

}
