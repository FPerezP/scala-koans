package org.functionalkoans.forscala

import org.functionalkoans.forscala.support.KoanSuite

class AboutMethods extends KoanSuite {

  koan(
    """In scala, methods can be placed inside in methods, this comes useful for
      | recursion where accumulator helper methods can be placed inside the outer method""") {
    def factorial(i: Int): Int = {
      def fact(i: Int, accumulator: Int): Int = {
        if (i <= 1)
          accumulator
        else
          fact(i - 1, i * accumulator)
      }
      fact(i, 1)
    }

    factorial(3) should be (6)
  }

  koan("""If a method does not of have equal it is considered `Unit` which is analogous to `void` in Java""")  {
    def foo(x:Int) {
      (x + 4) should be (9)
    }

    foo(5)
  }

  koan(
    """If you want to have an = on the method you can make the return type `Unit`,
      | this also analogous to `void""")  {
    def foo(x:Int):Unit = {
      (x + 4) should be (7)
    }

    foo(3)
  }

  koan("""Once you have an =, it is understood that there will be a return type and can be inferred""") {
     def foo(x:Int) = 3 + 4
     foo(3).isInstanceOf[Int] should be (true)
  }

  koan("""Of course if you wish to be explicit about the return type, you can attach it at the end of the method""") {
    def foo(x:Int):Int = 3 + 4
    foo(3).isInstanceOf[Int] should be (true)
  }

  koan("""As an important concept in function programming and in Scala, a method is also a function""")  {
    def foo(x:Int) = x * 2
    val result = List(1,2,3).map(foo) //map performs a function on each element
    result should be (List(2, 4, 6))
  }
}
