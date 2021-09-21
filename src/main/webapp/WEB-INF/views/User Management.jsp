<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>USER MANAGEMENT</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
	<div class="row">
		<form action="moveToRegister"><input type="submit" value="Add a user" class="btn btn-primary" width="200px" height="100px" /></form>
		<div class="col-md-8">
		<h3>${message}</h3>
			<table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">User ID:</th>
			      <th scope="col">User First Name:</th>
			      <th scope="col">Contact Number:</th>
			      <th scope="col">Gender:</th>
			      <th scope="col">Email:</th>
			      <th scope="col">Activation Mode:</th>
			      <th scope="col" colspan="2" class="text-center" style="position:relative;right:10px;">Action:</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach items="${ViewUsers}" var="user">
				    <tr>
				      <th scope="row">${user.userId}</th>
				      <td>${user.firstName}</td>
				      <td>${user.phone}</td>
				      <td>${user.gender}</td>
				      <td>${user.email}</td>
				      <td>${user.flag}</td>
				      <td><a class="glyphicon glyphicon-edit" data-target="#myModal" data-toggle="modal" style="color:grey;" href="editUser/${user.userId}"></a></td>
				    </tr>
				</c:forEach>
			  </tbody>
			</table>
			<h3 class="display-10"><a href="userHome">Go to Home Page</a></h3>
		</div>
	</div>
</div>
</body>
</html>