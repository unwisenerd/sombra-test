<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Log In</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/login.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/login.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/login class=navigation-location-link>Login</a></li>'}"
		var="navigation"></c:set>
	<%@ include file="header.jsp"%>
	<section class="section">
		<c:if test="${not empty success}">
			<div class="alert alert-success" role="alert">
				<span class="glyphicon glyphicon-ok message-icon"></span>${success}</div>
		</c:if>
		<c:if test="${param.logout != null}">
			<div class="alert alert-success" role="alert">
				<span class="glyphicon glyphicon-ok message-icon"></span>You have
				been logged out
			</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger" role="alert">
				<span class="glyphicon glyphicon-remove message-icon"></span>${error}</div>
		</c:if>
		<c:if test="${param.error != null}">
			<div class="alert alert-danger" role="alert">
				<span class="glyphicon glyphicon-remove message-icon"></span>
				<c:choose>
					<c:when test="${SPRING_SECURITY_LAST_EXCEPTION.message != null}">
					${SPRING_SECURITY_LAST_EXCEPTION.message}
					</c:when>
					<c:otherwise>
					Error
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>
		<div class="login-form-container">
			<h3 class="login-form-title">Log In</h3>
			<hr>
			<form id="login-form" action="login" method="POST">
				<div id="login-form-username" class="form-group">
					<label for="username">E-Mail</label>
					<div class="input-wrapper">
						<div class="input-group">
							<span class="input-group-addon"><i class="fa fa-user"
								aria-hidden="true"></i></span> <input name="username"
								class="form-control" />
						</div>
						<span class="glyphicon form-control-feedback"></span>
					</div>
				</div>
				<div id="login-form-password" class="form-group">
					<label for="password">Password</label>
					<div class="input-wrapper">
						<div class="input-group">
							<span class="input-group-addon"><i
								class="fa fa-unlock-alt" aria-hidden="true"></i></span><input
								name="password" type="password" class="form-control" />
						</div>
						<span class="glyphicon form-control-feedback"></span>
					</div>
				</div>
				<div class="checkbox">
					<input name="remember-me" type="checkbox"
						class="login-form-checkbox" /> <label for="remember-me">Remember
						Me</label>
				</div>
				<sec:csrfInput />
				<hr>
				<div class="login-form-submit">
					<button class="btn btn-default">
						<i class="fa fa-sign-in login-submit-icon" aria-hidden="true"></i>Log
						In
					</button>
				</div>
			</form>
		</div>
	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>