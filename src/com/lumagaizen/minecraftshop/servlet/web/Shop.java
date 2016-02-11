/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumagaizen.minecraftshop.servlet.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Taylor
 */
public class Shop extends HttpServlet
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
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			
			out.println("<head>");
			out.println("<meta charset='utf-8'>");
			out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
			out.println("<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>");
			out.println("<link rel='stylesheet' href='./assets/css/bootstrap.min.css' />");
			out.println("<link rel='stylesheet' href='./assets/css/bootstrap-theme.paper.css' />");
			out.println("<link rel='stylesheet' href='./assets/css/style.css' />");
			out.println("<script src='./assets/js/jquery-1-2.min.js' ></script>");
			out.println("<script src='./assets/js/bootstrap.min.js' ></script>");
			out.println("<script src='./assets/js/holder.js' ></script>");
			out.println("<script src='./assets/js/LinkedList.js' ></script>");
			out.println("<script src=\"./assets/js/Shop.js\"></script>");
			out.println("<title>Minecraft Shop</title>");
			out.println("</head>");
			
			out.println("<body>");
			out.println("</body>");
			
			/*
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
				Loading...
				<%
						ProductManager pm = new ProductManager();
						for(Product p : pm.getAllProducts()){
							printShopProduct(p, request, out);
						}
						%>
				
			</div>
		</div>
	</body>
</html>*/
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
		processRequest(request, response);
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
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo()
	{
		return "Short description";
	}// </editor-fold>

}
