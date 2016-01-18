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
    new PersonalTaxPresentation(12, 0, zeroTax.of(12)).report must equalTo("1,0,1,0")
  }

  def reportAccordingToSuper = {
    new PersonalTaxPresentation(120, 50, zeroTax.of(120)).report must equalTo("10,0,10,10")
    new PersonalTaxPresentation(120, 20, zeroTax.of(120)).report must equalTo("10,0,10,5")
    new PersonalTaxPresentation(120, 0, zeroTax.of(120)).report must equalTo("10,0,10,0")
  }

  def reportAccordingToIncomeTax = {
    new PersonalTaxPresentation(120, 0, IncomeTax(List("0 and over   $0 plus 10c for each $1 over $0")).of(120)).report must equalTo("10,1,9,0")
    new PersonalTaxPresentation(120, 0, IncomeTax(List("0 and over   $0 plus 20c for each $1 over $0")).of(120)).report must equalTo("10,2,8,0")
    new PersonalTaxPresentation(120, 0, IncomeTax(List("0 and over   $0 plus 100c for each $1 over $0")).of(120)).report must equalTo("10,10,0,0")
  }

  def reportErrorForSuper = {
    new PersonalTaxPresentation(1, 100, zeroTax.of(1)).report must equalTo("Error in calculating tax: Invalid Super Tax Rate(0 ~ 50% inclusive)")
    new PersonalTaxPresentation(1, 51, zeroTax.of(1)).report must equalTo("Error in calculating tax: Invalid Super Tax Rate(0 ~ 50% inclusive)")
  }

  def reportErrorForTaxTable = {
    new PersonalTaxPresentation(1, 10, IncomeTax(List()).of(1)).report must equalTo("Error in calculating tax: No match range in Tax Table")
    new PersonalTaxPresentation(1, 20, IncomeTax(List("")).of(1)).report must equalTo("Error in calculating tax: No match range in Tax Table")
    new PersonalTaxPresentation(1, 30, IncomeTax(List("making nonsense")).of(1)).report must equalTo("Error in calculating tax: No match range in Tax Table")
  }
}
