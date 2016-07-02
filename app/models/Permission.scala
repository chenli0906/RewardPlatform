package models

import play.api.libs.json.Json

/**
  * Created by 10170216 on 2016/6/25.
  */
case class Permission(isallowed:Boolean,actionname:String)

object Permission{
  implicit val personFormat = Json.format[Permission]
}
