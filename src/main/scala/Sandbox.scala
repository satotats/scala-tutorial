import scala.util.Random

@main def sandbox: Unit =
  println("if statement")
  if Random.nextBoolean() then println("too bad") else println("happy")

  println("\"for\" statement")
  for i <- List(0, 1, 2, 3) /* here is a generator*/ do println("for: " + i)

  println("with guards")
  for
    i <- List(0, 1, 2, 3)
    if i > 2 // guard
  do println("for: " + i)

  println("with \"yield\" keywords")
  val yields =
    for
      i <- List(0, 1, 2, 3)
    yield
      i * 2
  // i // if multiple lines are defined in yield section, the last value will be packed into the list
  println(yields)