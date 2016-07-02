import dal.DbAccessor
import org.scalatestplus.play.PlaySpec
import play.api.db.{Database, Databases}

/**
  * Created by 10170216 on 2016/6/25.
  */
class DataBaseTest extends PlaySpec with DbAccessor{

  def withMyDatabase[T](block: Database => T) = {
    Databases.withDatabase(
      driver = "com.mysql.jdbc.Driver",
      url = "jdbc:mysql://10.9.233.123:3310/reward",
      name = "reward",
      config = Map(
        "user" -> "root",
        "password" -> "U_tywg_2013"
      )
    )(block)
  }

  "A DataBase" must{
    "insert data into table through DbAccessor Api" in{
      val createTmpTable = "create table if not exists tmp(a Int)"
      val insertData = "insert into tmp(a) values(10)"
      val queryData = "select a from tmp"
      val dropTable = "drop table if exists tmp"
      withMyDatabase{
        db =>
          update(db,createTmpTable)
          update(db,insertData)
          val res = query(db,queryData).cellOption(0,0).getOrElse("0")
          res mustBe "10"
          update(db,dropTable)
      }
    }
  }

}
