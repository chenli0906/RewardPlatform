package controllers

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

import dal.DbAccessor
import models.{Classify, ClassifyInfo, JobClassify}
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}


class InitController @Inject()(db: Database) extends Controller with DbAccessor{

  def getClassify() = Action {
    val classifySql = s"""select distinct jobclassifyid, jobclassifyname from Dim_Job_Classify """

    val classify = query(db, classifySql).value

    classify.isEmpty match {
      case true => Ok(Json.toJson(Classify("", "")))
      case _ => {
        val res = classify.map(record => Classify(record.head, record(1)))
        Ok(Json.toJson(res))
      }
    }
  }

  def pageInit(userName: String, password: String) = Action {

    val classifySql = s"""select distinct jobclassifyid, jobclassifyname from Dim_Job_Classify """

    val classify = query(db, classifySql).value

    if(classify.isEmpty){
      val ret = InsertDataToDb()
      if(!ret) Ok(s"Insert into Database success.") else Ok(s"Query Database failed!")
    }
    else {
      val IdAndName = classify.map(x => (x.head,x(1)))//.zip(classify.map(y => y.head(1)))
      val time = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
      val res: List[JobClassify] = IdAndName.map(x => {
        val id = x._1
        val name = x._2
        val job = query(db, s"""select jobid, jobname, fee, jobcommittime from Job_Info where jobcommittime like '$time%%' and jobclassifyid = $id order by jobcommittime desc, fee desc limit 5""").value
        val jobInfo = job.headOption match {
          case None => List(ClassifyInfo("", "", "", ""))
          case Some(value) => List(ClassifyInfo(value.head, value(1), value(2), value(3)))
        }
        JobClassify(id, name, jobInfo)
      })

      Ok(Json.toJson(res))

    }
  }

  private def InsertDataToDb() = {
    val insertSql = s"""
    INSERT INTO Dim_Job_Classify VALUES(101,'美工设计');
    INSERT INTO Dim_Job_Classify VALUES(102,'软件开发');
    INSERT INTO Dim_Job_Classify VALUES(103,'文档处理');
    INSERT INTO Dim_Job_Classify VALUES(104,'其他类型');"""

    update(db, insertSql)
  }
}
