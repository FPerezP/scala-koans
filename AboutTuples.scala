package org.functionalkoans.forscala

import support.KoanSuite
import java.util.Date

class AboutTuples extends KoanSuite {

  koan("Tuples can be created easily") {
    val tuple = ("apple", "dog")
    tuple should be("apple", "dog")
  }

  koan("Tuple items may be accessed individually") {
    val tuple = ("apple", "dog")
    val fruit = tuple._1
    val animal = tuple._2

    fruit should be("apple")
    animal should be("dog")
  }

  koan("Tuples may be of mixed type") {
    val tuple5 = ("a", 1, 2.2, new Date(), BigDecimal(5))

    tuple5._2 should be(1)
    tuple5._5 should be(BigDecimal(5))
  }

  koan("Tuples items can be swapped on a Tuple 2") {
    val tuple = ("apple", 3).swap
    tuple._1 should be(3)
    tuple._2 should be("apple")
  }
}
