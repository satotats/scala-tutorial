package functional.determistic

/** 『関数型プログラミングの基礎』(立川察理, 2016) p.354-366 に記載の
 * 「決定性計算機」の実装のscala版 */

// scalaはinterface等が整備されているので
// 独自に代数的データ構造やmatcherといった仕組みを実装する必要はない

trait Expression

// composit pattern
case class Num(val value: Int) extends Expression

case class Add(val x: Expression, val y: Expression) extends Expression

object Calculator:
  def execute(exp: Expression): Int =
    exp match
      case Num(value) => value
      case Add(x, y) => execute(x) + execute(y)

@main def main() =
  val exp = Add(Num(1), Add(Num(2), Num(3)))
  println(Calculator.execute(exp))