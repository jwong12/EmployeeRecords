<!DOCTYPE html>
<%@ page errorPage="/WEB-INF/jsp/ErrorPage.jsp"%>

<html>
<style><jsp:include page="/WEB-INF/css/style.css"/></style>
<head>
	<meta charset="ISO-8859-1">
	<title>Employee Admin Tool</title>
</head>
<body>
	<div id="main">
		<div id="leftcol">
			<jsp:include page="/WEB-INF/jsp/Employees.jsp"/>
		</div>
		<div id="rightcol">
			<jsp:include page="/WEB-INF/jsp/addEmployees.jsp"/>
			<jsp:include page="/WEB-INF/jsp/findEmployees.jsp"/>
			<jsp:include page="/WEB-INF/jsp/deleteEmployee.jsp"/>
		</div>
	</div>
</body>
</html>
