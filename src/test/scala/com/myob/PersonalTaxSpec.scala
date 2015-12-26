package com.myob

import org.specs2.Specification

class PersonalTaxSpec extends Specification {
  def is = s2"""

  This is a specification for the 'Personal Tax'

  The Personal Tax should
    report by string                                $report
    report with super                               $reportAccordingToSuper
    report base on IncomeTax                        $reportAccordingToIncomeTax
                                                      """
  private val zeroTax = IncomeTax(List())

  def report = {
    new PersonalTax(12, 0, zeroTax).report must equalTo("1,0,1,0")
  }

  def reportAccordingToSuper = {
    new PersonalTax(120, 100, zeroTax).report must equalTo("10,0,10,10")
    new PersonalTax(120, 50, zeroTax).report must equalTo("10,0,10,5")
    new PersonalTax(120, 0, zeroTax).report must equalTo("10,0,10,0")
  }

  def reportAccordingToIncomeTax = {
    new PersonalTax(120, 0, IncomeTax(List("0 and over   $0 plus 10c for each $1 over $0"))).report must equalTo("10,1,9,0")
    new PersonalTax(120, 0, IncomeTax(List("0 and over   $0 plus 20c for each $1 over $0"))).report must equalTo("10,2,8,0")
    new PersonalTax(120, 0, IncomeTax(List("0 and over   $0 plus 100c for each $1 over $0"))).report must equalTo("10,10,0,0")
  }
}
