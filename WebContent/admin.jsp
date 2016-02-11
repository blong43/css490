<%@page import="com.lumagaizen.minecraftshop.WEB"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<jsp:include page="header.jsp"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Page</title>
</head>
<body>
<%= WEB.printNavbarHtml(request) %>
	<div class="container">
		<div class="row">
			<div class="col-md-9">
				<h2>Admin Page</h2>
				<h5>
					To update products click here: <a href="updateProductPage.jsp">Products</a></br>
					To view users click here: <a href="updateUserPage.jsp">Users</a></br>
					To view statistics click here: <a href="stats.jsp">Statistics</a></br>
					To view all transactions click here: <a href="allTransactions.jsp">Transactions</a>
				</h5>
			</div>
		</div>
	</div>
</body>
</html>