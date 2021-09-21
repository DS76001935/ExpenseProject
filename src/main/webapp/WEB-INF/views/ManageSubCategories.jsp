<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Manage Sub Categories</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
	<div class="row">
		<div class="col-md-8">
		<h3 class="display-10"><a href="userHome">Go Back</a></h3>
		<h3>${message}</h3>
			<table class="table table-striped">
			  <thead>
			    <tr>
			    	<th scope="col">Sub Category ID:</th>
			    	<th scope="col">Category Name:</th>
			      	<th scope="col">Sub Category Name:</th>
			      	<th scope="col" colspan="2" class="text-center" style="position:relative;right:30px;">Action:</th>
			    </tr>
			  </thead>
			  <tbody>
			  	<c:forEach items="${getAllSubCategories}" var="subCategory">
				    <tr>
				    	<td>${subCategory.subCatId}</td>
				      	<td>${subCategory.category.categoryName}</td>
				     	<td>${subCategory.subCatName}</td>
				     	<td><a class="glyphicon glyphicon-edit" style="color:grey;" href="editSubCategory/${subCategory.subCatId}"></a></td>
				     	<td><a class="glyphicon glyphicon-trash" style="color:red;" href="deleteSubCategory/${subCategory.subCatId}"></a></td>
				    </tr>
				</c:forEach>
			  </tbody>
			</table>
		</div>
	</div>
</div>
</body>
</html>