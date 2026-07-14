// Scala — Type System Features

@main def typeSystemDemo(): Unit =

  println("=== Type System Features ===\n")

  // --- Generics & bounds ---
  println("--- Generics & Bounds ---")

  // Upper bound: A must be a subtype of Comparable[A]
  def max[A <: Comparable[A]](a: A, b: A): A =
    if a.compareTo(b) >= 0 then a else b

  println(s"  max(3, 7) = ${max(3.asInstanceOf[Comparable[Int]], 7.asInstanceOf[Comparable[Int]])}")

  // Actually, let's use Ordering (type class, better style):
  def max2[A](a: A, b: A)(implicit ord: Ordering[A]): A =
    if ord.gteq(a, b) then a else b

  println(s"  max2(3, 7) = ${max2(3, 7)}")
  println(s"  max2('x', 'a') = ${max2('x', 'a')}")

  // --- Variance ---
  println("\n--- Variance ---")

  sealed trait MyList[+A]  // Covariant: List[Dog] is subtype of List[Animal]
  case class Cons[+A](head: A, tail: MyList[A]) extends MyList[A]
  case object MyNil extends MyList[Nothing]

  // Contravariant example (-A)
  trait Serializer[-A]:  // Serializer[Animal] is subtype of Serializer[Dog]
    def serialize(a: A): String

  val animalSer: Serializer[Any] = (a: Any) => s"$a"
  val intSer: Serializer[Int] = animalSer  // OK: contravariant

  // --- Union types (Scala 3) ---
  println("\n--- Union Types (Scala 3) ---")
  println("""
    // Int | String means either Int or String
    def process(input: Int | String): String =
      input match
        case i: Int    => s"Got int: $i"
        case s: String => s"Got string: $s"
    
    process(42)       // OK
    process("hello")  // OK
    // process(3.14)  // COMPILE ERROR: Double not allowed!
  """)

  // --- Intersection types (Scala 3) ---
  println("\n--- Intersection Types (Scala 3) ---")
  println("""
    // A & B means both A and B
    trait Reader:
      def read(): String
    
    trait Writer:
      def write(s: String): Unit
    
    def process(rw: Reader & Writer): Unit =
      val data = rw.read()
      rw.write(data)
    
    // Any type implementing both Reader and Writer is accepted
  """)

  // --- Type aliases ---
  println("\n--- Type Aliases ---")

  type UserId = Int
  type UserName = String
  type UserMap = Map[UserId, UserName]

  def findName(id: UserId, map: UserMap): Option[UserName] =
    map.get(id)

  val users: UserMap = Map(1 -> "Alice", 2 -> "Bob")
  println(s"  User 1: ${findName(1, users)}")

  // --- Abstract type members ---
  println("\n--- Abstract Type Members ---")

  trait Container:
    type A
    def value: A

  object StringContainer extends Container:
    type A = String
    val value: A = "Hello"

  object IntContainer extends Container:
    type A = Int
    val value: A = 42

  println(s"  StringContainer: ${StringContainer.value}")
  println(s"  IntContainer: ${IntContainer.value}")

  // --- Phantom types ---
  println("\n--- Phantom Types (compile-time safety) ---")

  sealed trait Locked
  sealed trait Unlocked

  case class Door[State](isOpen: Boolean)

  def open(door: Door[Locked]): Door[Unlocked] =
    Door[Unlocked](true)

  def close(door: Door[Unlocked]): Door[Locked] =
    Door[Locked](false)

  val lockedDoor = Door[Locked](false)
  // lockedDoor would need to be opened first

  println(s"  Phantom types prevent calling operations in wrong state")
  println(s"  (Compile-time state machine!)")
