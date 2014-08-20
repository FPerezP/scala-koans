package org.functionalkoans.forscala

import support.KoanSuite
class AboutSets extends KoanSuite {

  koan("Sets can be created easily") {
    val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    mySet.size should be(4)
  }

  koan("Sets contain distinct values") {
    val mySet = Set("Michigan", "Ohio", "Wisconsin", "Michigan")
    mySet.size should be(3)
  }

  koan("Sets can be added to easily") {
    val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val aNewSet = mySet + "Illinois"

    aNewSet.contains("Illinois") should be(true)

  }

  koan("Sets may be of mixed type") {
    val mySet = Set("Michigan", "Ohio", 12)

    mySet.contains(12) should be(true)

    mySet.contains("MI") should be(false)
  }

  koan("Sets can be checked for member existence") {
    val mySet = Set("Michigan", "Ohio", 12)

    mySet(12) should be(true)
    mySet("MI") should be(false)

  }

  koan("Set elements can be removed easily") {
    val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val aNewSet = mySet - "Michigan"

    aNewSet.contains("Michigan") should be(false)
  }

  koan("Set elements can be removed in multiple") {
    val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val aNewSet = mySet -- List("Michigan", "Ohio")

    aNewSet.contains("Michigan") should be(false)
    aNewSet.contains("Wisconsin") should be(true)
    aNewSet.size should be(2)
  }

  koan("Set elements can be removed with a tuple") {
    val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val aNewSet = mySet - ("Michigan", "Ohio") // Notice: single '-' operator for tuples

    aNewSet.contains("Michigan") should be(false)
    aNewSet.contains("Wisconsin") should be(true)
    aNewSet.size should be(2)
  }

  koan("Attempted removal of nonexistent elements from a set is handled gracefully") {
    val mySet = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val aNewSet = mySet - "Minnesota"

    aNewSet.equals(mySet) should be(true)
  }

  koan("Sets can be iterated easily") {
    val mySet = Set(1, 3, 4, 9)
    var sum = 0
    for (i <- mySet)
      sum = sum + i  //Of course this is the same thing as mySet.reduce(_ + _)

    sum should be(17)
  }

  koan("Two sets can be intersected easily") {
    val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val mySet2 = Set("Wisconsin", "Michigan", "Minnesota")
    val aNewSet = mySet1 intersect mySet2
    // NOTE: Scala 2.7 used **, deprecated for & or intersect in Scala 2.8

    aNewSet.equals(Set("Michigan", "Wisconsin")) should be(true)

  }

  koan("Two sets can be joined as their union easily") {
    val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val mySet2 = Set("Wisconsin", "Michigan", "Minnesota")
    val aNewSet = mySet1 union mySet2 // NOTE: You can also use the "|" operator

    aNewSet.equals(Set("Michigan", "Wisconsin", "Ohio", "Iowa", "Minnesota")) should be(true)

  }

  koan("A set is either a subset of another set or it isn't") {
    val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val mySet2 = Set("Wisconsin", "Michigan", "Minnesota")
    val mySet3 = Set("Wisconsin", "Michigan")

    mySet2 subsetOf mySet1 should be(false)
    mySet3 subsetOf mySet1 should be(true)

  }

  koan("The difference between two sets can be obtained easily") {
    val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val mySet2 = Set("Wisconsin", "Michigan")
    val aNewSet = mySet1 diff mySet2 // Note: you can use the "&~" operator if you *really* want to.

    aNewSet.equals(Set("Iowa", "Ohio")) should be(true)
  } 

  koan("Set equivalency is independent of order") {
    val mySet1 = Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    val mySet2 = Set("Wisconsin", "Michigan", "Ohio", "Iowa")

    mySet1.equals(mySet2) should be(true)
  }

}
