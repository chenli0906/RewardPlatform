package dal

import java.sql.ResultSet

/**
  * Created by 10170216 on 2016/6/22.
  */
object DbResult {

  implicit class ResultSetUtil(rs: ResultSet) {
    private val columnCount = rs.getMetaData.getColumnCount

    def rows: List[List[String]] = {
      var valueList: List[List[String]] = List()

      while (rs.next()) {
        val oneLine = (1 to columnCount).map(rs.getString).toList
        valueList = oneLine :: valueList
      }
      valueList.reverse
    }

    def columns: List[String] = {
      (1 to columnCount).map(rs.getMetaData.getColumnLabel).toList
    }
  }

}
