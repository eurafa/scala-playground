// Scala — Monad Basics

@main def monadBasics(): Unit =

  println("=== Monad Basics ===\n")

  // --- Option is a monad ---
  println("--- Option Monad ---")

  def safeDiv(x: Int, y: Int): Option[Int] =
    if y == 0 then None else Some(x / y)

  // Demonstrating monad laws with Option
  val a = 10
  val f: Int => Option[Int] = x => safeDiv(x, 2)

  // Left identity: pure(a).flatMap(f) == f(a)
  val leftIdentity = Some(a).flatMap(f) == f(a)
  println(s"  Left identity:  ${leftIdentity}")
  println(s"    Some(10).flatMap(x => safeDiv(x, 2)) = ${Some(10).flatMap(f)}")
  println(s"    f(10) = ${f(10)}")

  // Right identity: m.flatMap(pure) == m
  val m: Option[Int] = Some(42)
  val rightIdentity = m.flatMap(Some(_)) == m
  println(s"\n  Right identity: ${rightIdentity}")
  println(s"    Some(42).flatMap(Some(_)) = ${m.flatMap(Some(_))}")
  println(s"    Some(42) = ${m}")

  // --- Custom Maybe monad ---
  println("\n--- Custom Maybe Monad ---")

  enum Maybe[+A]:
    case Just(value: A)
    case Empty

    def map[B](f: A => B): Maybe[B] = this match
      case Just(v) => Just(f(v))
      case Empty   => Empty

    def flatMap[B](f: A => Maybe[B]): Maybe[B] = this match
      case Just(v) => f(v)
      case Empty   => Empty

  import Maybe.*

  val result = for
    x <- Just(10)
    y <- Just(5)
    z <- if y != 0 then Just(x / y) else Empty
  yield z

  println(s"  Custom Maybe monad: $result")

  // --- List is a monad ---
  println("\n--- List Monad ---")
  val listResult = for
    x <- List(1, 2, 3)
    y <- List(4, 5, 6)
  yield x * y
  println(s"  List monad: $listResult")

  // --- Future is a monad ---
  println("\n--- Future Monad (conceptually) ---")
  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global

  val futureResult = for
    x <- Future { 10 }
    y <- Future { 20 }
  yield x + y

  println("  Future.flatMap chains async operations")

  // --- Custom IO monad (simplified) ---
  println("\n--- Custom IO Monad (effect pattern) ---")

  case class IO[A](unsafeRun: () => A):
    def map[B](f: A => B): IO[B] =
      IO(() => f(unsafeRun()))

    def flatMap[B](f: A => IO[B]): IO[B] =
      IO(() => f(unsafeRun()).unsafeRun())

  object IO:
    def pure[A](a: A): IO[A] = IO(() => a)
    def delay[A](a: => A): IO[A] = IO(() => a)

  // Functional composition
  val program = for
    _     <- IO.delay(println("  Enter first number:"))
    a     <- IO.pure(10)
    _     <- IO.delay(println("  Enter second number:"))
    b     <- IO.pure(5)
    result <- IO.pure(a / b)
    _     <- IO.delay(println(s"  Result: $result"))
  yield result

  // Nothing runs until unsafeRun() is called!
  println("  (IO program defined, nothing executed yet)")
  program.unsafeRun()

  // --- Key monads at a glance ---
  println("\n--- Common Monads ---")
  println("""
    Option[A]:  Computation that may fail (no error info)
    Either[L,R]: Computation that may fail (with error info)
    Try[A]:     Computation that may throw an exception
    Future[A]:  Asynchronous computation
    List[A]:    Non-deterministic computation (multiple results)
    IO[A]:      Effectful computation (ZIO/Cats Effect)
    
    All support: map, flatMap, filter, for-comprehensions
  """)
