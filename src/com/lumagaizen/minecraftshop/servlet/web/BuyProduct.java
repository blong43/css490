package com.lumagaizen.minecraftshop.servlet.web;

import com.lumagaizen.minecraftshop.STRINGS;
import com.lumagaizen.minecraftshop.WEB;
import com.lumagaizen.minecraftshop.manager.ProductManager;
import com.lumagaizen.minecraftshop.manager.TransactionManager;
import com.lumagaizen.minecraftshop.model.Product;
import com.lumagaizen.minecraftshop.model.ProductTransaction;
import com.lumagaizen.minecraftshop.model.ShopUser;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Taylor
 */
public class BuyProduct extends HttpServlet
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
		String productIdStr = WEB.getParam("productId", request);
		Integer productId = null;
		try{
			productId = Integer.parseInt(productIdStr);
		}catch(Exception ex){
			response.sendError(404, STRINGS.ERROR_PRODUCT_NOT_FOUND);
			return;
		}
		ProductManager pm = new ProductManager();
		Product p = pm.getProductById(productId);
		if (p == null){
			response.sendError(404, STRINGS.ERROR_PRODUCT_NOT_FOUND);
			return;
		}
		ShopUser su = WEB.getCurrentUserFromDB(request);
		if (su == null){
			response.sendRedirect("./login.jsp");
			return;
		}
		try (PrintWriter out = response.getWriter())
		{
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println(WEB.printHeaderHtml());
			out.println("</head>");
			out.println("<body>");
			out.println("<div class='panel panel-primary'>");
			out.println(WEB.printNavbarHtml(request));
			if (su.getTokens() >= p.getPriceTokens()){
				TransactionManager tm = new TransactionManager();
				ProductTransaction trans = new ProductTransaction();
				trans.setAction("purchased");
				trans.setTokenChange(p.getPriceTokens());
				trans.setProductId(p.getProductId());
				trans.setTime(new Timestamp(System.currentTimeMillis()));
				trans.setUserId(su.getUserId());
				trans.setInCart(true);
				trans.setQuantity(1);
				boolean success = tm.insertProductTransaction(trans);
				if (success) {out.println("Added to pending transactions"+p.getProductWebsiteName());}
				else{ out.println("Failed to purchase "+p.getProductWebsiteName());}
			}else{
				out.println("Not enough tokens to complete this purchase.<br/>");
				out.println(p.getPriceTokens()+" tokens are required.<br/>");
				out.println("You have "+su.getTokens()+".<br/>");
			}
			out.println("</div>");// end panel
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
