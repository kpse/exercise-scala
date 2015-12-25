import org.specs2.Specification

class PaymentCalculatorSpec extends Specification {
  def is = s2"""

  This is a specification for the 'Payment Calculator'

  The Payment Calculator should
    report payslip                                 $payslip
                                                      """
  def payslip = PaymentCalculator(new TaxRate).payslip("David,Rudd,60050,9%,01 March – 31 March") must equalTo("David Rudd,01 March – 31 March,5004,922,4082,450")
}
