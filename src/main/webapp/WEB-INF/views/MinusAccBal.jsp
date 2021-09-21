<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update an account balance</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
${message}
	<div class="row">
		<div class="col-md-6">
			<form action="../minusAccBal" method="get">
				<input type="hidden" name="accountId" value="${account.accountId}" />
				<label>Account Name: ${account.accountName}</label><br/><br/> 	
				<label>Account Balance: ${account.accountBalance}</label>
				<br/><br/>
				<div class="form-group">
						<label for="input_amount">Please enter any amount:</label>
						<input name="input_amount" class="form-control" type="text" placeholder="Please enter any amount..." />
				</div>
				<div class="form-group">
					<input type="submit" value="Update" class="btn btn-warning" />
					<input type="submit" value="Back" class="btn btn-danger" formaction="../manageAccounts" />
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>