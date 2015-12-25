import org.specs2.Specification

class PaymentCalculatorSpec extends Specification {
  def is = s2"""

  This is a specification for the 'Payment Calculator'

  The Payment Calculator should
    report payslip for David                                $payslipForDavid
    report payslip for Ryan                                $payslipForRyan
                                                      """
  def payslipForDavid = PaymentCalculator(new IncomeTax).payslip("David,Rudd,60050,9%,01 March – 31 March") must equalTo("David Rudd,01 March – 31 March,5004,922,4082,450")
  def payslipForRyan = PaymentCalculator(new IncomeTax).payslip("Ryan,Chen,120000,10%,01 March – 31 March") must equalTo("Ryan Chen,01 March – 31 March,10000,2696,7304,1000")
}
