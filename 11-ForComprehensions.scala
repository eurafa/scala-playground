// Scala — For-Comprehensions

@main def forComprehensionsDemo(): Unit =

  println("=== For-Comprehensions ===\n")

  // --- Basic iteration ---
  println("--- Basic iteration ---")
  val result = for i <- 1 to 5 yield i * 2
  println(s"  for i <- 1 to 5 yield i * 2: $result")

  // --- With guard (filter) ---
  println("\n--- With Guards ---")
  val evens = for
    i <- 1 to 10
    if i % 2 == 0
  yield i
  println(s"  Evens: $evens")

  // --- Nested iteration (Cartesian product) ---
  println("\n--- Nested Iteration ---")
  val pairs = for
    i <- 1 to 3
    j <- 1 to 3
  yield (i, j)
  println(s"  Pairs: $pairs")

  // --- Multiple generators ---
  println("\n--- Multiple Generators ---")
  val colors = List("Red", "Green", "Blue")
  val sizes = List("S", "M", "L")
  val products = for
    color <- colors
    size  <- sizes
  yield s"$color-$size"
  println(s"  Products: $products")

  // --- With Option (short-circuit on None) ---
  println("\n--- Option For-Comprehension ---")
  case class Address(city: String, zip: String)
  case class Person(name: String, address: Option[Address])

  val alice = Person("Alice", Some(Address("NYC", "10001")))
  val bob   = Person("Bob", None)

  def getCity(person: Person): Option[String] =
    for
      addr    <- person.address
      city    <- Some(addr.city)
    yield city

  println(s"  Alice's city: ${getCity(alice)}")
  println(s"  Bob's city:   ${getCity(bob)}")

  // --- With Either (short-circuit on Left) ---
  println("\n--- Either For-Comprehension ---")

  def safeDiv(x: Int, y: Int): Either[String, Int] =
    if y == 0 then Left("Division by zero")
    else Right(x / y)

  def complexCalc(a: Int, b: Int, c: Int): Either[String, Int] =
    for
      x <- safeDiv(a, b)
      y <- safeDiv(x, c)
    yield y

  println(s"  (10/2)/2 = ${complexCalc(10, 2, 2)}")
  println(s"  (10/0)/2 = ${complexCalc(10, 0, 2)}")

  // --- For-comprehension without yield (foreach) ---
  println("\n--- For without yield (side effects) ---")
  for
    i <- 1 to 3
    j <- 1 to 3
  do
    print(s"($i,$j) ")
  println()

  // --- Variable binding in for ---
  println("\n--- Variable Bindings ---")
  val data = List("Alice:30", "Bob:25", "Charlie:35")
  val parsed = for
    entry <- data
    parts = entry.split(":")
    name = parts(0)
    age  = parts(1).toInt
    if age >= 30
  yield s"$name is $age"
  println(s"  Adults: $parsed")

  // --- Translation rules (mental model) ---
  println("\n--- Translation Rules ---")
  println("""
    for x <- expr yield f(x)
    → expr.map(x => f(x))

    for x <- expr if guard yield f(x)
    → expr.withFilter(x => guard).map(x => f(x))

    for x <- expr1; y <- expr2 yield f(x, y)
    → expr1.flatMap(x => expr2.map(y => f(x, y)))
  """)

  // --- Real-world: multi-step computation ---
  println("\n--- Real-World: Multi-Step ---")
  case class Order(id: String, amount: Double)
  case class Customer(name: String, orders: List[Order])

  val customers = List(
    Customer("Alice", List(Order("A1", 100), Order("A2", 200))),
    Customer("Bob",   List(Order("B1", 50))),
    Customer("Carol", List())
  )

  val highValueOrders = for
    customer <- customers
    order    <- customer.orders
    if order.amount > 75
  yield s"${customer.name}: ${order.id} ($$${order.amount})"

  println(s"  High value orders: $highValueOrders")
