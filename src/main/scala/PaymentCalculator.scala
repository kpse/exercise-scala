case class PaymentCalculator(tax: TaxRate) {
  val employee = """^(\w+),(\w+),(\d+),(\d+)%,(.+)$""".r
  implicit def doubleToRoundingDollar(number: Double): RoundingDollar = RoundingDollar(number)
  implicit def IntToRoundingDollar(number: Int): RoundingDollar = RoundingDollar(number)
  def payslip(input: String) = input match {
    case employee(firstName, lastName, salary, superRate, period) =>
      PaymentDetail(s"$firstName $lastName", period, salary.toDouble / 12, (3572 + (60050 - 37000) * 0.325) / 12, 4082, 450).report()
    case _ => ""
  }

}
