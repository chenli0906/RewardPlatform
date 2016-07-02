$(function(){

    $('#s_type').selectpicker({noneSelectedText:'请选择',width:"110px"});
    $('body > div > div.container-fluid > div > div.col-md-8 > div.search > div > button').css({
        marginTop:"8px",
        height:"35px"
    });
    $('#loginTitle').css({
        borderBottom:'solid 3px #F26B2F',
        width:"150px"
    });

    $('#loginTitle').click(function(){
        $('#loginPage').css({
            display:'block'
        });
        $('#registerPage').css({
            display:'none'
        });
        $('#loginTitle').css({
            borderBottom:'solid 3px #F26B2F',
            width:"150px",
            marginLeft:"20px"
        });
        $('.login-title').css({
             marginLeft:"9px"
         });
        $('#registerTitle').css({
            borderBottom:'none'
        });
    });
    $('#registerTitle').click(function(){
        $('#loginPage').css({
            display:'none'
        });
        $('#registerPage').css({
            display:'block'
        });
        $('#loginTitle').css({
            borderBottom:'none'
        });
        $('#registerTitle').css({
            borderBottom:'solid 3px #F26B2F',
            width:"150px"
        });
    });

    $('#jump1').click(function(){
        location.href = "./tasklist.html"
    });
    $('#jump2').click(function(){
        location.href = "./tasklist.html"
    });
    $('#jump3').click(function(){
        location.href = "./tasklist.html"
    });
    $('#jump4').click(function(){
        location.href = "./tasklist.html"
    });

     $('#login').click(function(){
     //   var username = $('#username1').text();
		var username = $('#username1').val();
        var password = $('#password1').val();
		
        if(username=="" || password==""){
            $('#loginPageInfo').text("用户名或密码为空！")
        }else{
			userLogin(username,password);
        }
     });

     function lookDetail(id){
        alert(id);
     }

     function loadInitData(){
//        $.ajax({
//            url: "/pageinit",
//            dataType: "json",
//            success: function (data) {

                $('#designer').empty();
                $('#softdev').empty();
                $('#doc').empty();
                $('#other').empty();
                var d = [
                    {"classifyId":"101","classifyName":"美工设计","JobList":[{"jobId":"11","jobName":"野外写生","fee":"2","commitTime":"2016-06-25 11:57:30.0"},{"jobId":"12","jobName":"雕像设计","fee":"22","commitTime":"2016-06-25 11:57:30.0"}]},
                    {"classifyId":"102","classifyName":"软件开发","JobList":[{"jobId":"2","jobName":"lili","fee":"550","commitTime":"2016-06-25 11:57:28.0"}]},
                    {"classifyId":"103","classifyName":"文档处理","JobList":[{"jobId":"3","jobName":"Word排版","fee":"150","commitTime":"2016-06-25 11:56:37.0"}]},
                    {"classifyId":"104","classifyName":"其他类型","JobList":[{"jobId":"4","jobName":"素描","fee":"3","commitTime":"2016-06-25 11:57:41.0"}]}
                ]

                var data1 = d[0].JobList;
                var data2 = d[1].JobList;
                var data3 = d[2].JobList;
                var data4 = d[3].JobList;
                var n1 = data1.length<5?data1.length:5;
                var n2 = data2.length<5?data2.length:5;
                var n3 = data3.length<5?data3.length:5;
                var n4 = data4.length<5?data4.length:5;

                for (var i = 0; i < n1; i++) {
                    $('#designer').append("<li data-id=" + data1[i].jobId + ">" + data1[i].jobName + " 赏金 ￥"+ data1[i].fee + "</li>");
                }
                for (var i = 0; i < n2; i++) {
                    $('#softdev').append("<li data-id=" + data2[i].jobId + ">" + data2[i].jobName + " 赏金 ￥"+ data2[i].fee + "</li>");
                }
                for (var i = 0; i < n3; i++) {
                    $('#doc').append("<li data-id=" + data3[i].jobId + ">" + data3[i].jobName + " 赏金 ￥"+ data3[i].fee + "</li>");
                }
                for (var i = 0; i < n4; i++) {
                    $('#other').append("<li data-id=" + data4[i].jobId + ">" + data4[i].jobName + " 赏金 ￥"+ data4[i].fee + "</li>");
                }
                $('ol li').click(function(){
                    var id = this.dataset.id;
                    location.href = "./detail.html?taskid="+id
                });

//            }
//        });
     }

     loadInitData();

     $('#register').click(function(){
         var username = $('#username2').val();
         var password = $('#password2').val();
         var password2 = $('#password3').val();
         var phone = $('#phone').val();
         var email = $('#email').val();

         if(username=="" || password==""){
             $('#registerPageInfo').text("用户名或密码为空！")
         }else{
             //在这里可以做电子邮箱和手机格式的验证			 
			 if(isEmail(email)&&isPhone(phone))
			 {
			    if(password== password2 )
				{
			    userRegister(username,password,password2,phone,email);
				}
				else
				{
				   document.getElementById("password2").value="";
				   document.getElementById("password3").value="";
			       alert("两次密码不对应，请重新输入");
				}
			 }
         }
     });

    $('#wlogin').click(function(){
        $('#myModal').modal();
    });

     $('#wregis').click(function(){
        $('#myModal').modal();
        $('#registerTitle').click();
     });

});

function isEmail(email)
{
  var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
  if(!myreg.test(email))
  {
  //   $('#email').attr("value","");
	 document.getElementById("email").value="";
     alert('提示\n\n请输入有效的E_mail！');
     return false;
   }
   else
   {
      return true;
   }
}

function isPhone(phone)
{
  var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
  if(!mobile.test(phone))
  {    
	// $('#phone').attr("value","");
	 document.getElementById("phone").value="";
	 alert('提示\n\n请输入有效的手机号！');
     return false;
   }
   else
   {
      return true;
   }
  
}
function userLogin(username,password){
      var url = '/login';
	  var data = {};
	  data.userName = username;
	  data.password = password;

	  if(username == ""){
	    alert("用户名不能为空");
	  }

	  if(password == ""){
	    alert("请输入密码");
	  }
	  
      $.ajax({
	 	url:url,   
		data: data,
		type:"GET",
		contentType:"application/json",
		success:function(Data){
		   if(Data.loginStatus == true)
		   {   
		       var userName = Data.userName;
		       if(userName == undefined){
		        $('#wlogin').text("你好,小强");
		       }else{
		       $('#wlogin').text("你好,"+userName);
		       }
		       $('#wlogin').click(function(){
                       
                   });

		       //$('#').html(username);
			 //  userCenter(data.info);
			// window.close();

		   }
		   else
		    {
			   alert("用户名与密码不对应，请重新输入");
			}
		}
	});
}
function userRegister(username, password, password2, phone, email){
       var url = '/regist';
	   var data= {};
	   data.username = username;
	   data.password = password;
	   data.phonenum = phone;
	   data.email = email;
       $.ajax({
	 	url:url,   
		data: data,
		type:"GET",
		contentType:"application/json",
		success:function(Data){
		   if(Data.isSuccess == true)
		   {   
		       var username = Data.username;
		      // $('#').html(username);
			 //  userCenter(data.info);
			 window.close();
		   }
		   else
		    {
			   alert(Data.message);
			}
		}
	});
}

