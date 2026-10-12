import org.apache.spark.sql.SparkSession

object SparkCatalogDemo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark Catalog - Hotel Bookings")
      .master("local[*]")
      .config("spark.sql.warehouse.dir", "spark-warehouse")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    try {
      import spark.implicits._

      val bookings = Seq(
        (101, "Ravi", "Hyderabad", 2, 5000.0),
        (102, "Sita", "Chennai", 3, 7500.0),
        (103, "Arjun", "Hyderabad", 1, 2500.0),
        (104, "Priya", "Bengaluru", 4, 9000.0),
        (105, "Kiran", "Chennai", 2, 6000.0)
      ).toDF("booking_id", "guest_name", "city", "nights", "amount")

      println("=== Available Databases ===")
      spark.sql("CREATE DATABASE IF NOT EXISTS hotel_analytics")
      spark.sql("SHOW DATABASES").show(false)

      println("=== Temporary View ===")
      bookings.createOrReplaceTempView("hotel_bookings_temp")
      spark.sql("SELECT * FROM hotel_bookings_temp").show(false)

      spark.sql("USE hotel_analytics")
      bookings.write.mode("overwrite").saveAsTable("hotel_bookings")

      println("=== Registered Tables ===")
      spark.sql("SHOW TABLES").show(false)

      println("=== Revenue by City ===")
      spark.sql("""
        SELECT city, COUNT(*) AS bookings, SUM(amount) AS revenue
        FROM hotel_bookings
        GROUP BY city
        ORDER BY city
      """).show(false)

      println("=== Table Schema ===")
      spark.table("hotel_bookings").printSchema()

      println("=== Catalog Table Metadata ===")
      println(spark.catalog.getTable("hotel_bookings"))

      println("=== Table Columns ===")
      spark.catalog.listColumns("hotel_bookings").show(false)

      println("=== Catalog Databases ===")
      spark.catalog.listDatabases().show(false)

      println("=== Catalog Tables ===")
      spark.catalog.listTables().show(false)
    } finally {
      spark.stop()
    }
  }
}
