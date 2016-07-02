package dal

/**
  * Created by 10170216 on 2016/6/22.
  */
class SqlResult(val name: List[String], val value: List[List[String]]) {
  def cell(row: Int, column: Int): String = {
    if (row < value.length && column < name.length) value(row)(column)
    else ""
  }

  def cellOption(row: Int, column: Int): Option[String] = {
    if (row < value.length && column < name.length) Some(value(row)(column))
    else None
  }

  def cell(row: Int, columnName: String): String = {
    val column = name.indexOf(columnName)
    if (row < value.length && column < name.length) value(row)(column)
    else ""
  }

  def cellOption(row: Int, columnName: String): Option[String] = {
    val column = name.indexOf(columnName)
    if (row < value.length && column < name.length) Some(value(row)(column))
    else None
  }

  override def toString = {
    (name :: value).map(_.mkString(", ")).mkString("\n")
  }
}

object SqlResult{

  def apply(name: List[String], value: List[List[String]]): SqlResult = new SqlResult(name, value)
}
