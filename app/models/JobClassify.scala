package models

import play.api.libs.json.Json

case class Classify(classifyId: String, classifyName: String)
object Classify {
  implicit val classifyFormat = Json.format[Classify]
}

case class ClassifyInfo(jobId: String, jobName: String, fee: String, commitTime: String)
object ClassifyInfo {
  implicit val infoFormat = Json.format[ClassifyInfo]
}

case class JobClassify(classifyId: String, classifyName: String, JobList: List[ClassifyInfo])

object JobClassify {
  implicit val classifyFormat = Json.format[JobClassify]
}

case class JobInfo(jobId: String, jobName: String, jobClassifyId: String, jobClassifyName: String, jobStatus: String, fee: String, jobCommitTime: String, jobPublishTime: String, jobExpectFinishTime: String, userName: String)
object JobInfo {
  implicit val jobFormat = Json.format[JobInfo]
}

case class JobResult(idAddSuccess: Boolean, message: String, jobInfo: JobInfo)
object JobResult {
  implicit val jobresultFormat = Json.format[JobResult]
}

case class UserInfo(loginStatus: Boolean, message: String, userId: String, userName: String, role: String, loginTime: String)
object UserInfo {
  implicit val userFormat = Json.format[UserInfo]
}


