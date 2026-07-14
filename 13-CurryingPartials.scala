// Scala — Currying & Partial Application

@main def curryingDemo(): Unit =

  println("=== Currying & Partial Application ===\n")

  // --- Curried function (multiple parameter lists) ---
  println("--- Curried Functions ---")

  def add(x: Int)(y: Int): Int = x + y
  println(s"  add(5)(3) = ${add(5)(3)}")

  // Partial application
  val add5 = add(5) _        // or add(5)
  println(s"  add5(10) = ${add5(10)}")
  println(s"  add5(20) = ${add5(20)}")

  // --- Regular to curried (eta-expansion) ---
  println("\n--- Converting Regular to Curried ---")

  def multiply(x: Int, y: Int): Int = x * y
  val curriedMultiply = (multiply _).curried
  val times2 = curriedMultiply(2)
  val times10 = curriedMultiply(10)

  println(s"  times2(5) = ${times2(5)}")      // 10
  println(s"  times10(5) = ${times10(5)}")    // 50

  // --- Partial application with _ ---
  println("\n--- Partial Application ---")

  def format(left: String, value: String, right: String): String =
    s"$left$value$right"

  val addBrackets = format("[", _: String, "]")
  val addParens = format("(", _: String, ")")

  println(s"  addBrackets(\"Scala\") = ${addBrackets("Scala")}")
  println(s"  addParens(\"Scala\") = ${addParens("Scala")}")

  // --- Practical: logger with configuration ---
  println("\n--- Practical: Configurable Logger ---")

  case class Logger(prefix: String, timestamp: Boolean):
    def log(message: String): String =
      val ts = if timestamp then s"[${java.time.LocalTime.now().withNano(0)}] " else ""
      s"$ts$prefix: $message"

  val debugLogger = Logger("DEBUG", timestamp = true)
  val errorLogger = Logger("ERROR", timestamp = false)

  println(s"  ${debugLogger.log("Starting...")}")
  println(s"  ${errorLogger.log("Failed!")}")

  // --- Function composition (andThen / compose) ---
  println("\n--- Function Composition ---")

  val toUpper: String => String = _.toUpperCase
  val exclaim: String => String = _ + "!"
  val repeat: String => String = _ * 3

  // Chain: compose or andThen
  val shout = toUpper andThen exclaim
  val repeatShout = shout andThen repeat

  println(s"  shout(\"hello\") = ${shout("hello")}")
  println(s"  repeatShout(\"hi\") = ${repeatShout("hi")}")

  // --- Lifting functions ---
  println("\n--- Lifting ---")

  val parseInt: String => Int = _.toInt
  val safeParseInt: String => Option[Int] = s =>
    try Some(s.toInt)
    catch { case _: NumberFormatException => None }

  println(s"  safeParseInt(\"42\") = ${safeParseInt("42")}")
  println(s"  safeParseInt(\"abc\") = ${safeParseInt("abc")}")

  // --- Tupled / untupled ---
  println("\n--- Tupled / Untupled ---")

  def sum(a: Int, b: Int): Int = a + b

  val sumTupled: ((Int, Int)) => Int = (sum _).tupled
  println(s"  sumTupled((3, 4)) = ${sumTupled((3, 4))}")

  val sumUntupled: (Int, Int) => Int = Function.untupled(sumTupled)
  println(s"  sumUntupled(3, 4) = ${sumUntupled(3, 4)}")
