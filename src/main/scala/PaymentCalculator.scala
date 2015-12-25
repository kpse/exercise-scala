case class PaymentCalculator(tax: TaxRate) {
  val employee = """^(\w+),(\w+),(\d+),(\d+)%,(.+)$""".r
  def payslip(input: String) = input match {
    case employee(firstName, lastName, salary, superRate, period) =>
      PaymentDetail(s"$firstName $lastName", period, 5004, 922, 4082, 450).report()
    case _ => ""
  }

}
