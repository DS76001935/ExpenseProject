<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Expense Tracking</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
	<h3 class="display-10"><a href="../userHome">Go to Home Page</a></h3>
	<h1 class="text-center" style="color: blue;">Expense Tracking</h1><br/><br/>
	<h4 class="text-center">${message}</h4>
	<h3 class="text-center" style="color: skyblue;">Welcome to the place where you can track your expenses as a whole...</h3><br/><br/>
	<div class="row">
		<div class="col-md-8">
			<table class="table table-striped table-dark">
			  <thead>
			    <tr>
			      <th scope="col" class="text-center">Expense ID:</th>
			      <th class="text-center">Payment Methods:</th>
			      <th class="text-center">Accounts:</th>
			      <th class="text-center">Expense Amount:</th>
			      <th class="text-center">Expense Date and Time:</th>
			      <th class="text-center">Category Name:</th>
			      <th class="text-center">Sub-Category Name:</th>
			      <th class="text-center">Payee Name:</th>
			      <th class="text-center">Label Name:</th>
			      <th class="text-center">Status:</th>
			      <th class="text-center">Description</th>
			      <th colspan="2" class="text-center" style="position:relative;left:50px;">Action:</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach items="${getAllExpenses}" var="expense">
			  		<tr>
			  			<td>${expense.expenseId}</td>
			  			<td>${expense.paymentMethod}</td>
			  			<td>${expense.accName}</td>
			  			<td>${expense.amount}</td>
			  			<td>${expense.expDateTime}</td>
			  			<td>${expense.category.categoryName}</td>
			  			<td>${expense.subCategory.subCatName}</td>
			  			<td>${expense.payee.payeeName}</td>
			  			<td>${expense.label.labelName}</td>
			  			<td>${expense.status}</td>
			  			<td>${expense.description}</td>
			  			<td><a class="glyphicon glyphicon-edit" data-target="#myModal" data-toggle="modal" href="editExpense/${expense.expenseId}"></a></td>
				      	<td><a class="glyphicon glyphicon-trash" href="deleteExpense/${expense.expenseId}"></a></td>
			  		</tr>
			  	</c:forEach>
			  </tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>