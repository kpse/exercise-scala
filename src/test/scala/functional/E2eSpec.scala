package functional

import java.io.File

import com.myob.MainApp
import org.specs2.mutable.{Before, Specification}

import scala.io.Source

class E2eSpec extends Specification with Before {
  override def is =
    s2"""

  We can read everything from files
    read user from file                              $readUserFromFile
    read tax table from file                         $readTaxTableFromFile
    write output to file                             $writeToFile
                                                     """

  private val outputFilePath = "src/test/resources/output.txt"

  def readUserFromFile = {
    val outContent = new java.io.ByteArrayOutputStream()
    Console.withOut(outContent) {
      MainApp.main(Array("src/test/resources/input_david.csv", "src/main/resources/default_tax_table"))
    }
    outContent.toString.split("\n").toList must contain("David Rudd,01 March – 31 March,5004,922,4082,450")
  }

  def readTaxTableFromFile = {
    val outContent = new java.io.ByteArrayOutputStream()
    Console.withOut(outContent) {
      MainApp.main(Array("src/test/resources/input.csv", "src/test/resources/zero_tax_table"))
    }
    outContent.toString.split("\n").toList must containAllOf(List("David Rudd,01 March – 31 March,5004,0,5004,450", "Ryan Chen,01 March – 31 March,10000,0,10000,1000"))
  }

  def writeToFile = {
    MainApp.main(Array("src/test/resources/input_ryan.csv", "src/main/resources/default_tax_table", outputFilePath))
    Source.fromFile(outputFilePath).getLines() must contain("Ryan Chen,01 March – 31 March,10000,2696,7304,1000")
  }

  override def before: Any = {
    try {
      new File(outputFilePath).delete()
    } catch {
      case t: Throwable => println(s"no such file $outputFilePath, it's fine")
    }
  }
}
