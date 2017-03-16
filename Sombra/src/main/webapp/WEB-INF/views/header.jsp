<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<header class="header">
	<div class="header-content">
		<a href="/" class="header-logo"><img src="/img/logo.png"
			alt="logo" class="logo-image" /></a>
		<nav class="header-navigation">
			<a href="/commodities" class="btn btn-link header-navigation-link"><i
				class="fa fa-shopping-basket header-navigation-icon"
				aria-hidden="true"></i>Commodities</a>
			<sec:authorize access="!isAuthenticated()">
				<a href="/register" class="btn btn-link header-navigation-link"><i
					class="fa fa-user-plus header-navigation-icon" aria-hidden="true"></i>Register</a>
				<a href="/login" class="btn btn-link header-navigation-link"><i
					class="fa fa-sign-in header-navigation-icon" aria-hidden="true"></i>Login</a>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<a href="/user/profile" class="btn btn-link header-navigation-link"><i
					class="fa fa-user header-navigation-icon" aria-hidden="true"></i>Profile</a>
				<form action="/logout" method="POST" class="header-logout-form">
					<button class="btn btn-link header-navigation-link">
						<i class="fa fa-sign-out header-navigation-icon"
							aria-hidden="true"></i>Logout
					</button>
					<sec:csrfInput />
				</form>
			</sec:authorize>
		</nav>
	</div>
</header>
<nav class="navigation">
	<ul class="breadcrumb navigation-location">
		<li class="navigation-location-item"><a href="/"
			class="navigation-location-link"><i
				class="fa fa-home navigation-location-icon" aria-hidden="true"></i>Home</a></li>
		<c:out value="${navigation}" escapeXml="false"></c:out>
	</ul>
	<div class="navigation-buttons">
		<sec:authorize access="hasRole('ADMIN')">
			<a href="/admin" class="btn btn-default navigation-button"><i
				class="fa fa-lock navigation-icon" aria-hidden="true"></i>Admin</a>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<a href="/user/orders" class="btn btn-default navigation-button"><i
				class="fa fa-shopping-bag navigation-icon" aria-hidden="true"></i>Orders</a>
		</sec:authorize>
		<a href="/cart" class="btn btn-default navigation-button"><i
			class="fa fa-shopping-cart navigation-icon" aria-hidden="true"></i>Cart</a>
	</div>
	<div class="navigation-user-greeting">
		Welcome, <b> <sec:authorize access="!isAuthenticated()">
				<sec:authentication property="name" />
			</sec:authorize> <sec:authorize access="isAuthenticated()">
				<sec:authentication property="principal.name" />
			</sec:authorize>
		</b>
	</div>
</nav>