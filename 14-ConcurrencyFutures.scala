// Scala — Futures & Concurrency
import scala.concurrent.{Future, Promise, Await}
import scala.concurrent.duration.*
import scala.util.{Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global

@main def concurrencyDemo(): Unit =

  println("=== Futures & Concurrency ===\n")

  // --- Creating Futures ---
  println("--- Creating Futures ---")

  val future1 = Future {
    Thread.sleep(100)
    "Hello from future!"
  }

  val future2 = Future { 42 }
  val failedFuture = Future { throw new RuntimeException("Boom!") }

  future1.onComplete {
    case Success(value) => println(s"  Future1 success: $value")
    case Failure(e)     => println(s"  Future1 failed: ${e.getMessage}")
  }

  // --- Mapping futures ---
  println("\n--- Mapping Futures ---")

  val mapped = future2.map(_ * 2)
  mapped.foreach(v => println(s"  Mapped: 42 * 2 = $v"))

  // --- flatMap (chaining async operations) ---
  println("\n--- Chaining Futures ---")

  def fetchUser(id: Int): Future[String] = Future {
    Thread.sleep(100)
    s"User_$id"
  }

  def fetchOrders(user: String): Future[List[String]] = Future {
    Thread.sleep(100)
    List(s"${user}_Order1", s"${user}_Order2")
  }

  // Chaining
  val ordersFuture: Future[List[String]] =
    fetchUser(42).flatMap { user =>
      fetchOrders(user)
    }

  // Using for-comprehension (cleaner!)
  val ordersFor = for
    user   <- fetchUser(42)
    orders <- fetchOrders(user)
  yield orders

  ordersFor.foreach(o => println(s"  Orders: $o"))

  // --- Recovering from failures ---
  println("\n--- Error Recovery ---")

  def unreliableService(id: Int): Future[String] = Future {
    if id < 0 then throw new IllegalArgumentException(s"Bad id: $id")
    else s"Data for $id"
  }

  val recovered = unreliableService(-1).recover {
    case e: IllegalArgumentException => s"Default (${e.getMessage})"
  }

  recovered.foreach(r => println(s"  Recovered: $r"))

  // --- Combining futures (parallel) ---
  println("\n--- Parallel Futures ---")

  def fetchPrice(item: String): Future[Double] = Future {
    Thread.sleep(50)
    item.hashCode.abs % 100 + 10.0
  }

  val applePrice  = fetchPrice("apple")
  val bananaPrice = fetchPrice("banana")
  val cherryPrice = fetchPrice("cherry")

  // Combine multiple futures into one
  val totalPrice = for
    a <- applePrice
    b <- bananaPrice
    c <- cherryPrice
  yield a + b + c

  totalPrice.foreach(t => println(f"  Total price: $$${t}%.2f"))

  // --- Future.sequence (List[Future] → Future[List]) ---
  println("\n--- Future.sequence ---")

  val itemIds = List(1, 2, 3, 4, 5)
  val itemFutures = itemIds.map(fetchUser)
  val allItems = Future.sequence(itemFutures)

  allItems.foreach(items => println(s"  All items: $items"))

  // --- Future.traverse (map + sequence in one) ---
  println("\n--- Future.traverse ---")

  val countFutures = Future.traverse(itemIds)(id =>
    Future {
      Thread.sleep(50)
      id * 2
    }
  )

  countFutures.foreach(counts => println(s"  Doubled: $counts"))

  // --- Promise (completing a Future externally) ---
  println("\n--- Promise ---")

  val promise = Promise[String]()
  val promisedFuture = promise.future

  // Complete the promise on a separate thread
  Future {
    Thread.sleep(50)
    promise.success("Completed via Promise!")
  }

  promisedFuture.foreach(msg => println(s"  $msg"))

  // --- Timeouts and fallback ---
  println("\n--- Timeout ---")

  val slowFuture = Future {
    Thread.sleep(2000)
    "Slow result"
  }

  val withTimeout = Future.firstCompletedOf(Seq(
    slowFuture,
    Future { Thread.sleep(100); "TIMEOUT (fallback)" }
  ))

  withTimeout.foreach(r => println(s"  First completed: $r"))

  // Wait a bit for all futures to complete
  println("\n  Waiting for futures...")
  Thread.sleep(1500)
