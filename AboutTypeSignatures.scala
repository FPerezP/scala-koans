package org.functionalkoans.forscala

import support.KoanSuite

class AboutTypeSignatures extends KoanSuite {
  koan("In Java you declare a generic type within a <>, in Scala it is []") {
    val z: List[String] = "Do" :: "Re" :: "Mi" :: "Fa" :: "So" :: "La" :: "Te" :: "Do" :: Nil
  }

  koan("Most of the time, Scala will infer the type and [] are optional") {
    val z = "Do" :: "Re" :: "Mi" :: "Fa" :: "So" :: "La" :: "Te" :: "Do" :: Nil //Infers that the list assigned to variable is of type List[String]
  }

  koan("A trait can be declared containing a type, where a concrete implementer will satisfy the type") {
    trait Randomizer[A] {
      def draw(): A
    }

    class IntRandomizer extends Randomizer[Int] {
      def draw() = {
        import util.Random
        Random.nextInt()
      }
    }

    val intRand = new IntRandomizer
    (intRand.draw < Int.MaxValue) should be (true)
  }

  koan("Class meta-information can be retrieved by class name by using classOf[className]") {
    classOf[String].getCanonicalName should be("java.lang.String")
    classOf[String].getSimpleName should be("String")
  }

  koan("Class meta-information can be derived from an object reference using getClass()") {
    val zoom = "zoom"
    zoom.getClass should be(classOf[String]) // Hint: classOf ...
    zoom.getClass.getCanonicalName should be("java.lang.String")
    zoom.getClass.getSimpleName should be("String")
  }

  koan("isInstanceOf[className] is used to determine the if an object reference is an instance of given class") {
    trait Randomizer[A] {
      def draw(): A
    }

    class IntRandomizer extends Randomizer[Int] {
      def draw() = {
        import util.Random
        Random.nextInt()
      }
    }

    val intRand = new IntRandomizer
    intRand.draw.isInstanceOf[Int] should be(true)
  }

  koan("asInstanceOf[className] is used to cast one reference to another") {
    trait Randomizer[A] {
      def draw(): A
    }

    class IntRandomizer extends Randomizer[Int] {
      def draw = {
        import util.Random
        Random.nextInt()
      }
    }

    val intRand = new IntRandomizer
    val rand = intRand
    val intRand2 = rand
    intRand2.isInstanceOf[IntRandomizer] should be(true)
  }

  koan("asInstanceOf[className] will throw a ClassCastException if a class derived from " +
    "and the class target aren't from the same inheritance branch") {
    trait Randomizer[A] {
      def draw(): A
    }

    class IntRandomizer extends Randomizer[Int] {
      def draw() = {
        import util.Random
        Random.nextInt()
      }
    }

    val intRand = new IntRandomizer

    intercept[ClassCastException] {
      intRand.asInstanceOf[String] //intRand cannot be cast to String
    }
  }

  koan("null.asInstanceOf[className] can be used to generate basic default values") {
    null.asInstanceOf[String] should be(null)
    null.asInstanceOf[Int] should be(0)
    null.asInstanceOf[Short] should be(0)
  }
}
