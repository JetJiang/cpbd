$(function(){
	//折线图
//	var ctx = document.getElementById("canvas").getContext("2d");
//	window.myLine = new Chart(ctx).Line(getdata(), {
//		responsive: true
//	});
	//日期选择器
	$(".form_datetime").datetimepicker({
		format: 'yyyy/mm/dd',
		autoclose:true,
		minView:'month',
		language:'zh-CN'
		});
	//select button
	$(".abjb1-item").click(function(){
		$("#abjb1").html($(this).html());
	});
	$(".abjb2-item").click(function(){
		$("#abjb2").html($(this).html());
	});
	$(".ma1-item").click(function(){
		$("#ma1").html($(this).html());
	});
	$(".ma2-item").click(function(){
		$("#ma2").html($(this).html());
	});
	//复选框
	$('input').iCheck({
		checkboxClass: 'icheckbox_square-blue',
	    increaseArea: '20%' // optional
	});
})
function selectdata(){
		var start = $("#start").val();
		var end = $("#end").val();
		$.ajax({
	           url:'/Days/getdatabycondition',
	           type:'get',
	           async:false,
	           dataType:'json',
	           data:{
	              'start':start,
	              'end':end
	           },
	           error:function(){
	             console.log("error");
	           },
	           success:function(data){
	        	   console.log("success");
	        	   lineChartData = {
	        			   labels:data.dates,
	        			   datasets : [
	        							{
	        								label: "My First dataset",
	        								fillColor : "rgba(220,220,220,0.2)",
	        								strokeColor : "rgba(220,220,220,1)",
	        								pointColor : "rgba(220,220,220,1)",
	        								pointStrokeColor : "#fff",
	        								pointHighlightFill : "#fff",
	        								pointHighlightStroke : "rgba(220,220,220,1)",
	        								data : data.days
	        							}
	        						]
	        	   }
	           }
	         });
		var ctx = document.getElementById("canvas").getContext("2d");
		window.myLine = new Chart(ctx).Line(lineChartData, {
			responsive: true
		});
	}
function getdata(){
		var lineChartData={};
		$.ajax({
	           url:'/Days/data',
	           type:'get',
	           async:false,
	           dataType:'json',
	           data:{
	              
	           },
	           error:function(){
	             console.log("error");
	           },
	           success:function(data){
	        	   console.log("success");
	        	   lineChartData = {
	        			   labels:data.dates,
	        			   datasets : [
	        							{
	        								label: "My First dataset",
	        								fillColor : "rgba(220,220,220,0.2)",
	        								strokeColor : "rgba(220,220,220,1)",
	        								pointColor : "rgba(220,220,220,1)",
	        								pointStrokeColor : "#fff",
	        								pointHighlightFill : "#fff",
	        								pointHighlightStroke : "rgba(220,220,220,1)",
	        								data : data.days
	        							}
	        						]
	        	   }
	           }
	         });
		return lineChartData;
	}