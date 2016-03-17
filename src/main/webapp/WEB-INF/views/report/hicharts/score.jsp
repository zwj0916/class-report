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
  <div id="container" style="width:550px;height:600px"></div>
  <script type="text/javascript">
  var ar = ${allIndex}['平均成绩'][0];
  var data = [ar['total'],ar['j_score'],ar['d_score'],ar['t_score'],ar['v_score']];
  console.log(data);
  
  
  $(function () {                                                                
	    $('#container').highcharts({                                           
	        chart: {                                                           
	            type: 'bar'                                                    
	        },                                                                 
	        title: {                                                           
	            text: '各部分的得分与总分'                    
	        },                                                                 
	        subtitle: {                                                        
	            text: ''                                  
	        },       
	        exporting: {
	            enabled: false
	        },
	        xAxis: {                                                           
	            categories: ['总成绩', '作业', '讨论', '测验', '观看视频'],
	            title: {                                                       
	                text: null                                                 
	            }                                                              
	        },                                                                 
	        yAxis: {                                                           
	            min: 0,                                                        
	            title: {                                                       
	                text: '',                             
	                align: 'high'                                              
	            },                                                             
	            labels: {                                                      
	                overflow: 'justify'                                        
	            }                                                              
	        },                                                                 
	        tooltip: {                                                  
	            valueSuffix: ' millions'                                       
	        },                                                                 
	        plotOptions: {                                                     
	            bar: {                                                         
	                dataLabels: {                                              
	                    enabled: true  ,
	                    format: '{point.y:.1f}'
	                }                                                          
	            }                                                              
	        },                                                                 
	        legend: {                                                          
	            //layout: 'vertical',                                            
	            //align: 'right',                                                
	            //verticalAlign: 'buttom',                                          
	            //x: -40,                                                        
	            y: 23,                                                        
	            floating: true,                                                
	            borderWidth: 1,                                                
	            backgroundColor: '#FFFFFF',                                    
	            shadow: true                                                   
	        },                                                                 
	        credits: {                                                         
	            enabled: false                                                 
	        },                                                                 
	        series: [{                                                         
	            name: '平均分',                                             
	            data: [88.75731, 0, 1.06917, 25.15296, 58.2585]                                 
	        }, {                                                               
	            name: '总分',                                             
	            data: [100, 10, 15, 15, 60]                                  
	        }]                                                                 
	    });                                                                    
	});                                                                                                                                              				                                                                                                                                           				
  </script>
</body>
</html>