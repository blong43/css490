<%@page import="com.lumagaizen.minecraftshop.WEB"%>
<%@page import="com.lumagaizen.minecraftshop.model.ShopUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"/>
<title>Logout</title>
<link rel="stylesheet" href="./assets/css/basic.css" />
</head>
<body>
	
	<%= WEB.printNavbarHtml(request) %>
	<%
		HttpServletRequest r = request;
		String returnURL = WEB.getParam("returnURL", r);
		if (returnURL == null) {
			returnURL = request.getHeader("referer");
		}
		if (WEB.isLoggedIn(r) && WEB.logout(r,response)) {
			response.sendRedirect(returnURL);
		}
	%>
</body>


</html>