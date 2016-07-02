package models

import play.api.libs.json.Json

/**
  * Created by 10170216 on 2016/6/24.
  */
case class UserBasicInfos(username:String,
                          account:String,
                          phonenum:String,
                          email:String,
                          registtime:String,
                          tag:String,
                          introduction:String,
                          publishingnum:String,
                          publishednum:String,
                          developingnum:String,
                          developednum:String,
                          avgpublishscore:String,
                          avgdevelopscore:String
                         )

object UserBasicInfos {

  implicit val personFormat = Json.format[UserBasicInfos]
}

case class UserJobBasicInfos(jobid:String,jobname:String,jobclassify:String,fee:String,expectfinishtime:String,jobstate:String)

object UserJobBasicInfos {

  implicit val personFormat = Json.format[UserJobBasicInfos]
}

case class UserJobInfos(userjobstate:String,jobinfolist:List[UserJobBasicInfos])

object UserJobInfos {

  implicit val personFormat = Json.format[UserJobInfos]
}

object UserJobState{
  val Develop = "developing"
  val Publish = "publishing"
  val Tender = "tendering"
  val Evaluate = "evaluating"
  val Check = "checking"
}



