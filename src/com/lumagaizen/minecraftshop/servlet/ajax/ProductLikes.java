package com.lumagaizen.minecraftshop.servlet.ajax;

import com.lumagaizen.minecraftshop.SQL;
import com.lumagaizen.minecraftshop.STRINGS;
import com.lumagaizen.minecraftshop.WEB;
import com.lumagaizen.minecraftshop.model.JsonErrorResponse;
import com.lumagaizen.minecraftshop.model.JsonResponse;
import com.lumagaizen.minecraftshop.model.ShopUser;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/PurchaseServlet")
public class ProductLikes extends HttpServlet
{

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter())
		{
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet ProductLikeServlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet ProductLikeServlet at " + request.getContextPath() + "</h1>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
//		processRequest(request, response);
		doPost(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doPost(request, response);
	}
	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String productId = WEB.getParam("product_id", request);
		String userId = WEB.getCookie("user_id", request);
		ShopUser su = new ShopUser();
		WEB.getCurrentUser(su, request);
		
		if (WEB.isLoggedIn(request) && !userId.isEmpty() && userId.equals(""+su.getUserId())){
			JsonResponse json = new JsonResponse();
			Connection conn = SQL.getConnection();
			try
			{
				PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) as c FROM "+SQL.TABLE_PRODUCT_LIKES() + " WHERE `product_id` = ? AND `user_id` = ?");
				ps.setInt(1, Integer.parseInt(productId));
				ps.setInt(2, Integer.parseInt(userId));
				ResultSet rs = ps.executeQuery();
				if (rs.next()){
					int numRows = rs.getInt("c");
					if (numRows == 0){
						PreparedStatement insert = conn.prepareStatement("INSERT INTO `"+SQL.TABLE_PRODUCT_LIKES()+"` (`product_id`, `user_id`) VALUES (?, ?);");
						insert.setInt(1, Integer.parseInt(productId));
						insert.setInt(2, Integer.parseInt(userId));
						int numRowsAffected = insert.executeUpdate();
						json.setSuccess(numRowsAffected > 0);
						json.setPayload("ADD");
					}else{
						PreparedStatement remove = conn.prepareStatement("DELETE FROM `"+SQL.TABLE_PRODUCT_LIKES()+"` WHERE `product_id` = ? AND `user_id` = ?");
						remove.setInt(1, Integer.parseInt(productId));
						remove.setInt(2, Integer.parseInt(userId));
						int numRowsAffected = remove.executeUpdate();
						json.setSuccess(numRowsAffected > 0);
						json.setPayload("REMOVE");
					}
				}else{
					json.setSuccess(false);
					json.setMessage("Could not get likes count for product/user combo. This shouldn't ever happen.");
				}
			}
			catch (NumberFormatException ex)
			{
				json = new JsonErrorResponse("Expected values for both 'user_id' and 'product_id'. ");
				Logger.getLogger(ProductLikes.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (SQLException ex)
			{
				json = new JsonErrorResponse("SQL Error occurred.");
				Logger.getLogger(ProductLikes.class.getName()).log(Level.SEVERE, null, ex);
			}
			SQL.returnConnection(conn);
			response.getWriter().println(json.toJson());
		}else{
			JsonResponse json = new JsonErrorResponse(STRINGS.ERROR_NOT_LOGGED_IN);
			response.setContentType("application/json");
			response.getWriter().println(json.toJson());
		}
	}
	public void printHelp(){
		
	}
	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo()
	{
		return "Handles C.R.U.D. for product likes.";
	}
	// </editor-fold>

}
