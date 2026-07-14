// Scala — Collections Basics

@main def collectionsBasicsDemo(): Unit =

  println("=== Collections Basics ===\n")

  // --- List (linked list, fast head/tail) ---
  println("--- List ---")
  val list1 = List(1, 2, 3, 4, 5)
  val list2 = 1 :: 2 :: 3 :: Nil           // Same as List(1,2,3)
  val empty = Nil

  println(s"  List:    $list1")
  println(s"  :: cons: $list2")
  println(s"  Head:    ${list1.head}")
  println(s"  Tail:    ${list1.tail}")
  println(s"  Length:  ${list1.length}")
  println(s"  Init:    ${list1.init}")      // All except last
  println(s"  Last:    ${list1.last}")

  // List operations return NEW lists
  println(s"  0 +: list: ${0 +: list1}")     // Prepend
  println(s"  list :+ 6: ${list1 :+ 6}")     // Append (slow!)
  println(s"  Take 3:   ${list1.take(3)}")
  println(s"  Drop 2:   ${list1.drop(2)}")

  // --- Vector (indexed, fast random access) ---
  println("\n--- Vector ---")
  val vec = Vector(1, 2, 3, 4, 5)
  println(s"  Vector:     $vec")
  println(s"  Index 2:    ${vec(2)}")        // Fast random access
  println(s"  Updated:    ${vec.updated(2, 99)}")  // New vector with changed value
  println(s"  Appended:   ${vec :+ 6}")       // Fast append!
  println(s"  Prepended:  ${0 +: vec}")       // Fast prepend!

  // --- Range (lazy range of values) ---
  println("\n--- Range ---")
  val range1 = 1 to 10                     // Inclusive
  val range2 = 1 until 10                  // Exclusive
  val range3 = 1 to 10 by 2                // Step
  val range4 = 10 to 1 by -1               // Reverse

  println(s"  1 to 10:       $range1")
  println(s"  1 until 10:    $range2")
  println(s"  1 to 10 by 2:  $range3")
  println(s"  10 to 1 by -1: $range4")

  // --- Map (key-value pairs) ---
  println("\n--- Map ---")
  val map = Map("Alice" -> 30, "Bob" -> 25, "Charlie" -> 35)
  println(s"  Map:       $map")
  println(s"  Keys:      ${map.keys}")
  println(s"  Values:    ${map.values}")
  println(s"  Get Alice: ${map("Alice")}")
  println(s"  Get safe:  ${map.get("Dave")}")       // Option[Int]
  println(s"  GetOrElse: ${map.getOrElse("Dave", 0)}")

  // "Updated" map (new map returned)
  val updatedMap = map + ("Dave" -> 40)              // Add entry
  val removed = map - "Alice"                        // Remove entry
  println(s"  Updated:   $updatedMap")
  println(s"  Removed:   $removed")

  // --- Set (unique elements) ---
  println("\n--- Set ---")
  val set = Set(1, 2, 3, 3, 2, 1)                    // Duplicates removed!
  println(s"  Set:         $set")
  println(s"  Contains 2:  ${set.contains(2)}")
  println(s"  Contains 5:  ${set(5)}")               // Apply = contains

  val set2 = set + 4 + 5
  val set3 = set - 2
  println(s"  Added:       $set2")
  println(s"  Removed:     $set3")
  println(s"  Union:       ${set.union(Set(3, 4, 5))}")
  println(s"  Intersect:   ${set.intersect(Set(3, 4, 5))}")

  // --- Mutable collections ---
  println("\n--- Mutable Collections (explicit import) ---")
  import scala.collection.mutable
  val buffer = mutable.ArrayBuffer(1, 2, 3)
  buffer += 4
  buffer ++= List(5, 6)
  println(s"  ArrayBuffer: $buffer")
  buffer.remove(0, 2)
  println(s"  After remove: $buffer")

  // Mutable map
  val mMap = mutable.Map("a" -> 1)
  mMap("b") = 2
  mMap.update("a", 10)
  println(s"  Mutable map: $mMap")

  // --- Converting between collections ---
  println("\n--- Conversions ---")
  val list = List(1, 2, 3)
  println(s"  List → Vector:   ${list.toVector}")
  println(s"  List → Set:      ${list.toSet}")
  println(s"  List → Array:    ${list.toArray.mkString("[", ",", "]")}")

  val array = Array(4, 5, 6)
  println(s"  Array → List:    ${array.toList}")
