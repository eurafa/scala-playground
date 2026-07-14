// Scala — Type Classes

// --- Define a type class ---
trait Show[A]:
  def show(a: A): String

// --- Provide instances ---
object ShowInstances:
  given Show[Int] with
    def show(a: Int): String = a.toString

  given Show[String] with
    def show(a: String): String = a

  given Show[Double] with
    def show(a: Double): String = f"$a%.2f"

  given [A](using s: Show[A]): Show[List[A]] with
    def show(a: List[A]): String =
      a.map(s.show).mkString("[", ", ", "]")

  given Show[Boolean] with
    def show(a: Boolean): String = if a then "✓" else "✗"

// --- Use the type class ---
object ShowSyntax:
  extension [A](a: A)(using s: Show[A])
    def show: String = s.show(a)

// --- Another type class: Eq ---
trait Eq[A]:
  def eq(a: A, b: A): Boolean

object EqInstances:
  given Eq[Int] with
    def eq(a: Int, b: Int): Boolean = a == b

  given Eq[String] with
    def eq(a: String, b: String): Boolean = a == b

  given [A](using e: Eq[A]): Eq[List[A]] with
    def eq(a: List[A], b: List[A]): Boolean =
      a.length == b.length && a.zip(b).forall((x, y) => e.eq(x, y))

// --- Use type class with context bound ---
def showAll[A: Show](items: A*): String =
  import ShowSyntax.*
  items.map(_.show).mkString(", ")

def maxOf[A](a: A, b: A)(using ord: Ordering[A]): A =
  if ord.gteq(a, b) then a else b

@main def typeClassesDemo(): Unit =

  import ShowInstances.given
  import ShowSyntax.*

  println("=== Type Classes ===\n")

  // ===== Using Show type class =====
  println("--- Show Type Class ---")

  println(s"  Int:    ${42.show}")
  println(s"  String: ${"hello".show}")
  println(s"  Double: ${3.14159.show}")
  println(s"  Bool:   ${true.show}")
  println(s"  List:   ${List(1, 2, 3).show}")
  println(s"  Mixed:  ${showAll(1, "hello", 3.14)}")

  // ===== Ordering type class =====
  println("\n--- Ordering Type Class ---")
  println(s"  maxOf(5, 3) = ${maxOf(5, 3)}")
  println(s"  maxOf("apple", "banana") = ${maxOf("apple", "banana")}")

  // ===== Custom serializer type class =====
  println("\n--- Custom Serializer ---")

  trait JsonSerializer[A]:
    def toJson(a: A): String

  object JsonSerializer:
    given JsonSerializer[Int] with
      def toJson(a: Int): String = a.toString

    given JsonSerializer[String] with
      def toJson(a: String): String = s""""$a""""

    given [A](using s: JsonSerializer[A]): JsonSerializer[List[A]] with
      def toJson(a: List[A]): String =
        a.map(s.toJson).mkString("[", ", ", "]")

  extension [A](a: A)(using s: JsonSerializer[A])
    def toJson: String = s.toJson(a)

  import JsonSerializer.given

  println(s"  JSON int:    ${42.toJson}")
  println(s"  JSON string: ${"hello".toJson}")
  println(s"  JSON list:   ${List(1, 2, 3).toJson}")

  // ===== Type class without instances =====
  println("\n--- Benefits of Type Classes ---")
  println("""
    ✓ Ad-hoc: can add to types you don't own
    ✓ Non-intrusive: no need to modify source
    ✓ Coherent: single instance per type (ideally)
    ✓ Composable: can derive from other type classes
    ✓ Type-safe: resolved at compile time
  """)
