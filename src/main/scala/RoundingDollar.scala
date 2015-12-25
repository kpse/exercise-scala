import scala.math.BigDecimal.RoundingMode

case class RoundingDollar(number: Double) {
  override def toString: String = BigDecimal.double2bigDecimal(number).setScale(0, RoundingMode.HALF_UP).toString()
}
