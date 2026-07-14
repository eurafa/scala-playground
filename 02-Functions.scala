// Scala — Functions

@main def functionsDemo(): Unit =

  // --- Method definitions (def) ---
  println("=== Method Definitions ===")

  // Basic function (return type inferred)
  def add(x: Int, y: Int) = x + y

  // Explicit return type (for public APIs)
  def multiply(x: Int, y: Int): Int = x * y

  // No-arg function
  def greeting(): String = "Hello, Scala!"

  // Unit-returning function (side effects)
  def log(message: String): Unit = println(s"[LOG] $message")

  println(s"  2 + 3 = ${add(2, 3)}")
  println(s"  4 × 5 = ${multiply(4, 5)}")
  println(s"  $greeting")
  log("Function called")

  // --- Functions with blocks ---
  println("\n=== Block Body ===")
  def factorial(n: Int): Int =
    if n <= 1 then 1
    else n * factorial(n - 1)

  def formatPerson(name: String, age: Int): String =
    val category = if age >= 18 then "Adult" else "Minor"
    s"$name ($age) — $category"

  println(s"  5! = ${factorial(5)}")
  println(s"  ${formatPerson("Alice", 30)}")

  // --- Anonymous functions (lambdas) ---
  println("\n=== Anonymous Functions ===")
  val square    = (x: Int) => x * x
  val addLambda = (x: Int, y: Int) => x + y
  val greet     = (name: String) => s"Hello, $name!"

  println(s"  square(5) = ${square(5)}")
  println(s"  add(2,3)  = ${addLambda(2, 3)}")
  println(s"  ${greet("Scala")}")

  // Short form: _ as placeholder
  val doubled: List[Int] = List(1, 2, 3).map(_ * 2)
  println(s"  List doubled: $doubled")

  // --- Default parameter values ---
  println("\n=== Default Parameters ===")
  def connect(host: String = "localhost", port: Int = 8080): String =
    s"Connecting to $host:$port"

  println(s"  ${connect()}")
  println(s"  ${connect("example.com")}")
  println(s"  ${connect(port = 9090)}")           // Named argument

  // --- Named arguments ---
  println("\n=== Named Arguments ===")
  def createUser(name: String, age: Int, email: String): String =
    s"User($name, $age, $email)"

  println(s"  ${createUser(email = "a@b.com", name = "Alice", age = 30)}")

  // --- Multiple parameter lists ---
  println("\n=== Multiple Parameter Lists ===")
  def curriedAdd(x: Int)(y: Int): Int = x + y

  val add5 = curriedAdd(5)          // Partially applied
  println(s"  curriedAdd(5)(3) = ${curriedAdd(5)(3)}")
  println(s"  add5(10) = ${add5(10)}")

  // --- By-name parameters ---
  println("\n=== By-Name Parameters ===")
  var logEnabled = false

  def debug(msg: => String): Unit =               // => means by-name (lazy eval)
    if logEnabled then println(s"[DEBUG] $msg")

  debug(s"Expensive computation: ${42 + 100}")    // Not evaluated if disabled!
  logEnabled = true
  debug(s"Now it evaluates: ${42 + 100}")

  // --- Varargs ---
  println("\n=== Varargs ===")
  def sum(numbers: Int*): Int = numbers.sum
  def formatList(items: String*): String =
    items.mkString("[", ", ", "]")

  println(s"  sum(1,2,3) = ${sum(1, 2, 3)}")
  println(s"  sum() = ${sum()}")
  println(s"  ${formatList("a", "b", "c")}")

  // --- Nested functions ---
  println("\n=== Nested Functions ===")
  def outerFunction(x: Int): Int =
    def innerFunction(y: Int): Int = y * y
    innerFunction(x) + x

  println(s"  outerFunction(5) = ${outerFunction(5)}")

  // --- Procedure syntax (Unit return) ---
  println("\n=== Procedure ===")
  def showStatus(message: String): Unit =     // : Unit is optional
    println(s"[STATUS] $message")

  showStatus("Everything works!")
