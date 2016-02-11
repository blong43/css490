/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumagaizen.minecraftshop;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL
{

	private final String username;
	private final String password;
	private final String database;
	private final int port;
	private final String host;
	private final LinkedList<Connection> connectionPool;
	
	private static SQL self = null;
	
	//<editor-fold defaultstate="collapsed" desc="Table name variables">
	private static String TABLE_PREFIX = "woolcity";
	public static String TABLE_USERS(){ return TABLE_PREFIX + "_users"; }
	public static String TABLE_USER_ITEMS(){ return TABLE_PREFIX + "_user_items"; }
	public static String TABLE_CATEGORIES(){ return TABLE_PREFIX + "_categories"; }
	public static String TABLE_DESIGN_META(){ return TABLE_PREFIX + "_design_meta"; }
	public static String TABLE_DISCOUNTS(){ return TABLE_PREFIX + "_discounts"; }
	public static String TABLE_DISCOUNT_PRODUCT_BRIDGE(){ return TABLE_PREFIX + "_discount_product_bridge"; }
	public static String TABLE_PRODUCTS(){ return TABLE_PREFIX + "_products"; }
	public static String TABLE_MONEY_PACKS(){ return TABLE_PREFIX + "_money_packs"; }
	public static String TABLE_PRODUCT_LIKES(){ return TABLE_PREFIX + "_product_likes"; }
	/** When a user purchases a product with tokens. **/
	public static String TABLE_PRODUCT_TRANSACTIONS(){ return TABLE_PREFIX + "_product_transactions"; }
	/** When a user pays another user. Or a server pays a user. **/
	public static String TABLE_TRANSFER_TRANSACTIONS(){ return TABLE_PREFIX + "_transfer_transactions"; }
	/** When a user purchases tokens with IRL money. **/
	public static String TABLE_MONEY_TRANSACTIONS(){ return TABLE_PREFIX + "_money_transactions"; }
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="Constructor">
	public SQL(String p_Host, int p_Port, String p_Username, String p_Password, String p_Database)
	{
		this.host = p_Host;
		this.port = p_Port;
		this.username = p_Username;
		this.password = p_Password;
		this.database = p_Database;
		this.connectionPool = new LinkedList<Connection>();
		
		try
		{
			DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} 
		catch (Exception ex)
		{
		}
	}
	//</editor-fold>
	
	//<editor-fold defaultstate="collapsed" desc="SQL core methods">
	/** 
	 * Returns an instance of SQL. Handles all configs needed for this operation.
	 * Only creates new SQL if SQL is null. This will NEVER return null. It will
	 * throw an exception before returning null.
	 * @return 
	 * @deprecated You probably don't need this anyways.
	 */
	public synchronized static SQL getInstance(){
		if (SQL.self == null){
			SQL.self = new SQL("wc.lumengaming.com",3306,"css490","uwb.edu","minecraft_shop");
//			SQL.self = new SQL("127.0.0.1",3306,"css490","uwb.edu","minecraft_shop");
//			SQL.self = new SQL("sql3.freemysqlhosting.net",3306,"sql368673","jG7*vR7!","sql368673");
		}
		return SQL.self;
	}
	/**
	 * Returns a SQL Connection object for use with queries. Removes any dead
	 * connections and creates new connections as needed. Don't forget to call
	 * returnConnection when finished! Don't bother trying to check if the 
	 * return value is null. It shouldn't ever return null, and if it DOES
	 * return null, that means your database is down, and the entire app should
	 * fail anyways.
	 * @return 
	 */
	public synchronized static Connection getConnection(){
		return SQL.getInstance().getCon();
	}
	
	/**
	 * Returns the connection back to the list of available connections for use.
	 * @param conn 
	 */
	public synchronized static void returnConnection(Connection conn){
		SQL.getInstance().returnCon(conn);
	}
	/**
	 * Returns a SQL Connection object for use with queries. Removes any dead
	 * connections and creates new connections as needed. Don't forget to call
	 * returnConnection when finished!
	 * @return 
	 * @deprecated Why not just use the static accessor method?
	 */
	public Connection getCon()
	{
		try{
			Connection output = null;
			// Cycle through until you find a working connection, or you run
			// out of connection objects. 
			int maxRetries = 3;
			while (!connectionPool.isEmpty() && 0 <-- maxRetries){
				Connection conn = connectionPool.removeFirst();
				if (conn.isValid(1)){
					output = conn;
					break;
				}
			}
			
			// If there are no good connections, return a NEW one.
			if (connectionPool.isEmpty()){
				// Create new connection
				output = DriverManager.getConnection("jdbc:mysql://" + host + ":" + this.port + "/" + this.database, this.username, this.password);
			}
			
			return output;
		}catch(SQLException ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	
	/**
	 * Returns the connection back to the list of available connections for use.
	 * @param conn 
	 * @deprecated Why not just use the static method?
	 */
	public void returnCon(Connection conn){
		if (conn != null){
			connectionPool.addLast(conn);
		}
	}
	//</editor-fold>
	
	
	//<editor-fold defaultstate="collapsed" desc="SQL convenience methods">
	/**
	 * An update query is constructed using the input values.
	 * Query is formed in this format... 
	 * @param tableName `x_users`
	 * @param toUpdate [`col` maps to val] 
	 * @return 
	 */
	public static String generateUpdateQueryString(String tableName, HashMap<String,Object> toUpdate, String whereStr){
		// Create the query
		String q = "UPDATE `"+tableName+"` SET ";

		// Add the columns
		boolean isFirstValue = true;
		for(String colName : toUpdate.keySet()){
			if (isFirstValue){
				isFirstValue = false;
				q += " `"+colName+"` = ? ";
			}else{
				q += ", `"+colName+"` = ? ";
			}
		}
		return q;
	}
	
	
	public void prepare(PreparedStatement statement, Object[] params) throws SQLException {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof String) {
					statement.setString(i + 1, (String) params[i]);
				} else if (params[i] instanceof Long) {
					statement.setLong(i + 1, (long) params[i]);
				} else if (params[i] instanceof Integer) {
					statement.setInt(i + 1, (Integer) params[i]);
				} else if (params[i] instanceof Boolean) {
					statement.setBoolean(i + 1, (boolean) params[i]);
				} else if (params[i] instanceof Timestamp) {
					statement.setTimestamp(i + 1, (Timestamp) params[i]);
				} else {
					statement.setObject(i + 1, params[i]);
				}
			}
		}
    }
	//</editor-fold>
}
