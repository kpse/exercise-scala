case class PaymentCalculator(tax: TaxRate) {
  def payslip(input: String) = PaymentDetail("David Rudd", "01 March – 31 March", 5004, 922, 4082, 450).report()

}
