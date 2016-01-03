package functional

import com.myob.{IncomeTax, PaymentCalculator}
import org.specs2.Specification

import scala.io.Source

class mainSpec extends Specification {
  def is = s2"""

  We can read everything from files
    report for 2 users                              $report
                                                     """

  def report = {
    val users = Source.fromURL(getClass.getResource("/input.csv"))
    val taxTable = Source.fromURL(getClass.getResource("/default_tax_table"))
    val expectation = Source.fromURL(getClass.getResource("/output_sample.csv"))

    val outputs = users.getLines().map(PaymentCalculator(IncomeTax(taxTable.getLines().toList)).payslip).toList

    outputs must containAllOf(expectation.getLines().toList)
  }
}
