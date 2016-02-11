<%@page import="java.util.HashMap"%>
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
<title>All User Transactions</title>
<link rel="stylesheet" href="./assets/css/bootstrap.css" />
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
			ShopUser user;
			Product prod;
			
			ArrayList<ProductTransaction> prodTrans = tm.getAllProductTransactions();
			if (!prodTrans.isEmpty())
			{
	%>
<div class="container">
	<h2>Transactions </h2>
	<div class="row">	
		<table class="table table-condensed">
			<tr>
				<th>Transaction No</th>
				<th>Username</th>
				<th>Product Name</th>
				<th>Action</th>
				<th>Token Charge</th>
				<th>Time</th>
			</tr>
	<%			
				HashMap<Integer,Product> prods = new HashMap<Integer,Product>();
				HashMap<Integer,ShopUser> users = new HashMap<Integer,ShopUser>();
				for (ProductTransaction prodTran : prodTrans)
				{
					if (!prodTran.getInCart())
					{
						if (!prods.containsKey(prodTran.getProductId())){
							prods.put(prodTran.getProductId(),p.getProductById(prodTran.getProductId()));
						}
						prod = prods.get(prodTran.getProductId());
						
						
						if (!users.containsKey(prodTran.getUserId())){
							users.put(prodTran.getUserId(),u.getShopUserByUserId(prodTran.getUserId()));
						}
						user =  users.get(prodTran.getUserId());
	%>
				<tr>
					<td><%= prodTran.getTransactionId() %>
					<td><%= user.getUsername() %>
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