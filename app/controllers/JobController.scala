package controllers

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

import dal.DbAccessor
import models.{JobInfo, JobResult}
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}


class JobController  @Inject()(db: Database) extends Controller with DbAccessor{

  def publish(userid: String, name: String, classify: String, description: String, fee: String, expectTime: String) = Action {
    val ret = JobInfo("", "", "", "", "", "", "", "", "", "")
    //1) 检查用户信息是否完整，如果不完整，需要先补充用户信息
    checkUserInfo(userid) match {
      case 0 => Ok(Json.toJson(JobResult(false, s"登录账户异常", ret)))
      case 1 => Ok(Json.toJson(JobResult(false, s"账户余额不足", ret)))
      case 2 => Ok(Json.toJson(JobResult(false, s"用户标签信息不足", ret)))
      case 3 => Ok(Json.toJson(JobResult(false, s"用户描述信息不足", ret)))
      case _ => {
        addJob(userid, name, classify, description, fee, expectTime)
      }
    }
  }
  private def addJob(userid: String, name: String, classify: String, description: String, fee: String, expectTime: String) = {
    val ret = JobInfo("", "", "", "", "", "", "", "", "", "")
    //1) get classify id by name
    val classifyId = classify//query(db, s"""select distinct jobclassifyid from Dim_Job_Classify where jobclassifyname = '$classify' """).value.head.head

    //2) add new job
    val time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
    val commitSql =
      s"""Insert into Job_Info set jobclassifyId = $classifyId,jobname = '$name',jobdescription = '$description',fee = $fee,jobcommittime = '$time',jobexpectfinishtime = '$expectTime',publishuserid = $userid""".stripMargin
    val commitRes = update(db, commitSql)
    if(!commitRes) {
      Ok(Json.toJson(JobResult(false, s"发布任务失败", ret)))
    } else {
      val jobInfo = query(db, s"""select jobId, jobName, b.jobClassifyname, c.jobstatusname, fee, jobcommittime, jobexpectfinishtime, d.username from job_info a, Dim_Job_Classify b, Dim_Job_Status c, user_info d where a.jobname = '$name' and a.publishuserid = $userid and a.jobClassifyid = b.jobclassifyid and a.jobstatusid = c.jobstatusid and a.publishuserid=d.userid""").value
      if (jobInfo.nonEmpty) {
        if (!UpdateUserInfo(userid)) {
          Ok(Json.toJson(JobResult(false, s"发布成功，但更新用户信息失败", ret)))
        } else {
          Ok(Json.toJson(JobResult(true, "发布任务成功", JobInfo(jobInfo.head.head, jobInfo.head(1), classifyId, jobInfo.head(2), jobInfo.head(3), jobInfo.head(4), jobInfo.head(5), "", jobInfo.head(6), jobInfo.head(7)))))
        }
      } else {
        Ok(Json.toJson(JobResult(false, "发布任务失败", ret)))
      }
    }
  }

  private def checkUserInfo(userId: String): Int = {
    val userInfo = query(db, s"""select account, tag, introduction from User_Info where userid = $userId""").value
    if(userInfo.isEmpty){
      0
    } else {
      val account      = userInfo.head.head
      val tag          = userInfo.head(1)
      val introduction = userInfo.head(2)
      if(account == null || account <= "0.0") 1
      else if(tag == "") 2
      else if(introduction == "") 3
      else -1
    }
  }

  private def UpdateUserInfo(userId: String) = {
    val updateSql = s"""Update User_Info set publishingnum = (publishingnum + 1) where userid = $userId"""
    update(db, updateSql)
  }

  def search(input: String, searchType: String) = Action {
    val whereCondition = searchType match {
      case "1" => s"""a.jobClassifyid = $input"""
      case _   => {
        val keyWordsArr = input.split(",")

        var cond = s"""( a.jobname like '%${keyWordsArr(0)}%'"""
        if(keyWordsArr.size == 1) {
           cond
        } else{
          (1 until keyWordsArr.size).foreach(index => cond += s" or a.jobname like '%${keyWordsArr(index)}%' ")
          cond + ")"
        }
      }
    }

    val searchSql =
      s"""select jobid,
         |jobname,
         |a.jobclassifyid,
         |b.jobclassifyname,
         |c.jobstatusname,
         |fee,
         |jobcommittime,
         |jobpublishtime,
         |jobexpectfinishtime,
         |d.username from job_info a, Dim_Job_Classify b, Dim_Job_Status c, user_info d where $whereCondition and a.jobClassifyid = b.jobclassifyid and a.jobstatusid = c.jobstatusid and d.userid = a.publishuserid """.stripMargin

    val result = query(db, searchSql).value
    if(result.isEmpty) {
      Ok(Json.toJson(JobInfo("", "", "", "", "", "", "", "", "", "")))
    } else {
      val rest: List[JobInfo] = result.map(x => JobInfo(x.head, x(1), x(2), x(3), x(4), x(5), x(6), x(7), x(8), x(9)))
      Ok(Json.toJson(rest))
    }
  }
}