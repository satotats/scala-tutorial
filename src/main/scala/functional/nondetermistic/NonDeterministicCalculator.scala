package functional.nondetermistic

trait Expression

case class Num(val value: Int) extends Expression

case class Ambient(val value: List[Expression]) extends Expression

case class Add(val x: Expression, val y: Expression) extends Expression

object NonDetermisticCalculator:
  /**
   * @param onSuccess 評価に成功した場合(式の評価結果としての値が正常に取得できた場合)
   * @param onFailure 評価に失敗した場合(Ambient型の値が空のlist、等)
   * */
  def execute(exp: Expression,
              onSuccess: (result: Int, onFailure: () => Int) => Int,
              onFailure: () => Int): Int = {
    exp match
      case Num(value) => onSuccess(value, onFailure)

      /// x,yともにあいまいな数である可能性があるので、それぞれあいまいな数を取る前提の処理を記述
      case Add(x, y) => execute(x, (resultX, onFailureX) => {
        execute(y, (resultY, onFailureY) => {
          onSuccess(resultX + resultY, onFailureY)
        }, onFailureX)
      }, onFailure)

      case Ambient(value) =>
        if (value.isEmpty) onFailure()
        else execute(value.first,
          onSuccess,
          // 緩和処理
          () => execute(Ambient(value.tail), onSuccess, onFailure)
        )

      case _ => // case no appropreate case found
        println("no expression had matched")
        0
  }

class Driver(exp: Expression) {
  private var suspendedComputation: () => Int = null
  private var onFailure: () => Int = () => throw Exception("no result found")
  private val onSuccess: (Int, () => Int) => Int = { (result, onFailure) =>
    suspendedComputation = onFailure
    result
  }

  def calculate(): Int = {
    if (suspendedComputation == null) {
      return NonDetermisticCalculator.execute(exp, onSuccess, onFailure)
    } else {
      return suspendedComputation()
    }
  }
}


@main def main() = {
  val exp = Add(Ambient(List(Num(1), Num(38), Num(9))), Add(Num(2), Num(3)))
  val driver = Driver(exp)
  println(driver.calculate())
  println(driver.calculate())
  println(driver.calculate())
  println(driver.calculate())
  println(driver.calculate())
}

extension[T] (list: List[T]) {
  def first = if (!list.isEmpty) list(0) else throw Exception("list is empty")
  /** listの先頭より後の要素を取得する */
  def tail = if (list.length > 1) list.slice(1, list.length) else List.empty
}

