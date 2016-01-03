package com.myob

import java.io.File

import scala.io.Source

object MainApp {
  def main(args: Array[String]): Unit = args.length match {
    case 3 =>
      val users = Source.fromFile(new File(args(0)))
      val taxTable = Source.fromFile(new File(args(1)))
      val output = new File(args(2))
      printToFile(output)(p => users.getLines().map(PaymentCalculator(IncomeTax(taxTable.getLines().toList)).payslip).foreach(p.println))
      println("output to file: " + args(2))
    case 2 =>
      val users = Source.fromFile(new File(args(0)))
      val taxTable = Source.fromFile(new File(args(1)))
      users.getLines().map(PaymentCalculator(IncomeTax(taxTable.getLines().toList)).payslip).foreach(println)
    case 1 =>
      val users = Source.fromFile(new File(args(0)))
      val taxTable = Source.fromURL(getClass.getResource("/default_tax_table"))
      users.getLines().map(PaymentCalculator(IncomeTax(taxTable.getLines().toList)).payslip).foreach(println)
    case _ =>
      println("usage:\n\tmyob userSalary.csv [taxTable.csv [out.csv]]")
  }

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try {
      op(p)
    } finally {
      p.close()
    }
  }
}
