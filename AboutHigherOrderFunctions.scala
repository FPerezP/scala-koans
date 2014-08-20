package org.functionalkoans.forscala

import support.KoanSuite

class AboutHigherOrderFunctions extends KoanSuite {

  koan("Meet lambda. Anonymous function") {
    def lambda = { x: Int => x + 1 }
    def lambda2 = (x:Int) => x + 1
    val lambda3 = (x:Int) => x + 1

    val lambda4 = new Function1[Int, Int] {
      def apply(v1: Int): Int = v1 + 1
    }

    def lambda5(x:Int) = x + 1


    val result = lambda(3)
    val `result1andhalf` = lambda.apply(3)

    val result2 = lambda2(3)
    val result3 = lambda3(3)
    val result4 = lambda4(3)
    val result5 = lambda5(3)

    result should be(4)
    result1andhalf should be(4)
    result2 should be(4)
    result3 should be(4)
    result4 should be(4)
    result5 should be(4)
  }

  koan("An anonymous function can also take on a different look by taking out the brackets") {
    def lambda = (x: Int) => x + 1
    def result = lambda(5)
    result should be(6)
  }

  koan("Meet closure. Closure is any function that closes over the environment") {
    var incrementer = 1

    def closure = {
      x: Int => x + incrementer
    }

    val result1 = closure(10)
    result1 should be(11)

    incrementer = 2

    val result2 = closure(10)
    result2 should be(12)
  }

  koan("We can take that closure and throw into a method and it will still hold the environment") {

    def summation(x:Int, y: Int => Int) = y(x)

    var incrementer = 3
    def closure = (x: Int) => x + incrementer

    val result = summation(10, closure)
    result should be(13)

    incrementer = 4
    val result2 = summation(10, closure)
    result2 should be(14)
  }

  koan("function returning another function") {
    def addWithoutSyntaxSugar(x: Int) = {
      new Function1[Int, Int]() {
        def apply(y: Int): Int = x + y
      }
    }
    addWithoutSyntaxSugar(1).
      isInstanceOf[Function1[Int,Int]] should be(true)

    addWithoutSyntaxSugar(2)(3) should be(5)

    def fiveAdder = addWithoutSyntaxSugar(5)
    fiveAdder(5) should be(10)
  }

  koan("function returning another function " +
    "using an anonymous function") {
    def addWithSyntaxSugar(x: Int) = (y:Int) => x + y

    addWithSyntaxSugar(1).isInstanceOf[Function1[Int,Int]] should be(true)
    addWithSyntaxSugar(2)(3) should be(5)

    def fiveAdder = addWithSyntaxSugar(5)
    fiveAdder(5) should be(10)
  }


  koan(
    """isInstanceOf is the same as instanceof in java, but in this case the parameter types can be
      | 'blanked out' using existential types with is a single underline, since parameter type are unknown
      | at runtime.""") {
    def addWithSyntaxSugar(x: Int) = (y:Int) => x + y

    addWithSyntaxSugar(1).isInstanceOf[Function1[Int, Int]] should be(true)
  }



  koan(
    """function taking another function as parameter. Helps in composing functions.
      | Hint: a map method applies the function to each element of a list""") {

    def makeUpper(xs: List[String]) = xs map {_.toUpperCase}

    def makeWhatEverYouLike(xs: List[String], sideEffect: String => String) = {
      xs map sideEffect
    }

    makeUpper(List("abc", "xyz", "123")) should be(List("ABC", "XYZ", "123"))

    makeWhatEverYouLike(List("ABC", "XYZ", "123"), {
      x => x.toLowerCase
    }) should be(List("abc", "xyz", "123"))

    //using it inline
    List("Scala", "Erlang", "Clojure") map {_.length} should be(List(5,6,7))
  }

  koan("Currying is a technique to transform function with multiple parameters to function with one parameter") {
    def multiply(x: Int, y: Int) = x * y
    (multiply _).isInstanceOf[Function2[_, _, _]] should be(true)
    val multiplyCurried = (multiply _).curried
    multiply(4, 5) should be(20)
    multiplyCurried(3)(2) should be(6)
  }

  koan("Currying allows you to create specialized version of generalized function") {
    def customFilter(f: Int => Boolean)(xs: List[Int]) = {
      xs filter f
    }
    def onlyEven(x: Int) = x % 2 == 0
    val xs = List(12, 11, 5, 20, 3, 13, 2)
    customFilter(onlyEven)(xs) should be(List(12, 20, 2))

    val onlyEvenFilter = customFilter(onlyEven) _
    onlyEvenFilter(xs) should be(List(12, 20, 2))

  }
}
