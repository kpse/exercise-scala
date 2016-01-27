package functional

import com.myob.{HistoricalIncomeTax, IncomeTax, PaymentCalculator}
import org.specs2.Specification

import scala.io.Source

class mainSpec extends Specification {
  def is = s2"""

  We can read everything from files
    report for 2 users                              $report
    report for 2 users in new format                $reportWithNewTaxFormat
    report in new format for other year             $reportWithNewTaxFormatInOtherYear
                                                     """

  def report = {
    val users = Source.fromURL(getClass.getResource("/input.csv"))
    val taxTable = Source.fromURL(getClass.getResource("/default_tax_table"))
    val expectation = Source.fromURL(getClass.getResource("/output_sample.csv"))

    val outputs = users.getLines().map(PaymentCalculator(IncomeTax(taxTable.getLines().toList), 2015).payslip).toList

    val expect: List[String] = expectation.getLines().toList
    outputs must containAllOf(expect)
    expect must containAllOf(outputs)
  }

  def reportWithNewTaxFormat = {
    val users = Source.fromURL(getClass.getResource("/input.csv"))
    val taxTable = Source.fromURL(getClass.getResource("/new_tax_format_table"))
    val expectation = Source.fromURL(getClass.getResource("/output_sample.csv"))

    val outputs = PaymentCalculator(HistoricalIncomeTax.parse(taxTable.getLines().toList), 2015).map(c => users.getLines().toList.map(c.payslip)).toList.flatten

    val expect: List[String] = expectation.getLines().toList
    outputs must containAllOf(expect)
    expect must containAllOf(outputs)
  }

  def reportWithNewTaxFormatInOtherYear = {
    val users = Source.fromURL(getClass.getResource("/input.csv"))
    val taxTable = Source.fromURL(getClass.getResource("/new_tax_format_table"))
    val expectation = Source.fromURL(getClass.getResource("/output_sample_new_tax.csv"))

    val outputs = PaymentCalculator(HistoricalIncomeTax.parse(taxTable.getLines().toList), 2016).map(c => users.getLines().toList.map(c.payslip)).toList.flatten

    val expect: List[String] = expectation.getLines().toList
    outputs must containAllOf(expect)
    expect must containAllOf(outputs)
  }
}
