package com.tw.apps

import StationDataTransformation.{franceMarseilleStationStatusJson2DF, nycStationStatusJson2DF}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.ScalaReflection
import org.scalatest._

class StationDataTransformationTest extends FeatureSpec with Matchers with GivenWhenThen {

  feature("Apply station status transformations to data frame") {
    val spark = SparkSession.builder.appName("Test App").master("local").getOrCreate()
    import spark.implicits._

    scenario("Transform nyc station data frame") {

      val testStationData =
        """{
          "station_id":"83",
          "bikes_available":19,
          "docks_available":41,
          "is_renting":true,
          "is_returning":true,
          "last_updated":1536242527,
          "name":"Atlantic Ave & Fort Greene Pl",
          "latitude":40.68382604,
          "longitude":-73.97632328
          }"""

      val schema = ScalaReflection.schemaFor[StationData].dataType //.asInstanceOf[StructType]

      Given("Sample data for station_status")
      val testDF1 = Seq(testStationData).toDF("raw_payload")


      When("Transformations are applied")
      val resultDF1 = testDF1.transform(nycStationStatusJson2DF(_, spark))

      Then("Useful columns are extracted")
      resultDF1.schema.fields(0).name should be("bikes_available")
      resultDF1.schema.fields(0).dataType.typeName should be("integer")
      resultDF1.schema.fields(1).name should be("docks_available")
      resultDF1.schema.fields(1).dataType.typeName should be("integer")
      resultDF1.schema.fields(2).name should be("is_renting")
      resultDF1.schema.fields(2).dataType.typeName should be("boolean")
      resultDF1.schema.fields(3).name should be("is_returning")
      resultDF1.schema.fields(3).dataType.typeName should be("boolean")
      resultDF1.schema.fields(4).name should be("last_updated")
      resultDF1.schema.fields(4).dataType.typeName should be("long")
      resultDF1.schema.fields(5).name should be("station_id")
      resultDF1.schema.fields(5).dataType.typeName should be("string")
      resultDF1.schema.fields(6).name should be("name")
      resultDF1.schema.fields(6).dataType.typeName should be("string")
      resultDF1.schema.fields(7).name should be("latitude")
      resultDF1.schema.fields(7).dataType.typeName should be("double")
      resultDF1.schema.fields(8).name should be("longitude")
      resultDF1.schema.fields(8).dataType.typeName should be("double")

      val row1 = resultDF1.head()
      row1.get(0) should be(19)
      row1.get(1) should be(41)
      row1.get(2) shouldBe true
      row1.get(3) shouldBe true
      row1.get(4) should be(1536242527)
      row1.get(5) should be("83")
      row1.get(6) should be("Atlantic Ave & Fort Greene Pl")
      row1.get(7) should be(40.68382604)
      row1.get(8) should be(-73.97632328)
    }

    scenario("Transform france marseille station data frame") {
      val docksAvailable = 19
      val bikesAvailable = 0
      val timestamp = "2021-06-08T20:14:49.042000Z"
      val stationId = "686e48654a218c70daf950a4e893e5b0"
      val latitude = 43.25402727813068
      val longitude = 5.401873594694653
      val name = "8149-391 MICHELET"
      val isRenting = true
      val isReturning = true

      val testStationData =
        s"""{
              "payload": {
                "network": {
                  "stations": [
                    {
                      "empty_slots": $docksAvailable,
                      "extra": {
                            "address": "391 MICHELET - 391 BOULEVARD MICHELET",
                            "banking": true,
                            "bonus": false,
                            "last_update": 1623183041000,
                            "slots": 19,
                            "status": "OPEN",
                            "uid": 8149
                      },
                      "free_bikes": $bikesAvailable,
                      "id": "$stationId",
                      "latitude": $latitude,
                      "longitude": $longitude,
                      "name": "$name",
                      "timestamp": "$timestamp"
                    }
                  ]
                }
              }
            }"""

      val schema = ScalaReflection.schemaFor[StationData].dataType //.asInstanceOf[StructType]

      Given("Sample data for station_status")
      val testDF1 = Seq(testStationData).toDF("raw_payload")


      When("Transformations are applied")
      val resultDF1 = testDF1.transform(franceMarseilleStationStatusJson2DF(_, spark))

      Then("Useful columns are extracted")
      resultDF1.schema.fields(0).name should be("bikes_available")
      resultDF1.schema.fields(0).dataType.typeName should be("integer")
      resultDF1.schema.fields(1).name should be("docks_available")
      resultDF1.schema.fields(1).dataType.typeName should be("integer")
      resultDF1.schema.fields(2).name should be("is_renting")
      resultDF1.schema.fields(2).dataType.typeName should be("boolean")
      resultDF1.schema.fields(3).name should be("is_returning")
      resultDF1.schema.fields(3).dataType.typeName should be("boolean")
      resultDF1.schema.fields(4).name should be("last_updated")
      resultDF1.schema.fields(4).dataType.typeName should be("long")
      resultDF1.schema.fields(5).name should be("station_id")
      resultDF1.schema.fields(5).dataType.typeName should be("string")
      resultDF1.schema.fields(6).name should be("name")
      resultDF1.schema.fields(6).dataType.typeName should be("string")
      resultDF1.schema.fields(7).name should be("latitude")
      resultDF1.schema.fields(7).dataType.typeName should be("double")
      resultDF1.schema.fields(8).name should be("longitude")
      resultDF1.schema.fields(8).dataType.typeName should be("double")

      val row1 = resultDF1.toJavaRDD.first()
      row1.get(0) should be(bikesAvailable)
      row1.get(1) should be(docksAvailable)
      row1.get(2) shouldBe isRenting
      row1.get(3) shouldBe isReturning
      row1.get(4) should be(1623183289)
      row1.get(5) should be(stationId)
      row1.get(6) should be(name)
      row1.get(7) should be(latitude)
      row1.get(8) should be(longitude)
    }
  }
}
