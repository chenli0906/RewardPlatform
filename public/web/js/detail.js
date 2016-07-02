$(function(){
     var taskid = location.href.split("?")[1];
     $('#d_re').click(function(){
        location.href="./index.html";
     })
//
//     $.ajax({
//           url: "/jobdetails/basicinfos?jobid="+taskid,
//           dataType: "json",
//           success: function (data) {
//                alert(data);
            var d = [{"jobname":"图片美化","fee":"200.1","description":"这是一张美丽的图片","jobclassify":"美工设计","jobstates":"开发中","jobcommittime":"2016-06-25 11:57:00.0","jobpublishtime":"2016-06-25 00:00:00.0","jobexpecttime":"2016-07-10 00:00:00.0","publisher":"ChenLi"}];
            var jobname = d[0].jobname;
            var fee = d[0].fee;
            var description = d[0].description;
            var jobclassify = d[0].jobclassify;
            var jobstates = d[0].jobstates;
            var jobcommittime = d[0].jobcommittime;
            var jobpublishtime = d[0].jobpublishtime;
            var jobexpecttime = d[0].jobexpecttime;
            var publisher = d[0].publisher;

            $('#taskcount').text(fee);
            $('.d_taskname').text(jobname);
            $('.d_user').text(publisher);
            $('.d_time').text(jobpublishtime);
            $('#u_description').text(description);

//           }
//     });

	$(".task_progress").loadStep({
      //ystep的外观大小
      //可选值：small,large
      size: "small",
      //ystep配色方案
      //可选值：green,blue
      color: "green",
      //ystep中包含的步骤
      steps: [{
    	//步骤名称
    	title: "待审核",
    	//步骤内容(鼠标移动到本步骤节点时，会提示该内容)
    	content: "赏金任务待审核"
      },{
    	title: "已发布",
    	content: "赏金任务已发布"
      },{
    	title: "竞标中",
    	content: "赏金任务竞标中"
      },{
    	title: "开发中",
    	content: "赏金任务开发中"
      },{
    	title: "开发完成",
    	content: "赏金任务开发完成"
      },{
        title: "验收中",
        content: "赏金任务验收中"
      },{
        title: "验收完成",
        content: "赏金任务验收完成"
      },{
        title: "待开发方评价",
        content: "待开发方评价"
      },{
        title: "待需求方评价",
        content: "待需求方评价"
      },{
        title: "完成",
        content: "赏金任务完成"
      }]
    });

  //  $(".task_progress").setStep(4);
    getbaseinfo();
	detail();
    $('#credit1').raty({ readOnly: true, score: 3});
    $('#credit2').raty({ readOnly: true, score: 2});

});

var step = "";
function getbaseinfo()
{
     var data={};
	 data.userid = "1";
	 data.jobid = "7";
     $.ajax({
	 	url:'/jobdetails/basicinfos',   
		data: data,
		type:"GET",
		async: false,
		contentType:"application/json",
		success:function(Data){
			if(Data[0].jobstates=="待审核"){step = 1};
			if(Data[0].jobstates=="发布成功"){step = 2};
			if(Data[0].jobstates=="竞标中"){step = 3};
			if(Data[0].jobstates=="开发中"){step = 4};
			if(Data[0].jobstates=="开发完成"){step = 5};
			if(Data[0].jobstates=="验收中"){step = 6};
			if(Data[0].jobstates=="验收完成"){step = 7};
			if(Data[0].jobstates=="待开发方评价"){step = 8};
			if(Data[0].jobstates=="待需求方评价"){step = 9};
			if(Data[0].jobstates=="完成"){step = 10};
			 $(".task_progress").setStep(3);
			 $('#dd_count_num').val()=Data[0].fee;
			}
						
	});

}
function detail()
{
     var data={};
	 data.userid = "1";
	 data.jobid = "7";
     $.ajax({
	 	url:'/permission/getpermission',   
		data: data,
		type:"GET",
		contentType:"application/json",
		success:function(Data){
           //发布成功！
		   if(Data.isallowed==true)
		   {
		      $('#changeButton').css("display","block");
		      document.getElementById("status").innerHTML = Data.actionname;
		   } 
		   else{
		      $('#changeButton').css("display","none");
		   }
		}
	});
}

function changeInfo(){
     var data={};
	 data.userid = "1";
	 data.jobid = "7";
	 var url="";
	 var status = $('#status').text();
	if(status=="发布"){url='/permission/publish';step=2};
	if(status=="参与竞标"){url='/permission/tender';step=3};
	if(status=="开发完成"){url='/permission/devdone';step=5};
	if(status=="验收"){url='/permission/check';step=6};
	if(status=="验收完成"){url='/permission/devdone';step=7};
	if(status=="确认"){url=' /permission/checkconfirm';step=8};
	if(status=="待开发方评价"){url='/submitJudgement';step=9};
	if(status=="待需求方评价"){url='/submitJudgement';step=10};
	//if(status=="完成"){url='/permission/checkconfirm'};
     $.ajax({
	 	url:url,   
		data: data,
		type:"GET",
		contentType:"application/json",
		success:function(Data){
           $(".task_progress").setStep(step);
		}
	});  
}

function apply()
{
     var data={};
	 data.userid = "1";
	 data.jobid = "7";
     url='/permission/gethirepermission';
	 $.ajax({
	 	url:url,   
		data: data,
		type:"GET",
		contentType:"application/json",
		success:function(Data){
		  if(true)
		//if(Data.isallowed==true)
		   {
		      $('#changeButton').css("display", "block");
		      document.getElementById("changeButton").innerHTML = "开发完成";
		   } 
		   else{
		      $('#changeButton').css("display", "none");
		   }
           $(".task_progress").setStep(4);
		   
		   $('.d_complete').hide();
		}
	});  
	 

}