<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Sign Up Form by Colorlib</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
	<div class="main">
		<section class="sign-in">
			<div class="container">
					<h4><u><a class="text-center" href="loginPage" style="color: grey; font-size: 20px;">Go Back To Login Page</a></u></h4>
					<div class="signin-image">
						<figure>
							<img src="resources/assets/otp.jpg" alt="" width="200px" height="200px">
						</figure>
					</div>
					<div class="signin-form">
						<h3 class="display-10">${message}</h3>
						<form method="post" action="otpCheck">
							<div class="form-group">
								<label for="otp" style="font-size: 20px;">Your OTP?</label>
									<input type="text" style="font-size: 20px; width: 555px; height: 40px;" class="form-control" name="otp" id="otp" placeholder="please enter your OTP number..." />
							</div>
							<div class="form-group">
								<input type="submit" style="font-size: 20px; width: 555px; height: 40px;" id="check" class="btn btn-primary" value="Check" />
							</div>
						</form>
					</div>
			</div>
		</section>
	</div>
</body>
</html>