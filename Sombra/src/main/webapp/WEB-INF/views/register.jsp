<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Register</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/register.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/register.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/register class=navigation-location-link>Register</a></li>'}"
		var="navigation"></c:set>
	<%@ include file="header.jsp"%>
	<section class="section">
		<c:if test="${not empty error}">
			<div class="alert alert-danger" role="alert">
				<span class="glyphicon glyphicon-remove message-icon"></span>${error}</div>
		</c:if>
		<div class="register-form-container">
			<h3 class="register-form-title">Register</h3>
			<hr>
			<form:form id="register-form" modelAttribute="user" action="register"
				method="POST">
				<div id="register-form-name" class="form-group">
					<label for="name">Name</label>
					<div class="input-wrapper">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user"
								aria-hidden="true"></i></span>
							<form:input path="name" class="form-control" />
						</div>
						<span class="glyphicon form-control-feedback"></span>
					</div>
				</div>
				<div id="register-form-surname" class="form-group">
					<label for="surname">Surname</label>
					<div class="input-wrapper">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-pencil"
								aria-hidden="true"></i></span>
							<form:input path="surname" class="form-control" />
						</div>
						<span class="glyphicon form-control-feedback"></span>
					</div>
				</div>
				<div id="register-form-email" class="form-group">
					<label for="email">E-Mail</label>
					<div class="input-wrapper">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-envelope"
								aria-hidden="true"></i></span>
							<form:input path="email" type="email" class="form-control" />
						</div>
						<span class="glyphicon form-control-feedback"></span>
					</div>
				</div>
				<div id="register-form-password" class="form-group">
					<label for="password">Password</label>
					<div class="input-wrapper">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="fa fa-unlock-alt" aria-hidden="true"></i></span>
							<form:password path="password" class="form-control" />
						</div>
						<span class="glyphicon form-control-feedback"></span>
					</div>
				</div>
				<div id="register-form-phone" class="form-group">
					<label for="phone">Phone</label>
					<div class="input-wrapper">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-phone"
								aria-hidden="true"></i></span>
							<form:input path="phone" class="form-control" />
						</div>
						<span class="glyphicon form-control-feedback"></span>
					</div>
				</div>
				<hr>
				<div class="register-form-submit">
					<form:button class="btn btn-default">
						<i class="fa fa-user-plus register-submit-icon" aria-hidden="true"></i>Register</form:button>
				</div>
			</form:form>
		</div>
	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>