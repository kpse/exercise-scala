package com.myob

import org.specs2.Specification

class PaymentCalculatorSpec extends Specification {
  def is = s2"""

  This is a specification for the 'Payment Calculator'

  The Payment Calculator should
    report payslip for David                               $payslipForDavid
    report payslip for Ryan                                $payslipForRyan
    report error for wrong input                           $reportError
    ignore spaces around comma                             $handleMoreSpace
    report half month payslip                              $halfMonthPayslip
    report one day payslip                                 $oneDayPayslip
    report zero payslip for weekend                        $nonWorkingDayPayslip
    report payslip for 2 months                            $crossMonthPaySlip
    report payslip for 3 and more months                   $cross3MonthPaySlip
    report payslip for super rate with fragment            $superRateWithFraction
                                                      """

  def payslipForDavid = {
    val taxTable: IncomeTax = new IncomeTax(List("$37,001 - $80,000       $3,572 plus 32.5c for each $1 over $37,000"))

    val payslip = PaymentCalculator(taxTable).payslip("David,Rudd,60050,9%,01 March – 31 March")

    payslip must equalTo("David Rudd,01 March – 31 March,5004,922,4082,450")
  }

  def payslipForRyan = {
    val taxTable = new IncomeTax(List("$80,001 - $180,000      $17,547 plus 37c for each $1 over $80,000"))

    val payslip = PaymentCalculator(taxTable).payslip("Ryan,Chen,120000,10%,01 March – 31 March")

    payslip must equalTo("Ryan Chen,01 March – 31 March,10000,2696,7304,1000")
  }

  def reportError = {
    val payslip = PaymentCalculator(new IncomeTax(List())).payslip("Something I don't understand")

    payslip must equalTo("The employee information consists of first name, last name, annual salary, super rate (%) and payment period, separated by comma.")
  }

  def handleMoreSpace = {
    val taxTable = new IncomeTax(List("$80,001 - $180,000      $17,547 plus 37c for each $1 over $80,000"))

    val payslip = PaymentCalculator(taxTable).payslip("Ryan ,Chen,120000,10%,01 March – 31 March")

    payslip must equalTo("Ryan Chen,01 March – 31 March,10000,2696,7304,1000")
  }

  def halfMonthPayslip = {
    val taxTable = new IncomeTax(List("$80,001 - $180,000      $0 plus 0c for each $1 over $80,000"))
    //12 actual working days / 22 total working days
    val payslip = PaymentCalculator(taxTable, 2015).payslip("Ryan,Chen,120000,0%,15 March – 31 March")

    payslip must equalTo("Ryan Chen,15 March – 31 March,5455,0,5455,0")

  }

  def oneDayPayslip = {
    val taxTable = new IncomeTax(List("$80,001 - $180,000      $0 plus 0c for each $1 over $80,000"))
    //1 actual working days / 22 total working days
    val payslip = PaymentCalculator(taxTable, 2015).payslip("Ryan,Chen,120000,0%,31 March – 31 March")

    payslip must equalTo("Ryan Chen,31 March – 31 March,455,0,455,0")

  }

  def nonWorkingDayPayslip = {
    val taxTable = new IncomeTax(List("$80,001 - $180,000      $0 plus 0c for each $1 over $80,000"))
    //0 actual working days / 22 total working days
    val payslip = PaymentCalculator(taxTable, 2015).payslip("Ryan,Chen,120000,0%,28 March – 29 March")

    payslip must equalTo("Ryan Chen,28 March – 29 March,0,0,0,0")

  }

  def crossMonthPaySlip = {
    val taxTable = new IncomeTax(List("$80,001 - $180,000      $0 plus 0c for each $1 over $80,000"))
    //2 actual working days / 22 total working days in March
    //3 actual working days / 22 total working days in April
    val payslip = PaymentCalculator(taxTable, 2015).payslip("Ryan,Chen,120000,0%,30 March – 03 April")

    payslip must equalTo("Ryan Chen,30 March – 31 March,909,0,909,0\nRyan Chen,01 April – 03 April,1364,0,1364,0")

  }

  def cross3MonthPaySlip = {
    val taxTable = new IncomeTax(List("$80,001 - $180,000      $0 plus 0c for each $1 over $80,000"))
    //2 actual working days / 22 total working days in March
    //22 actual working days / 22 total working days in April
    //1 actual working days / 21 total working days in May
    val payslip = PaymentCalculator(taxTable, 2015).payslip("Ryan,Chen,120000,0%,30 March – 01 May")

    payslip must equalTo("Ryan Chen,30 March – 31 March,909,0,909,0\nRyan Chen,01 April – 30 April,10000,0,10000,0\nRyan Chen,01 May – 01 May,476,0,476,0")

  }

  def superRateWithFraction = {
    val taxTable = new IncomeTax(List("$80,001 - $180,000      $0 plus 0c for each $1 over $80,000"))

    val payslip = PaymentCalculator(taxTable, 2015).payslip("Ryan,Chen,110000,9.5%,01 March – 31 March")

    payslip must equalTo("Ryan Chen,01 March – 31 March,9167,0,9167,871")
  }
}
