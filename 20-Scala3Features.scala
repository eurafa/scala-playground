/**
 * Scala 3 — New Language Features
 *
 * Key reminders about Scala 3 (Dotty) improvements:
 *   - Given/Using replaces implicits
 *   - Enums replace sealed + case objects
 *   - Union (|) and Intersection (&) types
 *   - Extension methods (instead of implicit classes)
 *   - Top-level definitions (no object wrapper needed)
 *   - Indentation-based syntax (optional)
 *   - Opaque types for zero-cost abstractions
 *   - Multiversal equality (CanEqual)
 *   - Match types (compile-time pattern matching on types)
 *   - Dependent function types
 */

@main def scala3Features(): Unit =

  println("=== Scala 3 Features ===\n")

  // --- Top-level definitions ---
  println("--- Top-Level Definitions ---")
  println("  (This file has no object wrapper in Scala 3!)")
  println("  Top-level methods, vals, types are now allowed.\n")

  // --- Given / Using (replaces implicit) ---
  println("--- Given/Using ---")

  trait Ord[T]:
    def compare(x: T, y: T): Int
    extension (x: T) def < (y: T) = compare(x, y) < 0
    extension (x: T) def > (y: T) = compare(x, y) > 0

  given intOrd: Ord[Int] with
    def compare(x: Int, y: Int): Int = x - y

  given listOrd[T](using ord: Ord[T]): Ord[List[T]] with
    def compare(x: List[T], y: List[T]): Int =
      (x, y) match
        case (Nil, Nil) => 0
        case (Nil, _)   => -1
        case (_, Nil)   => 1
        case (h1 :: t1, h2 :: t2) =>
          val cmp = ord.compare(h1, h2)
          if cmp != 0 then cmp else compare(t1, t2)

  def max[T](x: T, y: T)(using ord: Ord[T]): T =
    if ord.compare(x, y) >= 0 then x else y

  println(s"  max(3, 7) = ${max(3, 7)}")
  println(s"  max(List(1,2), List(3,4)) = ${max(List(1,2), List(3,4))}")

  // --- Extension methods ---
  println("\n--- Extension Methods ---")

  extension (s: String)
    def exclaim: String = s + "!"
    def repeat(n: Int): String = s * n
    def isPalindrome: Boolean = s == s.reverse

  println(s"  \"hello\".exclaim = ${"hello".exclaim}")
  println(s"  \"ha\".repeat(3) = ${"ha".repeat(3)}")
  println(s"  \"racecar\".isPalindrome = ${"racecar".isPalindrome}")

  // --- Union Types ---
  println("\n--- Union Types ---")

  def handle(input: Int | String): String =
    input match
      case i: Int    => s"Integer: $i"
      case s: String => s"String: ${s.length} chars"

  println(s"  handle(42) = ${handle(42)}")
  println(s"  handle(\"Scala 3\") = ${handle("Scala 3")}")

  // --- Intersection Types ---
  println("\n--- Intersection Types ---")

  trait Resettable:
    def reset(): Unit

  trait Growable[A]:
    def add(a: A): Unit

  def f(x: Resettable & Growable[String]): Unit =
    x.reset()
    x.add("first")

  println("  Intersection types: A & B requires both traits")

  // --- Opaque Types ---
  println("\n--- Opaque Types (zero-cost abstraction) ---")

  object Metrics:
    opaque type Temperature = Double

    object Temperature:
      def fromCelsius(c: Double): Temperature = c
      def fromFahrenheit(f: Double): Temperature = (f - 32) * 5 / 9

    extension (t: Temperature)
      def toCelsius: Double = t
      def toFahrenheit: Double = t * 9 / 5 + 32

  import Metrics.Temperature

  val boiling = Temperature.fromCelsius(100.0)
  // val wrong: Temperature = 42.0  // COMPILE ERROR! Opaque!

  println(s"  Opaque type: ${boiling.toCelsius}°C = ${boiling.toFahrenheit}°F")

  // --- Multiversal Equality (Safe equality) ---
  println("\n--- Multiversal Equality ---")

  println("""
    // Scala 3 prevents unsafe comparisons:
    // "42" == 42  // Compile error! (with -language:strictEquality)
    // Use CanEqual given instances for safe comparisons
  """)

  // --- Match Types ---
  println("\n--- Match Types (compile-time types) ---")

  type Elem[X] = X match
    case String => Char
    case List[t] => t
    case Array[t] => t

  val c: Elem[String] = 'A'     // Char
  val i: Elem[List[Int]] = 42   // Int
  // val s: Elem[Int] = ???     // Would not match

  println(s"  Match type Elem[String] = ${summon[Elem[String] =:= Char]}")
  println(s"  Pattern matching on types at compile time!")

  // --- Export Clauses ---
  println("\n--- Export Clauses ---")

  class MyList:
    private val inner = List(1, 2, 3)
    export inner.{isEmpty, head, tail, map, filter, flatMap}

  val myList = MyList()
  println(s"  Export: ${myList.map(_ * 2)}")

  // --- Scala 3 Migration Summary ---
  println("\n--- Scala 2 → Scala 3 Migration ---")
  println("""
    Scala 2              → Scala 3
    ───────────            ─────────
    implicit val          → given / using
    implicit class        → extension (x: T) def ...
    implicit def conv     → given Conversion[A, B]
    sealed trait + case   → enum
    A with B              → A & B (intersection)
    N/A                   → A | B (union)
    N/A                   → opaque type
    N/A                   → match types
    import pkg._          → import pkg.{*, given}
  """)
