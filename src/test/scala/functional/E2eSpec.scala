package functional

import com.myob.{IncomeTax, PaymentCalculator}
import org.specs2.Specification

import scala.io.Source

class E2eSpec extends Specification {
  def is = s2"""

  We can read everything from files
    report for 2 users                              $report
                                                     """

  def report = {
    val users = Source.fromURL(getClass.getResource("/input.csv"))
    val taxTable = Source.fromURL(getClass.getResource("/tax_table"))

    val lines = users.getLines()
    System.out.println(lines)
    val outputs: List[String] = lines.map(PaymentCalculator(IncomeTax(taxTable.getLines().toList)).payslip).toList

    outputs must contain("David Rudd,01 March – 31 March,5004,922,4082,450")
    outputs must contain("Ryan Chen,01 March – 31 March,10000,2696,7304,1000")
  }
}
