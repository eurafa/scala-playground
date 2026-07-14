// Scala — Higher-Order Functions

@main def higherOrderFunctionsDemo(): Unit =

  println("=== Higher-Order Functions ===\n")

  // --- Functions taking functions ---
  println("--- Function as Parameter ---")

  def twice(f: Int => Int, x: Int): Int = f(f(x))

  val add1 = (x: Int) => x + 1
  println(s"  twice(add1, 5) = ${twice(add1, 5)}")
  println(s"  twice(_ * 2, 3) = ${twice(_ * 2, 3)}")

  // --- Functions returning functions ---
  println("\n--- Function Returning Function ---")

  def multiplier(factor: Int): Int => Int =
    (x: Int) => x * factor

  val double = multiplier(2)
  val triple = multiplier(3)

  println(s"  double(5) = ${double(5)}")
  println(s"  triple(5) = ${triple(5)}")

  // --- Custom combinator: applyIf ---
  println("\n--- Custom Combinator ---")

  def applyIf[A](pred: A => Boolean)(f: A => A)(x: A): A =
    if pred(x) then f(x) else x

  val incrementEvens = applyIf[Int](_ % 2 == 0)(_ + 1) _
  println(s"  incrementEvens(3) = ${incrementEvens(3)}")    // 3 (odd)
  println(s"  incrementEvens(4) = ${incrementEvens(4)}")    // 5 (even)

  // --- Function composition ---
  println("\n--- Function Composition ---")

  val square = (x: Int) => x * x
  val negate = (x: Int) => -x

  // compose: apply square THEN negate
  val squareThenNegate = negate.compose(square)
  // andThen: apply negate THEN square
  val negateThenSquare = negate.andThen(square)

  println(s"  squareThenNegate(3) = ${squareThenNegate(3)}")    // -(3^2) = -9
  println(s"  negateThenSquare(3) = ${negateThenSquare(3)}")    // (-3)^2 = 9

  // --- Placeholder syntax ---
  println("\n--- Placeholder Syntax ---")

  val nums = List(1, 2, 3, 4, 5)
  println(s"  map(_ * 2):        ${nums.map(_ * 2)}")
  println(s"  filter(_ > 3):     ${nums.filter(_ > 3)}")
  println(s"  reduce(_ + _):     ${nums.reduce(_ + _)}")
  println(s"  sortWith(_ > _):   ${nums.sortWith(_ > _)}")

  // --- Eta-expansion (method → function) ---
  println("\n--- Eta-Expansion ---")

  def isEven(n: Int): Boolean = n % 2 == 0
  def multiply(a: Int, b: Int): Int = a * b

  val isEvenFn: Int => Boolean = isEven     // Method lifted to function
  val multiplyFn: (Int, Int) => Int = multiply

  println(s"  List(1,2,3,4).filter(isEven): ${List(1, 2, 3, 4).filter(isEven)}")

  // --- Lifting regular methods ---
  println("\n--- Lifting Partial Functions ---")

  val safeDiv: PartialFunction[(Int, Int), Int] = {
    case (a, b) if b != 0 => a / b
  }

  val lifted: ((Int, Int)) => Option[Int] = safeDiv.lift
  println(s"  lifted(10, 2) = ${lifted(10, 2)}")
  println(s"  lifted(10, 0) = ${lifted(10, 0)}")

  // --- Custom control structures ---
  println("\n--- Custom Control Structures ---")

  def unless(condition: => Boolean)(block: => Unit): Unit =
    if !condition then block

  var debugMode = false
  unless(debugMode):
    println("  This runs because debugMode is false")

  debugMode = true
  unless(debugMode):
    println("  This doesn't run")

  // --- Closures ---
  println("\n--- Closures ---")

  def makeCounter(): () => Int =
    var count = 0
    () =>
      count += 1
      count

  val counter = makeCounter()
  println(s"  counter(): ${counter()}")   // 1
  println(s"  counter(): ${counter()}")   // 2
  println(s"  counter(): ${counter()}")   // 3
