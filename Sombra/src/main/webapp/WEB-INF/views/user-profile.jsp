<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Profile</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/user-profile.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/user-profile.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/profile class=navigation-location-link>Profile</a></li>'}"
		var="navigation"></c:set>
	<%@ include file="header.jsp"%>
	<section class="section">
		<c:if test="${not empty success}">
			<div class="alert alert-success" role="alert">
				<span class="glyphicon glyphicon-ok message-icon"></span>${success}</div>
		</c:if>
		<c:if test="${not empty error}">
			<div class="alert alert-danger" role="alert">
				<span class="glyphicon glyphicon-remove message-icon"></span>${error}</div>
		</c:if>
		<div class="user-left-content">
			<div class="user-image-container">
				<c:choose>
					<c:when test="${user.image ne null}">
						<img src="${user.image}" alt="user" class="user-image">
					</c:when>
					<c:otherwise>
						<img src="/img/user.jpg" alt="user" class="user-image">
					</c:otherwise>
				</c:choose>
			</div>
			<div class="user-info-container">
				<dl class="user-info">
					<dt class="user-property">Name
					<dd class="user-value">${user.name}
					<dt class="user-property">Surname
					<dd class="user-value">${user.surname}
					<dt class="user-property">E-Mail
					<dd class="user-value">${user.email}
					<dt class="user-property">Phone
					<dd class="user-value">${user.phone}
				</dl>
			</div>
		</div><div class="user-right-content">
			<div class="user-forms-container">
				<h4 class="user-form-title">Change Name</h4>
				<form id="name-form" action="/user/profile/changeName" method="POST">
					<div class="form-group">
						<label for="name">Name</label>
						<div class="input-wrapper">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-user"
									aria-hidden="true"></i></span> <input name="name" type="text"
									class="form-control" placeholder="Name" />
							</div>
							<span class="glyphicon form-control-feedback"></span>
						</div>
					</div>
					<sec:csrfInput />
					<div class="user-form-submit">
						<button class="btn btn-default">Change</button>
					</div>
				</form>
				<hr>
				<h4 class="user-form-title">Change Surname</h4>
				<form id="surname-form" action="/user/profile/changeSurname"
					method="POST">
					<div class="form-group">
						<label for="surname">Surname</label>
						<div class="input-wrapper">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-pencil"
									aria-hidden="true"></i></span> <input name="surname" type="text"
									class="form-control" placeholder="Surname" />
							</div>
							<span class="glyphicon form-control-feedback"></span>
						</div>
					</div>
					<sec:csrfInput />
					<div class="user-form-submit">
						<button class="btn btn-default">Change</button>
					</div>
				</form>
				<hr>
				<h4 class="user-form-title">Change E-Mail</h4>
				<form id="email-form" action="/user/profile/changeEmail"
					method="POST">
					<div class="form-group">
						<label for="email">E-Mail</label>
						<div class="input-wrapper">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-envelope"
									aria-hidden="true"></i></span> <input name="email" type="email"
									class="form-control" placeholder="E-Mail" />
							</div>
							<span class="glyphicon form-control-feedback"></span>
						</div>
					</div>
					<sec:csrfInput />
					<div class="user-form-submit">
						<button class="btn btn-default">Change</button>
					</div>
				</form>
				<hr>
				<h4 class="user-form-title">Change Phone</h4>
				<form id="phone-form" action="/user/profile/changePhone"
					method="POST">
					<div class="form-group">
						<label for="phone">Phone</label>
						<div class="input-wrapper">
							<div class="input-group">
								<span class="input-group-addon"><i class="fa fa-phone"
									aria-hidden="true"></i></span> <input name="phone" type="text"
									class="form-control" placeholder="Phone" />
							</div>
							<span class="glyphicon form-control-feedback"></span>
						</div>
					</div>
					<sec:csrfInput />
					<div class="user-form-submit">
						<button class="btn btn-default">Change</button>
					</div>
				</form>
				<hr>
				<h4 class="user-form-title">Change Password</h4>
				<form id="password-form" action="/user/profile/changePassword"
					method="POST">
					<div class="form-group">
						<label for="password">Password</label>
						<div class="input-wrapper">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="fa fa-unlock-alt" aria-hidden="true"></i></span> <input
									name="password" type="password" class="form-control"
									placeholder="Password" />
							</div>
							<span class="glyphicon form-control-feedback"></span>
						</div>
					</div>
					<sec:csrfInput />
					<div class="user-form-submit">
						<button class="btn btn-default">Change</button>
					</div>
				</form>
				<hr>
				<h4 class="user-form-title">Change Image</h4>
				<form id="image-form"
					action="/user/profile/changeImage?_csrf=${_csrf.token}"
					method="POST" enctype="multipart/form-data">
					<input name="image" type="file" />
					<div class="user-form-submit">
						<button class="btn btn-default">Submit</button>
					</div>
				</form>
				<hr>
				<h4 class="user-form-title">Delete Image</h4>
				<form action="/user/profile/deleteImage?_csrf=${_csrf.token}"
					method="POST" enctype="multipart/form-data">
					<div class="user-form-submit">
						<button class="btn btn-default">Delete</button>
					</div>
				</form>
			</div>
		</div>
	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>