// Scala — Case Classes & Algebraic Data Types (ADTs)

@main def caseClassesDemo(): Unit =

  // --- Case classes basics ---
  println("=== Case Classes ===")

  case class Person(name: String, age: Int)

  // No 'new' keyword needed
  val alice = Person("Alice", 30)
  val bob   = Person("Bob", 25)

  println(s"  $alice")                              // Auto toString
  println(s"  Name: ${alice.name}, Age: ${alice.age}")  // Field accessors
  println(s"  equals: ${alice == Person("Alice", 30)}")   // Auto equals
  println(s"  hashCode: ${alice.hashCode}")              // Auto hashCode

  // --- copy() method ---
  println("\n=== Copy ===")
  val olderAlice = alice.copy(age = 31)
  val renamedBob = bob.copy(name = "Robert")
  println(s"  Original: $alice")
  println(s"  Older:    $olderAlice")
  println(s"  Renamed:  $renamedBob")

  // --- Pattern matching on case classes ---
  println("\n=== Pattern Matching ===")
  def describe(p: Person): String = p match
    case Person("Alice", age) if age > 30 => "Older Alice"
    case Person("Alice", _)               => "Young Alice"
    case Person(name, age) if age < 18    => s"$name is a minor"
    case Person(name, age)                => s"$name is $age years old"

  println(s"  ${describe(Person("Alice", 35))}")
  println(s"  ${describe(Person("Alice", 25))}")
  println(s"  ${describe(Person("Bob", 16))}")
  println(s"  ${describe(Person("Charlie", 40))}")

  // --- Sealed trait ADT (Algebraic Data Type) ---
  println("\n=== Sealed Trait ADT ===")
  sealed trait Shape
  case class Circle(radius: Double) extends Shape
  case class Rectangle(width: Double, height: Double) extends Shape
  case object Origin extends Shape                    // Singleton

  def area(shape: Shape): Double = shape match
    case Circle(r)           => math.Pi * r * r
    case Rectangle(w, h)     => w * h
    case Origin              => 0.0
    // No default needed — sealed means exhaustive!

  println(s"  Circle(5) area:    ${area(Circle(5))}")
  println(s"  Rectangle(3,4) area: ${area(Rectangle(3, 4))}")
  println(s"  Origin area:           ${area(Origin)}")

  // --- Optional values (Option = Some/None ADT) ---
  println("\n=== Option ADT ===")
  def safeDivide(a: Int, b: Int): Option[Double] =
    if b == 0 then None
    else Some(a.toDouble / b)

  safeDivide(10, 2) match
    case Some(result) => println(s"  10/2 = $result")
    case None         => println("  Division by zero!")

  safeDivide(10, 0) match
    case Some(result) => println(s"  10/0 = $result")
    case None         => println("  Division by zero!")

  // --- Case objects ---
  println("\n=== Case Objects ===")
  sealed trait Status
  case object Active extends Status
  case object Inactive extends Status
  case object Pending extends Status

  def statusMessage(s: Status): String = s match
    case Active   => "User is active"
    case Inactive => "User is inactive"
    case Pending  => "User creation is pending"

  println(s"  ${statusMessage(Active)}")
  println(s"  ${statusMessage(Pending)}")

  // --- Generic case classes ---
  println("\n=== Generic Case Classes ===")
  case class Box[A](value: A)

  val intBox = Box(42)
  val strBox = Box("Hello")
  val listBox = Box(List(1, 2, 3))

  println(s"  intBox:  $intBox")
  println(s"  strBox:  $strBox")
  println(s"  listBox: $listBox")

  // --- Case class with default values ---
  println("\n=== Default Values ===")
  case class Config(
    host: String = "localhost",
    port: Int = 8080,
    debug: Boolean = false
  )

  val default = Config()
  val custom = Config(host = "example.com", debug = true)

  println(s"  Default: $default")
  println(s"  Custom:  $custom")

  // --- Destructuring in variable declarations ---
  println("\n=== Destructuring ===")
  val person = Person("Alice", 30)
  val Person(name, age) = person
  println(s"  Destructured: name = $name, age = $age")
