package controllers

import javax.inject.Inject

import dal.DbAccessor
import models.{UserBasicInfos, UserJobBasicInfos, UserJobInfos}
import models.UserJobState._
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

/**
  * Created by 10170216 on 2016/6/24.
  */
class UserInfoPageController @Inject() (db: Database) extends Controller with DbAccessor{

  private def getUserRole(userid:String) = {
    val roleSql = s"select role from user_info where userid=$userid"
    query(db,roleSql).cellOption(0,0).getOrElse("user")
  }

  def getUserBasicInfo(userid:String) = Action{
    val role = getUserRole(userid)
    if(role == "user"){
      val userBasicInfoSql =
        s"""SELECT username,
                 account,
                 phonenum,
                 email,
                 registtime,
                 tag,
                 introduction,
                 publishingnum,
                 publishednum,
                 developingnum,
                 developednum,
                 avgpublishscore,
                 avgdevelopscore
          FROM user_info
          WHERE userid=$userid"""
      val res = query(db,userBasicInfoSql).value

      val infoSets = if(res.isEmpty){
        List(UserBasicInfos("","","","","","","","","","","","",""))
      }else{
        res.map{line =>
          UserBasicInfos(line.head,line(1),line(2),line(3),line(4),line(5),line(6),line(7),line(8),line(9),line(10),line(11),line(12))
        }
      }
      Ok(Json.toJson(infoSets))
    }else{
      val adminBasicInfo = UserBasicInfos("admin","","","","","","","","","","","","")
      Ok(Json.toJson(adminBasicInfo))
    }

  }

  def getUserJobInfo(userid:String) = Action{
    val role = getUserRole(userid)
    if(role == "user"){
      val developingJobInfosSql =
        s"""SELECT a.jobid,
                   a.jobname,
                   c.jobclassifyname,
                   a.fee,
                   a.jobexpectfinishtime,
                   b.jobstatusname
            FROM job_info a,
                 dim_job_status b,
                 dim_job_classify c
            WHERE a.jobclassifyid = c.jobclassifyid
              AND a.jobstatusid = b.jobstatusid
              AND a.jobstatusid != 201
              AND a.developuserid = $userid"""

      val publishingJobInfosSql =
        s"""SELECT a.jobid,
                   a.jobname,
                   c.jobclassifyname,
                   a.fee,
                   a.jobexpectfinishtime,
                   b.jobstatusname
            FROM job_info a,
                 dim_job_status b,
                 dim_job_classify c
            WHERE a.jobclassifyid = c.jobclassifyid
              AND a.jobstatusid = b.jobstatusid
              AND a.jobstatusid != 201
              AND a.publishuserid = $userid"""

      val tenderJobInfosSql =
        s"""SELECT a.jobid,
                   a.jobname,
                   c.jobclassifyname,
                   a.fee,
                   a.jobexpectfinishtime,
                   b.jobstatusname
            FROM job_info a,
                 dim_job_status b,
                 dim_job_classify c
            WHERE a.jobclassifyid = c.jobclassifyid
              AND a.jobstatusid = b.jobstatusid
              AND a.jobstatusid != 201
              AND a.tenderidlist like '%|$userid|%'"""

      val evaluatingJobInfosSql =
        s"""SELECT a.jobid,
                   a.jobname,
                   c.jobclassifyname,
                   a.fee,
                   a.jobexpectfinishtime,
                   b.jobstatusname
            FROM job_info a,
                 dim_job_status b,
                 dim_job_classify c
            WHERE a.jobclassifyid = c.jobclassifyid
              AND a.jobstatusid = b.jobstatusid
              AND (a.jobstatusid = 208 OR a.jobstatusid = 209)
              AND (a.publishuserid = $userid OR a.developuserid = $userid)"""

      def jobInfoForStateSql(state:String) = state match {
        case Develop =>
          developingJobInfosSql
        case Evaluate =>
          evaluatingJobInfosSql
        case Publish =>
          publishingJobInfosSql
        case Tender =>
          tenderJobInfosSql
      }

      def getUserJobBasicInfos(sql:String): List[UserJobBasicInfos] ={
        val res = query(db,sql).value
        if(res.isEmpty){
          List(UserJobBasicInfos("","","","","",""))
        }else{
          res.map{x => UserJobBasicInfos(x.head,x(1),x(2),x(3),x(4),x(5))}
        }

      }

      val userJobInfos: List[UserJobInfos] = List(Develop,Evaluate,Publish,Tender).map{
        state => UserJobInfos(state, getUserJobBasicInfos(jobInfoForStateSql(state)))
      }
      Ok(Json.toJson(userJobInfos))

    }else {

      val adminJobInfosSql =
        s"""SELECT a.jobid,
                   a.jobname,
                   c.jobclassifyname,
                   a.fee,
                   a.jobexpectfinishtime,
                   b.jobstatusname
            FROM job_info a,
                 dim_job_status b,
                 dim_job_classify c
            WHERE a.jobclassifyid = c.jobclassifyid
              AND a.jobstatusid = b.jobstatusid
              AND a.jobstatusid = 201"""

      val res = query(db,adminJobInfosSql).value

      val adminJobInfos = if(res.isEmpty){
        List(UserJobBasicInfos("","","","","",""))
      }else{
        res.map{x => UserJobBasicInfos(x.head,x(1),x(2),x(3),x(4),x(5))}
      }

      Ok(Json.toJson(UserJobInfos(Check,adminJobInfos)))
    }


  }

}
