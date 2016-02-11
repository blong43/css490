/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumagaizen.minecraftshop.servlet.ajax;

import com.lumagaizen.minecraftshop.WEB;
import com.lumagaizen.minecraftshop.manager.ProductManager;
import com.lumagaizen.minecraftshop.model.JsonResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Taylor
 */
public class Products extends HttpServlet
{
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
		JsonResponse json = new JsonResponse();
		json.setSuccess(true);
		ProductManager pm = new ProductManager();
		try{
			int limit = Integer.MAX_VALUE;
			try{ limit = Integer.parseInt(WEB.getParam("limit", request)); }catch(NumberFormatException nfe){}
			
			String method = WEB.getParam("method", request);
			String catIdStr = WEB.getParam("categoryId", request);
			String productIdStr = WEB.getParam("productId", request);
			String searchText = WEB.getParam("searchText", request);
			String sortStr = WEB.getParam("sort", request);
			boolean isLimitedEdition = "true".equalsIgnoreCase(WEB.getParam("isLimitedEdition", request));
			boolean isDiscounted = "true".equalsIgnoreCase(WEB.getParam("isDiscounted", request));
			Integer catId = null; try{ catId = Integer.parseInt(catIdStr); } catch (NumberFormatException nfe){}
			Integer productId = null; try{ productId = Integer.parseInt(productIdStr); } catch (NumberFormatException nfe){}
			
			switch(method){
				case "getProductsByCategoryId":
					json.setPayload(pm.getProductsByCategoryId(catId));
					break;
				case "getProductById":
					json.setPayload(pm.getProductById(productId));
					break;
				case "getProductsBySearch":
					json.setPayload(pm.getProductsBySearch(searchText,catId, isLimitedEdition, isDiscounted, sortStr,limit));
					break;
				case "getAllProducts":
					json.setPayload(pm.getAllProducts());
					break;
				default:
					//response.sendError(400,"Invalid method parameter. Specify a valid param, and consider checking the documentation.");
					json.setSuccess(false);
					json.setMessage("Invalid method parameter(s)");
					break;
			}
		}catch(NumberFormatException nfe){
			json.setMessage(nfe.getMessage());
			json.setSuccess(false);
		}
		response.getWriter().println(json.toJson());
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
		response.setContentType("application/json;charset=UTF-8");
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo()
	{
		return "C.R.U.D. Products";
	}// </editor-fold>
}
