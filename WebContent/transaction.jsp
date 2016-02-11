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
<title>Transaction</title>
</head>
<body>
	<%= WEB.printNavbarHtml(request) %>
	<%
		HttpServletRequest r = request;

		if (!WEB.isLoggedIn(r))
		{
			out.println("<h2>Login to view your transactions</h2>");
		}
		else
		{
			String quantity = WEB.getParam("quantity", r);
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
	<div class="row">
		<h2>Pending Transactions for <%=user.getUsername()%></h2>
		<table class="table table-condensed">
			<tr>
				<th>Purchase | Delete | Update</th>
				<th>Amount</th>
				<th>Product</th>
				<th>Action</th>
				<th>Token Charge</th>
				<th>Time</th>
			</tr>
	<%		
				for (ProductTransaction prodTran : prodTrans)
				{
					if (prodTran.getInCart())
					{
						prod = p.getProductById(prodTran.getProductId());
						
	%>
				<tr>
					<td>
					<form method="POST" action="PurchaseServlet">
					<input type="hidden" name="trans_id" value=<%=prodTran.getTransactionId()%>>
					<input type="hidden" name="returnURL" value="./transaction.jsp">
					<input type="submit" name="action" value="Purchase">
					<input type="submit" name="action" value="Delete">
					<input type="submit" name="action" value="Update">
					<input type="text" placeholder="Quantity Update" name="quantity" value=<%=quantity%>>					
					</form> 
					</td>
					<td><%= prodTran.getQuantity() %> </td>
					<td><%= prod.getProductWebsiteName()%></td>
					<td><%= prodTran.getAction()%></td>
					<td><%= prodTran.getTokenChange()%></td>
					<td><%= prodTran.getTime() %></td>
				</tr>
	<%
					}
				}
	%>
			</table>
	</div>
</div>					
	<%
			} else {
				out.println("No pending transactions");
			}
		}
	%>
		
	
</body>
</html>