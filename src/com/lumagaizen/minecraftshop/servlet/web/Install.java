package com.lumagaizen.minecraftshop.servlet.web;

import com.lumagaizen.minecraftshop.SQL;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Taylor
 */
public class Install extends HttpServlet
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
			out.println("<title>Servlet Install</title>");			
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Install at " + request.getContextPath() + "</h1>");
			out.println("</body>");
			out.println("</html>");
			installTables();
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

	private void installTables()
	{
		Connection conn = SQL.getConnection();
		ArrayList<String> queriesToRun = new ArrayList<>();
		
		//<editor-fold defaultstate="collapsed" desc="Queries to run">
		//<editor-fold defaultstate="collapsed" desc="Categories">
		queriesToRun.add("CREATE TABLE `woolcity_categories` (\n" +
"	`category_id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
"	`category_website_name` VARCHAR(128) NOT NULL DEFAULT 'Uncategorized',\n" +
"	`category_minecraft_name` VARCHAR(128) NOT NULL DEFAULT '&dUncategorized',\n" +
"	`design_meta_id` INT(11) NOT NULL DEFAULT '0',\n" +
"	`category_lore` VARCHAR(512) NOT NULL DEFAULT 'No description available',\n" +
"	PRIMARY KEY (`category_id`)\n" +
")\n" +
"COMMENT='This thing is for the various sub-menus in the shop.'\n" +
"COLLATE='latin1_swedish_ci'\n" +
"ENGINE=InnoDB\n" +
";");
		//</editor-fold>
		
		//<editor-fold defaultstate="collapsed" desc="Design Meta">
		queriesToRun.add("CREATE TABLE `woolcity_design_meta` (\n" +
"	`design_meta_id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
"	`material` VARCHAR(50) NOT NULL DEFAULT 'Barrier',\n" +
"	`data_value` TINYINT(4) NOT NULL DEFAULT '0',\n" +
"	`is_enchanted` TINYINT(4) NOT NULL DEFAULT '0',\n" +
"	`image_url` TEXT NOT NULL,\n" +
"	PRIMARY KEY (`design_meta_id`)\n" +
")\n" +
"COLLATE='latin1_swedish_ci'\n" +
"ENGINE=InnoDB\n" +
";");
		//</editor-fold>
		
		//<editor-fold defaultstate="collapsed" desc="products">
		queriesToRun.add("CREATE TABLE `woolcity_products` (\n" +
"	`product_id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
"	`server_id` INT(11) NOT NULL DEFAULT '0',\n" +
"	`category_id` INT(11) NOT NULL DEFAULT '0',\n" +
"	`product_minecraft_name` VARCHAR(128) NOT NULL DEFAULT 'Untitled Product',\n" +
"	`product_website_name` VARCHAR(128) NOT NULL DEFAULT 'Untitled Product',\n" +
"	`price_tokens` INT(11) NOT NULL,\n" +
"	`is_active` TINYINT(1) NOT NULL DEFAULT '1',\n" +
"	`lore` TEXT NULL,\n" +
"	`is_limited_edition` TINYINT(1) NOT NULL DEFAULT '0',\n" +
"	`available_until_time` TIMESTAMP NULL DEFAULT NULL,\n" +
"	`is_limited_uses` TINYINT(1) NOT NULL DEFAULT '0',\n" +
"	`num_uses` INT(11) NULL DEFAULT NULL,\n" +
"	`is_able_to_expire` TINYINT(1) NOT NULL DEFAULT '0',\n" +
"	`time_before_expiring` BIGINT(20) NULL DEFAULT NULL,\n" +
"	`commands_on_use` VARCHAR(2048) NULL DEFAULT NULL,\n" +
"	`commands_on_expire` VARCHAR(2048) NULL DEFAULT NULL,\n" +
"	PRIMARY KEY (`product_id`)\n" +
")\n" +
"COLLATE='latin1_swedish_ci'\n" +
"ENGINE=InnoDB\n" +
";");
		//</editor-fold>
		
		//<editor-fold defaultstate="collapsed" desc="users">
		queriesToRun.add("CREATE TABLE `woolcity_users` (\n" +
"	`user_id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
"	`username` VARCHAR(32) NOT NULL,\n" +
"	`uuid` VARCHAR(36) NULL DEFAULT NULL,\n" +
"	`ipv4` VARCHAR(64) NULL DEFAULT NULL,\n" +
"	`tokens` INT(11) NOT NULL DEFAULT '0',\n" +
"	`display_name` VARCHAR(48) NULL DEFAULT NULL,\n" +
"	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
"	`first_login` BIGINT(20) NOT NULL DEFAULT '0',\n" +
"	`last_login` BIGINT(20) NOT NULL DEFAULT '0',\n" +
"	`pw_hash` VARCHAR(64) NULL DEFAULT '0',\n" +
"	`salt` VARCHAR(64) NOT NULL DEFAULT 'pepper' COMMENT 'By default, provide a salt based on time of creation',\n" +
"	PRIMARY KEY (`user_id`),\n" +
"	UNIQUE INDEX `username` (`username`),\n" +
"	UNIQUE INDEX `uuid` (`uuid`)\n" +
")\n" +
"COLLATE='latin1_swedish_ci'\n" +
"ENGINE=InnoDB\n" +
"AUTO_INCREMENT=22\n" +
";");
		//</editor-fold>
		
		//<editor-fold defaultstate="collapsed" desc="Likes">
		queriesToRun.add("CREATE TABLE `woolcity_users` (\n" +
"	`user_id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
"	`username` VARCHAR(32) NOT NULL,\n" +
"	`uuid` VARCHAR(36) NULL DEFAULT NULL,\n" +
"	`ipv4` VARCHAR(64) NULL DEFAULT NULL,\n" +
"	`tokens` INT(11) NOT NULL DEFAULT '0',\n" +
"	`display_name` VARCHAR(48) NULL DEFAULT NULL,\n" +
"	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
"	`first_login` BIGINT(20) NOT NULL DEFAULT '0',\n" +
"	`last_login` BIGINT(20) NOT NULL DEFAULT '0',\n" +
"	`pw_hash` VARCHAR(64) NULL DEFAULT '0',\n" +
"	`salt` VARCHAR(64) NOT NULL DEFAULT 'pepper' COMMENT 'By default, provide a salt based on time of creation',\n" +
"	PRIMARY KEY (`user_id`),\n" +
"	UNIQUE INDEX `username` (`username`),\n" +
"	UNIQUE INDEX `uuid` (`uuid`)\n" +
")\n" +
"COLLATE='latin1_swedish_ci'\n" +
"ENGINE=InnoDB\n" +
"AUTO_INCREMENT=22\n" +
";minecraft_shop\n" +
"minecraft_shopminecraft_shop");
		//</editor-fold>
		
		//</editor-fold>
		
		for(String q : queriesToRun){
			try
			{
				Statement stmnt = conn.createStatement();
				stmnt.executeUpdate(q);
			}
			catch (SQLException ex)
			{
				Logger.getLogger(Install.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		SQL.returnConnection(conn);
	}

}
