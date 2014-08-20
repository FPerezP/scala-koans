package org.functionalkoans.forscala

import org.functionalkoans.forscala.support.KoanSuite


class AboutExtractors extends KoanSuite {
  koan("When you create a case class, it automatically can be used with " +
    "pattern matching since it has an extractor") {
    case class Employee(firstName: String, lastName: String)

    val rob = new Employee("Robin", "Williams")
    val result = rob match {
      case Employee("Robin", _) => "Where's Batman?"
      case _ => "No Batman Joke For You"
    }

    result should be("Where's Batman?")
  }


  koan(
    """What's an extractor? In Scala it's a method in any `object` called `unapply`, and that method
      | is used to disassemble the object given by returning a tuple wrapped in an option. Extractors can be used
      | to assign values.""") {

    class Car(val make: String, val model: String, val year: Short, val topSpeed: Short)

    object ChopShop {
      def unapply(x: Car) = Some(x.make, x.model, x.year, x.topSpeed)
    }

    val ChopShop(a, b, c, d) = new Car("Chevy", "Camaro", 1978, 120)

    a should be("Chevy")
    b should be("Camaro")
    c should be(1978)
    d should be(120)
  }


  koan( """Of course an extractor can be used in pattern matching...""") {
    class Car(val make: String, val model: String, val year: Short, val topSpeed: Short)

    object ChopShop {
      def unapply(x: Car) = Some(x.make, x.model, x.year, x.topSpeed)
    }

    val x = new Car("Chevy", "Camaro", 1978, 120) match {
      case ChopShop(s, t, u, v) => (s, t)
      case _ => ("Ford", "Edsel")
    }

    x._1 should be("Chevy")
    x._2 should be("Camaro")
  }

  koan(
    """Since we aren't really using u and v in the previous pattern matching with can replace them with _.""") {
    class Car(val make: String, val model: String, val year: Short, val topSpeed: Short)

    object ChopShop {
      def unapply(x: Car) = Some(x.make, x.model, x.year, x.topSpeed)
    }


    val x = new Car("Chevy", "Camaro", 1978, 120) match {
      case ChopShop(s, t, _, _) => (s, t)
      case _ => ("Ford", "Edsel")
    }

    x._1 should be("Chevy")
    x._2 should be("Camaro")
  }

  koan("As long as the method signatures aren't the same, " +
    "you can have an many unapply methods as you want") {
    class Car(val make: String, val model: String, val year: Short, val topSpeed: Short)
    class Employee(val firstName: String, val middleName: Option[String], val lastName: String)

    object Tokenizer {
      def unapply(x: Car) = Some(x.make, x.model, x.year, x.topSpeed)

      def unapply(x: Employee) = Some(x.firstName, x.lastName)
    }

    val result = new Employee("Kurt", None, "Vonnegut") match {
      case Tokenizer(c, d) => "c: %s, d: %s".format(c, d)
      case _ => "Not found"
    }

    result should be("c: Kurt, d: Vonnegut")
  }

  koan(
    """An extractor can be any stable object, including instantiated classes with an unapply method.""") {

    class Car(val make: String, val model: String, val year: Short, val topSpeed: Short) {
      def unapply(x: Car) = Some(x.make, x.model)
    }

    val camaro = new Car("Chevy", "Camaro", 1978, 122)

    val result = camaro match {
      case camaro(make, model) => "make: %s, model: %s".format(make, model)
      case _ => "unknown"
    }

    result should be("make: Chevy, model: Camaro")
  }

  koan(
    """What is typical is to create a custom extractor in the companion object of the class.
      | In this koan, we use it as an assignment""") {

    class Employee(val firstName: String,
                   val middleName: Option[String],
                   val lastName: String)

    object Employee {
      //factory methods, extractors, apply
      //Extractor: Create tokens that represent your object
      def unapply(x: Employee) =
        Some(x.lastName, x.middleName, x.firstName)
    }

    val singri = new Employee("Singri", None, "Keerthi")

    val Employee(a, b, c) = singri

    a should be("Keerthi")
    b should be(None)
    c should be("Singri")
  }

  koan("In this koan we use the unapply for pattern matching employee objects") {

    class Employee(val firstName: String,
                   val middleName: Option[String],
                   val lastName: String)

    object Employee {
      //factory methods, extractors, apply
      //Extractor: Create tokens that represent your object
      def unapply(x: Employee) =
        Some(x.lastName, x.middleName, x.firstName)
    }

    val singri = new Employee("Singri", None, "Keerthi")

    val result = singri match {
      case Employee("Singri", None, x) => "Yay, Singri %s! with no middle name!".format(x)
      case Employee("Singri", Some(x), _) => "Yay, Singri with a middle name of %s".format(x)
      case _ => "I don't care, going on break"
    }

    result should be("I don't care, going on break")
  }
}
