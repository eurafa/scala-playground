// Scala — Either, Try & Error Handling
import scala.util.{Try, Success, Failure}
import scala.io.Source

@main def eitherAndTryDemo(): Unit =

  println("=== Either & Try ===\n")

  // --- Try (exception-safe computation) ---
  println("--- Try Basics ---")

  val safeDivide: Try[Int] = Try(10 / 0)     // Catches exception!
  val safeSuccess: Try[Int] = Try(10 / 2)

  safeDivide match
    case Success(v) => println(s"  Success: $v")
    case Failure(e) => println(s"  Failure: ${e.getClass.getSimpleName}: ${e.getMessage}")

  println(s"  Try(10/2):     $safeSuccess")
  println(s"  Try(10/0):     $safeDivide (no crash!)")

  // --- Try operations ---
  println("\n--- Try Operations ---")
  val result = safeSuccess.map(_ * 2)
  val filtered = safeSuccess.filter(_ > 3)
  val recovered = safeDivide.recover { case _: ArithmeticException => 0 }

  println(s"  map(_*2):          $result")
  println(s"  filter(_ > 3):     $filtered")
  println(s"  recover:           $recovered")

  // getOrElse
  println(s"  getOrElse on Try:  ${safeDivide.getOrElse(-1)}")

  // --- Either (biased to Right) ---
  println("\n--- Either Basics ---")

  def parseAge(s: String): Either[String, Int] =
    s.toIntOption match
      case Some(age) if age >= 0 => Right(age)
      case Some(age)             => Left(s"Negative age: $age")
      case None                  => Left(s"Not a number: $s")

  val valid = parseAge("30")
  val invalid1 = parseAge("-5")
  val invalid2 = parseAge("xyz")

  println(s"  parseAge(\"30\"):  $valid")
  println(s"  parseAge(\"-5\"):  $invalid1")
  println(s"  parseAge(\"xyz\"): $invalid2")

  // Either is right-biased (map/flatMap operate on Right)
  val doubledAge = valid.map(_ * 2)
  val mappedErr = invalid1.map(_ * 2)        // Left unchanged
  println(s"  map on Right:  $doubledAge")
  println(s"  map on Left:   $mappedErr")

  // --- Real-world: validation pipeline ---
  println("\n--- Validation Pipeline ---")

  case class User(name: String, age: Int, email: String)

  def validateName(name: String): Either[String, String] =
    if name.nonEmpty && name.length >= 2 then Right(name)
    else Left(s"Invalid name: $name")

  def validateAge(age: Int): Either[String, Int] =
    if age >= 0 && age < 150 then Right(age)
    else Left(s"Invalid age: $age")

  def validateEmail(email: String): Either[String, String] =
    if email.contains("@") then Right(email)
    else Left(s"Invalid email: $email")

  def createUser(name: String, age: Int, email: String): Either[String, User] =
    for
      n <- validateName(name)
      a <- validateAge(age)
      e <- validateEmail(email)
    yield User(n, a, e)

  println(s"  ${createUser("Alice", 30, "alice@example.com")}")
  println(s"  ${createUser("", -5, "bad-email")}")

  // --- fold (handle both cases) ---
  println("\n--- Either.fold ---")

  def displayAge(input: String): String =
    parseAge(input).fold(
      error => s"ERROR: $error",
      age   => s"Age is $age"
    )

  println(s"  ${displayAge("30")}")
  println(s"  ${displayAge("xyz")}")

  // --- Try from unsafe Java API ---
  println("\n--- Try wrapping unsafe APIs ---")

  def readFile(path: String): Try[String] =
    Try {
      val source = Source.fromFile(path)
      try source.mkString finally source.close()
    }

  readFile("/nonexistent/file.txt") match
    case Success(content) => println(s"  File: ${content.take(50)}...")
    case Failure(e)       => println(s"  Failed: ${e.getMessage}")

  // --- Either with collections ---
  println("\n--- Either in Collections ---")
  val inputs = List("10", "20", "xyz", "30", "abc")
  val parsed: List[Either[String, Int]] = inputs.map(parseAge)

  val successes = parsed.collect { case Right(v) => v }
  val errors = parsed.collect { case Left(e) => e }

  println(s"  Successes: $successes")
  println(s"  Errors:    $errors")
