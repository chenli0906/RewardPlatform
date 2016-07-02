$(function(){
 $('#u_re').click(function(){
        location.href="./index.html";
     })

/*	document.getElementById("count").innerHTML = "300"+"元"; //根据用户信息填写
	document.getElementById("phone").innerHTML = "15019480761";
	document.getElementById("email").innerHTML = "hh@zte.com.cn";
	document.getElementById("publishing").innerHTML = "10";
	document.getElementById("developing").innerHTML = "10";
	document.getElementById("published").innerHTML = "20";
	document.getElementById("developed").innerHTML = "10";*/
	defalutTask();
	var data = {};
	data = defalutBasicInfo();
	document.getElementById("name").innerHTML = data[0].username;
	document.getElementById("count").innerHTML = data[0].account; //根据用户信息填写
	document.getElementById("phone").innerHTML = data[0].phonenum;
	document.getElementById("email").innerHTML = data[0].email;
	document.getElementById("publishing").innerHTML = data[0].publishingnum;
	document.getElementById("developing").innerHTML = data[0].developingnum;
	document.getElementById("published").innerHTML = data[0].publishednum;
	document.getElementById("developed").innerHTML = data[0].developednum;
	document.getElementById("usertag").innerHTML = data[0].tag;
	document.getElementById("userdesc").innerHTML = data[0].introduction;
//	var data.avgpublishscore data.avgdevelopscore
	
    //星星打分函数
    $('#devcredit').raty({ readOnly: true, score: parseInt(data[0].avgdevelopscore,10) });
    $('#zbcredit').raty({ readOnly: true, score: parseInt(data[0].avgpublishscore,10) });

    //
    $('#description').hover(function(){

    });

    function pageChange(index){
        $("#pageControl").pagination(56, {
            num_edge_entries: 2,
            num_display_entries: 4,
            callback: function(index, jq){

                 $("#pageControl a,#pageControl span").css({
                        border:"solid 1px black",
                        backgroundColor: "white",
                        padding: "8px",
                        fontSize: "8px",
                        marginLeft: "12px",
                        marginTop: "10px"
                    });
            },
            items_per_page:1
        });
        $("#pageControl a,#pageControl span").css({
            border:"solid 1px black",
            backgroundColor: "white",
            padding: "8px",
            fontSize: "8px",
            marginLeft: "12px",
            marginTop: "10px"
        });
    }

    pageChange(2)

});

var developinghtml ="";
var publishinghtml ="";
var tenderinghtml ="";
var evaluatinghtml="";
function getTask(e,taskStatus)
{
  //任务状态列表
   $('.task_tag').removeClass("task-selected");
   $(e).addClass("task-selected");
 // $('.task_tag').addClass("task-selected");
  document.getElementById("task-list").innerHTML = "";
  if(taskStatus=="1"){
     document.getElementById("task-list").innerHTML = developinghtml;
  }
  if(taskStatus=="2"){
    document.getElementById("task-list").innerHTML = developinghtml;
  }
  if(taskStatus=="3"){
     document.getElementById("task-list").innerHTML = tenderinghtml;
  }
  if(taskStatus=="4"){
    document.getElementById("task-list").innerHTML = evaluatinghtml;
  }
   
//******************************************************/
}

function defalutTask() //默认选择开发中
{
    var data={};
	data.userid = "1";
    $.ajax({
      url:'/userinfo/jobinfos',
	  data:data,
	  type:"GET",
	  async: false,
	  contentType:"application/json",
	  success:function(Data){
	    for(i=0;i<Data.length;i++){
		
		if(Data[i].userjobstate == "developing" ){

		       developinghtml = "";
		
			  for(j=0;j<Data[i].jobinfolist.length;j++)
			  {
			      if(Data[i].jobinfolist[j].jobid!="")
			      {
			           var jobid = Data[i].jobinfolist[j].jobid;
                       var jobname = Data[i].jobinfolist[j].jobname;
                       var jobclassify = Data[i].jobinfolist[j].jobclassify;
                       var fee = Data[i].jobinfolist[j].fee;
                       var expectfinishtime = Data[i].jobinfolist[j].expectfinishtime;
                       var jobstate = Data[i].jobinfolist[j].jobstate;
                        developinghtmltmp= '<div class="task">'+
                         '<div class="tasktop"></div>' +
                         '<div>'
                            + '<span class="taskname">'+jobname+'</span>'+
                             '<span class="tasktype">'+jobclassify+'</span>'+
                        ' </div>'+
                         '<div style="margin-top: 30px;">'+
                             '<span class="taskstatus">'+jobstate+'</span>'+
                             '<span class="taskdeadline">交付日期：<span>'+expectfinishtime+'</span></span>'+
                             '<span class="taskcount">悬赏金额：￥<span>'+fee+'</span></span>'+
                             '<span class="taskdetail">'+'<a href ="index.html" onclick = "getTaskDetail(jobid)">详情</a>'+'</span>'
                         +'</div>'+
                      '</div>';

                       //document.getElementById("task-list").innerHTML = html;
                         developinghtml += developinghtmltmp;
			      }

			  }
	  
		  }  
		  if(Data[i].userjobstate == "publishing" ){

		     publishinghtml ="";
		  
		  	for(k=0;k<Data[i].jobinfolist.length;k++)
			  {
			       if(Data[i].jobinfolist[k].jobid!="")
                  {
                     var jobid = Data[i].jobinfolist[k].jobid;
                       var jobname = Data[i].jobinfolist[k].jobname;
                       var jobclassify = Data[i].jobinfolist[k].jobclassify;
                       var fee = Data[i].jobinfolist[k].fee;
                       var expectfinishtime = Data[i].jobinfolist[k].expectfinishtime;
                       var jobstate = Data[i].jobinfolist[k].jobstate;
                        publishinghtmltmp= '<div class="task">'+
                         '<div class="tasktop"></div>' +
                         '<div>'
                            + '<span class="taskname">'+jobname+'</span>'+
                             '<span class="tasktype">'+jobclassify+'</span>'+
                        ' </div>'+
                         '<div style="margin-top: 30px;">'+
                             '<span class="taskstatus">'+jobstate+'</span>'+
                             '<span class="taskdeadline">交付日期：<span>'+expectfinishtime+'</span></span>'+
                             '<span class="taskcount">悬赏金额：￥<span>'+fee+'</span></span>'+
                             '<span class="taskdetail">'+'<a href ="index.html" onclick = "getTaskDetail(jobid)">详情</a>'+'</span>'
                         +'</div>'+
                      '</div>';

                       //document.getElementById("task-list").innerHTML = html;
                         publishinghtml += publishinghtmltmp;
                  }

			  }
		
		}
		if(Data[i].userjobstate == "tendering" ){

		      tenderinghtml ="";
		
		   for(l=0;l<Data[i].jobinfolist.length;l++)
			  {
			       if(Data[i].jobinfolist[l].jobid!="")
			       {
			           var jobid = Data[i].jobinfolist[l].jobid;
                       var jobname = Data[i].jobinfolist[l].jobname;
                       var jobclassify = Data[i].jobinfolist[l].jobclassify;
                       var fee = Data[i].jobinfolist[l].fee;
                       var expectfinishtime = Data[i].jobinfolist[l].expectfinishtime;
                       var jobstate = Data[i].jobinfolist[l].jobstate;
                        tenderinghtmltmp= '<div class="task">'+
                         '<div class="tasktop"></div>' +
                         '<div>'
                            + '<span class="taskname">'+jobname+'</span>'+
                             '<span class="tasktype">'+jobclassify+'</span>'+
                        ' </div>'+
                         '<div style="margin-top: 30px;">'+
                             '<span class="taskstatus">'+jobstate+'</span>'+
                             '<span class="taskdeadline">交付日期：<span>'+expectfinishtime+'</span></span>'+
                             '<span class="taskcount">悬赏金额：￥<span>'+fee+'</span></span>'+
                             '<span class="taskdetail">'+'<a href ="index.html" onclick = "getTaskDetail(jobid)">详情</a>'+'</span>'
                         +'</div>'+
                      '</div>';

                       //document.getElementById("task-list").innerHTML = html;
                         tenderinghtml += tenderinghtmltmp;
			       }

			  }
		
		}
		if(Data[i].userjobstate == "evaluating" ){

		  evaluatinghtml ="";
		
		 for(m=0;m<Data[i].jobinfolist.length;m++)
			  {
			      if(Data[i].jobinfolist[m].jobid!=""){
			      var jobid = Data[i].jobinfolist[m].jobid;
                   var jobname = Data[i].jobinfolist[m].jobname;
                   var jobclassify = Data[i].jobinfolist[m].jobclassify;
                   var fee = Data[i].jobinfolist[m].fee;
                   var expectfinishtime = Data[i].jobinfolist[m].expectfinishtime;
                   var jobstate = Data[i].jobinfolist[m].jobstate;
                    evaluatinghtmltmp= '<div class="task">'+
                      '<div class="tasktop"></div>' +
                      '<div>'
                         + '<span class="taskname">'+jobname+'</span>'+
                          '<span class="tasktype">'+jobclassify+'</span>'+
                     ' </div>'+
                      '<div style="margin-top: 30px;">'+
                          '<span class="taskstatus">'+jobstate+'</span>'+
                          '<span class="taskdeadline">交付日期：<span>'+expectfinishtime+'</span></span>'+
                          '<span class="taskcount">悬赏金额：￥<span>'+fee+'</span></span>'+
                          '<span class="taskdetail">'+'<a href ="index.html" onclick = "getTaskDetail(jobid)">详情</a>'+'</span>'
                      +'</div>'+
                   '</div>';

                    //document.getElementById("task-list").innerHTML = html;
                     evaluatinghtml += evaluatinghtmltmp;
			      }

			  }
		
		}
  
    }
    document.getElementById("task-list").innerHTML = developinghtml;
  }
});
}

function defalutBasicInfo()
 {
      var data1={};
      var res={};
      data1.userid = "1";
      $.ajax({
          url:'/userinfo/basicinfos',
          data:data1,
          type:"GET",
          async: false,
          contentType:"application/json",
          success:function(Data){
             res = Data;
          },
      });
   return res;
}
