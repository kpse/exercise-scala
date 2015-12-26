package com.myob

import org.specs2.Specification

class PersonalTaxSpec extends Specification {
  def is = s2"""

  This is a specification for the 'Personal Tax'

  The Personal Tax should
    report by string                                $report
    report with super                               $reportAccordingToSuper
    report base on IncomeTax                        $reportAccordingToIncomeTax
    report error if super rate over 50              $reportErrorForSuper
    report error tax input is invalid               $reportErrorForTaxTable
                                                      """
  private val zeroTax = IncomeTax(List("0 and over   $0 plus 0c for each $1 over $0"))

  def report = {
    new PersonalTax(12, 0, zeroTax).report must equalTo("1,0,1,0")
  }

  def reportAccordingToSuper = {
    new PersonalTax(120, 50, zeroTax).report must equalTo("10,0,10,10")
    new PersonalTax(120, 20, zeroTax).report must equalTo("10,0,10,5")
    new PersonalTax(120, 0, zeroTax).report must equalTo("10,0,10,0")
  }

  def reportAccordingToIncomeTax = {
    new PersonalTax(120, 0, IncomeTax(List("0 and over   $0 plus 10c for each $1 over $0"))).report must equalTo("10,1,9,0")
    new PersonalTax(120, 0, IncomeTax(List("0 and over   $0 plus 20c for each $1 over $0"))).report must equalTo("10,2,8,0")
    new PersonalTax(120, 0, IncomeTax(List("0 and over   $0 plus 100c for each $1 over $0"))).report must equalTo("10,10,0,0")
  }

  def reportErrorForSuper = {
    new PersonalTax(1, 100, zeroTax).report must equalTo("Error in calculating tax: Invalid Super Tax Rate(0 ~ 50% inclusive)")
    new PersonalTax(1, 51, zeroTax).report must equalTo("Error in calculating tax: Invalid Super Tax Rate(0 ~ 50% inclusive)")
  }

  def reportErrorForTaxTable = {
    new PersonalTax(1, 100, IncomeTax(List())).report must equalTo("Error in calculating tax: No match range in Tax Table")
    new PersonalTax(1, 51, IncomeTax(List(""))).report must equalTo("Error in calculating tax: No match range in Tax Table")
  }
}
