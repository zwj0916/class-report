<%@page import="com.phenix.cr.util.Const"%>
<%@page import="com.phenix.cr.util.Configure"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>403 无权访问</title>
<style type="text/css">
html,body,* {
	font-family: "Microsoft YaHei" ! important;
	font-size: 14;
}
</style>
</head>
<%
    session.invalidate();
%>
<body>
	<div>
		<div>
			 
		</div>
		<div>
			<a href="<c:url value="<%=Configure.getInstance().getProperty(Const.LOGOUT_URL)%>"/>">返回</a>
		</div>
	</div>
</body>
</html>