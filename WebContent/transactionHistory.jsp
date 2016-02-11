<%@page import="com.lumagaizen.minecraftshop.WEB" %>
<%@page import="com.lumagaizen.minecraftshop.model.ProductTransaction"%>
<%@page import="com.lumagaizen.minecraftshop.model.ShopUser"%>
<%@page import="com.lumagaizen.minecraftshop.model.Product"%>
<%@page import="com.lumagaizen.minecraftshop.model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.lumagaizen.minecraftshop.manager.UserManager"%>
<%@page import="com.lumagaizen.minecraftshop.manager.ProductManager"%>
<%@page import="com.lumagaizen.minecraftshop.manager.TransactionManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"/>
<title>Transaction History</title>
</head>
<body>
	<%= WEB.printNavbarHtml(request) %>
	<%
		HttpServletRequest r = request;

		if (!WEB.isLoggedIn(r))
		{
			out.println("<h2>Login to view your transaction history</h2>");
		}
		else
		{
			String p_userId = WEB.getParam("user_id", r);

			TransactionManager tm = new TransactionManager();
			UserManager u = new UserManager();
			ProductManager p = new ProductManager(); 
			ShopUser user = new ShopUser();
			Product prod;
			
			WEB.getCurrentUser(user, r);
			ArrayList<ProductTransaction> prodTrans = tm.getProductTransactionByUserId(user.getUserId());
			if (!prodTrans.isEmpty())
			{
	%>
<div class="container">
	<h2>Transaction History For <%=user.getUsername()%></h2>
	<div class="row">	
		<table class="table table-condensed">
			<tr>
				<th>Order No</th>
				<th>Product</th>
				<th>Action</th>
				<th>Token Charge</th>
				<th>Time</th>
			</tr>
	<%			
				int i = 1;
				for (ProductTransaction prodTran : prodTrans)
				{
					if (!prodTran.getInCart())
					{
						prod = p.getProductById(prodTran.getProductId());
	%>
				<tr>
					<td><%= i++ %>
					<td><%= prod.getProductWebsiteName()%></td>
					<td><%= prodTran.getAction()%></td>
					<td><%= prodTran.getTokenChange()%></td>
					<td><%= prodTran.getTime() %></td>
				</tr>
	<%
					}
				}
			} else {
				out.println("No transactions");
			}
		}
	%>
	</table>
	</div>
</div>

</body>
</html>