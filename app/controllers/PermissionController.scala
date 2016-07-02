package controllers

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

import dal.DbAccessor
import models.Permission
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

/**
  * Created by 10170216 on 2016/6/25.
  */
class PermissionController @Inject() (db: Database) extends Controller with DbAccessor{

  //决定用户是否有权限操作需求
  def judgeAction(userid:String,jobid:String) = Action{

    val getCurrentJobInfoSql =
      s"""SELECT jobstatusid,
                 publishuserid,
                 developuserid
          FROM job_info
          WHERE jobid=$jobid"""

    val getUserRoleSql =
      s"""select role from user_info where userid = $userid"""

    val jobinfo = query(db,getCurrentJobInfoSql)

    val userrole = query(db,getUserRoleSql).cellOption(0,"role").getOrElse("")

    val jobstats = jobinfo.cellOption(0,"jobstatusid").getOrElse("")

    val publisherid = jobinfo.cellOption(0,"publishuserid").getOrElse("")

    val developerid = jobinfo.cellOption(0,"developuserid").getOrElse("")

    val permission = jobstats match {
      case "201" =>
        Permission(if ("admin" == userrole) true else false,"发布")
      case "202" =>
        Permission(if(userid != "" && userid != publisherid) true else false,"参与竞标")
      case "203" =>
        Permission(if(userid != "" && userid != publisherid) true else false,"参与竞标")
      case "204" =>
        Permission(if(userid != "" && userid == developerid) true else false,"开发完成")
      case "205" =>
        Permission(if(userid != "" && userid == publisherid) true else false,"验收")
      case "206" =>
        Permission(if(userid != "" && userid == publisherid) true else false,"验收完成")
      case "207" =>
        Permission(if(userid != "" && userid == developerid) true else false,"确认")
      case "208" =>
        Permission(if(userid != "" && userid == developerid) true else false,"开发方评价")
      case "209" =>
        Permission(if(userid != "" && userid == publisherid) true else false,"需求方评价")
      case "210" =>
        Permission(isallowed = false,"")

    }

    Ok(Json.toJson(permission))
  }

  def judgeHireAction(userid:String,jobid:String) = Action{

    val getJobPublisher = s"select publishuserid,jobstatusid from job_info where jobid = $jobid"

    val res = query(db,getJobPublisher)

    val publisherid = res.cellOption(0,0).getOrElse("")

    val statesid = res.cellOption(0,1).getOrElse("")

    if (publisherid == userid && statesid == "203"){
      Ok(Json.toJson(Permission(isallowed = true,"雇佣")))
    }else{
      Ok(Json.toJson(Permission(isallowed = false,"雇佣")))
    }
  }

  //发布操作
  def publishAction(userid:String,jobid:String) = Action{
    val currenttime = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
    val updateSql = s"update job_info set jobpublishtime = '$currenttime' , jobstatusid = 202 where jobid = $jobid"
    if(update(db,updateSql)){
      Ok(Json.parse("""{"status" : true}"""))
    }else {
      Ok(Json.parse("""{"status" : false}"""))
    }
  }

  //竞标操作
  def tenderAction(userid:String,jobid:String) = Action{
    val tenderSql = s"update job_info set tenderidlist=CONCAT_WS('|', ,'$userid'),jobstatusid=203 where jobid = $jobid"
    if(update(db,tenderSql)){
      Ok(Json.parse("""{"status" : true}"""))
    }else{
      Ok(Json.parse("""{"status" : false}"""))
    }
  }

  //雇佣操作
  def hireAction(userid:String,jobid:String) = Action{
    val currenttime = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
    val updateJobInfo = s"update job_info set developuserid=$userid, jobstarttime = '$currenttime', tenderidlist = '',jobstatusid=204 where jobid = $jobid"
    val updateUserInfo = s"update user_info set developingnum = developingnum+1 where userid = $userid"
    db.withTransaction {
      conn =>
        val stmt = conn.createStatement
        stmt.executeUpdate(updateJobInfo)
        stmt.executeUpdate(updateUserInfo)
        Ok(Json.parse("""{"status" : true}"""))
    }
  }

  //开发完成操作
  def devDoneAction(userid:String,jobid:String) = Action{
    val currenttime = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
    val updateJobInfo = s"update job_info set jobfinishtime = '$currenttime', jobstatusid=205 where jobid=$jobid"
    if(update(db,updateJobInfo)){
      Ok(Json.parse("""{"status" : true}"""))
    }else{
      Ok(Json.parse("""{"status" : false}"""))
    }
  }

  //验收操作
  def checkAction(userid:String,jobid:String) = Action{
    val updateJobInfo = s"update job_info set jobstatusid=206 where jobid=$jobid"
    if(update(db,updateJobInfo)){
      Ok(Json.parse("""{"status" : true}"""))
    }else{
      Ok(Json.parse("""{"status" : false}"""))
    }
  }

  //验收完成操作
  def checkDoneAction(userid:String,jobid:String) = Action{
    db.withTransaction{
      conn =>
        val stmt = conn.createStatement
        val getRewardSql = s"select fee,publishuserid,developuserid from job_info where jobid = $jobid"
        val res = stmt.executeQuery(getRewardSql)
        res.next()
        val reward = res.getDouble("fee")
        val publisherid = res.getInt("publishuserid")
        val developerid = res.getInt("developuserid")
        val paySql = s"update user_info set account = account-$reward where userid=$publisherid"
        val collectSql = s"update user_info set account = account+$reward where userid=$publisherid"
        val updateJobInfo = s"update job_info set jobstatusid = 207 where jobid = $jobid"
        stmt.executeUpdate(paySql)
        stmt.executeUpdate(collectSql)
        stmt.executeUpdate(updateJobInfo)
        Ok(Json.parse("""{"status" : true}"""))
    }
  }

  //验收确认操作
  def checkConfirmAction(userid:String,jobid:String) = Action{
    val updateJobInfo = s"update job_info set jobstatusid=208 where jobid=$jobid"
    if(update(db,updateJobInfo)){
      Ok(Json.parse("""{"status" : true}"""))
    }else{
      Ok(Json.parse("""{"status" : false}"""))
    }
  }



}
