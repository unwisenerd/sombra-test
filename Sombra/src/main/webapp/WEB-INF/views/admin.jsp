<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Admin Page</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/admin.css">
</head>
<body>
	<c:set
		value="${'<li class=navigation-location-item><a href=/admin class=navigation-location-link>Admin</a></li>'}"
		var="navigation"></c:set>
	<%@ include file="header.jsp"%>
	<section class="section">
		<div class="admin-navigation">
			<h3 class="admin-title">Admin Menu</h3>
			<hr>
			<a href="/admin/categories"
				class="btn btn-default admin-navigation-button">Manage
				Categories</a> <a href="/admin/commodities"
				class="btn btn-default admin-navigation-button">Manage
				Commodities</a> <a href="/admin/orders"
				class="btn btn-default admin-navigation-button">Manage Orders</a> <a
				href="/admin/subcategories"
				class="btn btn-default admin-navigation-button">Manage
				SubCategories</a> <a href="/admin/users"
				class="btn btn-default admin-navigation-button">Manage Users</a>
		</div>
	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>