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
	            type: 'column'
	        },
	        title: {
	            text: '视频观看完成度各范围人数'
	        },
	        subtitle: {
	            text: ''
	        },
	        exporting: {
	            enabled: false
	        },
	        xAxis: {
	            type: 'category',
	            labels: {
	                //rotation: -45,
	                style: {
	                    fontSize: '13px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: ''
	            }
	        },
	        legend: {
	            enabled: false
	        },
	        tooltip: {
	            pointFormat: '{point.y}'
	        },
	        series: [{
	            name: 'Population',
	            data: data,
	            dataLabels: {
	                enabled: true,
	                //rotation: -90,
	                //color: '#FFFFFF',
	                align: 'right',
	                //format: '{point.y:.1f}', // one decimal
	                y: 10, // 10 pixels down from the top
	                //style: {
	                //    fontSize: '13px',
	                //    fontFamily: 'Verdana, sans-serif'
	                //}
	            }
	        }]
	    });
	});
  </script>
</body>
</html>