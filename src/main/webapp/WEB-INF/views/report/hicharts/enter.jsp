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
	            text: '进入平台的学生'
	        },
	        subtitle: {
	            text: ''
	        },
	        xAxis: {
	            categories: ['2015-01-01','2015-01-02','2015-01-03','2015-01-04','2015-01-05','2015-01-06','2015-01-07','2015-01-08','2015-01-09','2015-01-10','2015-01-11','2015-01-12'],
	            labels:{
	            	rotation:-45
	        	}
	        },
	        exporting: {
	            enabled: false
	        },
	        yAxis: {
	            title: {
	                text: '人数'
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
	                    enabled: true
	                },
	                enableMouseTracking: false
	            }
	        },
	        series: [{
	            name: '',
	            data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
	        }]
	    });
	});		
  </script>
</body>
</html>