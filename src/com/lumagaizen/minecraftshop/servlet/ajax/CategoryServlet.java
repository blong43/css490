/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumagaizen.minecraftshop.servlet.ajax;

import com.google.gson.Gson;
import com.lumagaizen.minecraftshop.manager.ProductManager;
import com.lumagaizen.minecraftshop.model.Category;
import com.lumagaizen.minecraftshop.model.JsonResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Taylor
 */
public class CategoryServlet extends HttpServlet
{
	private final ProductManager productManager;
	public CategoryServlet(){
		this.productManager = new ProductManager();
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
		response.setContentType("text/json;charset=UTF-8");
		try (PrintWriter out = response.getWriter())
		{
			JsonResponse json = new JsonResponse();
			ArrayList<Category> allCategories = this.productManager.getAllCategories();
			json.setSuccess(true);
			json.setPayload(allCategories);
			com.google.gson.Gson gson = new Gson();
			String jsonStr = gson.toJson(json);
			out.println(jsonStr);
		}
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
