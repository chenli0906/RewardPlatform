$(function(){
    $('#t_type').selectpicker({val:"",width:"100px",marginTop:"20px"});
    $('#p_re').click(function(){
        location.href="./index.html";
     })
	$.ajax({
            url: "/getClassify",
            dataType: "json",
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    $('#t_type').append("<option value=" + data[i].classifyId + ">" + data[i].classifyName + "</option>");
                }
                $('#t_type').selectpicker('refresh');
            }
        });
     //   $('#t_type').css("width","100px");
      //  $('#t_type').css("marginTop","20px");
})
function getNeeds()
{
   //var userinfo = ""
   var description = $('#pub_description').val();
   var taskClassify = $('#t_type').val();
   var taskName = $('#pub_title').val();
   var money = $('#money').val();
   var tel = $('#pub_phone').val();
   var expectTime= $('#pub_time').val();
   var url = "/publish";
   var data={};
   data.userid = "1";
   data.name = taskName;
   data.description = description;
   data.classify = taskClassify;
   data.fee = money;
   data.expectTime = expectTime;
   data.tel = tel;
   
   $.ajax({
	 	url:url,   
		data: data,
		type:"GET",
		contentType:"application/json",
		success:function(Data){
           //发布成功！
		   alert(Data.message);
		}
	});
	
	
}
