<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.lumagaizen.minecraftshop.WEB" %>
<%@page import="com.lumagaizen.minecraftshop.model.ShopUser"%>
<%@page import="com.lumagaizen.minecraftshop.manager.UserManager"%>
<!DOCTYPE html>
<html>
    <head>
		<jsp:include page="header.jsp"/>
		<link rel="stylesheet" href="./assets/css/basic.css" />
    </head>
    <body>
		<%= WEB.printNavbarHtml(request) %>
		<%
			response.sendRedirect("./shop.jsp");
			if (WEB.isLoggedIn(request))
			{
				UserManager um = new UserManager();
				ShopUser user = new ShopUser();
				WEB.getCurrentUser(user, request);
				out.println("<h2>Welcome back to the Minecraft Shop " + user.getUsername() + "<h2>");
			}
			else
			{
				out.println("<h2>Welcome to the Minecraft Shop<h2>");
				out.println("<h5> To view our products navigate to our Shop </h5>");
				out.println("<h5> To view your transactions please login</h5>");
			}
		%>
		<h4> Top 10 Best Sellers </h4>
		<h4> Top 5 Best Sellers Per Categories</h4>
		
    </body>
</html>
