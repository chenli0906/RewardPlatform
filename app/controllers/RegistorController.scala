package controllers

import javax.inject.Inject

import dal.DbAccessor
import models.RegisterInfo
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

/**
  * Created by 10170216 on 2016/6/24.
  */
class RegistorController @Inject() (db: Database) extends Controller with DbAccessor{

  def regist(username: String,password: String,phonenum: String,email: String) = Action{
    val judgeSql = s"select count(1) from user_info where binary username = '$username'"
    if(query(db,judgeSql).cell(0,0) == "0"){
      val registSql = s"insert into user_info set username='$username',password='$password',phonenum='$phonenum',email='$email',role='user'"
      if (update(db,registSql)){
        Ok(Json.toJson(RegisterInfo(true, "注册成功", username)))
      }else{
        Ok(Json.toJson(RegisterInfo(false, "注册失败，请重试", "")))
      }
    }else{
      Ok(Json.toJson(RegisterInfo(false, s"$username 用户名已存在", "")))
    }

  }
}
