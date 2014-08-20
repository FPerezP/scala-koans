package org.functionalkoans.forscala

import support.KoanSuite
import org.scalatest.matchers.ShouldMatchers

class AboutPartiallyAppliedFunctions extends KoanSuite with ShouldMatchers {
  koan("""A partially applied function is a function that you do not apply any or all the
         | arguments, creating another function. This partially applied function
         | doesn't apply any arguments""") {
    def sum(a: Int, b: Int, c: Int) = a + b + c
    val sum3 = sum _
    sum3(1, 9, 7) should be(17)
    sum(4, 5, 6) should be(15)
  }

  koan("""Partially applied functions can replace any number of arguments""") {
    def sum(a: Int, b: Int, c: Int) = a + b + c
    val sumC = sum(1, 10, _: Int)
    sumC(4) should be(15)
    sum(4, 5, 6) should be(15)
  }
}
