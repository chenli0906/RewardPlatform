import dal.SqlResult
import org.scalatestplus.play.PlaySpec

/**
  * Created by 10170216 on 2016/6/25.
  */
class SqlResultTest extends PlaySpec {

  "A SqlResult" must {
    "get cell value through the row and column index" in {
      val name = List("a", "b", "c", "d")
      val value = List(List("1", "2", "3", "4"), List("5", "6", "7", "8"))
      SqlResult(name, value).cell(0, 3) mustBe "4"
      SqlResult(name, value).cellOption(1, 2).getOrElse("0") mustBe "7"
    }
    "get cell value through the row index and column name" in {
      val name = List("a", "b", "c", "d")
      val value = List(List("1", "2", "3", "4"), List("5", "6", "7", "8"))
      SqlResult(name, value).cell(0, "d") mustBe "4"
      SqlResult(name, value).cellOption(1, "c").getOrElse("0") mustBe "7"
    }
  }
}
