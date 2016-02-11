<%@page import='com.lumagaizen.minecraftshop.WEB' %>
<%@page import='com.lumagaizen.minecraftshop.model.ShopUser'%>
<%@page import='com.lumagaizen.minecraftshop.manager.UserManager'%>
<nav class='navbar navbar-default navbar-static-top'>
  <div class='container-fluid'>
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class='navbar-header'>
      <button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#bs-example-navbar-collapse-1'>
        <span class='sr-only'>Toggle navigation</span>
        <span class='icon-bar'></span>
        <span class='icon-bar'></span>
        <span class='icon-bar'></span>
      </button>
      <a class='navbar-brand' href='./shop.jsp'>Minecraft Shop</a>
    </div>
    <%
    String auth = "Authenticate";
   	int tokens = 0;
    boolean isAdmin = false;
   	boolean isLoggedIn = WEB.isLoggedIn(request);
   	if (isLoggedIn)
	{
   		isAdmin = WEB.isAdmin(request);
		UserManager um = new UserManager();
		ShopUser user = new ShopUser();
		WEB.getCurrentUser(user, request);
		user = um.getShopUserByUserId(user.getUserId());
		tokens = user.getTokens();
		auth = "Welcome, " + user.getUsername();
	}%>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class='collapse navbar-collapse' id='bs-example-navbar-collapse-1'>
      <ul class='nav navbar-nav'>
      	<% if (isLoggedIn && !isAdmin) {	%>
      	<li role='presentation'><p>Tokens: <%= tokens %></p></li>
		<li role='presentation'><a href='./transaction.jsp'>Transaction</a></li>
		<li role='presentation'><a href='./transactionHistory.jsp'>Transaction History</a></li>
		<%}	%>
      </ul>
      <ul class='nav navbar-nav navbar-right'>
        <li class='dropdown'>
          <a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-expanded='false'><%=auth%><span class='caret'></span></a>
          <ul class='dropdown-menu' role='menu'>
          	<% if (!isLoggedIn) { %>
			<li role='presentation'><a href='register.jsp?returnURL=./index.jsp'>Register</a></li>
			<li role='presentation'><a href='login.jsp?returnURL=./index.jsp'>Login</a></li>
			<%}	%>
			<% if (isLoggedIn) {	%>
			<li role='presentation'><a href='logout.jsp?returnURL=./index.jsp'>Logout</a></li>
			<% if (isAdmin) {	%>
			<li role='presentation'><a href='admin.jsp'>Admin Page</a>
			<%}}%>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
