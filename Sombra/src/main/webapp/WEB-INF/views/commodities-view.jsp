<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>View</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/commodities-view.css">
<script defer src="/js/jquery-3.1.1.min.js"></script>
<script defer src="/js/bootstrap.min.js"></script>
<script defer src="/js/commodities-view.js"></script>
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/commodities class=navigation-location-link>Commodities</a></li>'}${'<li class=navigation-location-item><a href=/commodities/view/'}${commodity.id}${' class=navigation-location-link>View</a></li>'}"
		var="navigation"></c:set>
	<%@ include file="header.jsp"%>
	<section class="section">
		<c:choose>
			<c:when test="${empty commodity}">
				<div class="alert alert-danger" role="alert">
					<span class="glyphicon glyphicon-remove message-icon"></span>Nothing
					here
				</div>
			</c:when>
			<c:otherwise>
				<div class="commodity-left-content">
					<div class="commodity-image-container">
						<c:choose>
							<c:when test="${commodity.image ne null}">
								<img src="${commodity.image}" alt="commodity"
									class="commodity-image" />
							</c:when>
							<c:otherwise>
								<img src="/img/commodity.png" alt="commodity"
									class="commodity-image" />
							</c:otherwise>
						</c:choose>
					</div>
				</div><div class="commodity-right-content">
					<div class="commodity-header">
						<h3 class="commodity-name">${commodity.name}</h3>
						<hr>
						<div class="commodity-price">$${commodity.price}</div>
					</div>
					<div class="commodity-description-container">
						<dl class="commodity-description">
							<dt class="commodity-property">Brand
							<dd class="commodity-value">${commodity.brand}
							<dt class="commodity-property">Country
							<dd class="commodity-value">${commodity.country}
							<dt class="commodity-property">Consist
							<dd class="commodity-value">${commodity.consist}
							<dt class="commodity-property">Available
							<dd class="commodity-value">
								<c:choose>
									<c:when test="${commodity.available > 0}">${commodity.available}
									</c:when>
									<c:otherwise>
										Not Available
									</c:otherwise>
								</c:choose>
						</dl>
					</div>
					<div class="commodity-form">
						<div class="form-group">
							<label for="quantity">Quantity</label> <input id="quantity"
								name="quantity" type="number" min="1"
								max="${commodity.available - cartQuantity}" value="1"
								class="form-control">
						</div>
						<div class="commodity-add-button">
							<c:choose>
								<c:when test="${commodity.available > 0}">
									<button id="add-to-cart" data-loading-text="Loading..."
										class="btn btn-default">
										<i class="fa fa-shopping-cart commodity-add-icon"
											aria-hidden="true"></i>Add To Cart
									</button>
								</c:when>
								<c:otherwise>
									<button id="add-to-cart" data-loading-text="Loading..."
										class="btn btn-default" disabled="disabled">
										<i class="fa fa-shopping-cart commodity-add-icon"
											aria-hidden="true"></i>Add To Cart
									</button>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
				<sec:csrfInput />
				<div id="result-popup" class="modal fade">
					<div class="popup-table">
						<div class="modal-dialog popup-cell">
							<div class="modal-content popup-content">
								<div class="modal-body">
									<button id="result-popup-hide" class="close">
										<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
									</button>
									<div id="result-text"></div>
								</div>
								<sec:csrfInput />
								<div class="modal-footer popup-footer">
									<button id="result-confirm" class="btn btn-default">Confirm</button>
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