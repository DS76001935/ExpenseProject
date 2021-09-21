<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
	
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<style type="text/css">
.error{
	color:red;
}
</style>
</head>
<body class="mt-5 bg-warning">
	<div class="container">
		<div class="row" style="margin-top: 50px;">
			<h3 class="display-10 text-center">${message}</h3>
			<div class="col-md-4"></div>
			<div class="col-md-4 alert alert-success">
				<div class="h2 text-center">
					<a href="userData"> Update Password</a>
					<hr>
				</div>
				<s:form action="forgotPass" Class="form-border" method="post" modelAttribute="forgotValidate">

					<div class="form-group">
						<s:label path="password">New Password:</s:label>
						<s:input type="password" class="form-control" path="password" />
						<s:errors path="password" class="error"></s:errors>
					</div>
					<div class="form-group">
						<s:label path="cpassword">Confirm Password:</s:label>
						<s:input type="password" class="form-control" path="cpassword" />
						<s:errors path="cpassword" class="error"></s:errors>
					</div>


					<div class="text-center">
						<input type="submit" value="Update" />
					</div>
				</s:form>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
</body>
</html>