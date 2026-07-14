// Scala — Option Handling (Maybe monad)

@main def optionDemo(): Unit =

  println("=== Option Handling ===\n")

  // --- Creating Options ---
  println("--- Creating Options ---")
  val some: Option[Int] = Some(42)
  val none: Option[Int] = None
  val fromNullable = Option("hello")
  val fromNull = Option(null)

  println(s"  Some(42):        $some")
  println(s"  None:            $none")
  println(s"  Option(null):    $fromNull")

  // --- Mapping Options ---
  println("\n--- Mapping ---")
  val mapped = some.map(_ * 2)
  val noneMapped = none.map(_ * 2)

  println(s"  Some(42).map(_*2):      $mapped")
  println(s"  None.map(_*2):          $noneMapped")

  // --- flatMap (for chaining optional operations) ---
  println("\n--- flatMap ---")
  def safeDivide(a: Int, b: Int): Option[Int] =
    if b == 0 then None else Some(a / b)

  val chain1 = safeDivide(10, 2).flatMap(r => safeDivide(r, 0))
  val chain2 = safeDivide(10, 2).flatMap(r => safeDivide(r, 2))

  println(s"  (10/2)/0 = $chain1")
  println(s"  (10/2)/2 = $chain2")

  // --- getOrElse / orElse ---
  println("\n--- Getting Values ---")
  println(s"  Some(42).getOrElse(0):        ${some.getOrElse(0)}")
  println(s"  None.getOrElse(0):            ${none.getOrElse(0)}")
  println(s"  None.orElse(Some(99)):        ${none.orElse(Some(99))}")

  // --- filter ---
  println("\n--- filter ---")
  println(s"  Some(42).filter(_ > 10):      ${some.filter(_ > 10)}")
  println(s"  Some(42).filter(_ > 100):     ${some.filter(_ > 100)}")

  // --- Pattern matching ---
  println("\n--- Pattern Matching ---")
  def describe(opt: Option[Int]): String = opt match
    case Some(v) if v > 100 => s"Large value: $v"
    case Some(v)            => s"Value: $v"
    case None               => "No value"

  println(s"  ${describe(Some(42))}")
  println(s"  ${describe(Some(999))}")
  println(s"  ${describe(None)}")

  // --- Real-world: safe lookup pattern ---
  println("\n--- Safe Lookup ---")
  val config = Map(
    "host" -> "localhost",
    "port" -> "8080",
    "debug" -> "true"
  )

  def getPort: Option[Int] =
    config.get("port").flatMap(s => s.toIntOption)

  val port = getPort.getOrElse(80)
  println(s"  Port: $port")

  // --- Folding ---
  println("\n--- fold ---")
  val result1 = some.fold("No value")(v => s"Got: $v")
  val result2 = none.fold("No value")(v => s"Got: $v")
  println(s"  fold on Some:  $result1")
  println(s"  fold on None:  $result2")

  // --- Option in collections ---
  println("\n--- Options in Collections ---")
  val optionals = List(Some(1), None, Some(2), None, Some(3))

  val flattened = optionals.flatMap(_.toList)
  println(s"  flatMap:       $flattened")

  val collected = optionals.collect { case Some(v) => v }
  println(s"  collect:       $collected")
