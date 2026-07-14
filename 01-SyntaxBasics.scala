// Scala — Syntax Basics

@main def syntaxBasics(): Unit =

  // --- val vs var ---
  println("=== val vs var ===")
  val immutable = 42          // Immutable binding (prefer this)
  // immutable = 43           // COMPILE ERROR!
  var mutable = 42            // Mutable binding
  mutable = 43                // OK
  println(s"  var mutable = $mutable")

  // --- Type inference ---
  println("\n=== Type Inference ===")
  val name = "Scala"                         // Inferred: String
  val count = 42                             // Inferred: Int
  val price = 19.99                          // Inferred: Double
  val isReady = true                         // Inferred: Boolean
  val list = List(1, 2, 3)                   // Inferred: List[Int]
  val map = Map("a" -> 1, "b" -> 2)          // Inferred: Map[String, Int]

  // Explicit type annotation (when needed)
  val explicit: String = "Hello"
  val annotated: List[Int] = List(1, 2, 3)

  // --- String interpolation ---
  println("\n=== String Interpolation ===")
  println(s"  Hello, $name! You have $count items")
  println(s"  Price: $$$price")              // Escaped $: $$price
  println(f"  Formatted: $price%.2f")         // Formatted: f-interpolator
  println(raw"  Raw string: \n doesn't escape") // raw-interpolator

  // --- if-else is an expression ---
  println("\n=== If-Else Expression ===")
  val age = 25
  val category = if age < 18 then "Minor" else "Adult"
  println(s"  Age $age → $category")

  val temperature = 30
  val weather = 
    if temperature > 35 then "Hot"
    else if temperature > 20 then "Warm"
    else "Cold"
  println(s"  $temperature°C → $weather")

  // --- match expression (replaces switch) ---
  println("\n=== Match Expression ===")
  val day = 3
  val dayName = day match
    case 1 => "Monday"
    case 2 => "Tuesday"
    case 3 => "Wednesday"
    case 4 => "Thursday"
    case 5 => "Friday"
    case 6 | 7 => "Weekend"
    case _ => "Invalid"
  println(s"  Day $day → $dayName")

  // Match on type
  val obj: Any = "Hello, Scala!"
  val description = obj match
    case s: String => s"String: ${s.length} chars"
    case i: Int    => s"Int: $i"
    case _         => "Unknown type"
  println(s"  Description: $description")

  // --- for loop ---
  println("\n=== For Loops ===")

  // Simple iteration
  for i <- 1 to 5 do
    print(s"$i ")
  println()

  // With guard (filter)
  for
    i <- 1 to 10
    if i % 2 == 0
  do
    print(s"$i ")
  println()

  // Nested iteration
  for
    i <- 1 to 3
    j <- 1 to 3
  do
    print(s"($i,$j) ")
  println()

  // --- for comprehension (yield) ---
  println("\n=== For Comprehension (yield) ===")
  val doubled = for i <- 1 to 5 yield i * 2
  println(s"  doubled: $doubled")

  val evens = for
    i <- 1 to 10
    if i % 2 == 0
  yield i * i
  println(s"  even squares: $evens")

  // --- while loop ---
  println("\n=== While Loop ===")
  var i = 0
  while i < 3 do
    print(s"  i=$i")
    i += 1
  println()

  // --- try-catch (expression) ---
  println("\n=== Try-Catch Expression ===")
  val result = try
    "42".toInt
  catch
    case e: NumberFormatException =>
      println(s"  Error: ${e.getMessage}")
      -1
  finally
    println("  Finally block (always runs)")
  println(s"  Result: $result")

  // --- println & readLine ---
  println("\n=== I/O Basics ===")
  println("  Print with newline (println)")
  print("  Print without newline")
  println(" ← continues on same line")
  printf("  Formatted: %s is %d years old%n", "Alice", 30)

  // Uncomment to read input:
  // val input = scala.io.StdIn.readLine("Enter something: ")
  // println(s"You entered: $input")
