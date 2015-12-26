package com.myob

import org.specs2.Specification

class RoundingDollarSpec extends Specification {
  def is = s2"""

  This is a specification for the 'RoundingDollar'

  The RoundingDollar should
    report integer always                               $report
    rounding half up                                $rounding
    be multiplied                           $multiply
    be subtracted                             $subtract
                                                      """

  def report = {
    new RoundingDollar(11.1).toString must not contain "."
    new RoundingDollar(11.5).toString must not contain "."
    new RoundingDollar(11.9).toString must not contain "."
  }
  def rounding = {
    new RoundingDollar(11.1).toString must equalTo("11")
    new RoundingDollar(11.2).toString must equalTo("11")
    new RoundingDollar(11.3).toString must equalTo("11")
    new RoundingDollar(11.4).toString must equalTo("11")
    new RoundingDollar(11.49).toString must equalTo("11")
    new RoundingDollar(11.5).toString must equalTo("12")
    new RoundingDollar(11.51).toString must equalTo("12")
    new RoundingDollar(11.6).toString must equalTo("12")
    new RoundingDollar(11.7).toString must equalTo("12")
    new RoundingDollar(11.8).toString must equalTo("12")
    new RoundingDollar(11.9).toString must equalTo("12")
  }
  def multiply = {
    s"${new RoundingDollar(3) * new RoundingDollar(5)}" must equalTo("15")
  }
  def subtract = {
    s"${new RoundingDollar(2) - new RoundingDollar(1)}" must equalTo("1")
  }

}
