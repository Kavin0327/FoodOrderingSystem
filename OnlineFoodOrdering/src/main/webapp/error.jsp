<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Error Page</title>
<style>
.error-container {
	max-width: 600px;
	margin: 50px auto;
	padding: 20px;
	border: 1px solid #ff0000;
	background-color: #ffe6e6;
	text-align: center;
}

.error-title {
	color: #ff0000;
	font-size: 24px;
	margin-bottom: 10px;
}

.error-message {
	font-size: 18px;
	margin-bottom: 20px;
}

.home-link {
	color: #0000ff;
	text-decoration: none;
}
</style>
</head>
<body>
	<div class="error-container">
		<h1>Error Occured.!</h1>
		<div class="error-title">
			<%=request.getAttribute("errorType")%></div>
		<div class="error-message"><%=request.getAttribute("errorMessage")%></div>
		<a href="/OnlineFoodOrdering/login.html" class="home-link">Go to
			Home</a>
	</div>
</body>
</html>
