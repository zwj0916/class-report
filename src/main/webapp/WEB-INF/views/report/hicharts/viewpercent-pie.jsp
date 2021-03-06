<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"   %>
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
	//真实数据
  var ar = ${allIndex}['观看完成度分布'];
  var data = new Array();
  for( var i in ar){
	  data.push([ar[i]['name'],ar[i]['count(*)']]);
	  
  }
  console.log(data);
  //模拟数据
  //var data =  [
  //             ['>90', 10],
  //             ['80-90', 200],
  //             ['70-80', 200],
  //             ['60-70', 200],
  //             ['<60', 100]
  //         ];
  
  $(function () {
	    $('#container').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: '视频观看完成度各范围人数占比'
	        },
	        exporting: {
	            enabled: false
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
	                    style: {
	                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
	                    }
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Browser share',
	            data: data
	        }]
	    });
	});

  </script>
</body>
</html>