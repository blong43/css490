<%-- 
    Document   : stats
    Created on : Mar 11, 2015, 11:04:58 PM
    Author     : Taylor
--%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.lumagaizen.minecraftshop.WEB" %>
<%@page import="com.lumagaizen.minecraftshop.model.ShopUser"%>
<%@page import="com.lumagaizen.minecraftshop.manager.UserManager"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Random"%>
<%@page import="com.lumagaizen.minecraftshop.model.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.lumagaizen.minecraftshop.manager.ProductManager"%>
<%@page import="com.lumagaizen.minecraftshop.manager.TransactionManager"%>
<%@page import="com.lumagaizen.minecraftshop.model.ProductTransaction"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.lumagaizen.minecraftshop.SQL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    	<jsp:include page="header.jsp"/>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stats Page</title>
    </head>
    <body>
    <%= WEB.printNavbarHtml(request) %>
    <div class="container">
        <h1>Statistics</h1>
		<%
//				ProductManager pm = new ProductManager();
//				ArrayList<Product> products = pm.getAllProducts();
//				TransactionManager tm = new TransactionManager();
//				ProductTransaction trans = new ProductTransaction();
//				UserManager um = new UserManager();
//				ArrayList<ShopUser> users = um.getAllUsers();
//				for (int i = 0; i < 5; i++){
//					ShopUser su = users.get(new Random().nextInt(users.size()));
//					Product p = products.get(new Random().nextInt(products.size()));
//					trans.setAction("purchased");
//					trans.setTokenChange(p.getPriceTokens());
//					trans.setProductId(p.getProductId());
//					trans.setTime(new Timestamp(Math.abs(System.currentTimeMillis() - new Random().nextLong())));
//					trans.setUserId(su.getUserId());
//					trans.setInCart(true);
//					trans.setQuantity(1);
//					tm.insertProductTransaction(trans);
//				}
				
			//<editor-fold defaultstate="collapsed" desc="Week">
			Connection conn = SQL.getConnection();
				String q = "SELECT COUNT(*) as `count`, p.product_website_name `pName` FROM woolcity_product_transactions `t` " 
					+ "INNER JOIN woolcity_products `p` ON p.product_id = t.product_id "
					+ "GROUP BY t.product_id "
					+ "ORDER BY COUNT(*) DESC "
					+ "limit 10";
				Statement sTopTen = conn.createStatement();
				ResultSet rs = sTopTen.executeQuery(q);
			%>
			<h3>Top Ten Best Sellers</h3>
			<table class="table table-condensed">
				<tr><td>Rank</td><td>Product</td>
			<%	int rank = 1;
				while (rs.next())
				{
					out.println("<tr><td>"+(rank++)+"</td><td>"+rs.getString("pName")+"</td></tr>");
				}%>
			</table>	
			<%	
			q = "SELECT SUM(trans.token_change) as `total`, CONCAT('Week ',WEEK(trans.time),', Year ', YEAR(trans.time)) as `t` FROM woolcity_product_transactions trans WHERE `in_cart`=0 GROUP BY CONCAT(YEAR(trans.time), '/', WEEK(trans.time)) ORDER BY MAX(trans.transaction_id) DESC LIMIT 2";
			Statement sWeekly = conn.createStatement();
			rs = sWeekly.executeQuery(q);
			%>
			<h3>Bi-weekly Report</h3>
			<table class="table">
				<tr><td>Week</td><td>Tokens People Spent</td><td>%Change</td></tr>
			<%
			Double curWeekTotal = null;
			while (rs.next()){
				if (curWeekTotal == null){
					curWeekTotal = (double) rs.getInt("total");
				}
				double percentChange = (curWeekTotal - rs.getInt("total"))/curWeekTotal*100;
				out.println("<tr><td>"+rs.getString("t")+"</td><td>"+rs.getInt("total")+"</td><td>"+Math.round(percentChange)+"%</td></tr>");
			}
			%>
			</table>
			<%
			//</editor-fold>
			q = "SELECT SUM(trans.token_change) as `total`, CONCAT('Month ',MONTH(trans.time),', Year ', YEAR(trans.time)) as `t` FROM woolcity_product_transactions trans where `in_cart`=0 GROUP BY CONCAT(YEAR(trans.time), '/', MONTH(trans.time)) ORDER BY MAX(trans.transaction_id) DESC";
			Statement sMonthly = conn.createStatement();
			rs = sMonthly.executeQuery(q);
			%>
			<h3>Monthly Report</h3>
			<table class="table">
				<tr><td>Month</td><td>Tokens People Spent</td><td>%Change</td></tr>
			<%
			Double curMonthTotal = null;
			while (rs.next()){
				if (curMonthTotal == null){
					curMonthTotal = (double) rs.getInt("total");
				}
				double percentChange = (curMonthTotal - rs.getInt("total"))/curMonthTotal*100;
				out.println("<tr><td>"+rs.getString("t")+"</td><td>"+rs.getInt("total")+"</td><td>"+Math.round(percentChange)+"%</td></tr>");
			}
			SQL.returnConnection(conn);
			%>
			</table>
			<%
			//</editor-fold>
			q = "SELECT username, u.user_id, tc.product_id, cnt FROM woolcity_users u "
				+	"INNER JOIN (SELECT COUNT(*) as cnt, `user_id`,`product_id` FROM `woolcity_product_transactions` GROUP BY user_id, `product_id`) "
				+	"tc ON tc.user_id = u.user_id "
				+	"WHERE cnt >= 2 GROUP BY `username`"
				+	"ORDER BY cnt DESC";
			Statement bought2Plus = conn.createStatement();
			rs = bought2Plus.executeQuery(q);
			%>
			<h3>Users with 2+ Buys of Product</h3>
			<table class="table">
				<tr><td>User</td><td>Product</td><td>Amount</td></tr>
			<%
				while(rs.next())
				{
					out.println("<tr><td>"+rs.getString("username")+"</td><td>"+rs.getInt("tc.product_id")+"</td><td>"+rs.getInt("cnt")+"</td></tr>");
				}
			%>
			</table>
			<% 	
				q = "SELECT category_website_name,u.username,cnt FROM \n" +
					"(SELECT username, u.user_id, tc.category_id, cnt FROM woolcity_users u\n" +
					"INNER JOIN (SELECT COUNT(*) as cnt, `user_id`,p.`category_id` FROM `woolcity_product_transactions` tc\n" +
					"INNER JOIN `woolcity_products` p ON p.product_id = tc.product_id\n" +
					"WHERE tc.time > ?  \n" +
					"GROUP BY user_id, `category_id`) \n" +
					"tc ON tc.user_id = u.user_id\n" +
					"WHERE cnt >= 2) t\n" +
					"INNER JOIN `woolcity_categories` cat ON cat.category_id = t.category_id\n" +
					"INNER JOIN `woolcity_users` u ON u.user_id = t.user_id\n" +
					"ORDER BY t.category_id,cnt DESC";
						
				PreparedStatement biWeeklyTopCategoryBuyers = conn.prepareStatement(q);
				biWeeklyTopCategoryBuyers.setTimestamp(1, new Timestamp(System.currentTimeMillis()-1209600000));
				rs = biWeeklyTopCategoryBuyers.executeQuery();
				out.println("<h3>Monthly Customers that Buy 2+ time Per Category </h3>");
				out.println("<table class='table'>");
				out.println("<tr><td>Category</td><td>Username</td><td>Amount</td></tr>");
				while(rs.next()){
					out.println("<tr><td>"+rs.getString("category_website_name")+"</td><td>"+rs.getString("username")+"</td><td>"+rs.getInt("cnt")+"</td></tr>");
				}
				out.println("</table>");
				
				q = "SELECT prod.product_website_name , COUNT(*) as cnt FROM woolcity_product_transactions t INNER JOIN woolcity_products prod ON prod.product_id = t.product_id WHERE t.time > ? GROUP BY t.product_id ORDER BY COUNT(*) DESC";
				PreparedStatement biWeeklyTopProducts = conn.prepareStatement(q);
				biWeeklyTopProducts.setTimestamp(1, new Timestamp(System.currentTimeMillis()-1209600000));
				rs = biWeeklyTopProducts.executeQuery();
				out.println("<h3>BiWeekly Top Products</h3>");
				out.println("<table class='table'>");
				out.println("<tr><td>Rank</td><td>Product</td><td>Amount</td></tr>");
				int i = 1;
				while(rs.next()){
					out.println("<tr><td>"+(i++)+"</td><td>"+rs.getString("product_website_name")+"</td><td>"+rs.getInt("cnt")+"</td></tr>");
				}
				out.println("</table>");
				SQL.returnConnection(conn);
			%>
			</table>
			
			</div>
    </body>
</html>
