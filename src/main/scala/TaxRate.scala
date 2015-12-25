
case class TaxRate() {
  def of(salary: Double, superRate: Double) = PersonalTax(salary, superRate)
}

