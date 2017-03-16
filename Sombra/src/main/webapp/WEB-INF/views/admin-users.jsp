<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Users</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/admin-users.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/bootstrap.min.js"></script>
<script defer src="/js/admin-users.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/admin class=navigation-location-link>Admin</a></li>'}${'<li class=navigation-location-item><a href=/admin/users	 class=navigation-location-link>Users</a></li>'}"
		var="navigation"></c:set>
	<%@ include file="header.jsp"%>
	<section class="section">
		<c:choose>
			<c:when test="${empty users}">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-remove message-icon"></span>Nothing
					here
				</div>
			</c:when>
			<c:otherwise>
				<div class="users-container">
					<table id="content-table" class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Surname</th>
								<th>Email</th>
								<th>Phone</th>
								<th>Locked</th>
								<th>Enabled</th>
								<th>Role</th>
								<th>Image</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${users}">
								<tr>
									<td>${user.id}</td>
									<td>${user.name}</td>
									<td>${user.surname}</td>
									<td>${user.email}</td>
									<td>${user.phone}</td>
									<td>${user.locked}</td>
									<td>${user.enabled}</td>
									<td>${user.role}</td>
									<td><c:if test="${user.image != null}">
											<img src="${user.image}" alt="user" class="user-image">
										</c:if></td>
									<td><button class="btn btn-default" data-id="${user.id}"
											data-type="edit">Edit</button></td>
									<td><button class="btn btn-default" data-id="${user.id}"
											data-type="delete">Delete</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<hr>
					<div class="add-user-button">
						<button id="add-popup-show" class="btn btn-default">Add
							User</button>
					</div>
				</div>

				<div id="edit-popup" class="modal fade">
					<div class="popup-table">
						<div class="modal-dialog popup-cell">
							<div class="modal-content popup-content">
								<div class="modal-header popup-header">
									<button id="edit-popup-hide" class="close">
										<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
									</button>
									<h3>Edit User</h3>
								</div>
								<div class="modal-body">
									<input id="edit-id" type="hidden">
									<div class="form-group">
										<label for="edit-name">Name</label> <input id="edit-name"
											type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-surname">Surname</label> <input
											id="edit-surname" type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-email">E-Mail</label> <input id="edit-email"
											type="email" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-phone">Phone</label> <input id="edit-phone"
											type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-locked">Locked</label> <select
											id="edit-locked" class="form-control">
											<option value="false">false</option>
											<option value="true">true</option>
										</select>
									</div>
									<div class="form-group">
										<label for="edit-enabled">Enabled</label> <select
											id="edit-enabled" class="form-control">
											<option value="true">true</option>
											<option value="false">false</option>
										</select>
									</div>
									<div class="form-group">
										<label for="edit-role">Role</label> <select id="edit-role"
											class="form-control">
											<option value="1">ROLE_USER</option>
											<option value="0">ROLE_ADMIN</option>
										</select>
									</div>
									<div class="popup-button-center">
										<button id="edit-submit" class="btn btn-default">Submit</button>
									</div>
									<hr>
									<h4 class="edit-popup-title">Edit Image</h4>
									<input name="image" type="file" />
									<div class="popup-button-center">
										<button id="edit-image-submit" class="btn btn-default">Submit</button>
									</div>
									<hr>
									<h4 class="edit-popup-title">Delete Image</h4>
									<div class="popup-button-center">
										<button id="delete-image-submit" class="btn btn-default">Submit</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="add-popup" class="modal fade">
					<div class="popup-table">
						<div class="modal-dialog popup-cell">
							<div class="modal-content popup-content">
								<div class="modal-header popup-header">
									<button id="add-popup-hide" class="close">
										<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
									</button>
									<h3>Add User</h3>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<label for="add-name">Name</label> <input id="add-name"
											type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-surname">Surname</label> <input
											id="add-surname" type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-email">E-Mail</label> <input id="add-email"
											type="email" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-password">Password</label> <input
											id="add-password" type="password" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-phone">Phone</label> <input id="add-phone"
											type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-locked">Locked</label> <select id="add-locked"
											class="form-control">
											<option value="false">false</option>
											<option value="true">true</option>
										</select>
									</div>
									<div class="form-group">
										<label for="add-enabled">Enabled</label> <select
											id="add-enabled" class="form-control">
											<option value="true">true</option>
											<option value="false">false</option>
										</select>
									</div>
									<div class="form-group">
										<label for="add-role">Role</label> <select id="add-role"
											class="form-control">
											<option value="1">ROLE_USER</option>
											<option value="0">ROLE_ADMIN</option>
										</select>
									</div>
								</div>
								<div class="modal-footer popup-footer">
									<button id="add-submit" class="btn btn-default">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<sec:csrfInput />
			</c:otherwise>
		</c:choose>
	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>