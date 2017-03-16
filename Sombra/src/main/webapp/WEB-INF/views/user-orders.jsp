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
<link rel="stylesheet" href="/css/user-orders.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/bootstrap.min.js"></script>
<script defer src="/js/user-orders.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/user/orders class=navigation-location-link>Orders</a></li>'}"
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
								<th>Commodity</th>
								<th>Quantity</th>
								<th>Price</th>
								<th>Paid</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${orders}">
								<tr>
									<td>${order.id}</td>
									<td>${order.commodity.name}</td>
									<td>${order.quantity}</td>
									<td>$${order.quantity * order.commodity.price}</td>
									<c:choose>
										<c:when test="${order.paid}">
											<td>Yes</td>
											<td><a
												href="/resources/orders/${order.id}/${order.document}.pdf"
												class="btn btn-default order-button">Check</a></td>
										</c:when>
										<c:otherwise>
											<td>No</td>
											<td><button data-id="${order.id}" data-type="pay"
													class="btn btn-default order-button">Pay</button></td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div id="card-popup" class="modal fade">
					<div class="popup-table">
						<div class="modal-dialog popup-cell">
							<div class="modal-content popup-content">
								<div class="modal-header popup-header">
									<button id="card-popup-hide" class="close">
										<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
									</button>
									<h3 class="card-popup-title">Enter Your Credit Card</h3>
								</div>
								<div class="modal-body">
									<form id="card-form" action="/user/orders/pay" method="POST">
										<sec:csrfInput />
										<input id="id" name="id" type="hidden">
										<div id="card-form-number" class="form-group">
											<label for="card">Card</label>
											<div class="input-wrapper">
												<div class="input-group">
													<span class="input-group-addon"><i
														class="fa fa-credit-card" aria-hidden="true"></i></span> <input
														name="card" type="text" class="form-control" />
												</div>
												<span class="glyphicon form-control-feedback"></span>
											</div>
										</div>
										<div class="popup-button-center">
											<button id="card-submit" class="btn btn-default">Submit</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>