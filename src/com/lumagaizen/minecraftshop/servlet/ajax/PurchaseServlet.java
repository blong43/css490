package com.lumagaizen.minecraftshop.servlet.ajax;
import com.lumagaizen.minecraftshop.SQL;
import com.lumagaizen.minecraftshop.STRINGS;
import com.lumagaizen.minecraftshop.WEB;
import com.lumagaizen.minecraftshop.model.ProductTransaction;
import com.lumagaizen.minecraftshop.model.ShopUser;
import com.lumagaizen.minecraftshop.model.Product;
import com.lumagaizen.minecraftshop.model.Category;
import com.lumagaizen.minecraftshop.model.JsonErrorResponse;
import com.lumagaizen.minecraftshop.model.JsonResponse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lumagaizen.minecraftshop.manager.UserManager;
import com.lumagaizen.minecraftshop.manager.ProductManager;
import com.lumagaizen.minecraftshop.manager.TransactionManager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PurchaseServlet
 */
@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Takes care of CRUD functionality for the ProductTransaction class for when a
	 * customer adds an item, updates quantity, delete, and purchases a product.
	 * For quantity the user needs to input a number above 0
	 * For insert, ProductTransaction needs a userId, productId, action, tokenChange, amount, and inCart variable
	 * Needs more error checking done...
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String returnURL = request.getParameter("returnURL");
		String action = request.getParameter("action");
		ShopUser su = WEB.getCurrentUserFromDB(request);
		UserManager um = new UserManager();
		TransactionManager tm = new TransactionManager();
		ProductTransaction pt = new ProductTransaction();
		JsonResponse json = new JsonResponse();
		try
		{
			int transId = Integer.parseInt(request.getParameter("trans_id"));
			pt = tm.getProductTransactionByTransactionId(transId);
			
			if (action.equals("Update"))
			{
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				if (quantity > 0) 
				{
					pt.setQuantity(quantity);
					json.setSuccess(tm.updateProductTransactionByTransactionId(pt, transId));
					json.setPayload("Updated");
				}
			}
			else if (action.equals("Delete"))
			{
				json.setSuccess(tm.deleteProductTransactionByTransactionId(transId));
				json.setPayload("Deleted");
			}
			else if (action.equals("Purchase"))
			{
				int token = su.getTokens();
				su.setTokens( (token-pt.getTokenChange()) );
				json.setSuccess(um.updateShopUserByUserId(su, su.getUserId()));
				pt.setInCart(false);
				json.setSuccess(tm.updateProductTransactionByTransactionId(pt, transId));
				json.setPayload("Purchased");
			}	
		}
		catch (NumberFormatException ex)
		{
			json = new JsonErrorResponse("Need more information!");
			Logger.getLogger(ProductLikes.class.getName()).log(Level.SEVERE, null, ex);
		}
		response.sendRedirect(returnURL);
		
	}
}
