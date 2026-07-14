// Scala — Enums & Sum Types (Scala 3)

@main def enumsDemo(): Unit =

  println("=== Scala 3 Enums & Sum Types ===\n")

  // --- Simple enum (Scala 3) ---
  println("--- Simple Enum ---")
  enum Color:
    case Red, Green, Blue, Yellow

  val myColor = Color.Red
  println(s"  My color: $myColor")
  println(s"  Ordinal: ${myColor.ordinal}")

  // Iterate over all values
  println(s"  All colors: ${Color.values.toList}")

  // --- Parameterized enum ---
  println("\n--- Parameterized Enum ---")

  enum Planet(val mass: Double, val radius: Double):
    case Mercury extends Planet(3.303e23, 2.4397e6)
    case Venus   extends Planet(4.869e24, 6.0518e6)
    case Earth   extends Planet(5.976e24, 6.37814e6)
    case Mars    extends Planet(6.421e23, 3.3972e6)

    def surfaceGravity: Double =
      val G = 6.67300E-11
      G * mass / (radius * radius)

  println(s"  Earth gravity: ${Planet.Earth.surfaceGravity} m/s²")
  println(s"  Mars gravity:  ${Planet.Mars.surfaceGravity} m/s²")

  // --- Enum with methods ---
  println("\n--- Enum with Methods ---")

  enum HttpStatus(code: Int):
    case Ok         extends HttpStatus(200)
    case NotFound   extends HttpStatus(404)
    case Error      extends HttpStatus(500)

    def isSuccess: Boolean = code >= 200 && code < 300
    def isError: Boolean = code >= 400

  println(s"  Ok.isSuccess = ${HttpStatus.Ok.isSuccess}")
  println(s"  NotFound.isSuccess = ${HttpStatus.NotFound.isSuccess}")

  // --- ADT-style sealed trait (Scala 2 & 3 compatible) ---
  println("\n--- ADT-style (sealed trait) ---")

  sealed trait Option[+A]:
    def map[B](f: A => B): Option[B] = this match
      case Some(v) => Some(f(v))
      case None    => None

  case class Some[+A](value: A) extends Option[A]
  case object None extends Option[Nothing]

  val opt: Option[Int] = Some(42)
  println(s"  opt.map(_ * 2) = ${opt.map(_ * 2)}")

  // --- Parameterized ADT (Scala 3) ---
  println("\n--- Parameterized ADT ---")

  enum Tree[+A]:
    case Leaf(value: A)
    case Branch(left: Tree[A], right: Tree[A])

  val tree = Tree.Branch(
    Tree.Branch(Tree.Leaf(1), Tree.Leaf(2)),
    Tree.Leaf(3)
  )

  def sumTree(t: Tree[Int]): Int = t match
    case Tree.Leaf(v)            => v
    case Tree.Branch(l, r)       => sumTree(l) + sumTree(r)

  println(s"  Tree sum: ${sumTree(tree)}")

  // --- Sealed enum with exports ---
  println("\n--- Sealed Enum ---")

  sealed enum Direction:
    case North, South, East, West

    def opposite: Direction = this match
      case North => South
      case South => North
      case East  => West
      case West  => East

  println(s"  North.opposite = ${Direction.North.opposite}")

  // --- Scala 3 vs old-style sealed trait ---
  println("\n--- Scala 3 enum vs traditional sealed trait ---")
  println("""
    // Traditional (Scala 2):
    sealed trait Status
    case object Active extends Status
    case object Inactive extends Status
    
    // Scala 3:
    enum Status:
      case Active, Inactive
    
    // Both support pattern matching exhaustively!
  """)

  // --- Exhaustive matching ---
  println("\n--- Exhaustive Pattern Matching ---")

  def planetType(p: Planet): String = p match
    case Planet.Mercury | Planet.Venus => "Inner planet"
    case Planet.Earth                  => "Home"
    case Planet.Mars                   => "Red planet"
    // No default needed — sealed/enum ensures exhaustiveness

  println(s"  ${planetType(Planet.Earth)}")
  println(s"  ${planetType(Planet.Mars)}")
