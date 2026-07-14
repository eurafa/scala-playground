# Scala Playground

A comprehensive collection of Scala examples covering syntax, functional programming, and advanced FP concepts. Designed to help you **remember** Scala if you haven't used it in a while.

## How to Run

```bash
# Install Scala first: https://www.scala-lang.org/download/

# Run individual scripts (Scala 3):
scala-cli 01-SyntaxBasics.scala

# Or compile and run with scalac:
scalac 01-SyntaxBasics.scala -d out
scala -cp out SyntaxBasics

# Using scala-cli (recommended):
scala-cli run 01-SyntaxBasics.scala
```

## What's Covered

| File | Topic | Key Concepts |
|------|-------|-------------|
| `01-SyntaxBasics.scala` | Variables, expressions, control flow | `val`/`var`, `if`, `match`, `for`, `while` |
| `02-Functions.scala` | Functions in Scala | `def`, anonymous functions, default params, named args |
| `03-Immutability.scala` | Immutability & functional style | `val`, immutable collections, side-effect-free |
| `04-Recursion.scala` | Recursion & tail recursion | `@tailrec`, accumulator pattern, Trampolines |
| `05-CaseClasses.scala` | Case classes & sealed traits | ADTs, algebraic data types, copy, equality |
| `06-PatternMatching.scala` | Pattern matching deep dive | Destructuring, guards, extractors, sealed matching |
| `07-CollectionsBasics.scala` | Collections API | `List`, `Vector`, `Map`, `Set`, `Seq` |
| `08-CollectionsFP.scala` | Functional collections | `map`, `flatMap`, `filter`, `fold`, `scan`, groupBy |
| `09-OptionHandling.scala` | Option type | `Some`, `None`, `getOrElse`, `map`, `flatMap` |
| `10-EitherAndTry.scala` | Either & Try monads | `Right`/`Left`, error handling, `recover` |
| `11-ForComprehensions.scala` | For-comprehensions | Monadic composition, `yield`, translation rules |
| `12-HigherOrderFunctions.scala` | HOFs & combinators | `map`, `flatMap`, `filter`, `foldLeft`, `reduce` |
| `13-CurryingPartials.scala` | Currying & partial application | `curried`, `partiallyApplied`, function composition |
| `14-ConcurrencyFutures.scala` | Futures & concurrency | `Future`, `Promise`, `ExecutionContext`, `flatMap` |
| `15-ImplicitsAndGiven.scala` | Implicits / Given/Using | Scala 2 `implicit` vs Scala 3 `given`/`using` |
| `16-EnumsAndSumTypes.scala` | Enums & algebraic types | Scala 3 enums, `sealed enum`, parameterized enums |
| `17-TypeSystem.scala` | Type system features | Generics, variances, bounds, union/intersection types |
| `18-TypeClasses.scala` | Type classes & contextual abstraction | `trait`, given instances, context bounds |
| `19-MonadBasics.scala` | Monad concepts | `pure`, `flatMap`, `map`, laws, custom monads |
| `20-AlgebraicEffects.scala` | ZIO/Cats-style effects | IO monad, `ZIO`, `Task`, resource handling |

## Scala 2 vs Scala 3 Quick Reference

| Feature | Scala 2 | Scala 3 |
|---------|---------|---------|
| Implicits | `implicit val` | `given` / `using` |
| Enum | `sealed abstract class` + case objects | `enum` keyword |
| Union types | N/A | `A \| B` |
| Intersection types | `A with B` | `A & B` |
| Extension methods | `implicit class` | `extension (x: T)` |
| Context bounds | `[T: Ordering]` | `[T: Ordering]` (same) |
| Multiversal equality | N/A | `Eql` / `CanEqual` |
| Top-level definitions | Must be in `object`/`class` | Allowed at top level |
| `match` syntax | `x match { case ... }` | Same + `match` as expression |
| Indentation syntax | Braces `{ }` | Significant indentation (optional) |

## Key FP Concepts to Remember

1. **Expression-oriented**: Everything is an expression (returns a value)
2. **Immutability by default**: Prefer `val` over `var`, immutable collections
3. **Pure functions**: No side effects, same input → same output
4. **Referential transparency**: Expression can be replaced by its value
5. **Algebraic Data Types (ADTs)**: Product types (`case class`) + Sum types (`sealed trait`)
6. **Pattern matching**: Destructure and dispatch in one construct
7. **Higher-order functions**: Functions that take/return functions
8. **Monadic composition**: `flatMap` chains, `for`-comprehensions
9. **Type classes**: Ad-hoc polymorphism via implicits/given
10. **Effects**: `Option`, `Either`, `Try`, `Future`, `IO`
# scala-playground
