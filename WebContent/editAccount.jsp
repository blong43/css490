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
        <h1>Edit Account Settings</h1>
        <%--     C O L L E C T _ P A R A M E T E R S                        --%>
        <%
            HttpServletRequest r = request;
			
            // Assign ShopUser variables from form data where available.
            ShopUser su = WEB.getCurrentUserFromDB(r);
			
			// if not logged in, rickroll them.
			if (su == null){
				response.sendRedirect("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
			}
			
            boolean isSent = ("true".equalsIgnoreCase(WEB.getParam("isSent",r)));
			if (isSent){
				su.setUsername(WEB.getParam("username",r));
				String pw = WEB.getParam("password",r);
				String cPw = WEB.getParam("confirmPassword",r);
				su.setDisplayName(WEB.getParam("displayName",r));
				
				if (pw.equals(cPw) && pw.length() > 0){
					su.setPasswordByRawValue(pw);
					UserManager um = new UserManager();
					// Attempt to register the user. 
					if (isSent && um.updateShopUserByUserId(su, su.getUserId())){
						out.println("Update was successful.");
					}else{
						out.println("Update failed.");
					}
				}else{
					out.println("<script>alert('Hey! Those passwords do not match, or else you left them empty. Try again!')</script>");
				}
			}
			
            //su.setFirstLogin(System.currentTimeMillis());
            //su.setLastLogin(System.currentTimeMillis());
            //String p_ipv4 = request.getRemoteAddr();
            //su.setIpv4(request.getRemoteAddr());
            
            
        %>
        <form method="POST" action="./register.jsp">
            <label>Display Name</label> <input type="text" name="displayName" placeholder="Display Name" value="<%= (su != null ? su.getDisplayName() : "") %>"><br/>
            <label>Username</label> <input type="text" name="username" placeholder="username" value="<%= (su != null ? su.getUsername() : "") %>"><br/>
            <label>Password</label> <input type="password" name="password" placeholder="password"><br/>
            <label>Confirm Password</label> <input type="password" name="confirmPassword" placeholder="Confirm Password"><br/>
            <input type="hidden" name="isSent" value="true">
            <input type="submit" value="Save">
        </form><br/>
        </div>
    </body>
</html>
