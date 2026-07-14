// Scala — Immutability & Functional Style

@main def immutabilityDemo(): Unit =

  // --- val vs var ---
  println("=== val (immutable) vs var (mutable) ===")
  val fixed = List(1, 2, 3)
  // fixed = List(4, 5, 6)  // COMPILE ERROR!

  var mutable = List(1, 2, 3)
  mutable = List(4, 5, 6)     // OK (but discouraged)
  println(s"  Mutable var: $mutable")

  // --- Immutable collections ---
  println("\n=== Immutable Collections ===")
  import scala.collection.immutable.{Vector, HashSet, HashMap}

  val vec = Vector(1, 2, 3)          // Immutable vector
  val set = HashSet(1, 2, 3)         // Immutable hash set
  val map = HashMap("a" -> 1, "b" -> 2)

  // "Modifications" return NEW collections
  val extended = vec :+ 4            // New vector with 4 appended
  val prepended = 0 +: vec           // New vector with 0 prepended

  println(s"  Original: $vec")
  println(s"  Extended: $extended")
  println(s"  Prepended: $prepended")

  // --- Updating (creating modified copies) ---
  println("\n=== Creating Modified Copies ===")
  case class Person(name: String, age: Int)

  val alice = Person("Alice", 30)
  val olderAlice = alice.copy(age = 31)     // Returns new instance!
  val renamedAlice = alice.copy(name = "Alice Smith")

  println(s"  Original: $alice")
  println(s"  Older:    $olderAlice")
  println(s"  Renamed:  $renamedAlice")

  // --- Pure functions ---
  println("\n=== Pure Functions ===")

  // PURE: same input → same output, no side effects
  def pureAdd(x: Int, y: Int): Int = x + y

  // IMPURE: has side effects (printing, file I/O, etc.)
  def impureAdd(x: Int, y: Int): Int =
    println(s"  Adding $x + $y")     // side effect!
    x + y

  println(s"  Pure: ${pureAdd(2, 3)}")
  println(s"  Impure: ${impureAdd(2, 3)}")

  // --- Referential transparency ---
  println("\n=== Referential Transparency ===")

  // RT: expression can be safely replaced by its value
  def rtAdd(x: Int, y: Int): Int =
    x + y

  // These two are equivalent:
  val a = rtAdd(2, 3) + rtAdd(2, 3)
  val b = 5 + 5                      // Replaced rtAdd(2,3) with its value
  println(s"  RT: $a == $b")

  // --- Side-effect-free operations ---
  println("\n=== Side-Effect-Free Transformations ===")
  val numbers = List(1, 2, 3, 4, 5)

  val transformed =
    numbers
      .filter(_ % 2 == 0)            // Keep even numbers
      .map(_ * 10)                   // Multiply by 10
      .take(3)                       // Take first 3 results

  println(s"  Original:     $numbers")
  println(s"  Transformed:  $transformed")
  println(s"  Original (unchanged): $numbers")   // Original not mutated!

  // --- Local mutability (acceptable) ---
  println("\n=== Local Mutability ===")
  def sumWithVar(list: List[Int]): Int =
    var total = 0                    // Mutable locally, but function is pure
    for i <- list do
      total += i
    total

  def sumWithVal(list: List[Int]): Int =
    list.foldLeft(0)(_ + _)          // FP style: no vars

  println(s"  With var:  ${sumWithVar(List(1, 2, 3))}")
  println(s"  With fold: ${sumWithVal(List(1, 2, 3))}")

  // --- Lazy val (deferred evaluation) ---
  println("\n=== Lazy val ===")
  lazy val expensive = {
    println("  Computing expensive value...")
    42
  }

  println("  Before accessing lazy val")
  println(s"  expensive = $expensive")   // Computed now
  println(s"  expensive (cached) = $expensive")  // Already computed
