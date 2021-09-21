<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update an expense</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
<h4><a href="/manageExpenses">Go Back to "Expense Tracking" Page</a></h4>
<h3 class="text-center">${message}</h3>
	<div class="row">
		<div class="col-md-6">
			<form action="../modifyExpense" name="expenseUpdateForm" method="get">
				<input type="hidden" name="expenseId" value="${expense.expenseId}" />
				<div class="form-group">
						<label>Payment Method: ${expense.paymentMethod}</label>
				</div>
				<div class="form-group">
						<label>Account: ${expense.accName}</label>
				</div>
				<div class="form-group">
						<label for="amount">Expense Amount:</label>
						<input name="amount" class="form-control" type="text" value="${expense.amount}" />
				</div>
				<div class="form-group">
						<label for="expDateTime">Date & Time Picker:</label>
						<input name="expDateTime" class="form-control" type="date" value="${expense.expDateTime}" />
				</div>
				<div class="form-group">
						<label for="categoryName">Category:</label>
					<select style="width:40%;" name="categoryName" id="categoryName" class="form-control" required>
						<option value="${expense.category.categoryName}">${expense.category.categoryName}</option>
						<c:forEach items="${getAllCategories}" var="getCat">
							<option value="${getCat.categoryName}">${getCat.categoryName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
						<label for="subCatName">Sub-Category:</label>
						<select style="width:40%;" name="subCatName" id="subCatName" class="form-control" required>
							<option value="${expense.subCategory.subCatName}">${expense.subCategory.subCatName}</option>
						</select>
				</div>
				<div class="form-group">
						<label for="payeeName">Payee:</label>
						<select style="width:40%;" name="payeeName" class="form-control" required>
							<option value="${expense.payee.payeeName}">${expense.payee.payeeName}</option>
							<c:forEach items="${getAllPayees}" var="payee">
								<option value="${payee.payeeName}">${payee.payeeName}</option>
							</c:forEach>
					</select>
				</div>
				<div class="form-group">
						<label for="labelName">Label:</label>
						<select style="width:40%;" name="labelName" class="form-control" required>
							<option value="${expense.label.labelName}">${expense.label.labelName}</option>
							<c:forEach items="${getAllLabels}" var="label">
								<option value="${label.labelName}">${label.labelName}</option>
							</c:forEach>
					</select>
				</div>
				<div class="form-group">
						<label for="status">Status:</label>
						<input name="status" class="form-control" type="text" value="${expense.status}" />
				</div>
				<div class="form-group">
						<label for="description">Description:</label>
						<input name="description" class="form-control" type="text" value="${expense.description}" />
				</div>
				<div class="form-group">
					<input type="submit" value="Update" class="btn btn-warning" />
					<!-- <input type="reset" value="Back" class="btn btn-danger" formaction="../manageExpenses" /> -->
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript">
function checkSubCategory(){
	let categoryName = document.expenseUpdateForm.categoryName.value;
	console.log(categoryName);
	
	$.get("../check/"+categoryName,function(data){
		console.log(data);
		console.log("go");
		getSubCategories(data);
	});
}
function getSubCategories(data){
	$("#subCatName").empty();
	data.forEach((item,index) =>{
		console.log(item.subCatName);
		$("#subCatName").append("<option>"+item.subCatName+"</option>");
	});
}
$(document).ready(function(){
	$("#categoryName").change(function(){
		checkSubCategory();
		console.log("go...go");
	});
});
</script>
</body>
</html>