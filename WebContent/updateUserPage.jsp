<%@page import="com.lumagaizen.minecraftshop.manager.UserManager"%>
<%@page import="com.lumagaizen.minecraftshop.model.ShopUser"%>
<%@page import="com.lumagaizen.minecraftshop.WEB" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update User</title>
</head>
<body>
	<%= WEB.printNavbarHtml(request) %>
	<div class="container">
		<table class="table table-condensed">
			<h2> Users </h2>
			<tr>
				<th>User No</th>
				<th>Username</th>
				<th>Display Name</th>
				<th></th>
			</tr>
	<%	int i = 0;
		UserManager um = new UserManager();		
		for (ShopUser user : um.getAllUsers())
		{%>
			<tr>
				<td><%= ++i %> </td>
				<td><%= user.getUsername() %></td>
				<td><%= user.getDisplayName() %></td>
				<td><form method="POST" action="UpdateUserServlet">
					<input type="hidden" name="userId" value=<%= user.getUserId() %>>
					<input type="hidden" name="returnURL" value="./updateUserPage.jsp">
					<input type="hidden" name="visited" value="0">
					<input type="submit" name="action" value="Update">					
				</form></td>
			</tr>
	<%  }%>
		</table>
 	</div>
 
</body>
</html>