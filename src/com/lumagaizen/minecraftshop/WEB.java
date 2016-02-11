package com.lumagaizen.minecraftshop;

import com.lumagaizen.minecraftshop.manager.UserManager;
import com.lumagaizen.minecraftshop.model.ShopUser;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.owasp.encoder.Encode;

/**
 * This is a static utilities class for methods that are required by the JSP and
 * Servlet files.
 *
 * @author Taylor
 */
public class WEB
{
	//<editor-fold defaultstate="collapsed" desc="Authentication">

	/**
	 * Returns TRUE if SESSION["isLoggedIn"] == true, and the user agent matches
	 * the expected value. It might also return true if the set cookies can be
	 * used to log the user in successfully.
	 *
	 * @param r
	 * @return
	 */
	public static boolean isLoggedIn(HttpServletRequest r)
	{
		try
		{

			String userAgent = r.getHeader("User-Agent");

			Object isLoggedInObj = r.getSession(true).getAttribute("isLoggedIn");
			if (isLoggedInObj != null && (boolean) isLoggedInObj == true)
			{
				Object uaHash = r.getSession().getAttribute("userAgentHash");
				if (uaHash != null && STATIC.md5(userAgent).equals((String) uaHash))
				{
					return true;
				}
			}
			String username = WEB.getCookie("username", r);
			String user_id = WEB.getCookie("user_id", r);
			String secureKey = STATIC.md5(userAgent + username + user_id);
			if (secureKey.equals(WEB.getCookie("secure_key", r)))
			{
				r.getSession(true).setAttribute("isLoggedIn", true);
				r.getSession(true).setAttribute("userAgentHash", STATIC.md5(userAgent));
				return true;
			}
		}
		catch (Exception ex)
		{
			System.err.println(ex);
		}
		return false;
	}

	/**
	 * Returns true if logged out okay.
	 *
	 * @param r
	 * @param response
	 * @return
	 */
	public static boolean logout(HttpServletRequest r, HttpServletResponse response)
	{

		// We need all the required inputs to work properly.
		if (r == null)
		{
			return false;
		}

		r.getSession(true).setAttribute("isLoggedIn", null);
		r.getSession(true).setAttribute("userAgentHash", null);
		r.getSession(true).setAttribute("user_id", null);
		r.getSession(true).invalidate();

		response.addCookie(new Cookie("username", null));
		response.addCookie(new Cookie("user_id", null));
		response.addCookie(new Cookie("secure_key", null));
		return true;
	}

	/**
	 * Returns true if user+password match a user+password from the DB.
	 *
	 * @param username
	 * @param password
	 * @param r
	 * @param response used for setting cookies
	 * @return
	 */
	public static boolean login(String username, String password, HttpServletRequest r, HttpServletResponse response)
	{

		// We need all the required inputs to work properly.
		if (password == null || password.length() == 0 || username == null || username.length() == 0 || r == null)
		{
			return false;
		}
		UserManager us = new UserManager();
		ShopUser su = us.getShopUserByUsername(username);
		if (su != null)
		{
			if (su.generatePasswordHash(password).equals(su.getPasswordHash()))
			{

				String userAgent = r.getHeader("User-Agent");
				r.getSession(true).setAttribute("isLoggedIn", true);
				r.getSession(true).setAttribute("user_id", "" + su.getUserId());
				r.getSession(true).setAttribute("userAgentHash", STATIC.md5(userAgent));

				response.addCookie(new Cookie("username", su.getUsername()));
				response.addCookie(new Cookie("user_id", "" + su.getUserId()));
				response.addCookie(new Cookie("secure_key", STATIC.md5(userAgent + su.getUsername() + su.getUserId())));
				return true;
			}
		}
		return false;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="UTILITY">
	/**
	 * Returns the GET or POST or PUT or DELETE value that corresponds to the
	 * key given. If it would normally return null, itwill instead return "" an
	 * empty string.
	 */
	public static String getParam(String p_key, HttpServletRequest request)
	{
		String s = request.getParameter(p_key);
		if (s == null)
		{
			return "";
		}
		else
		{
			return s;
		}
	}

	/**
	 * Returns the cookie String value by the key specified, or an empty string.
	 */
	public static String getCookie(String p_key, HttpServletRequest request)
	{
		Cookie[] cookies = request.getCookies();
		for (Cookie cook : cookies)
		{
			if (cook.getName().equals(p_key))
			{
				return cook.getValue();
			}
		}
		return null;
	}

	/**
	 * Encodes the input so that XSS is not possible. Also makes sure people
	 * cannot execute random scripts.
	 *
	 * @param input
	 * @return
	 */
	public static String htmlEncode(String input)
	{
		return Encode.forHtml(input);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="getCurrentUserFromDB">
	/**
	 * Returns null if not found. Works based on the session attribute for the
	 * logged in user. Makes a database call every time. This only works if the
	 * user is already logged in. In the event a user is not logged in, null
	 * will be returned. (So no need to use isLoggedIn before calling this)
	 *
	 * @param request
	 * @return
	 */
	public static ShopUser getCurrentUserFromDB(HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if (session != null)
		{
			Object userIdObj = session.getAttribute("user_id");
			if (userIdObj != null)
			{
				UserManager um = new UserManager();
				int userId = -1;
				try
				{
					String userIdStr = (String) userIdObj;
					ShopUser su = um.getShopUserByUserId(Integer.parseInt(userIdStr));
					return su;
				}
				catch (NumberFormatException nfe)
				{
				}
			}
		}
		return null;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="getCurrentUser">
	/**
	 * Sets the current user via Cookies, does not return anything
	 *
	 * @param user
	 * @param request
	 */
	public static void getCurrentUser(ShopUser user, HttpServletRequest request)
	{
		boolean foundUserId = false, foundUsername = false;
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		if (cookies != null)
		{
			for (int i = 0; i < cookies.length; i++)
			{
				cookie = cookies[i];
				if (cookie.getName().equals("user_id"))
				{
					int userId = Integer.parseInt(cookie.getValue());
					user.setUserId(userId);
					foundUserId = true;
				}
				if (cookie.getName().equals("username"))
				{
					user.setUsername(cookie.getValue());
					foundUsername = true;
				}
				if (foundUserId && foundUsername)
				{
					break;
				}
			}
		}
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="isAdmin">
	public static boolean isAdmin(HttpServletRequest request)
	{
		UserManager um = new UserManager();
		ShopUser temp = new ShopUser();
		ShopUser su = null;
		getCurrentUser(temp, request);
		su = um.getShopUserByUserId(temp.getUserId());
		return su.isAdmin();
	}
	//</editor-fold>

	public static String printHeaderHtml()
	{
		String s = "";
		s += "<meta charset='utf-8'>";
		s += "<meta http-equiv='X-UA-Compatible' content='IE=edge'>";
		s += "<meta name='viewport' content='width=device-width, initial-scale=1'>";
		s += "<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>";
		s += "<link rel='stylesheet' href='./assets/css/bootstrap.min.css' />";
		s += "<link rel='stylesheet' href='./assets/css/bootstrap-theme.paper.css' />";
		s += "<link rel='stylesheet' href='./assets/css/style.css' />";
		s += "<script src='./assets/js/jquery-1-2.min.js' ></script>";
		s += "<script src='./assets/js/bootstrap.min.js' ></script>";
		s += "<script src='./assets/js/holder.js' ></script>";
		s += "<script src='./assets/js/LinkedList.js' ></script>";
		s += "<title>Minecraft Shop</title>";
		return s;
	}

	public static String printNavbarHtml(HttpServletRequest request)
	{
		String s = "";
		s += "<nav class='navbar navbar-default navbar-static-top'>";
		s += "<div class='container-fluid'>";
		s += "<div class='navbar-header'>";
		s += "<button type='button' class='navbar-toggle collapsed' data-toggle='collapse' data-target='#bs-example-navbar-collapse-1'>";
		s += "<span class='sr-only'>Toggle navigation</span>";
		s += "<span class='icon-bar'></span>";
		s += "<span class='icon-bar'></span>";
		s += "<span class='icon-bar'></span>";
		s += "</button>";
		s += "<a class='navbar-brand' href='./shop.jsp'>Minecraft Shop</a>";
		s += "</div>"; // end the navbar-header...
		s += "<div class='collapse navbar-collapse' id='bs-example-navbar-collapse-1'>";
		s += "<ul class='nav navbar-nav'>";
		ShopUser su = WEB.getCurrentUserFromDB(request);
		String welcomeMessage = "Welcome!";
		if (su != null){ welcomeMessage = "Welcome, "+su.getDisplayName();}
		if (su != null)
		{
			s += "<li role='presentation'>Tokens: " + su.getTokens() + "</li>";
			s += "<li role='presentation'><a href='./transaction.jsp'>Transaction</a></li>";
			s += "<li role='presentation'><a href='./transactionHistory.jsp'>Transaction History</a></li>";
			s += "<li role='presentation'><a href='./editAccount.jsp'>My Account</a></li>";
		}
		s += "</ul>";
		s += "<ul class='nav navbar-nav navbar-right'>";
		s += "<li class='dropdown'>";
		s += "<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-expanded='false'>"+welcomeMessage+"<span class='caret'></span></a>";
		s += "<ul class='dropdown-menu' role='menu'>";
		if (su != null)
		{
			s += "<li role='presentation'><a href='logout.jsp?returnURL=./index.jsp'>Logout</a></li>";
			if (su.isAdmin())
			{
				s += "<li role='presentation'><a href='admin.jsp'>Admin Page</a>";
			}
		}
		else
		{
			s += "<li role='presentation'><a href='register.jsp?returnURL=./index.jsp'>Register</a></li>";
			s += "<li role='presentation'><a href='login.jsp?returnURL=./index.jsp'>Login</a></li>";
		}
		s += "</ul>";
		s += "</ul>";
		s += "</li>";
		s += "</ul>";
		s += "</div><!-- /.navbar-collapse -->";
		s += "</div><!-- /.container-fluid -->";
		s += "</nav>";
		return s;
	}
}
