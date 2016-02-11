/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumagaizen.minecraftshop.manager;

import com.lumagaizen.minecraftshop.SQL;
import com.lumagaizen.minecraftshop.model.Category;
import com.lumagaizen.minecraftshop.model.DesignMeta;
import com.lumagaizen.minecraftshop.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Taylor
 */
public class ProductManager
{
	private final String selProducts = "SELECT  * FROM `"+SQL.TABLE_PRODUCTS()+"` p INNER JOIN 	`"+SQL.TABLE_DESIGN_META()+"` dm ON dm.design_meta_id = p.design_meta_id";
	private final String selCategories = "SELECT  * FROM `"+SQL.TABLE_CATEGORIES()+"` c INNER JOIN 	`"+SQL.TABLE_DESIGN_META()+"` dm ON dm.design_meta_id = c.design_meta_id";
	
	//<editor-fold defaultstate="collapsed" desc="getCategoryById">
	public Category getCategoryById(int categoryId){
		String q = selCategories + " WHERE `category_id` = "+categoryId;
		Category c = null;
		Connection conn = SQL.getConnection();
		try
		{
			PreparedStatement prepareStatement = conn.prepareStatement(q);
			ResultSet rs = prepareStatement.executeQuery();
			if (rs.next()){
				c = readCategory(rs);
			}
		}
		catch (SQLException ex)
		{
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return c;
	}
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="getCategoryList">
	/**
	 * Returns a list of all categories that are specified with a row
	 * in th SQL table. This probably will not include any "categories"
	 * that have been added such as "hot", "on sale", or "expiring soon".
	 * @return 
	 */
	public ArrayList<Category> getAllCategories(){
		ArrayList<Category> cats = new ArrayList<>();
		String q = selCategories;
		Connection conn = SQL.getConnection();
		try
		{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(q);
			while(rs.next()){
				Category cat = readCategory(rs);
				if (cat != null){
					cats.add(cat);
				}
			}
		}
		catch (SQLException ex)
		{
			Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (Exception ex)
		{
			Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return cats;
	}
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="getAll">
	public ArrayList<Product> getAllProducts(){
		ArrayList<Product> cats = new ArrayList<>();
		String q = selProducts;
		Connection conn = SQL.getConnection();
		try
		{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(q);
			while(rs.next()){
				Product cat = readProduct(rs);
				if (cat != null){
					cats.add(cat);
				}
			}
		}
		catch (SQLException ex)
		{
			Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return cats;
	}
	
	public ArrayList<Product> getProductsByCategoryId(int categoryId){
		ArrayList<Product> cats = new ArrayList<>();
		String q = selProducts + " WHERE `category_id` = "+categoryId;
		Connection conn = SQL.getConnection();
		try
		{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(q);
			while(rs.next()){
				Product cat = readProduct(rs);
				if (cat != null){
					cats.add(cat);
				}
			}
		}
		catch (SQLException ex)
		{
			Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return cats;
	}
	
	//<editor-fold defaultstate="collapsed" desc="getProductsBySearch">
	/**
	 * 
	 * @param searchText			Can be null or empty if you do not want to include this search.
	 * 
	 * @param categoryId			the int ID of the category. Make it null if 
	 *								you do not want to filter by categories.
	 * 
	 * @param isLimitedEditionOnly	True if you only want items that are limited 
	 *								edition.
	 * @param isSaleItemsOnly		True if you only want items that are on sale.
	 * 
	 * @param p_orderByStr  MUST be null or guarded against. This will 
	 *								not be parameterized in the query so SQL 
	 *								injection MIGHT Occur if not careful.
	 * @return 
	 */
	public ArrayList<Product> getProductsBySearch(String searchText,Integer categoryId, boolean isLimitedEditionOnly, boolean isSaleItemsOnly, String p_orderByStr, int limit){
		String orderByStr = "";
		switch(p_orderByStr.toUpperCase()){
			case "NEW":
				orderByStr = " ORDER BY `product_id` DESC";break;
			case "LIKED":
				orderByStr = " ORDER BY (SELECT COUNT(*) FROM `woolcity_product_likes` t WHERE p.product_id = t.product_id) DESC";break;
			case "POPULAR":
				orderByStr = " ORDER BY (SELECT COUNT(*) FROM `woolcity_product_transactions` t WHERE p.product_id = t.product_id) DESC";break;
			case "PRICE_LOW":
				orderByStr = " ORDER BY `price_tokens` ASC";break;
			case "PRICE_HIGH":
				orderByStr = " ORDER BY `price_tokens` DESC";break;
			default:
				orderByStr = "";
		}
		
		ArrayList<Product> output = new ArrayList<Product>();
		Connection conn = SQL.getConnection();
		String q = selProducts;
		ArrayList<String> filters = new ArrayList<>();
		if (categoryId != null){
			filters.add("`category_id` = "+categoryId);
		}
		
		if (isLimitedEditionOnly){
			filters.add("`is_limited_edition` = 1");
		}
		if (isSaleItemsOnly){
				//throw new UnsupportedOperationException("Not supported yet.");
		}
		
		boolean isTextSet = (searchText != null && !"".equals(searchText));
		if (isTextSet){
			filters.add("`product_website_name` LIKE ?");
		}
		
		boolean isFirstFilter = true;
		for(String s : filters){
			if (isFirstFilter){
				q += " WHERE ";
				isFirstFilter = false;
			}else{
				q += " AND ";
			}
			q += s;
		}
		
		
		if (orderByStr != null && !"".equals(orderByStr)){
			q += " " + orderByStr;
		}
		q += " LIMIT "+limit;
		try
		{
			PreparedStatement ps = conn.prepareStatement(q);
			if (isTextSet){ 
				ps.setString(1, "%" + searchText + "%");
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				Product p = readProduct(rs);
				if (p != null){
					output.add(p);
				}
			}
		}
		catch (SQLException ex)
		{
			Logger.getLogger(ProductManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return output;
	}
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="get Product by ID">
	public Product getProductById(int productId)
	{
		String q = selProducts + " WHERE `product_id` = "+productId;
		Product p = null;
		Connection conn = SQL.getConnection();
		try
		{
			PreparedStatement prepareStatement = conn.prepareStatement(q);
			ResultSet rs = prepareStatement.executeQuery();
			if (rs.next()){
				return readProduct(rs);
			}
		}
		catch (SQLException ex)
		{
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return p;
	}
//</editor-fold>
	
	
	//<editor-fold defaultstate="collapsed" desc="Read">
	/**
	 * Expects that the result set has been primed with rs.next() before this
	 * method is called. This method then reads in all the values of the RS
	 * object to a new ShopUser object. Returns null on failure.
	 *
	 * @param rs
	 * @return
	 */
	protected DesignMeta readDesignMeta(ResultSet rs) {
		try {
			DesignMeta meta = new DesignMeta();
			meta.setDataValue(rs.getByte("data_value"));
			meta.setDesignMetaId(rs.getInt("design_meta_id"));
			meta.setImageUrl(rs.getString("image_url"));
			meta.setIsEnchanted(rs.getInt("is_enchanted") == 1);
			meta.setMaterial(rs.getString("material"));
			return meta;
		} catch (SQLException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	
	/**
	 * Expects that the result set has been primed with rs.next() before this
	 * method is called. This method then reads in all the values of the RS
	 * object to a new ShopUser object. Returns null on failure.
	 *
	 * @param rs
	 * @return
	 */
	private Category readCategory(ResultSet rs)
	{
		try {
			Category c = new Category();
			c.setCategoryId(rs.getInt("category_id"));
			c.setCategoryMinecraftName(rs.getString("category_minecraft_name"));
			c.setCategoryWebsiteName(rs.getString("category_website_name"));
			DesignMeta meta = readDesignMeta(rs);
			c.setDesignMeta(meta);
			return c;
		} catch (SQLException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	
	/**
	 * Expects that the result set has been primed with rs.next() before this
	 * method is called. This method then reads in all the values of the RS
	 * object to a new ShopUser object. Returns null on failure.
	 *
	 * @param rs
	 * @return
	 */
	protected Product readProduct(ResultSet rs) {
		try {
			// missing category and server ID infos
			Product pr = new Product();
			pr.setDesignMeta(readDesignMeta(rs));
			pr.setProductId(rs.getInt("product_id"));
			pr.setProductMinecraftName(rs.getString("product_minecraft_name"));
			pr.setProductWebsiteName(rs.getString("product_website_name"));
			pr.setPriceTokens(rs.getInt("price_tokens"));
			pr.setIsActive(rs.getInt("is_active") == 1);
			pr.setDescription(rs.getString("description"));
			pr.setIsLimitedEdition(rs.getInt("is_limited_edition") == 1);
			pr.setAvailableUntilTime(rs.getTimestamp("available_until_time"));
			pr.setIsLimitedUses(rs.getInt("is_limited_uses") == 1);
			pr.setNumUses(rs.getInt("num_uses"));
			pr.setIsAbleToExpire(rs.getInt("is_able_to_expire") == 1);
			pr.setTimeBeforeExpiringMs(rs.getLong("time_before_expiring_ms"));
			pr.setCommandsOnExpire(rs.getString("commands_on_expire"));
			pr.setCommandsOnUse(rs.getString("commands_on_use"));
			return pr;
		} catch (SQLException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	//</editor-fold>
	
}
