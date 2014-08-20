package org.functionalkoans.forscala

import support.KoanSuite
import scala.collection.mutable

class AboutMutableSets extends KoanSuite {

  koan("Mutable sets can be created easily") {
    val mySet = mutable.Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    mySet.size should be(4)
    mySet += "Oregon"
    mySet contains "Oregon" should be(true)
  }

  koan("Mutable sets can have elements removed") {
    val mySet = mutable.Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    mySet -= "Ohio"
    mySet contains "Ohio" should be(false)
  }

  koan("Mutable sets can have tuples of elements removed") {
    val mySet = mutable.Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    mySet -= ("Iowa", "Ohio")
    mySet contains "Ohio" should be(false)
    mySet.size should be(2)
  }

  koan("Mutable sets can have tuples of elements added") {
    val mySet = mutable.Set("Michigan", "Wisconsin")
    mySet += ("Iowa", "Ohio")
    mySet contains "Ohio" should be(true)
    mySet.size should be(4)
  }

  koan("Mutable sets can have Lists of elements added") {
    val mySet = mutable.Set("Michigan", "Wisconsin")
    mySet ++= List("Iowa", "Ohio")
    mySet contains "Ohio" should be(true)
    mySet.size should be(4)
  }

  koan("Mutable sets can have Lists of elements removed") {
    val mySet = mutable.Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    mySet --= List("Iowa", "Ohio")
    mySet contains "Ohio" should be(false)
    mySet.size should be(2)
  }

  koan("Mutable sets can be cleared") {
    val mySet = mutable.Set("Michigan", "Ohio", "Wisconsin", "Iowa")
    mySet.clear() // Convention is to use parens if possible when method called changes state
    mySet contains "Ohio" should be(false)
    mySet.size should be(0)
  }
}
