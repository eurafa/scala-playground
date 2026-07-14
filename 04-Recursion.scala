// Scala — Recursion & Tail Recursion
import scala.annotation.tailrec

@main def recursionDemo(): Unit =

  // --- Non-tail recursive (stack unsafe) ---
  println("=== Non-Tail Recursion ===")

  def factorial(n: Int): Int =
    if n <= 1 then 1
    else n * factorial(n - 1)    // NOT tail: n * (result) needs to compute after

  println(s"  factorial(5) = ${factorial(5)}")
  // factorial(10000) would STACK OVERFLOW!

  // --- Tail-recursive version (stack safe) ---
  println("\n=== Tail Recursion (@tailrec) ===")

  def factorialTail(n: Int): Int =
    @tailrec
    def loop(acc: Int, n: Int): Int =
      if n <= 1 then acc
      else loop(acc * n, n - 1)   // Tail call: loop() is the last operation
    
    loop(1, n)

  println(s"  factorialTail(5) = ${factorialTail(5)}")
  println(s"  factorialTail(10000) runs fine (no stack overflow)")

  // --- Accumulator pattern ---
  println("\n=== Accumulator Pattern ===")

  // Non-tail reverse
  def reverseList[A](list: List[A]): List[A] = list match
    case Nil          => Nil
    case head :: tail => reverseList(tail) :+ head

  // Tail recursive with accumulator
  def reverseListTail[A](list: List[A]): List[A] =
    @tailrec
    def loop(remaining: List[A], acc: List[A]): List[A] =
      remaining match
        case Nil          => acc
        case head :: tail => loop(tail, head :: acc)
    
    loop(list, Nil)

  val list = List(1, 2, 3, 4, 5)
  println(s"  Original: $list")
  println(s"  Reverse:  ${reverseListTail(list)}")

  // --- Fibonacci: non-tail vs tail ---
  println("\n=== Fibonacci ===")

  // Non-tail (O(2^n), terrible!)
  def fib(n: Int): BigInt =
    if n <= 1 then n
    else fib(n - 1) + fib(n - 2)

  // Tail recursive (O(n), efficient)
  def fibTail(n: Int): BigInt =
    @tailrec
    def loop(a: BigInt, b: BigInt, n: Int): BigInt =
      if n == 0 then a
      else loop(b, a + b, n - 1)
    
    loop(0, 1, n)

  println(s"  fib(10) = ${fibTail(10)}")
  println(s"  fib(50) = ${fibTail(50)}")
  println(s"  fib(100) = ${fibTail(100)}")

  // --- Multiple recursive calls (tail impossible) ---
  println("\n=== Trampolines for Mutual Recursion ===")
  // When tail recursion isn't possible, use trampolines or convert to iterative

  // Mutual recursion (isEven / isOdd)
  def isEven(n: Int): Boolean =
    if n == 0 then true
    else isOdd(n - 1)

  def isOdd(n: Int): Boolean =
    if n == 0 then false
    else isEven(n - 1)

  println(s"  isEven(42) = ${isEven(42)}")
  println(s"  isOdd(42) = ${isOdd(42)}")

  // --- Converting loops to recursion ---
  println("\n=== Loop → Recursion ===")

  // Imperative sum
  def sumImperative(list: List[Int]): Int =
    var acc = 0
    for x <- list do acc += x
    acc

  // Recursive sum
  def sumRecursive(list: List[Int]): Int =
    @tailrec
    def loop(remaining: List[Int], acc: Int): Int =
      remaining match
        case Nil          => acc
        case head :: tail => loop(tail, acc + head)
    
    loop(list, 0)

  val nums = (1 to 100).toList
  println(s"  Sum 1-100: ${sumRecursive(nums)}")
