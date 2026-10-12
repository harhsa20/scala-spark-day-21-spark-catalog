# Day 21 — Spark Catalog

## Objective
Practice Spark databases, tables, temporary views, SQL queries, and catalog metadata using hotel booking data.

## Technologies
- Scala 2.12.18
- Apache Spark 3.5.3
- sbt 1.12.15
- Java 17

## Features
- List databases using SHOW DATABASES
- Create the hotel_analytics database
- Create and query a temporary view
- Register hotel bookings as a Spark table
- List tables using SHOW TABLES
- Calculate booking counts and revenue by city
- Inspect table schema, columns, and catalog metadata

## Sample Data
Five hotel bookings across Hyderabad, Chennai, and Bengaluru.

## Run the Project
```bash
sbt "runMain SparkCatalogDemo"
