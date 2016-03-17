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
说明：
<ul>
	<li>1. 选择文件点击上传。</li>
	<li>2. 待页面加载完后全选页面中的内容，粘贴到word中。</li>
	<li>3. 编辑部分内容，另存为pdf。</li>
	<li>4. 编辑 <a target="_blank" href="<%=request.getContextPath()%>/resources/hicharts/chapterpercent.html">课程各章节视频观看完成情况 </a></li>
	<li>4. 编辑 <a target="_blank" href="<%=request.getContextPath()%>/resources/hicharts/enter.html">进入平台的学生</a></li>
</ul>
<form action="<%=request.getContextPath()%>/index/uploadFile" method="post" enctype="multipart/form-data" target="_blank">
	<input type="file" name="data"/>
	<input type="submit"/>
</form>
</body>
</html>
