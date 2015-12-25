import org.specs2.Specification

class PaymentCalculatorSpec extends Specification {
  def is = s2"""

  This is a specification for the 'Payment Calculator'

  The Payment Calculator should
    report payslip for David                                $payslipForDavid
    report payslip for Ryan                                $payslipForRyan
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
}
