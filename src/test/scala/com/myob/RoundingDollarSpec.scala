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
    new RoundingDollar(11.1).toInt must equalTo(11)
    new RoundingDollar(11.5).toInt must equalTo(12)
    new RoundingDollar(11.9).toInt must equalTo(12)
  }
  def rounding = {
    new RoundingDollar(11.1).toInt must equalTo(11)
    new RoundingDollar(11.2).toInt must equalTo(11)
    new RoundingDollar(11.3).toInt must equalTo(11)
    new RoundingDollar(11.4).toInt must equalTo(11)
    new RoundingDollar(11.49).toInt must equalTo(11)
    new RoundingDollar(11.5).toInt must equalTo(12)
    new RoundingDollar(11.51).toInt must equalTo(12)
    new RoundingDollar(11.6).toInt must equalTo(12)
    new RoundingDollar(11.7).toInt must equalTo(12)
    new RoundingDollar(11.8).toInt must equalTo(12)
    new RoundingDollar(11.9).toInt must equalTo(12)
  }
  def multiply = {
    val dollar = new RoundingDollar(3) * new RoundingDollar(5)
    dollar.toInt must equalTo(15)
  }
  def subtract = {
    val dollar = new RoundingDollar(2) - new RoundingDollar(1)
    dollar.toInt must equalTo(1)
  }

}
