import scala.util.chaining.scalaUtilChainingOps

class MyClass {
  def multi(x : Int, y : Int): Int = x * y
  def plus(x : Int, y : Int): Int = x + y

  def pipe() : Int = {
    val x = 2.pipe(plus(_, 2)).pipe(multi(_, 2))
    x
  }
}
