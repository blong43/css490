<%@page import="com.lumagaizen.minecraftshop.WEB"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="header.jsp"/>
		<link rel="stylesheet" href="./assets/css/bootstrap.css" />
		<title>Login</title>
	</head>
	<body>
		<%= WEB.printNavbarHtml(request) %>
		<div class="container">
		<h1>Login</h1>
		<%
			HttpServletRequest r = request;
			HttpServletResponse r2 = response;
			String p_username = WEB.getParam("username", r);
			String p_password = WEB.getParam("password", r);

			String returnURL = WEB.getParam("returnURL", r);
			if (returnURL == null)
			{
				returnURL = request.getHeader("referer");
			}

			boolean isSent = "true".equals(WEB.getParam("isSent", r));
			//out.println(request.getRemoteHost()+"<br/>");
			out.println("Your IPv4 address is: " + request.getRemoteAddr());
			if (isSent)
			{
				if (WEB.login(p_username, p_password, r, r2))
				{
					response.sendRedirect(returnURL);// Change this to have a redirect variable later
				}
				else
				{
					out.println("Login failed. Try again.");
				}
			}
		%>
		<form method="POST" action = "./login.jsp">
			<input type="text" name="username" placeholder="username" value="<%= p_username%>"><br/>
			<input type="password" name="password" placeholder="password" value="<%= p_password%>"><br/>
			<input type="hidden" name="returnURL" value="<%= returnURL%>"><br/>
			<input type="hidden" name="isSent" value="true"><br/>
			<input type="submit">
		</form>
		</div>
	</body>

</html>