package dal

import dal.DbResult._
import play.api.Logger
import play.api.db.Database


/**
  * Created by 10170216 on 2016/6/23.
  */
trait DbAccessor {

  def query(db: Database,sql: String): SqlResult ={
    db.withConnection{
      conn =>
        try{
          val stmt = conn.createStatement
          val rs = stmt.executeQuery(sql)
          SqlResult(rs.columns,rs.rows)
        }catch{
          case e:Exception =>
            Logger.error("catch SQLException when executeQuery sql: "+ sql, e)
            SqlResult(List(), List())
        }

    }
  }

  def update(db: Database,sql: String): Boolean = {
    db.withConnection{
      conn =>
        try{
          val stmt = conn.createStatement
          val rs = stmt.executeUpdate(sql)
          true
        }catch {
          case e: Exception =>
            Logger.error("catch SQLException when executeUpdate sql: " + sql, e)
            false
        }

    }
  }

}
