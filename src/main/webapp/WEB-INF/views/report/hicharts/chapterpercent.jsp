<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <script type="text/javascript" src="http://img.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="http://img.hcharts.cn/highcharts/highcharts.js"></script>
		<script type="text/javascript" src="http://img.hcharts.cn/highcharts/modules/exporting.js"></script>
  <script>
    //左侧Javascript代码
  </script>
</head>
<body>
  <div id="container" style="width:550px;height:400px"></div>
  <script type="text/javascript">
  $(function () {
	    $('#container').highcharts({
	        chart: {
	            type: 'line'
	        },
	        title: {
	            text: '课程各章节视频观看完成情况'
	        },
	        subtitle: {
	            text: ''
	        },
	        exporting: {
	            enabled: false
	        },
	        xAxis: {
	            categories: ['第一章','第二章','第三章','第四章','第五章','第六章'],
	            labels:{
	            	rotation:-45
	        	}
	        },
	        yAxis: {
	            title: {
	                text: '百分比'
	            },
	            labels:{
	            	rotation:-45,
	            	format: '<b>{value} %'
	        	}
	        },
	        tooltip: {
	            enabled: true,
	            formatter: function() {
	                return '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C';
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        plotOptions: {
	            line: {
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.y:.1f} %',
	                },
	                enableMouseTracking: false,
	            }
	        },
	        series: [{
	            name: '',
	            data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5]
	        }]
	    });
	});		
  </script>
</body>
</html>