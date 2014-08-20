package org.functionalkoans.forscala

import org.scalatest.matchers.ShouldMatchers
import support.KoanSuite
import language.postfixOps
import Stream._

class AboutTraversables extends KoanSuite with ShouldMatchers {


  koan( """Traverables are the superclass of Lists, Arrays, Maps, Sets, Streams, and more.
          |   The methods involved can be applied to each other in a different type.  ++ appends
          |   two Traversables together.""") {

    val set = Set(1, 9, 10, 22)
    val list = List(3, 4, 5, 10)
    val result = set ++ list
    result.size should be(7)

    val result2 = list ++ set
    result2.size should be(8)
  }

  koan( """map will apply the given function on all elements of a
          |  Traversable and return a new collection of the result.""") {
    val set = Set(1, 3, 4, 6)
    val result = set.map(_ * 4)
    result.last should be(24)
  }

  koan( """flatten will smash all child Traversables within a Traversable""") {
    val list = List(List(1), List(2, 3, 4), List(5, 6, 7), List(8, 9, 10))
    list.flatten should be(List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
  }

  koan( """flatMap will not only apply the given function on all elements of a Traversable,
          |  but all elements within the elements and flatten the results""") {
    val list = List(List(1), List(2, 3, 4), List(5, 6, 7), List(8, 9, 10))
    val result = list.flatMap(_.map(_ * 4))
    result should be(List(4, 8, 12, 16, 20, 24, 28, 32, 36, 40))
  }

  koan( """flatMap of Options will filter out all Nones and Keep the Somes""") {
    val list = List(1, 2, 3, 4, 5)
    val result = list.flatMap(it => if (it % 2 == 0) Some(it) else None)
    result should be(List(2, 4))
  }

  koan( """collect will apply a partial function to all elements of a Traversable
          and will return a different collection. In this koan, a case fragment is a partial function.""") {
    val list = List(4, 6, 7, 8, 9, 13, 14)
    val result = list.collect {
      case x: Int if (x % 2 == 0) => x * 3
    }
    result should be(List(12, 18, 24, 42))
  }

  koan( """collect will apply a partial function to all elements of a Traversable
          |  and will return a different collection. In this koan, two case fragments are chained to create
          |  a more robust result.""") {
    val list = List(4, 6, 7, 8, 9, 13, 14)
    val partialFunction1: PartialFunction[Int, Int] = {
      case x: Int if x % 2 == 0 => x * 3
    }
    val partialFunction2: PartialFunction[Int, Int] = {
      case y: Int if y % 2 != 0 => y * 4
    }
    val result = list.collect(partialFunction1 orElse partialFunction2)
    result should be(List(12, 18, 28, 24, 36, 52, 42))
  }

  koan( """foreach will apply a function to all elements of a Traversable, but unlike
          | the map function, it will not return anything since the return type is Unit, which
          | is like a void return type in Java, C++""") {
    val list = List(4, 6, 7, 8, 9, 13, 14)
    list.foreach(num => println(num * 4))
    list should be(List(4, 6, 7, 8, 9, 13, 14))
  }

  koan( """toArray will convert any Traversable to an Array, which is a special wrapper around a
          |  primitive Java array.""") {
    val set = Set(4, 6, 7, 8, 9, 13, 14)
    val result = set.toArray
    result.isInstanceOf[Array[Int]] should be(true)
  }

  koan( """toList will convert any Traversable to a List.""") {
    val set = Set(4, 6, 7, 8, 9, 13, 14)
    val result = set.toList

    result.isInstanceOf[List[_]] should be(true)
  }

  koan( """toList, as well as other conversion methods like toSet, toArray,
          |  will not convert if the collection type is the same.""") {
    val list = List(5, 6, 7, 8, 9)
    val result = list.toList
    result eq list should be(true) //Reminder: eq tests for reference equality
  }

  koan( """toIterable will convert any Traversable to an Iterable. This is a base
          |  trait for all Scala collections that define an iterator method to step
          |  through one-by-one the collection's elements.
          |  (see AboutIterable koan).""") {

    val set = Set(4, 6, 7, 8, 9, 13, 14)
    val result = set.toIterable
    result.isInstanceOf[Iterable[_]] should be(true)
  }

  koan( """toSeq will convert any Traversable to a Seq which is an ordered Iterable
          |  and is the superclass to List, Queues, and Vectors. Sequences provide
          |  a method apply for indexing. Indices range from 0 up the the
          |  length of a sequence.""") {
    val set = Set(4, 6, 7, 8, 9, 13, 14)
    val result = set.toSeq
    result.isInstanceOf[Seq[_]] should be(true)
  }

  koan( """toIndexedSeq will convert any Traversable to an IndexedSeq which is
          |  an indexed sequence used in
          |  Vectors and Strings""") {
    val set = Set(4, 6, 7, 8, 9, 13, 14)
    val result = set.toIndexedSeq
    result.isInstanceOf[IndexedSeq[_]] should be(true)
  }

  koan( """toStream will convert any Traversable to a Stream which is
          |  a lazy list where elements are evaluated as they
          |  are needed.""") {
    val list = List(4, 6, 7, 8, 9, 13, 14)
    val result = list.toStream
    result.isInstanceOf[Stream[_]] should be(true)
    (result take 3) should be(List(4, 6, 7))
  }

  koan( """toSet will convert any Traversable to a Set which is
          |  a collection of unordered, unique values""") {
    val list = List(4, 6, 7, 8, 9, 13, 14)
    val result = list.toSet
    result.isInstanceOf[Set[_]] should be(true)
  }

  koan( """toMap will convert any Traversable to a Map. How it's
          | used depends on the original collection; if it's a List or Seq,
          | it should be of parameterized type Tuple2.""") {
    val list = List("Phoenix" -> "Arizona", "Austin" -> "Texas")
    val result = list.toMap
    result.isInstanceOf[Map[_, _]] should be(true)
  }

  koan( """toMap will convert a Set to a Map,
          | it should be of parameterized type Tuple2.""") {
    val set = Set("Phoenix" -> "Arizona", "Austin" -> "Texas")
    val result = set.toMap
    result.isInstanceOf[Map[_, _]] should be(true)
  }

  koan( """isEmpty is pretty self evident""") {
    val map = Map("Phoenix" -> "Arizona", "Austin" -> "Texas")
    map.isEmpty should be(false)

    val set = Set()
    set.isEmpty should be(true)
  }

  koan( """nonEmpty is pretty self evident too""") {
    val map = Map("Phoenix" -> "Arizona", "Austin" -> "Texas")
    map.nonEmpty should be(true)

    val set = Set()
    set.nonEmpty should be(false)
  }

  koan( """size provides the size of the traversable""") {
    val map = Map("Phoenix" -> "Arizona", "Austin" -> "Texas")
    map.size should be(2)
  }

  koan( """hasDefiniteSize will return true if there is traversable that has a
          finite end, otherwise false""") {
    val map = Map("Phoenix" -> "Arizona", "Austin" -> "Texas")
    map.hasDefiniteSize should be(true)

    import Stream.cons
    val stream = cons(0, cons(1, Stream.empty))
    stream.hasDefiniteSize should be(false)
  }

  koan( """head will return the first element of an ordered collection, or some random
          | element if order is not defined like in a Set or Map""") {
    val list = List(10, 19, 45, 1, 22)
    list.head should be(10)
  }

  koan( """headOption will return the first element as an Option of an order collection,
          | or some random element if order is not defined.  If a first element
          | is not available, then None is returned""") {
    val list = List(10, 19, 45, 1, 22)
    list.headOption should be(Some(10))

    val list2 = List()
    list2.headOption should be(None)
  }

  koan( """last will return the last element of an ordered collection, or some random
          | element if order is not defined like in a Set or Map""") {
    val list = List(10, 19, 45, 1, 22)
    list.last should be(22)
  }

  koan( """lastOption will return the first element as an Option of an order collection,
          | or some random element if order is not defined.  If a first element
          | is not available, then None is returned""") {
    val list = List(10, 19, 45, 1, 22)
    list.lastOption should be(Some(22))

    val list2 = List()
    list2.lastOption should be(None)
  }

  koan( """find will locate the first item that matches a predicate p as Some or None if
          | an element is not found""") {
    val list = List(10, 19, 45, 1, 22)
    list.find(_ % 2 != 0) should be(Some(19))

    val list2 = List(4, 8, 16)
    list2.find(_ % 2 != 0) should be(None)
  }

  koan( """tail will return the rest of the collection without the head""") {
    val list = List(10, 19, 45, 1, 22)
    list.tail should be(List(19, 45, 1, 22))
  }

  koan( """init will return the rest of the collection without the last""") {
    val list = List(10, 19, 45, 1, 22)
    list.init should be(List(10, 19, 45, 1))
  }

  koan( """Given a `from` index, and a `to` index, slice will return the part of the
          |  collection including `from`, and excluding `to`""") {
    val list = List(10, 19, 45, 1, 22)
    list.slice(1, 3) should be(List(19, 45))
  }

  koan( """Take will return the the first number of elements given.""") {
    val list = List(10, 19, 45, 1, 22)
    list.take(3) should be(List(10, 19, 45))
  }

  koan( """Take is used often with Streams, and Streams after all are Traversable""") {
    def streamer(v: Int): Stream[Int] = cons(v, streamer(v + 1))
    val a = streamer(2)
    (a take 3 toList) should be(List(2, 3, 4))
  }

  koan( """Drop will take the rest of the Traversable except
          |  the number of elements given""") {
    def streamer(v: Int): Stream[Int] = cons(v, streamer(v + 1))
    val a = streamer(2)
    ((a drop 6) take 3).toList should be(List(8, 9, 10))
  }

  koan( """takeWhile will continually accumulate elements until a predicate
          |  is no longer satisfied.  In this koan, TreeSet is Traversable.
          |  TreeSet also is also sorted.""") {
    val list = List(87, 44, 5, 4, 200, 10, 39, 100)
    list.takeWhile(_ < 100) should be(List(87, 44, 5, 4))
  }

  koan( """dropWhile will continually drop elements until a predicate
          |  is no longer satisfied.  Again, TreeSet is Traversable.
          |  TreeSet also is also sorted.""") {
    val list = List(87, 44, 5, 4, 200, 10, 39, 100)
    list.dropWhile(_ < 100) should be(List(200, 10, 39, 100))
  }

  koan( """filter will take out all elements that don't satisfy a predicate. An
          |  Array is also Traversable.""") {
    val array = Array(87, 44, 5, 4, 200, 10, 39, 100)
    array.filter(_ < 100) should be(Array(87, 44, 5, 4, 10, 39))
  }

  koan( """filterNot will take out all elements that satisfy a predicate. An
          |  Array is also Traversable.""") {
    val array = Array(87, 44, 5, 4, 200, 10, 39, 100)
    array.filterNot(_ < 100) should be(Array(200, 100))
  }

  koan( """splitAt will split a Traversable at a position, returning a 2 product
          |  Tuple.  Array is Traversable. splitAt is also defined as
          |  (xs take n, xs drop n)""") {
    val array = Array(87, 44, 5, 4, 200, 10, 39, 100)
    val result = array splitAt 3
    result._1 should be(Array(87, 44, 5))
    result._2 should be(Array(4, 200, 10, 39, 100))
  }

  koan( """span will split a Traversable according to predicate, returning
          |  a 2 product Tuple.  Array is Traversable, span
          |  is also defined as (xs takeWhile p, xs dropWhile p)""") {
    val array = Array(87, 44, 5, 4, 200, 10, 39, 100)
    val result = array span (_ < 100)
    result._1 should be(Array(87, 44, 5, 4))
    result._2 should be(Array(200, 10, 39, 100))
  }

  koan( """partition will split a Traversable according to predicate, return
          |  a 2 product Tuple. The left side are the elements satisfied by
          |  the predicate, the right side is not. Array is Traversable,
          |  partition is also defined as (xs filter p, xs filterNot p)""") {
    val array = Array(87, 44, 5, 4, 200, 10, 39, 100)
    val result = array partition (_ < 100)
    result._1 should be(Array(87, 44, 5, 4, 10, 39))
    result._2 should be(Array(200, 100))
  }

  koan( """groupBy will categorize a Traversable according to function, and return
          a map with the results.  This koan uses Partial Function chaining.  If you are
          still unfamiliar with PartialFunctions, see AboutPartialFunctions koans.""") {

    val array = Array(87, 44, 5, 4, 200, 10, 39, 100)

    val oddAndSmallPartial: PartialFunction[Int, String] = {
      case x: Int if x % 2 != 0 && x < 100 => "Odd and less than 100"
    }

    val evenAndSmallPartial: PartialFunction[Int, String] = {
      case x: Int if x != 0 && x % 2 == 0 && x < 100 => "Even and less than 100"
    }

    val negativePartial: PartialFunction[Int, String] = {
      case x: Int if x < 0 => "Negative Number"
    }

    val largePartial: PartialFunction[Int, String] = {
      case x: Int if x > 99 => "Large Number"
    }

    val zeroPartial: PartialFunction[Int, String] = {
      case x: Int if x == 0 => "Zero"
    }

    val result = array groupBy {
        oddAndSmallPartial orElse
        evenAndSmallPartial orElse
        negativePartial orElse
        largePartial orElse
        zeroPartial
    }

    (result("Even and less than 100") size) should be(3)
    (result("Large Number") size) should be(2)
  }

  koan( """forall will determine if a predicate is valid for all members of a
          |  Traversable.""") {
    val list = List(87, 44, 5, 4, 200, 10, 39, 100)
    val result = list forall (_ < 100)
    result should be(false)
  }

  koan( """`exists` will determine if a predicate
          | is valid for some members of a Traversable.""") {
    val list = List(87, 44, 5, 4, 200, 10, 39, 100)
    val result = list exists (_ < 100)
    result should be(true)
  }

  koan( """`count` will count the number of elements that satisfy a predicate
          | in a Traversable.""") {
    val list = List(87, 44, 5, 4, 200, 10, 39, 100)
    val result = list count (_ < 100)
    result should be(6)
  }

  koan( """ `/:` or `foldLeft` will combine an operation starting with a seed and combining from the left.  Fold Left
          | is defined as (seed /: list), where seed is the initial value.  Once the fold is established, you
          | provide a function that takes two arguments.  The first argument is the running total of the operation,
          | and the second element is the next element of the list.
          |
          | Given a Traversable (x1, x2, x3, x4), an initial value of init, an operation op,
          | foldLeft is defined as: (((init op x1) op x2) op x3) op x4)""") {
    val list = List(5, 4, 3, 2, 1)
    val result = (0 /: list) {
      (`running total`, `next element`) => `running total` - `next element`
    }
    result should be(-15)

    val result2 = list.foldLeft(0) {
      (`running total`, `next element`) => `running total` - `next element`
    }
    result2 should be(-15)

    val result3 = (0 /: list)(_ - _) //Short hand
    result3 should be(-15)

    val result4 = list.foldLeft(0)(_ - _)
    result4 should be(-15)

    (((((0 - 5) - 4) - 3) - 2) - 1) should be(-15)
  }

  koan( """ `:\` or foldRight` will combine an operation starting with a seed and combining from the right.  Fold right
          | is defined as (list :\ seed), where seed is the initial value.  Once the fold is established, you
          | provide a function that takes two elements.  The first is the next element of the list, and the
          | second element is the running total of the operation.
          |
          | Given a Traversable (x1, x2, x3, x4), an initial value of init, an operation op,
          | foldRight is defined as: x1 op (x2 op (x3 op (x4 op init)))""") {

    val list = List(5, 4, 3, 2, 1)
    val result = (list :\ 0) {
      (`next element`, `running total`) => `next element` - `running total`
    }
    result should be(3)

    val result2 = (list :\ 0) {
      (`next element`, `running total`) => `next element` - `running total`
    }
    result2 should be(3)

    val result3 = (list :\ 0)(_ - _) //Short hand
    result3 should be(3)

    val result4 = list.foldRight(0)(_ - _)
    result4 should be(3)

    (5 - (4 - (3 - (2 - (1 - 0))))) should be(3)
  }

  koan( """`reduceLeft` is the similar to foldLeft, except that the seed is the head value""") {
    val intList = List(5, 4, 3, 2, 1)
    intList.reduceLeft {
      _ + _
    } should be(15)

    val stringList = List("Do", "Re", "Me", "Fa", "So", "La", "Te", "Do")
    stringList.reduceLeft {
      _ + _
    } should be("DoReMeFaSoLaTeDo")
  }

  koan( """`reduceRight` is the similar to foldRight, except that the seed is the last value""") {
    val intList = List(5, 4, 3, 2, 1)
    intList.reduceRight {
      _ + _
    } should be(15)

    val stringList = List("Do", "Re", "Me", "Fa", "So", "La", "Te", "Do")
    stringList.reduceRight {
      _ + _
    } should be("DoReMeFaSoLaTeDo")
  }

  koan( """There are some methods that take much of the folding work out by providing basic functionality.
          |  `sum` will add all the elements, product will multiply, min would determine the smallest element, and
          |  `max` the largest.""") {
    val intList = List(5, 4, 3, 2, 1)
    intList.sum should be(15)
    intList.product should be(120)
    intList.max should be(5)
    intList.min should be(1)
  }

  koan( """You would choose foldLeft/reduceLeft or foldRight/reduceRight based on your mathematical goal.
          | One other reason for deciding is performance.  foldLeft is more performant since it uses
          | tail recursion and is optimized. This koan will either work or you will receive a
          | StackOverflowError. If you do receive a StackOverflowError, try reducing the MAX_SIZE value.""") {

    //val MAX_SIZE = 1000000
    //val reduceLeftStartTime = new java.util.Date
    //(1 to MAX_SIZE) reduceLeft (_ + _)
    //val reduceLeftEndTime = new java.util.Date


    //val reduceRightStartTime = new java.util.Date
    //(1 to MAX_SIZE) reduceRight (_ + _)
    //val reduceRightEndTime = new java.util.Date

    //val totalReduceLeftTime = reduceLeftEndTime.getTime - reduceLeftStartTime.getTime
    //val totalReduceRightTime = reduceRightEndTime.getTime - reduceRightStartTime.getTime

    //(totalReduceRightTime > totalReduceLeftTime) should be(true)
  }

  koan( """`transpose` will take a traversable of traversables and group them by their position in
          |  it's own traversable.  E.g. ((x1, x2),(y1, y2)).transpose = (x1, y1), (x2, y2).
          |  or ((x1, x2, x3),(y1, y2, y3),(z1, z2, z3)).transpose = ((x1, y1, z1), (x2, y2, z2), (x3, y3, z3))""") {
    val list = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
    list.transpose should be(List(List(1, 4, 7), List(2, 5, 8), List(3, 6, 9)))

    val list2 = List(List(1), List(4))
    list2.transpose should be(List(List(1, 4)))
  }

  koan( """`mkString` will format a Traversable using a given string as the delimiter.""") {
    val list = List(1, 2, 3, 4, 5)
    list.mkString(",") should be("1,2,3,4,5")
  }

  koan( """`mkString` will also take a beginning and ending string to surround the list.""") {
    val list = List(1, 2, 3, 4, 5)
    list.mkString(">", ",", "<") should be(">1,2,3,4,5<")
  }

  koan( """`addString` will take a StringBuilder to add the contents of list into the builder.""") {
    val stringBuilder = new StringBuilder()
    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    stringBuilder.append("I want all numbers 6-12: ")
    list.filter(it => it > 5 && it < 13).addString(stringBuilder, ",")
    stringBuilder.mkString should be("I want all numbers 6-12: 6,7,8,9,10,11,12")
  }

  koan("Traversables can have views which allow you to efficiently do compound work.") {
    val lst = List(1, 2, 3)
    var history = List[String]()

    def addHistory(s: String) {
      history = history :+ s
    }

    lst.map { x => addHistory("Doubling %s".format(x)); x * 2}.map
            { x => addHistory("Adding 1 to %s".format(x)); x + 1}

    history(0) should be("Doubling 1")
    history(1) should be("Doubling 2")
    history(2) should be("Doubling 3")
    history(3) should be("Adding 1 to 2")
    history(4) should be("Adding 1 to 4")
    history(5) should be("Adding 1 to 6")

    history = List[String]()

    lst.view.map { x => addHistory("Doubling %s".format(x)); x * 2}.map {
                   x => addHistory("Adding 1 to %s".format(x)); x + 1}.force

    history(0) should be("Doubling 1")
    history(1) should be("Adding 1 to 2")
    history(2) should be("Doubling 2")
    history(3) should be("Adding 1 to 4")
    history(4) should be("Doubling 3")
    history(5) should be("Adding 1 to 6")
  }

  koan( """Views can also accept a `to` and `from` value which takes a subset and performs your view
          |  functions on the subset.""") {
    val list = List(1, 2, 3, 4, 5, 6, 7, 8)
    list.view(3, 6).map(_ + 2).map(_ * 10).force should be(List(60, 70, 80))
  }
}
