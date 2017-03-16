<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Main Page</title>
<meta name="viewport" content="width=768px">
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/index.css">
</head>
<body>
	<%@ include file="header.jsp"%>
	<section class="section">
		<div class="splash">
			<img src="/img/splash.jpg" alt="" class="splash-image" />
			<div class="splash-wrap">
				<hr>
				<span class="splash-text">Sale Started</span>
				<hr>
			</div>
		</div>
		<div class="categories">
			<div class="left">
				<img src="/img/men.png" alt="men" />
				<div class="men-wrap">
					<a href="/commodities?category=2"
						class="btn btn-link category-link"><i
						class="fa fa-mars-stroke category-icon" aria-hidden="true"></i>Men
						Clothing</a>
				</div>
			</div><div class="right">
				<img src="/img/women.png" alt="women" />
				<div class="women-wrap">
					<a href="/commodities?category=1"
						class="btn btn-link category-link"><i
						class="fa fa-venus category-icon" aria-hidden="true"></i>Women
						Clothing</a>
				</div>
			</div>
		</div>
	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>