// Scala — Pattern Matching Deep Dive

@main def patternMatchingDemo(): Unit =

  println("=== Pattern Matching Deep Dive ===\n")

  // --- Matching literals ---
  println("--- Matching Literals ---")
  def describeNum(n: Int): String = n match
    case 0          => "Zero"
    case 1          => "One"
    case 2          => "Two"
    case _          => "Other"
  println(s"  ${describeNum(1)}")
  println(s"  ${describeNum(42)}")

  // --- Matching with guards ---
  println("\n--- Guards (if conditions) ---")
  def classify(n: Int): String = n match
    case x if x < 0    => "Negative"
    case 0             => "Zero"
    case x if x < 10   => "Small positive"
    case x if x < 100  => "Medium"
    case _             => "Large"
  println(s"  ${classify(-5)}")
  println(s"  ${classify(0)}")
  println(s"  ${classify(7)}")
  println(s"  ${classify(42)}")
  println(s"  ${classify(999)}")

  // --- Matching on types ---
  println("\n--- Type Matching ---")
  def typeInfo(x: Any): String = x match
    case s: String    => s"String with length ${s.length}"
    case i: Int       => s"Integer: $i"
    case d: Double    => s"Double: $d"
    case b: Boolean   => s"Boolean: $b"
    case l: List[_]   => s"List with ${l.size} elements"
    case _            => "Unknown type"
  println(s"  ${typeInfo("hello")}")
  println(s"  ${typeInfo(42)}")
  println(s"  ${typeInfo(List(1,2,3))}")

  // --- Destructuring case classes ---
  println("\n--- Destructuring Case Classes ---")
  sealed trait Expr
  case class Num(value: Int) extends Expr
  case class Add(left: Expr, right: Expr) extends Expr
  case class Mul(left: Expr, right: Expr) extends Expr

  def eval(e: Expr): Int = e match
    case Num(v)                    => v
    case Add(Num(0), right)        => eval(right)      // 0 + x = x
    case Add(left, Num(0))         => eval(left)       // x + 0 = x
    case Add(left, right)          => eval(left) + eval(right)
    case Mul(left, right)          => eval(left) * eval(right)

  val expr = Add(Mul(Num(2), Num(3)), Num(4))   // (2*3) + 4
  println(s"  (2*3)+4 = ${eval(expr)}")

  // --- @ pattern (binding) ---
  println("\n--- @ Pattern (Bind whole match) ---")
  def describeSeq(seq: Seq[Int]): String = seq match
    case head +: tail => s"Head: $head, Tail: $tail"
    case Nil              => "Empty"
  println(s"  ${describeSeq(List(1,2,3))}")

  // --- Sequence patterns ---
  println("\n--- Sequence Patterns ---")
  def seqInfo(s: Seq[Int]): String = s match
    case Nil              => "Empty"
    case List(a)          => s"Single: $a"
    case List(a, b)       => s"Pair: ($a, $b)"
    case List(a, b, c)    => s"Triple: ($a, $b, $c)"
    case a :: b :: rest   => s"$a, $b, then ${rest.size} more"
  println(s"  ${seqInfo(Nil)}")
  println(s"  ${seqInfo(List(1))}")
  println(s"  ${seqInfo(List(1,2))}")
  println(s"  ${seqInfo(List(1,2,3,4,5))}")

  // --- OR patterns (|) ---
  println("\n--- OR Patterns (|) ---")
  def isWeekend(day: String): Boolean = day match
    case "Saturday" | "Sunday" => true
    case _                     => false
  println(s"  Saturday is weekend? ${isWeekend("Saturday")}")
  println(s"  Monday is weekend? ${isWeekend("Monday")}")

  // --- Custom extractors (unapply) ---
  println("\n--- Custom Extractors ---")
  object Even:
    def unapply(n: Int): Option[Int] =
      if n % 2 == 0 then Some(n / 2) else None

  object Positive:
    def unapply(n: Int): Option[Int] =
      if n > 0 then Some(n) else None

  def describeNum2(n: Int): String = n match
    case Even(half)         => s"Even, half is $half"
    case Positive(x)        => s"Positive odd: $x"
    case _                  => "Zero or negative"
  println(s"  ${describeNum2(10)}")
  println(s"  ${describeNum2(7)}")
  println(s"  ${describeNum2(0)}")

  // --- Infix patterns ---
  println("\n--- Infix Patterns ---")
  val listMatch: List[Int] = List(1, 2, 3, 4)
  listMatch match
    case 1 :: 2 :: rest => println(s"  Starts with 1,2 then: $rest")
    case _              => println("  No match")

  // --- Tuples destructuring ---
  println("\n--- Tuple Destructuring ---")
  val pair = ("Alice", 30)
  pair match
    case (name, age) => println(s"  $name is $age years old")
