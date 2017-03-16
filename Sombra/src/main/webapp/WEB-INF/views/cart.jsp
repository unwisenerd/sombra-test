<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Shopping Cart</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/cart.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/bootstrap.min.js"></script>
<script defer src="/js/cart.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/cart class=navigation-location-link>Cart</a></li>'}"
		var="navigation"></c:set>
	<%@ include file="header.jsp"%>
	<section class="section">
		<c:choose>
			<c:when test="${empty commodities}">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-remove message-icon"></span>Nothing
					here
				</div>
			</c:when>
			<c:otherwise>
				<div class="cart-container">
					<table id="content-table" class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Brand</th>
								<th>Quantity</th>
								<th>Price</th>
								<th></th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="commodity" items="${commodities}">
								<tr>
									<td>${commodity.key.id}</td>
									<td>${commodity.key.name}</td>
									<td>${commodity.key.brand}</td>
									<td>${commodity.value}</td>
									<td>${commodity.key.price * commodity.value}</td>
									<td><a href="/commodities/view/${commodity.key.id}"
										class="btn btn-default">View</a></td>
									<td><button class="btn btn-default"
											data-id="${commodity.key.id}" data-type="order">Order</button></td>
									<td><button class="btn btn-default"
											data-id="${commodity.key.id}" data-type="delete">Delete</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
		<sec:csrfInput />
	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>