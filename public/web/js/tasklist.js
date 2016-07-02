$(function(){
 $('#t_re').click(function(){
        location.href="./index.html";
     })
    $('#tasklistall').DataTable().destroy();
    $("#tasklistall").dataTable({
        //lengthMenu: [5, 10, 20, 30],//这里也可以设置分页，但是不能设置具体内容，只能是一维或二维数组的方式，所以推荐下面language里面的写法。
        paging: true,//分页
        ordering: true,//是否启用排序
        searching: true,//搜索
        sScrollY:"300px",
        columns: [//对应上面thead里面的序列
            { data: "jobName" },//字段名字和返回的json序列的key对应
            { data: "jobClassifyName" },
            { data: "fee" },
            { data: "jobStatus"},
            { data: "jobPublishTime"},
            { data: "jobExpectFinishTime" },
            { data: "userName" },
			{ data: "taskdetail"},
			{ data: "jobId"}
        ],
        "fnCreatedRow":function(nRow,aData,iDataIndex){
//            alert("create");
        },
		"columnDefs": [{    
			"targets": [8], //隐藏第六列，从第0列开始
			"visible": false    
		}], 		
		"fnRowCallback": function( nRow, aData, iDisplayIndex ) {
			$('td:eq(7)',nRow).html('<a href="detail.html">详情</a>');
            },						
        language: {
//            lengthMenu: '<select class="form-control input-xsmall">' + '<option value="1">1</option>' + '<option value="10">10</option>' + '<option value="20">20</option>' + '<option value="30">30</option>' + '<option value="40">40</option>' + '<option value="50">50</option>' + '</select>条记录',//左上角的分页大小显示。
            search: '<span style="font-weight:bold;font-size:15px;">搜索：</span><span class="label" style="">搜索：</span>',//右上角的搜索文本，可以写html标签

            paginate: {//分页的样式内容。
                previous: "上一页",
                next: "下一页",
                first: "第一页",
                last: "最后"
            },

            zeroRecords: "没有内容",//table tbody内容为空时，tbody的内容。
            //下面三者构成了总体的左下角的内容。
            info: "总共_PAGES_ 页，显示第_START_ 到第 _END_ ，共 _TOTAL_ 条，每页_MAX_ 条 ",//左下角的信息显示，大写的词为关键字。
            infoEmpty: "0条记录",//筛选为空时左下角的显示。
            infoFiltered: ""//筛选之后的左下角筛选提示，
        },
        paging: true,
        pagingType: "full_numbers",//分页样式的类型
    });

    var d = [
        {"jobId":"1","jobName":"炒菜做饭","jobClassifyId":"101","jobClassifyName":"美工设计","jobStatus":"待开发者评价","fee":"200.1","jobCommitTime":"2016-06-25 09:47:54.0","jobPublishTime":"2016-06-25 00:00:00.0","taskdetail":"详情","jobExpectFinishTime":"2016-07-10 00:00:00.0","userName":"ChenLi"},{"jobId":"2","jobName":"打扫卫生","jobClassifyId":"101","taskdetail":"详情","jobClassifyName":"美工设计","jobStatus":"待审核","fee":"200.1","jobCommitTime":"2016-06-25 11:57:08.0","jobPublishTime":"2016-06-25 00:00:00.0","jobExpectFinishTime":"2016-07-10 00:00:00.0","userName":"ChenLi","taskdetail":"详情"},{"jobId":"3","jobName":"照顾小孩","taskdetail":"详情","jobClassifyId":"101","jobClassifyName":"美工设计","jobStatus":"发布成功","fee":"200.1","jobCommitTime":"2016-06-25 11:57:01.0","jobPublishTime":"2016-06-25 00:00:00.0","jobExpectFinishTime":"2016-07-10 00:00:00.0","userName":"ChenLi"},{"jobId":"4","taskdetail":"详情","jobName":"家教","jobClassifyId":"101","jobClassifyName":"美工设计","jobStatus":"待审核","fee":"200.1","jobCommitTime":"2016-06-25 11:57:01.0","jobPublishTime":"2016-06-25 00:00:00.0","jobExpectFinishTime":"2016-07-10 00:00:00.0","userName":"ChenLi"},{"taskdetail":"详情","jobId":"5","jobName":"图片美化","jobClassifyId":"101","jobClassifyName":"美工设计","jobStatus":"开发中","fee":"200.1","jobCommitTime":"2016-06-25 11:57:00.0","jobPublishTime":"2016-06-25 00:00:00.0","jobExpectFinishTime":"2016-07-10 00:00:00.0","userName":"ChenLi"}
    ]	
    $("#tasklistall_wrapper > div:nth-child(1) > div:nth-child(1)").css({display:"none"});
    $("#tasklistall_filter").css({float:"left"});
    $("#tasklistall_filter > label > input").css({marginLeft:"-50px"});
    $("#tasklistall").dataTable().fnAddData(d,"random");
});

function getdata(input,searchType)
{
    var data={};
	data.input=input;
	data.searchType =searchType;
	var d = $.ajax({
	 	url:'/search',   
		data: "",
		type:"GET",
		contentType:"application/json",
		success:function(Data){
            return Data;
		}
	});	
	
}