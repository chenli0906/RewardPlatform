package controllers

import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

import dal.DbAccessor
import models.UserInfo
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}


class LoginController @Inject()(db: Database)extends Controller with DbAccessor{

  def login(userName: String, password: String) = Action {
    val res = query(db, s"select username,password, userid, role from User_Info where username = '${userName}'").value

    if(res.isEmpty) {
      Ok(Json.toJson(UserInfo(false, "用户名不存在，请先注册.", "", "", "", "")))
    } else {
      val name    = res.head.head
      val pwd     = res.head(1)
      val id      = res.head(2)
      val role    = res.head(3)

      if(name == userName && pwd == password){
//        new InitControllers(db).pageInit(userName, password)
        val user = UserInfo(true, "登录成功", id, name, role, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
        Ok(Json.toJson(user))
      }
      else {
        Ok(Json.toJson(UserInfo(false, "用户名或密码错误", "", "", "", "")))
      }
    }
  }
}
