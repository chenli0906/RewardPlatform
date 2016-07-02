package models

import play.api.libs.json.Json

/**
  * Created by 10171169 on 2016/6/25.
  */

case class RegisterInfo(isSuccess: Boolean, message: String, userName: String)

object RegisterInfo{
  implicit val registerFormat = Json.format[RegisterInfo]
}
