package controllers

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

import dal.DbAccessor
import models.{JudgementInfo, JudgementInfoForAdmin}
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}


class JudgeController @Inject()(db: Database) extends Controller with DbAccessor{
  def submitJudgement(jobId: String,
                      judgeType: String,
                      publisherId: String,
                      developerId: String,
                      judgement: String,
                      score: String) = Action {
    // 1 - 发布者对完成者的评价
    // 2 - 完成者对发布者的评价
    val time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
    judgeType match {
      case "1" => {
        val addJudgementSql =
          s"""Update User_Judgement_Info set
              |developerJudgeTime = '$time',
              |developerJudge = '$judgement',
              |publisherScore = $score
              |where jobid = $jobId""".stripMargin
        if(! update(db, addJudgementSql)){
          Ok(Json.parse("""{"status" : false}"""))
        } else {
          updatePublishUserInfo(judgeType, publisherId)
          updateDevelopUserInfo(judgeType, developerId)
          updateJobStatus(jobId: String, "210")
          Ok(Json.parse("""{"status" : true}"""))
        }
      }
      case "2" => {
        val addJudgementSql =
          s"""Insert into User_Judgement_Info set
              |jobid = $jobId,
              |publisherid  = $publisherId,
              |developerid  = $developerId,
              |publisherJudgeTime = '$time',
              |publisherJudge = '$judgement',
              |developerScore = '$score'""".stripMargin
        if(! update(db, addJudgementSql)){
          Ok(Json.parse("""{"status" : false}"""))
        } else {
          updatePublishUserInfo(judgeType, publisherId)
          updateDevelopUserInfo(judgeType, developerId)
          updateJobStatus(jobId: String, "209")
          Ok(Json.parse("""{"status" : true}"""))
        }
      }
    }
  }

  private def updatePublishUserInfo(judgeType: String, publisherId: String) = {

    judgeType match {
      case "1" => {
        //更新发布者信息中的已完成任务数
        val updateSql = s"""Update user_info set publishingnum = (publishingnum - 1), publishednum = (publishednum + 1) where userid = $publisherId"""
        update(db, updateSql)
      }
      case "2" => {
        //更新开发者对发布者的信息更新
        //先从评价表中获取发布者的已更新的评价信息，主要为得分
        val getLatestScore = query(db, s"""select avg(developerScore) as avgdevelopscore from User_Judgement_Info where publisherid = $publisherId """).value
        val latestScore = if(getLatestScore.isEmpty) "0" else getLatestScore.head.head
        val updateSql = s"""Update user_info set avgdevelopscore = $latestScore where userid = $publisherId"""
        update(db, updateSql)
      }
    }
  }

  private def updateDevelopUserInfo(judgeType: String, developerId: String) = {

    judgeType match {
      case "1" => {
        //更新发布者对开发者打分信息
        //先从评价表中获取发布者的已更新的评价信息，主要为得分
        val getLatestScore = query(db, s"""select avg(publisherScore) as avgdevelopscore from User_Judgement_Info where developerid = $developerId """).value
        val latestScore = if(getLatestScore.isEmpty) "0" else getLatestScore.head.head
        val updateSql = s"""Update user_info set avgdevelopscore = $latestScore where userid = $developerId"""
        update(db, updateSql)
      }
      case "2" => {
        //更新开发者对发布者的信息更新
        val updateSql = s"""Update user_info set developingnum = (developingnum - 1), developednum = (developednum + 1) where userid = $developerId"""
        update(db, updateSql)
      }
    }
  }

  private def updateJobStatus(jobId: String, stausId: String) = {
    val updateStatus = s"""update job_info set jobstatusid = $stausId where jobid = $jobId"""
    update(db, updateStatus)
  }

  def getLatestTopNJudgement(userid: String, userType: String) = Action {
    // 1 - 发布方查看开发方  开发ID
    // 2 - 开发方查看发布方  发布ID
    // 3 - 发布方查看已收到  发布ID
    // 4 - 开发方查看已收到  开发ID
    // 5 - 管理员查看已收到和已发送  用户ID

    userType match {
      case "1" => {
        val sql = s"""select publisherJudge, publisherScore,judgeTime from User_Judgement_Info where developerid = $userid odery by publisherJudgeTime desc """
        queryJudgementById(userid, sql)
      }
      case "2" => {
        val sql = s"""select developerJudge, developerScore, judgeTime from User_Judgement_Info where publisherid = $userid odery by developerJudgeTime desc """
        queryJudgementById(userid, sql)
      }
      case "3" => {
        val sql = s"""select publisherJudge, publisherScore, judgeTime from User_Judgement_Info where publisherid = $userid odery by developerJudgeTime desc """
        queryJudgementById(userid, sql)
      }
      case "4" => {
        val sql = s"""select developerJudge, developerScore, judgeTime from User_Judgement_Info where developerid = $userid odery by publisherJudgeTime desc """
        queryJudgementById(userid, sql)
      }
      case _ => {
        val sendSql = s"""select developerJudge, developerScore, judgeTime from User_Judgement_Info where publisherid = $userid odery by publisherJudgeTime desc """
        val receiveSql = s"""select publisherJudge, publisherScore, judgeTime from User_Judgement_Info where developerid = $userid odery by developerJudgeTime desc"""
        val res = JudgementInfoForAdmin(getJudgementById(userid, sendSql), getJudgementById(userid, receiveSql))
        Ok(Json.toJson(res))
      }
    }
  }

  private def queryJudgementById(userId: String, sql: String) = {
    val result = query(db, sql).value
    result.isEmpty match {
      case true => Ok(Json.toJson(JudgementInfo("", "", "")))
      case _ => Ok(Json.toJson(result.map(judge => JudgementInfo(judge.head, judge(1), judge(2)))))
    }
  }

  private def getJudgementById(userId: String, sql: String): List[JudgementInfo] = {
    val result = query(db, sql).value
    result.isEmpty match {
      case true => List(JudgementInfo("", "", ""))
      case _ => result.map(judge => JudgementInfo(judge.head, judge(1), judge(2)))
    }
  }
}
