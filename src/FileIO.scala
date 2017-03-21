import java.io.{File, FileWriter}
import scala.collection.mutable
import scala.io.Source

case class Purchase(customerId: String, date: String, creditCard: String, cvv: String, category: String) {
  override def toString = s"Customer: ${customerId}, Date: ${date}\n"
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
    println(promptMsg)
    io.StdIn.readLine()
  }

  def main(args: Array[String]): Unit = {
    val search = prompt(s"\nPlease enter your search category (Furniture, Alcohol, Toiletries, Shoes, Food, Jewelry):\n")
    val results = purchases.filter(_.category == search)
    val fw = new FileWriter(new File("filtered_purchases.prn"))
    results.foreach(line => {
      println(line)
      fw.write(line.toString)
    })
    fw.close()
  }
}