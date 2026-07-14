// Scala — Implicits (Scala 2) / Given & Using (Scala 3)

// Scala 2 style (compatible with Scala 3)
@main def implicitsDemo(): Unit =

  println("=== Implicits & Given/Using ===\n")

  // --- Implicit parameters (Scala 2) ---
  println("--- Implicit Parameters (Scala 2) ---")

  case class Config(host: String, port: Int)

  def connect(implicit config: Config): String =
    s"Connecting to ${config.host}:${config.port}"

  implicit val defaultConfig: Config = Config("localhost", 8080)

  // Implicit is automatically resolved!
  println(s"  ${connect}")

  // Can still pass explicitly
  println(s"  ${connect(Config("example.com", 9090))}")

  // --- Given/Using (Scala 3) ---
  println("\n--- Given/Using (Scala 3 style) ---")
  println("""
    // Scala 3 syntax:
    given appConfig: Config = Config("localhost", 8080)
    given Int = 42   // anonymous given
    
    def connect(using config: Config): String = ???
    def sum(using n: Int): Int = n + 10
  """)

  // --- Type class example (scala.math.Ordering) ---
  println("\n--- Type Classes: Ordering ---")

  val numbers = List(3, 1, 4, 1, 5, 9, 2, 6)

  // sort takes implicit Ordering[Int]
  println(s"  Natural order:    ${numbers.sorted}")
  println(s"  Reverse order:    ${numbers.sorted(Ordering[Int].reverse)}")

  // Custom sorting
  case class Person(name: String, age: Int)
  val people = List(Person("Alice", 30), Person("Bob", 25), Person("Charlie", 35))

  implicit val ageOrdering: Ordering[Person] = Ordering.by(_.age)
  println(s"  Sorted by age:    ${people.sorted}")

  // --- Context bounds [T: TypeClass] ---
  println("\n--- Context Bounds ---")

  def minOfList[A: Ordering](list: List[A]): A =
    list.min  // Ordering[A] is automatically available

  println(s"  min of numbers:   ${minOfList(numbers)}")
  println(s"  min of strings:   ${minOfList(List("d", "a", "c", "b"))}")

  // --- Extension methods (Scala 3 style) ---
  println("\n--- Extension Methods ---")
  println("""
    // Scala 2: implicit class RichString(s: String) { ... }
    // Scala 3: extension (s: String) def exclaim: String = s + "!"
  """)

  // Scala 2 style implicit class:
  implicit class RichInt(val n: Int) extends AnyVal:
    def isEven: Boolean = n % 2 == 0
    def isOdd: Boolean = !isEven
    def squared: Int = n * n

  println(s"  4.isEven = ${4.isEven}")
  println(s"  7.isOdd = ${7.isOdd}")
  println(s"  5.squared = ${5.squared}")

  // --- Implicit conversions (use sparingly!) ---
  println("\n--- Implicit Conversions (caution!) ---")
  println("""
    // Avoid implicit conversions! Prefer extension methods.
    // implicit def strToInt(s: String): Int = s.toInt
    // val x: Int = "42"  // would work but is confusing
  """)

  // --- Organizing implicits ---
  println("\n--- Implicit Resolution Order ---")
  println("""
    1. Local scope (current block)
    2. Imported (import foo._)
    3. Companion object of the type
    4. Package object (discouraged in Scala 3)
  """)
