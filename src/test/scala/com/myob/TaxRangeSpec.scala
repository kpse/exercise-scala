package com.myob

import org.specs2.Specification

class TaxRangeSpec extends Specification {
  def is = s2"""

  This is a specification for the 'TaxRange'

  The TaxRange should
    check income range                               $checkRange
    report amount base on input                      $report
                                                      """

  def checkRange = {
    new TaxRange(1, 2, 0, 0, 0).isInRange(1.1) must beTrue
    new TaxRange(1, 2, 0, 0, 0).isInRange(0) must beFalse
    new TaxRange(1, 2, 0, 0, 0).isInRange(1) must beTrue
    new TaxRange(1, 2, 0, 0, 0).isInRange(2) must beTrue
  }

  def report = {
    new TaxRange(1, 3, 0, 0, 0).amount(1) must equalTo(0)
    new TaxRange(1, 3, 0, 1, 0).amount(1) must equalTo(1)
    new TaxRange(1, 3, 12, 1, 0).amount(12) must equalTo(2)
    new TaxRange(1, 3, 12, 1, 12).amount(24) must equalTo(2)
  }

}
