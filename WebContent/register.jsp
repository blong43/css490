<%@page import="com.lumagaizen.minecraftshop.WEB"%>
<%@page import="com.lumagaizen.minecraftshop.manager.UserManager"%>
<%@page import="com.lumagaizen.minecraftshop.model.ShopUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
		<jsp:include page="header.jsp"/>
        <title>Register</title>
        <link rel="stylesheet" href="./assets/css/bootstrap.css" />
    </head>
    <body>
    	
    	<%= WEB.printNavbarHtml(request) %>
    	<div class="container">
        <h1>Registration</h1>
		<p> You will get 100,000 tokens after registering!</p>
        <%--     C O L L E C T _ P A R A M E T E R S                        --%>
        <%
            HttpServletRequest r = request;
			
            String returnURL = WEB.getParam("returnURL", r);
            if (returnURL == null){
                returnURL = request.getHeader("referer");
            }
            
            // Assign ShopUser variables from form data where available.
            ShopUser su = new ShopUser();
            su.setUsername(WEB.getParam("username",r));
            su.setPasswordByRawValue(WEB.getParam("password",r));
            su.setDisplayName(WEB.getParam("displayName",r));
            su.setUuid(WEB.getParam("uuid", r));
            
            su.setFirstLogin(System.currentTimeMillis());
            su.setLastLogin(System.currentTimeMillis());
            String p_ipv4 = request.getRemoteAddr();
            su.setIpv4(request.getRemoteAddr());
            
            boolean isSent = ("true".equalsIgnoreCase(WEB.getParam("isSent",r)));
            
            
            // Attempt to register the user. 
            if (isSent && registerNewUser(su)){
                out.println("Registration was successful.");
                if (returnURL == null){
                    response.sendRedirect("./index.jsp");
                }else{
                    response.sendRedirect(returnURL);
                }
            }else{
                if (isSent){
                    out.println("Registration was not successful.");
                }
        %>
        <form method="POST" action="./register.jsp">
            <span>Your IPv4 address is : <%= su.getIpv4() %></span><br/>
            <input type="text" name="username" placeholder="username" value="<%= su.getUsername() %>"><br/>
            <input type="password" name="password" placeholder="password"><br/>
            <input type="text" name="uuid" placeholder="uuid" value="<%= su.getUuid() %>"><br/>
            <input type="text" name="displayName" placeholder="Display Name" value="<%= su.getDisplayName() %>"><br/>
            <input type="hidden" name="returnURL" value="<%= returnURL %>"><br/>
            <input type="hidden" name="isSent" value="true">
            <input type="submit" value="Register">
        </form><br/>
        <%
            }
        %>
        </div>
    </body>
</html>

<%---         M E T H O D S _ B E L O W _ T H I S _ L I N E                 --%>
<%! 
/**
 * Returns true if a new user was able to be registered using the backend 
 * methods. If you want to tell a user what they did wrong, front-end validation
 * should be used with javascript.
 */
private boolean registerNewUser(ShopUser su){
    UserManager us = new UserManager();
    return us.insertShopUser(su);
}
%>