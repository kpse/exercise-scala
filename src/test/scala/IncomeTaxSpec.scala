import org.specs2.Specification

class IncomeTaxSpec extends Specification {
  def is = s2"""

  This is a specification for the 'IncomeTax'

  The IncomeTax should
    report tax base on input table                                $report
    report tax with a basic tax value                             $reportWithBasic
                                                      """

  def report = {
    val tax = IncomeTax(List("$18,201 - $37,000       19c for each $1 over $18,200")).of(18200 + 32)
    s"$tax" must equalTo("1")
  }

  def reportWithBasic = {
    val tax = IncomeTax(List("$37,001 - $80,000       $3,572 plus 32.5c for each $1 over $37,000")).of(60050)
    s"$tax" must equalTo("922")
  }

}
