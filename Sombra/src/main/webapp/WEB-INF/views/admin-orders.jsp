<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Orders</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/admin-orders.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/bootstrap.min.js"></script>
<script defer src="/js/admin-orders.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/admin class=navigation-location-link>Admin</a></li>'}${'<li class=navigation-location-item><a href=/admin/orders class=navigation-location-link>Orders</a></li>'}"
		var="navigation"></c:set>
	<%@ include file="header.jsp"%>
	<section class="section">
		<c:choose>
			<c:when test="${empty orders}">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-remove message-icon"></span>Nothing
					here
				</div>
			</c:when>
			<c:otherwise>
				<div class="orders-container">
					<table id="content-table" class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Quantity</th>
								<th>Paid</th>
								<th>Commodity</th>
								<th>User</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<tr>
									<td>${order.id}</td>
									<td>${order.quantity}</td>
									<td>${order.paid}</td>
									<td>${order.commodity.name}</td>
									<td>${order.user.email}</td>
									<td><button class="btn btn-default" data-id="${order.id}"
											data-type="edit">Edit</button></td>
									<td><button class="btn btn-default" data-id="${order.id}"
											data-type="delete">Delete</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<hr>
					<div class="add-order-button">
						<button id="add-popup-show" class="btn btn-default">Add
							Order</button>
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
									<h3>Edit Order</h3>
								</div>
								<div class="modal-body">
									<input id="edit-id" type="hidden">
									<div class="form-group">
										<label for="edit-quantity">Quantity</label> <input
											id="edit-quantity" type="number" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-paid">Paid</label> <select id="edit-paid"
											class="form-control">
											<option value="false">false</option>
											<option value="true">true</option>
										</select>
									</div>
									<div class="form-group">
										<label for="edit-commodity">Commodity</label> <select
											id="edit-commodity" class="form-control">
											<option value="">None</option>
											<c:forEach var="commodity" items="${commodities}">
												<option value="${commodity.id}">${commodity.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<label for="edit-user">User</label> <select id="edit-user"
											class="form-control">
											<option value="">None</option>
											<c:forEach var="user" items="${users}">
												<option value="${user.id}">${user.email}</option>
											</c:forEach>
										</select>
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
									<h3>Add Commodity</h3>
								</div>
								<div class="modal-body">
									<input id="add-id" type="hidden">
									<div class="form-group">
										<label for="add-quantity">Quantity</label> <input
											id="add-quantity" type="number" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-paid">Paid</label> <select id="add-paid"
											class="form-control">
											<option value="false">false</option>
											<option value="true">true</option>
										</select>
									</div>
									<div class="form-group">
										<label for="add-commodity">Commodity</label> <select
											id="add-commodity" class="form-control">
											<option value="">None</option>
											<c:forEach var="commodity" items="${commodities}">
												<option value="${commodity.id}">${commodity.name}</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<label for="add-user">User</label> <select id="add-user"
											class="form-control">
											<option value="">None</option>
											<c:forEach var="user" items="${users}">
												<option value="${user.id}">${user.email}</option>
											</c:forEach>
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