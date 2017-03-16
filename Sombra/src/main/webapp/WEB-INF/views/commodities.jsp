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
<link rel="stylesheet" href="/css/commodities.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/commodities.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/commodities class=navigation-location-link>Commodities</a></li>'}"
		var="navigation"></c:set>
	<%@ include file="header.jsp"%>
	<section class="section">
		<c:if test="${empty commodities}">
			<div class="alert alert-danger" role="alert">
				<span class="glyphicon glyphicon-remove message-icon"></span>Nothing
				here
			</div>
		</c:if>
		<div class="commodities-container">
			<div class="filter-container">
				<form id="search" action="/commodities" method="GET">
					<div class="form-group">
						<label for="category">Category</label> <select id="category"
							name="category" class="form-control">
							<option value="">Not Selected</option>
							<c:forEach var="category" items="${categories}">
								<c:choose>
									<c:when test="${category.id eq param.category}">
										<option selected="selected" value="${category.id}">${category.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${category.id}">${category.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="subcategory">SubCategory</label> <select
							id="subcategory" name="subcategory" class="form-control">
							<option value="">Not Selected</option>
							<c:forEach var="subCategory" items="${subCategories}">
								<c:choose>
									<c:when test="${subCategory.id eq param.subCategory}">
										<option selected="selected"
											data-category="${subCategory.category.id}"
											value="${subCategory.id}">${subCategory.name}</option>
									</c:when>
									<c:otherwise>
										<option data-category="${subCategory.category.id}"
											value="${subCategory.id}">${subCategory.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="name">Name</label> <input name="name" type="text"
							placeholder="Name" value="${param.name}" class="form-control">
					</div>
					<div class="form-group">
						<label for="brand">Brand</label> <select name="brand"
							class="form-control">
							<option value="">Not Selected</option>
							<c:forEach var="brand" items="${brands}">
								<c:choose>
									<c:when test="${brand eq param.brand}">
										<option selected="selected" value="${brand}">${brand}</option>
									</c:when>
									<c:otherwise>
										<option value="${brand}">${brand}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="country">Country</label> <select name="country"
							class="form-control">
							<option value="">Not Selected</option>
							<c:forEach var="country" items="${countries}">
								<c:choose>
									<c:when test="${country eq param.country}">
										<option selected="selected" value="${country}">${country}</option>
									</c:when>
									<c:otherwise>
										<option value="${country}">${country}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="consist">Consist</label> <input name="consist"
							type="text" value="${param.consist}" placeholder="Consist"
							class="form-control">
					</div>
					<div class="form-group">
						<label for="price">Price</label> <input name="price" type="number"
							min="${minPrice}" max="${maxPrice}" value="${param.price}"
							placeholder="Price" class="form-control">
					</div>
					<div class="form-group">
						<label for="order">Order</label> <select name="order"
							class="form-control">
							<option value="">Default</option>
							<option value="name">Name</option>
							<option value="brand">Brand</option>
							<option value="country">Country</option>
							<option value="consist">Consist</option>
							<option value="price">Price</option>
						</select>
					</div>
					<div class="form-group">
						<label for="direction">Direction</label> <select name="direction"
							class="form-control">
							<option value="">Default</option>
							<option value="asc">Ascending</option>
							<option value="desc">Descending</option>
						</select>
					</div>
					<div class="form-group">
						<label for="count">Count</label> <input name="count" type="number"
							value="${param.count}" placeholder="Count" class="form-control">
					</div>
					<div class="filter-submit-container">
						<button class="btn btn-default filter-form-button">Apply
							Filter</button>
						<a href="/commodities" class="btn btn-default filter-form-button">Reset
							Filter</a>
					</div>
				</form>
			</div>
			<c:forEach var="commodity" items="${commodities}">
				<a href="/commodities/view/${commodity.id}" class="commodity-link">
					<span class="commodity-container"> <span class="commodities"><c:choose>
								<c:when test="${commodity.image ne null}">
									<img src="${commodity.image}" alt="commodity"
										class="commodity-image" />
								</c:when>
								<c:otherwise>
									<img src="/img/commodity.png" alt="commodity"
										class="commodity-image" />
								</c:otherwise>
							</c:choose> <span class="commodity-name-container"><span
								class="commodity-name-value">${commodity.name}</span> </span> <span
							class="commodity-country-container"><span
								class="commodity-country-value">${commodity.country}</span> </span> <span
							class="commodity-price-container"><span
								class="commodity-price-value">$${commodity.price}</span> </span> </span>
				</span>
				</a>
			</c:forEach>
		</div>

		<c:url value="/commodities" var="filter">
			<c:forEach var="entry" items="${param}">
				<c:if test="${entry.key != 'page'}">
					<c:param name="${entry.key}" value="${entry.value}" />
				</c:if>
			</c:forEach>
		</c:url>

		<div class="paging-container">
			<ul class="pagination paging">
				<c:url value="${filter}" var="page">
					<c:param name="page" value="0" />
				</c:url>
				<li><a href="${page}" class="paging-link">First</a></li>
				<c:choose>
					<c:when test="${current==0}">
						<li class="disabled"><a href="#" class="paging-link">&laquo;</a></li>
					</c:when>
					<c:otherwise>
						<c:url value="${filter}" var="page">
							<c:param name="page" value="${current-1}" />
						</c:url>
						<li><a href="${page}" class="paging-link">&laquo;</a></li>
					</c:otherwise>
				</c:choose>
				<li class="active"><a href="#"
					class="paging-status paging-link">Page ${current+1} / ${pages}</a></li>
				<c:choose>
					<c:when test="${current==pages-1}">
						<li class="disabled"><a href="#" class="paging-link">&raquo;</a></li>
					</c:when>
					<c:otherwise>
						<c:url value="${filter}" var="page">
							<c:param name="page" value="${current+1}" />
						</c:url>
						<li><a href="${page}" class="paging-link">&raquo;</a></li>
					</c:otherwise>
				</c:choose>
				<c:url value="${filter}" var="page">
					<c:param name="page" value="${pages-1}" />
				</c:url>
				<li><a href="${page}" class="paging-link">Last</a></li>
			</ul>
		</div>
	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>