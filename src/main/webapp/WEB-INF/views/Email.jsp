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

<body>
<div class="container">
	<div class="row">
		<div class="col-md-6">
			<h3 class="display-10 text-center">${message}</h3>
			<s:form action="verifyEmail" method="post" modelAttribute="emailValidate">
				<div class="form-group">
					<s:label path="email">Enter email</s:label>
					<s:input type="email" class="form-control" path="email"/>
					<s:errors path="email" class="error"></s:errors>
				</div>
				<div class="form-group">
					<input type="submit" style="font-size: 20px; width: 555px; height: 40px;" class="btn btn-warning" value="Verify" />
				</div>
			</s:form>
		</div>
	</div>
</div>
</body>
</html>