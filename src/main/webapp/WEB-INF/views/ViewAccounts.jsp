<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Show All Accounts</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
	<span><b style="font-size: 20px;">+ &nbsp;:&nbsp;Add some amount into existing balance in your respected account.</b></span><br/>
	<span><b style="font-size: 20px;">-&nbsp;&nbsp;&nbsp;:&nbsp;Deduct some amount into existing balance in your respected account.</b></span>
	<div class="row">
		<div class="col-md-8">
		<h3>${message}</h3>
			<table class="table table-striped">
			  <thead>
			    <tr>
			      <th scope="col">Your Account ID:</th>
			      <th scope="col">Your Account Name:</th>
			      <th scope="col">Your Account Balance:</th>
			      <th scope="col" colspan="3" class="text-center">Action:</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach items="${getAllAccounts}" var="account">
				    <tr>
				      <th scope="row">${account.accountId}</th>
				      <td>${account.accountName}</td>
				      <td>${account.accountBalance}</td>
				      <td><a class="glyphicon glyphicon-plus" style="color:grey;" href="editAccount/${account.accountId}"></a></td>
				      <td><a class="glyphicon glyphicon-minus" style="color:grey;" href="editAccount1/${account.accountId}"></a></td>
				      <td><a class="glyphicon glyphicon-trash" style="color:red;" href="deleteAccount/${account.accountId}"></a></td>
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