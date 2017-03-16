<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Commodities</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/admin-commodities.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/bootstrap.min.js"></script>
<script defer src="/js/admin-commodities.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/admin class=navigation-location-link>Admin</a></li>'}${'<li class=navigation-location-item><a href=/admin/commodities class=navigation-location-link>Commodities</a></li>'}"
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
				<div class="commodities-container">
					<table id="content-table" class="table table-striped">
						<thead>
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Brand</th>
								<th>Country</th>
								<th>Consist</th>
								<th>Price</th>
								<th>Available</th>
								<th>SubCategory</th>
								<th>Image</th>
								<th></th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="commodity" items="${commodities}">
								<tr>
									<td>${commodity.id}</td>
									<td>${commodity.name}</td>
									<td>${commodity.brand}</td>
									<td>${commodity.country}</td>
									<td>${commodity.consist}</td>
									<td>${commodity.price}</td>
									<td>${commodity.available}</td>
									<td>${commodity.subCategory.name}</td>
									<td><c:if test="${commodity.image != null}">
											<img src="${commodity.image}" alt="commodity"
												class="commodity-image">
										</c:if></td>
									<td><button class="btn btn-default"
											data-id="${commodity.id}" data-type="edit">Edit</button></td>
									<td><button class="btn btn-default"
											data-id="${commodity.id}" data-type="delete">Delete</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<hr>
					<div class="add-commodity-button">
						<button id="add-popup-show" class="btn btn-default">Add
							Commodity</button>
					</div>
					<hr>
					<div class="add-commodities-button">
						<form action="/admin/commodities/addFromCsv?_csrf=${_csrf.token}"
							method="POST" enctype="multipart/form-data">
							<input name="csv" type="file" />
							<button id="edit-image-submit" class="btn btn-default">Add
								From CSV</button>
						</form>
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
									<h3>Edit Commodity</h3>
								</div>
								<div class="modal-body">
									<input id="edit-id" type="hidden">
									<div class="form-group">
										<label for="edit-name">Name</label> <input id="edit-name"
											type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-brand">Brand</label> <input id="edit-brand"
											type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-country">Country</label> <input
											id="edit-country" type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-consist">Consist</label> <input
											id="edit-consist" type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-price">Price</label> <input id="edit-price"
											type="number" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-available">Available</label> <input
											id="edit-available" type="number" class="form-control">
									</div>
									<div class="form-group">
										<label for="edit-subcategory">SubCategory</label> <select
											id="edit-subcategory" class="form-control">
											<option value="">None</option>
											<c:forEach var="subCategory" items="${subCategories}">
												<option value="${subCategory.id}">${subCategory.name}</option>
											</c:forEach>
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
									<h3>Add Commodity</h3>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<label for="add-name">Name</label> <input id="add-name"
											type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-brand">Brand</label> <input id="add-brand"
											type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-country">Country</label> <input
											id="add-country" type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-consist">Consist</label> <input
											id="add-consist" type="text" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-price">Price</label> <input id="add-price"
											type="number" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-available">Available</label> <input
											id="add-available" type="number" class="form-control">
									</div>
									<div class="form-group">
										<label for="add-subcategory">SubCategory</label> <select
											id="add-subcategory" class="form-control">
											<option value="">None</option>
											<c:forEach var="subcategory" items="${subcategories}">
												<option value="${subcategory.id}">${subcategory.name}</option>
											</c:forEach>
											<option></option>
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