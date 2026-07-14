// Scala — Functional Collections API

@main def collectionsFPDemo(): Unit =

  println("=== Functional Collections API ===\n")

  val numbers = 1 to 10
  val words = List("apple", "banana", "cherry", "date", "elderberry")

  // --- map (transform each element) ---
  println("--- map ---")
  val doubled = numbers.map(_ * 2)
  val wordLengths = words.map(_.length)
  println(s"  doubled:      $doubled")
  println(s"  word lengths: $wordLengths")

  // --- filter (keep matching elements) ---
  println("\n--- filter ---")
  val evens = numbers.filter(_ % 2 == 0)
  val longWords = words.filter(_.length > 5)
  println(s"  evens:        $evens")
  println(s"  long words:   $longWords")

  // --- flatMap (map + flatten) ---
  println("\n--- flatMap ---")
  val letters = words.flatMap(_.toList)
  val pairs = List(1, 2, 3).flatMap(i => List(i, i * 10))
  println(s"  letters:  $letters")
  println(s"  pairs:    $pairs")

  // flatMap with Option (filter out None)
  val options = List(Some(1), None, Some(2), None, Some(3))
  val values = options.flatMap(_.toList)     // toList: Some → List(1), None → Nil
  println(s"  values from options: $values")

  // --- foldLeft / foldRight ---
  println("\n--- fold ---")
  val sum = numbers.foldLeft(0)(_ + _)
  val product = numbers.foldLeft(1)(_ * _)
  val concat = words.foldLeft("")(_ + _)
  println(s"  sum:      $sum")
  println(s"  product:  ${product.toLong}")
  println(s"  concat:   $concat")

  // fold with different accumulator type
  val lengths = words.foldLeft(0)(_ + _.length)
  println(s"  total length: $lengths")

  // --- reduce ---
  println("\n--- reduce ---")
  val sumR = numbers.reduce(_ + _)
  val maxR = numbers.reduce(_ max _)
  val minR = numbers.reduce(_ min _)
  println(s"  reduce sum:  $sumR")
  println(s"  reduce max:  $maxR")
  println(s"  reduce min:  $minR")

  // --- scan (all intermediate fold values) ---
  println("\n--- scan ---")
  val runningSum = numbers.scanLeft(0)(_ + _)
  println(s"  running sum: $runningSum")

  // --- groupBy ---
  println("\n--- groupBy ---")
  val byFirstLetter = words.groupBy(_.head)
  val byLength = words.groupBy(_.length)
  println(s"  by first letter: $byFirstLetter")
  println(s"  by length:       $byLength")

  // --- partition / span / splitAt ---
  println("\n--- partition / span / splitAt ---")
  val (evens2, odds) = numbers.partition(_ % 2 == 0)
  val (small, large) = numbers.partition(_ <= 5)
  val (first3, rest) = numbers.splitAt(3)
  val (till5, after5) = numbers.span(_ <= 5)     // stops at first false

  println(s"  evens:        $evens2")
  println(s"  odds:         $odds")
  println(s"  first3:       $first3")
  println(s"  span till 5:  $till5")

  // --- zip / unzip ---
  println("\n--- zip / unzip ---")
  val indices = 1 to words.length
  val zipped = words zip indices
  println(s"  zip:    $zipped")

  val (names, ages) = List(("Alice", 30), ("Bob", 25)).unzip
  println(s"  names:  $names")
  println(s"  ages:   $ages")

  // --- Chaining operations ---
  println("\n--- Chaining (pipeline) ---")
  val result = (1 to 20)
    .filter(_ % 2 == 0)          // Keep even
    .map(_ * 3)                  // Multiply by 3
    .filter(_ > 20)              // Keep > 20
    .map(x => (x, x.toString))   // Pair with string
    .toMap                       // Convert to map
  println(s"  Pipeline result: $result")

  // --- Check forAny / forAll ---
  println("\n--- Existential / Universal ---")
  println(s"  Any even?     ${numbers.exists(_ % 2 == 0)}")
  println(s"  All positive? ${numbers.forall(_ > 0)}")
  println(s"  Contains 5?   ${numbers.contains(5)}")
  println(s"  Find > 5:     ${numbers.find(_ > 5)}")    // Option
