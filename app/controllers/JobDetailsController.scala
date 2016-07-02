package controllers

import javax.inject.Inject

import dal.DbAccessor
import models.{JobDetailsInfo, TenderUserInfo}
import play.api.Logger
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

/**
  * Created by 10170216 on 2016/6/25.
  */
class JobDetailsController @Inject() (db: Database) extends Controller with DbAccessor{

  def getJobBasicInfos(jobid:String) = Action{
    val jobBasicInfoSql =
      s"""SELECT a.jobname,
                 a.fee,
                 b.jobclassifyname,
                 c.jobstatusname,
                 a.jobcommittime,
                 a.jobpublishtime,
                 a.jobexpectfinishtime,
                 a.jobdescription,
                 d.username
          FROM job_info a,
               dim_job_classify b,
               dim_job_status c,
               user_info d
          WHERE a.jobclassifyid = b.jobclassifyid
            AND a.jobstatusid = c.jobstatusid
            AND a.publishuserid = d.userid
            AND a.jobid = $jobid"""

    val res = query(db,jobBasicInfoSql).value

    val jobBasicInfos = if(res.isEmpty){
      List(JobDetailsInfo("","","","","","","","",""))
    }else{
      res.map{x => JobDetailsInfo(x.head,x(1),x(2),x(3),x(4),x(5),x(6),x(7),x(8))}
    }

    Ok(Json.toJson(jobBasicInfos))
  }

  def getTenderUserList(jobid:String) =Action {

    val getTenderIdListSql =
      s"""select tenderidlist from job_info where jobid=$jobid"""

    def getTenderUserSql(userid:String) =
      s"""SELECT username,
                 developednum,
                 avgdevelopscore,
                 tag
          FROM user_info
          WHERE userid=$userid"""

    val tenderIdList = query(db,getTenderIdListSql).cellOption(0,0).getOrElse("")

    Logger.info("tenderIdList: ++++++++++" + tenderIdList)

    def getTenderUserStructs(res: List[List[String]]) ={
      if(res.isEmpty){
        TenderUserInfo("","","","")
      }else{
        res.map{line => TenderUserInfo(line.head,line(1),line(2),line(3))}.head
      }
    }

    if(tenderIdList != "" && tenderIdList != null){
      Ok(Json.toJson(tenderIdList.split('|').tail.map{userid => getTenderUserStructs(query(db,getTenderUserSql(userid)).value)}))
    }else{
      Ok(Json.parse("""{"status" : "none"}"""))
    }
  }

}
