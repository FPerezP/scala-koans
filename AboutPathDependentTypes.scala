package org.functionalkoans.forscala

import org.functionalkoans.forscala.support.KoanSuite

case class Board(length: Int, height: Int) {
  case class Coordinate(x: Int, y: Int) {
    require(0 <= x && x < length && 0 <= y && y < height)
  }
  val occupied = scala.collection.mutable.Set[Coordinate]()
}

class AboutPathDependentTypes extends KoanSuite {
  koan(
    """When a class is instantiated inside of another object, it belongs to the object.  This is a path
      | dependent type. Once established, it cannot be placed inside of another object""") {

    val b1 = Board(20, 20)
    val b2 = Board(30, 30)
    val c1 = b1.Coordinate(15, 15)
    val c2 = b2.Coordinate(25, 25)

    b1.occupied += c1
    b2.occupied += c2
    // Next line doesn't compile, uncomment to try, then comment and answer the koan next
//    b1.occupied += c2

    c1.x should be (15)
  }
}
