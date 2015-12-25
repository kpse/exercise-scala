case class PaymentCalculator(tax: TaxRate) {
  val employee = """^(\w+),(\w+),(\d+),(\d+)%,(.+)$""".r

  def payslip(input: String) = input match {
    case employee(firstName, lastName, salary, superRate, period) =>
      PaymentDetail(s"$firstName $lastName", period, tax.of(salary.toDouble, superRate.toDouble)).report()
    case _ => ""
  }


}
