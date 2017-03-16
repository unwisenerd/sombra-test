<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Categories</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/admin-categories.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/bootstrap.min.js"></script>
<script defer src="/js/admin-categories.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/admin class=navigation-location-link>Admin</a></li>'}${'<li class=navigation-location-item><a href=/admin/categories class=navigation-location-link>Categories</a></li>'}"
		var="navigation"></c:set>
	<%@ include file="header.jsp"%>
	<section class="section">
		<c:choose>
			<c:when test="${empty categories}">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-remove message-icon"></span>Nothing
					here
				</div>
			</c:when>
			<c:otherwise>
				<div class="categories-container">
					<table id="content-table" class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="category" items="${categories}">
								<tr>
									<td>${category.id}</td>
									<td>${category.name}</td>
									<td><button class="btn btn-default"
											data-id="${category.id}" data-type="edit">Edit</button></td>
									<td><button class="btn btn-default"
											data-id="${category.id}" data-type="delete">Delete</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<hr>
					<div class="add-category-button">
						<button id="add-popup-show" class="btn btn-default">Add
							Category</button>
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
									<h3>Edit Category</h3>
								</div>
								<div class="modal-body">
									<input id="edit-id" type="hidden">
									<div class="form-group">
										<label for="edit-name">Name</label> <input id="edit-name"
											type="text" class="form-control">
									</div>
								</div>
								<div class="modal-footer popup-footer">
									<button id="edit-submit" class="btn btn-default">Submit</button>
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
									<h3>Add Category</h3>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<label for="add-name">Name</label> <input id="add-name"
											type="text" class="form-control">
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