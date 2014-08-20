package org.functionalkoans.forscala

import support.KoanSuite

class AboutTraits extends KoanSuite {
  koan("A class uses the extends keyword to mixin a trait if it is the only relationship the class inherits") {
    case class Event(name: String)

    trait EventListener {
      def listen(event: Event): String
    }


    class MyListener extends EventListener {
      def listen(event: Event): String = {
        event match {
          case Event("Moose Stampede") => "An unfortunate moose stampede occurred"
          case _ => "Nothing of importance occurred"
        }
      }
    }

    val evt = Event("Moose Stampede")
    val myListener = new MyListener
    myListener.listen(evt) should be ("An unfortunate moose stampede occurred")
  }

  koan("A class can only \'extend\' from one class or trait, any subsequent extension should use the keyword \'with\'") {

    case class Event(name: String)

    trait EventListener {
      def listen(event: Event): String
    }

    class OurListener

    class MyListener extends OurListener with EventListener {
      def listen(event: Event) : String = {
        event match {
          case Event("Woodchuck Stampede") => "An unfortunate woodchuck stampede occurred"
          case _ => "Nothing of importance occurred"
        }
      }
    }

    val evt = Event("Woodchuck Stampede")
    val myListener = new MyListener
    myListener.listen(evt) should be ("An unfortunate woodchuck stampede occurred")
  }

  koan("Traits are polymorphic. Any type can be referred to by another type if related by extension") {
    case class Event(name: String)

    trait EventListener {
      def listen(event: Event): String
    }

    class MyListener extends EventListener {
      def listen(event: Event) : String = {
        event match {
          case Event("Moose Stampede") => "An unfortunate moose stampede occurred"
          case _ => "Nothing of importance occurred"
        }
      }
    }

    val myListener = new MyListener

    myListener.isInstanceOf[MyListener] should be(true)
    myListener.isInstanceOf[EventListener] should be(true)
    myListener.isInstanceOf[Any] should be(true)
    myListener.isInstanceOf[AnyRef] should be(true)
  }

  koan("Traits can have concrete implementations that can be mixed into concrete classes with it's own state") {
    trait Logging {
      var logCache = List[String]()

      def log(value: String) = {
        logCache = logCache :+ value
        println(value)
      }
    }

    class Welder extends Logging {
      def weld() {
        log("welding pipe")
      }
    }

    class Baker extends Logging {
      def bake() {
        log("baking cake")
      }
    }

    val welder = new Welder
    welder.weld()


    val baker = new Baker
    baker.bake()

    welder.logCache.size should be(1)
    baker.logCache.size should be(1)
  }

  koan("Traits are instantiated before a classes instantiation") {
    var sb = List[String]()

    trait T1 {
      sb = sb :+ "In T1: x=%s".format(x)
      val x = 1
      sb = sb :+ "In T1: x=%s".format(x)
    }

    class C1 extends T1 {
      sb = sb :+ "In C1: y=%s".format(y)
      val y = 2
      sb = sb :+ "In C1: y=%s".format(y)
    }

    sb = sb :+ "Creating C1"
    new C1
    sb = sb :+ "Created C1"

    println("=====")
    println(sb.mkString(";"))
    sb.mkString(";") should be("Creating C1;In T1: x=0;In T1: x=1;In C1: y=0;In C1: y=2;Created C1")
  }


  koan("Traits are instantiated before a classes instantiation from left to right") {
    var sb = List[String]()

    trait T1 {
      sb = sb :+ "In T1: x=%s".format(x)
      val x = 1
      sb = sb :+ "In T1: x=%s".format(x)
    }

    trait T2 {
      sb = sb :+ "In T2: z=%s".format(z)
      val z = 1
      sb = sb :+ "In T2: z=%s".format(z)
    }

    class C1 extends T1 with T2 {
      sb = sb :+ "In C1: y=%s".format(y)
      val y = 2
      sb = sb :+ "In C1: y=%s".format(y)
    }

    sb = sb :+ "Creating C1"
    new C1
    sb = sb :+ "Created C1"

    sb.mkString(";") should be("Creating C1;In T1: x=0;In T1: x=1;In T2: z=0;In T2: z=1;In C1: y=0;In C1: y=2;Created C1")
  }

  koan("Instantiations are tracked and will not allow a duplicate instantiation. " +
    "Note T1 extends T2, and C1 also extends T2, but T2 is only instantiated once.") {

    var sb = List[String]()

    trait T1 extends T2 {
      sb = sb :+ "In T1: x=%s".format(x)
      val x = 1
      sb = sb :+ "In T1: x=%s".format(x)
    }

    trait T2 {
      sb = sb :+ "In T2: z=%s".format(z)
      val z = 1
      sb = sb :+ "In T2: z=%s".format(z)
    }

    class C1 extends T1 with T2 {
      sb = sb :+ "In C1: y=%s".format(y)
      val y = 2
      sb = sb :+ "In C1: y=%s".format(y)
    }

    sb = sb :+ "Creating C1"
    new C1
    sb = sb :+ "Created C1"

    sb.mkString(";") should be("Creating C1;In T2: z=0;In T2: z=1;In T1: x=0;In T1: x=1;In C1: y=0;In C1: y=2;Created C1")
  }


  koan("The diamond of death (http://en.wikipedia.org/wiki/Diamond_problem) is avoided since " +
    "instantiations are tracked and will not allow multiple instantiations") {

    var sb = List[String]()

    trait T1 {
      sb = sb :+ "In T1: x=%s".format(x)
      val x = 1
      sb = sb :+ "In T1: x=%s".format(x)
    }

    trait T2 extends T1 {
      sb = sb :+ "In T2: z=%s".format(z)
      val z = 2
      sb = sb :+ "In T2: z=%s".format(z)
    }

    trait T3 extends T1 {
      sb = sb :+ "In T3: w=%s".format(w)
      val w = 3
      sb = sb :+ "In T3: w=%s".format(w)
    }

    class C1 extends T2 with T3 {
      sb = sb :+ "In C1: y=%s".format(y)
      val y = 4
      sb = sb :+ "In C1: y=%s".format(y)
    }

    sb = sb :+ "Creating C1"
    new C1
    sb = sb :+ "Created C1"

    sb.mkString(";") should be("Creating C1;In T1: x=0;In T1: x=1;In T2: z=0;In T2: z=2;In T3: w=0;In T3: w=3;In C1: y=0;In C1: y=4;Created C1")
  }
}
