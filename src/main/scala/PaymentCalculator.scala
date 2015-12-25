case class PaymentCalculator(tax: TaxRate) {
  val employee = """^(\w+),(\w+),(\d+),(\d+)%,(.+)$""".r

  def payslip(input: String) = input match {
    case employee(firstName, lastName, salary, superRate, period) =>
      val myTax = tax.of(salary.toDouble, superRate.toDouble)
      PaymentDetail(s"$firstName $lastName", period, myTax.grossIncome, myTax.incomeTax, myTax.netIncome, myTax.superTax).report()
    case _ => ""
  }


}
