<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update a user</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
<h3 class="text-center">${message}</h3>
	<div class="row">
		<div class="col-md-6">
			<form action="../updateData" method="post">
				<input type="hidden" name="userId" value="${user.userId}" />
				<div class="form-group">
						<label>User Id: ${user.userId}</label><br/>
						<label>User First Name: ${user.firstName}</label><br/>
						<label>Contact Number : ${user.phone}</label><br/>
						<label>Gender Type: ${user.gender}</label><br/>
						<label>Email Id: ${user.email}</label><br/>
						<label>Type Of Role: ${user.role.roleName}</label><br/>
						<label>User Activation Mode: ${user.flag}</label><br/><br/>
						<label for="flag">Activation Status:</label>
						<select name="flag">
							<option value="select an activation mode">Select an activation mode</option>
							<option value="true">True</option>
							<option value="false">False</option>
						</select>
				</div>
				<div class="form-group">
					<input type="submit" value="Update" class="btn btn-warning" />
				</div>
			</form>
			<a href="manageUsers"><button type="submit" class="btn btn-success">Go Back To Home Page</button></a>
		</div>
	</div>
</div>
</body>
</html>