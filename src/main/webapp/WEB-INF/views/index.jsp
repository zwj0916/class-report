<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<style type="text/css">
</style>
<title>CF</title>
<base
	href="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/"
					+ request.getContextPath().replace("/", "") + "/"%>">
</head>
<body>
	<script src="resources/jquery.min.js"></script>
	<script src="resources/echarts.js"></script>
	<script type="text/javascript">
	window.location.href = "<%=request.getContextPath()%>/index/report";
	</script>
</body>
</html>