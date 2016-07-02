package models

import play.api.libs.json.Json


case class JudgementInfo(judgement: String, judgeScore: String, time: String)

object JudgementInfo {
  implicit val judgementFormat = Json.format[JudgementInfo]
}

case class JudgementInfoForAdmin(acceptedJudgement: List[JudgementInfo], sendJudgement: List[JudgementInfo])

object JudgementInfoForAdmin {
  implicit val judgementAdminFormat = Json.format[JudgementInfoForAdmin]
}