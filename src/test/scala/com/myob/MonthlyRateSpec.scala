package com.myob

import org.specs2.Specification

class MonthlyRateSpec extends Specification {
  def is = s2"""

  This is a specification for the 'MonthlyRate'

  The MonthlyRate should
    output date if it contains only one month             $report
    split date from 2 months                              $split2Months
    split date from 3 and more months                     $split3Months
                                                      """

  def report = {
    val rates = MonthlyRate.split("12 March – 20 March", 2016)
    rates must equalTo(List(MonthlyRate("12 March – 20 March", 2016)))
  }

  def split2Months = {
    val rates = MonthlyRate.split("12 March – 20 April", 2016)
    rates must equalTo(List(MonthlyRate("12 March – 31 March", 2016), MonthlyRate("01 April – 20 April", 2016)))
  }

  def split3Months = {
    val rates = MonthlyRate.split("12 March – 20 May", 2016)
    rates must equalTo(List(MonthlyRate("12 March – 31 March", 2016), MonthlyRate("01 April – 30 April", 2016), MonthlyRate("01 May – 20 May", 2016)))
  }

}
