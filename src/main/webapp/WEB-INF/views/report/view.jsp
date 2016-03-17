<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>结课报告</title>
<style type="text/css">
<!--
.STYLE1 {font-family: "微软雅黑"}
body {
	font-family: "微软雅黑";
	width:756px;
}
-->

</style>
</head>

<body >
<div  > 

<div class="STYLE1">
  <p>目录：</p>
  <p><a href="#id21">课程基本情况</a></p>
  <p><a href="#id22">参与课程的学生情况</a></p>
  <p><a href="#id23">视频观看情况</a></p>
  <p><a href="#id24">课程学习结果分析</a></p>
  <br/>
    <br/>
	  <br/>
	    <br/>
		  <br/>
</div>

<div class="STYLE1" id = "id21">
<h2>1 《${allIndex.课程名称}》 课程学习情况分析</h2>
  <h3>1.1 课程基本情况 </h3>

<table border="1" cellspacing="0" cellpadding="0">
  <tr>
    <td width="142" valign="top"><p align="center">
      章节数量 </p></td>
    <td width="142" valign="top"><p align="center">视频总时长（s） </p></td>
    <td width="142" valign="top"><p align="center">考试权重比 </p></td>
    <td width="142" valign="top"><p align="center">结课时间 </p></td>
  </tr>
  <tr>
    <td width="142" valign="top"><p align="center">共六章 </p></td>
    <td width="142" valign="top"><p align="center">14893</p></td>
    <td width="142" valign="top"><p align="center">观看视频：测验：讨论：作业 <br />
      =60:15:15:10 </p></td>
    <td width="142" valign="top"><p align="center">2015.12.7 </p></td>
  </tr>
</table>
<p align="left">    Web 开发新时代、从HTML 和XHTML 到HTML5、创建HTML5 文档、 
  实战HTML5  表单、实战HTML5 画布、HTML5 音频与视频、Web 存储、离线 
  应用、Workers  多线程处理、Geolocation 地理位置、CSS3 概述、CSS 选择器、 
  文本和字体与颜色、背景和边框、2D 变形、设计动画、网页布局、用户界面、 
  CSS3 其他新特性。 
  </p>
  </div>
  <div class="STYLE1" id = "id22">
  <h3>1.2 参与课程的学生情况 </h3>
  <p align="left">本学期，全校共有${allIndex.总人数} 名学生选择了《${allIndex.课程名称}》课程的学习， <br />
  开课初期平台导入/注册学生情况如下： <br />
  <img src="<%=request.getContextPath()%>/index/export-report/enter/${batchId }" alt="1" /> <br />
  其中有${allIndex.进度小于0的总人数}  个学生从未进行过登录学习，因此实际参与该课程学习的人数为${allIndex.进度大于0的总人数}人。  </p>
  </div>
  <div class="STYLE1" id = "id23">
<h3>1.3 视频观看情况</h3>
  <p align="left">参与课程学习的大多数学生已按要求完成了课程视频的观看，这些学生视频 
  观看完成度的平均值为<fmt:formatNumber type="number" value="${allIndex.观看完成度的平均值}" maxFractionDigits="2"/>
%，视频观看完成情况良好。 
  在实际参与课程学习的学生中，课程所有视频的总体观看完成情况如下图。 
  其中视频观看单项不及格（视频观看完成度在60%以下）的学生有${allIndex.视频观看完成度在60以下} 人， 
  占参与学习总人数的<fmt:formatNumber type="number" value="${allIndex.视频观看完成度在60以下占比}" maxFractionDigits="2"/>%。 <br />
  <img src="<%=request.getContextPath()%>/index/export-report/viewpercent/${batchId }" alt="2"/> <br />
  <img src="<%=request.getContextPath()%>/index/export-report/viewpercent-pie/${batchId }" alt="3"/> <br />
  本课程内容共六章，在实际参与学习的学生中，各章的视频观看完成情况（平均）都较好，均在86%以上，但随着课程内容越靠后，该章的视频观看完成度 
  呈微弱递减的下降趋势。 <br />
  各章的视频观看完成情况如下图： <br />
  <img src="<%=request.getContextPath()%>/index/export-report/chapterpercent/${batchId }" alt="4"/> <br />
  </p>
  </div>
  <div class="STYLE1" id = "id24">
  <h3>1.4 课程学习结果分析</h3>
  <h4>1.4.1 各部分得分情况</h4>
  本门课程的成绩由观看视频、作业、测验和讨论四部分组成，四部分的占比 
  为60:10:15:15，即观看视频总分60 分，测验总分15 分，讨论总分15  分，作 
  业总分10  分。实际参与课程学习的学生各部分的得分与总分的情况如下图。 
  由图中可以看出，实际参与学习的学生总成绩平均分为
<fmt:formatNumber type="number" value="${allIndex.平均成绩[0].total}" maxFractionDigits="2"/>
 分，其中在观看视频方面的表现最好，平均已得到大概
<fmt:formatNumber type="number" value="${allIndex.平均成绩[0].v_score*100/60}" maxFractionDigits="2"/>%
的得分；在讨论方面的表现次之，平均得分在
<fmt:formatNumber type="number" value="${allIndex.平均成绩[0].d_score*100/15}" maxFractionDigits="2"/>%
左右；再次是在测验方面的表现，平均得到大概
<fmt:formatNumber type="number" value="${allIndex.平均成绩[0].t_score*100/15}" maxFractionDigits="2"/>%
的得分；由于作业的提交情况不理想，在作业方面的平均得分较低。
<fmt:formatNumber type="number" value="${allIndex.平均成绩[0].j_score*100/15}" maxFractionDigits="2"/>% <br />
  <img src="<%=request.getContextPath()%>/index/export-report/score/${batchId }" alt="5" /> <br />
  <h4>1.4.2 学生成绩分布</h4>
  <p>
  该课程实际参与学习的人数为${allIndex.进度大于0的总人数} 人，这些学生成绩分布情况如下图： <br />
  <img src="<%=request.getContextPath()%>/index/export-report/score-range/${batchId }" alt="6" /> <br />
  <img src="<%=request.getContextPath()%>/index/export-report/score-range-pie/${batchId }" alt="7" /> <br />
  由图和数据可以看出，在实际参与课程学习的学生中，有
<fmt:formatNumber type="number" value="${100*allIndex.进度大于0且及格的总人数/allIndex.进度大于0的总人数}" maxFractionDigits="2"/>%
的学生成 
  绩及格，顺利完成该课程；但也有<fmt:formatNumber type="number" value="${100*(allIndex.进度大于0的总人数-allIndex.进度大于0且及格的总人数)/allIndex.进度大于0的总人数}" maxFractionDigits="2"/>%的学生（${allIndex.进度大于0的总人数-allIndex.进度大于0且及格的总人数} 人）成绩不及格。 </p>
  </div>
</div>
</body>
</html>
