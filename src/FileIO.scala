import java.io.{File, FileWriter}
import scala.collection.mutable
import scala.io.Source

case class Purchase(customerId: String, date: String, creditCard: String, cvv: String, category: String) {
  override def toString = s"Customer: ${customerId}, Date: ${date}"
}

object FileIO {

  val purchases = mutable.MutableList[Purchase]()
  Source
    .fromFile("purchases.csv")
    .getLines().drop(1)
    .foreach(line => {
      val Array(customerId, date, creditCard, cvv, category) = line.split(",").map(_.trim)
      purchases += Purchase(customerId, date, creditCard, cvv, category)
    })

  def prompt(promptMsg: String) = {
    println(promptMsg);
    io.StdIn.readLine()
  }

  def searchItemAndPrintToFile {
    val search = prompt(s"\nPlease enter your search category (Furniture, Alcohol, Toiletries, Shoes, Food, Jewelry):\n")
    val results = purchases.filter(_.category.equals(search))
    results.foreach(line => println(line))
    val f = new File("filtered_purchases.prn")
    val fw = new FileWriter(f)
    fw.write(results.mkString("\n"))
    fw.close()
  }

  def main(args: Array[String]): Unit = {
    searchItemAndPrintToFile
  }
}