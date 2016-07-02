package models

import play.api.libs.json.Json

/**
  * Created by 10170216 on 2016/6/25.
  */
case class JobDetailsInfo(jobname:String,
                          fee:String,
                          jobclassify:String,
                          jobstates:String,
                          jobcommittime:String,
                          jobpublishtime:String,
                          jobexpecttime:String,
                          description:String,
                          publisher:String
                         )

object JobDetailsInfo{
  implicit val personFormat = Json.format[JobDetailsInfo]
}

case class TenderUserInfo(username:String,
                          developednum:String,
                          avgdevelopedscore:String,
                          tag:String)

object TenderUserInfo{
  implicit val personFormat = Json.format[TenderUserInfo]
}