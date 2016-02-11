<%@page import="java.util.ArrayList"%>
<%@page import="java.io.IOException"%>
<%@page import="com.lumagaizen.minecraftshop.model.Product"%>
<%@page import="com.lumagaizen.minecraftshop.model.Category"%>
<%@page import="com.lumagaizen.minecraftshop.WEB"%>
<%@page import="com.lumagaizen.minecraftshop.manager.ProductManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.lumagaizen.minecraftshop.SQL"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<jsp:include page="header.jsp"/>
		<script src="./assets/js/Shop.js"></script>
		<title>Shop</title>
	</head>
	<body>
		<%= WEB.printNavbarHtml(request) %>	
		<div class="row">
			<div id="shop-search-settings" class="col-md-offset-2 col-md-8">	
				<div class="col-xs-12"> <!-- Adds padding -->
					<div class="panel panel-primary">
						<div class="panel-heading">Search Settings</div>
						<div class="panel-body"><% printSearchForm(request, out);%></div>
					</div>						
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-offset-2 col-md-8" id="shop-product-display" >
				<script>shop.renderShopProducts();</script>
			</div>
		</div>
	</body>
</html>

<%!
	protected void printShopProduct(Product p,HttpServletRequest request, JspWriter out) throws IOException
	{
		String s = "";	
		out.println("<div class='col-sm-6 col-md-4' style='max-width:320px;'>");
		out.println("<div class='thumbnail' style='border: 1px solid;'>");
		out.println("<img src='holder.js/320x200' alt='...' style='border: 1px solid;'/>");
		out.println("<div class='caption'>");
		out.println("<h4>"+WEB.htmlEncode(p.getProductMinecraftName())+"</h4>");
		out.println("<p>Product description about a very nice product that people should totally buy!</p>");
		out.println("<p><strike style='color:red;'>Price: 2000 tokens</strike><br/>Price: 25 tokens</p>");
		out.println("<p><button class='btn btn-primary' role='button' onclick='alert('Do you want to buy this?\')'>Buy</button>");
		out.println("<button class='btn btn-primary' role='button'onclick='alert('Liked!')'>+1 (203 Likes)</button>");
		out.println("</p>");
		out.println("</div>");
		out.println("</div>");
		out.println("</div>");
	}
	/**
	 * Generates an <option value> tag. if selectedValue == value, it will show
	 * as "selected"
	 */
	protected String selOptionHelper(String value, String label, String selectedValue)
	{
		String s = "<option value = '" + WEB.htmlEncode(value) + "' ";
		if (value.equalsIgnoreCase(selectedValue))
		{
			s += " selected ";
		}
		s += ">" + WEB.htmlEncode(label) + "</option>";
		return s;
	}
	
	// J S P _ B E L O W _ H E R E 
	protected void printSearchForm(HttpServletRequest request, JspWriter out) throws IOException
	{
		ProductManager pm = new ProductManager();
		String category = WEB.getParam("categoryId", request);
		String searchText = WEB.getParam("searchText", request);
		boolean isLimitedEdition = "true".equalsIgnoreCase(WEB.getParam("isLimitedEdition", request));
		boolean isDiscounted = "true".equalsIgnoreCase(WEB.getParam("isDiscounted", request));
		int limit = Integer.MAX_VALUE;
		try{ limit = Integer.parseInt(WEB.getParam("limit", request)); }catch(NumberFormatException nfe){}
		String sortOrder = WEB.getParam("sort", request);
		if (sortOrder == "")
		{
			sortOrder = "NEW";
		}

		//------------ P R I N T _ F O R M ---------------------------------
		//out.println("<form class='form-group' method='GET' action='#' onSubmit='JavaScript:shop.searchProductsViaForm();'>");
			out.println("<div class='col-md-3'>");
				out.println("<input type='text' class='form-control' placeholder='Search' name='searchText'  id='searchText' value='" + WEB.htmlEncode(searchText) + "'>");
			out.println("</div>");
			out.println("<div class='col-md-3'>");
				out.println("<input type='text' class='form-control' placeholder='Limit' name='limit'  id='limit' value='"+limit+"'>");
			out.println("</div>");
			out.println("<div class='col-md-3'>");
				out.println("<select name='categoryId'  id='categoryId' value='" + WEB.htmlEncode(category) + "' class='form-control'>");
					out.println(selOptionHelper("", "Any Category", category));
					for (Category cat : pm.getAllCategories())
					{
						out.println(selOptionHelper("" + cat.getCategoryId(), cat.getCategoryWebsiteName(), category));
					}
				out.println("</select>");
			out.println("</div>");
			out.println("<div class='col-md-3'>");
				out.println("<select name='sort'  id='sort' value='" + WEB.htmlEncode(sortOrder) + "' class='form-control'>");
					out.println(selOptionHelper("NEW", "Newest", sortOrder));
					out.println(selOptionHelper("LIKED", "Highest Rating", sortOrder));
					out.println(selOptionHelper("POPULAR", "Most Popular", sortOrder));
					out.println(selOptionHelper("PRICE_LOW", "Cheapest", sortOrder));
					out.println(selOptionHelper("PRICE_HIGH", "Most Expensive", sortOrder));
				out.println("</select>");
			out.println("</div>");
			out.println("<div class='col-md-3'>");
				out.println("<button id='btnSearch' class='btn btn-success form-control' onclick='shop.searchProductsViaForm();'>Search</button>");
			out.println("</div><br/>");
			out.println("<div class='col-md-12'>");
			out.println("<input type='checkbox'" + (isLimitedEdition ? "checked" : "") + " name='isLimitedEdition'  id='isLimitedEdition' value='true'> Limited Edition");
			out.println("<br/>");
			out.println("<input type='checkbox'" + (isDiscounted ? "checked" : "") + " name='isDiscounted'  id='isDiscounted' value='true'> On Sale");
			out.println("</div>");
		//out.println("</form>");
	}

%>