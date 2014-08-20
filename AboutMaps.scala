package org.functionalkoans.forscala

import support.KoanSuite

class AboutMaps extends KoanSuite {

  koan("Maps can be created easily") {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
    myMap.size should be(4)
  }

  koan("Maps contain distinct pairings") {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Michigan")
    myMap.size should be(3)
  }

  koan("Maps can be added to easily") {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Michigan")

    val aNewMap = myMap + ("IL" -> "Illinois")

    aNewMap.contains("IL") should be(true)

  }

  koan("Map values can be iterated") {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Michigan")

    val mapValues = myMap.values

    mapValues.size should be(3)

    mapValues.head should be("Michigan")

    val lastElement = mapValues.last
    lastElement should be("Wisconsin")

    // for (mval <- mapValues) println(mval)

    // NOTE that the following will not compile, as iterators do not implement "contains"
    //mapValues.contains("Illinois") should be (true)
  }

  koan("Maps insertion with duplicate key updates previous entry with subsequent value") {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "MI" -> "Meechigan")

    val mapValues = myMap.values

    mapValues.size should be(3)

    myMap("MI") should be("Meechigan")
  }

  koan("Map keys may be of mixed type") {
    val myMap = Map("Ann Arbor" -> "MI", 49931 -> "MI")
    myMap("Ann Arbor") should be("MI")
    myMap(49931) should be("MI")
  }

  koan("Mixed type values can be added to a map ") {
    val myMap = scala.collection.mutable.Map.empty[String, Any]
    myMap("Ann Arbor") = (48103, 48104, 48108)
    myMap("Houghton") = 49931

    myMap("Houghton") should be(49931)
    myMap("Ann Arbor") should be((48103, 48104, 48108))
    // what happens if you change the Any to Int
  }


  koan("Maps may be accessed") {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
    myMap("MI") should be("Michigan")
    myMap("IA") should be("Iowa")
  }

  koan("Map elements can be removed easily") {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
    val aNewMap = myMap - "MI"

    for (oneElement <- aNewMap) println(oneElement)
    aNewMap.contains("MI") should be(false)
  }

  koan("Accessing a map by key results in an exception if key is not found") {

    val myMap = Map("OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")

    // Cheat Code (because this is hard to illustrate): uncomment the intercept code to make this pass 
    intercept[NoSuchElementException] {

      myMap("MI") should be(__)
    }
  }

  koan("Map elements can be removed in multiple") {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
    val aNewMap = myMap -- List("MI", "OH")

    aNewMap.contains("MI") should be(false)

    aNewMap.contains("WI") should be(true)
    aNewMap.size should be(2)
  }

  koan("Map elements can be removed with a tuple") {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
    val aNewMap = myMap - ("MI", "WI") // Notice: single '-' operator for tuples

    aNewMap.contains("MI") should be(false)
    aNewMap.contains("OH") should be(true)
    aNewMap.size should be(2)
  }

  koan("Attempted removal of nonexistent elements from a map is handled gracefully") {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
    val aNewMap = myMap - "MN"

    aNewMap.equals(myMap) should be(true)
  }

  koan("Map equivalency is independent of order") {

    val myMap1 = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")
    val myMap2 = Map("WI" -> "Wisconsin", "MI" -> "Michigan", "IA" -> "Iowa", "OH" -> "Ohio")

    myMap1.equals(myMap2) should be(true)
  }
}
