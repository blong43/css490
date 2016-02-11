package com.lumagaizen.minecraftshop.manager;

import com.lumagaizen.minecraftshop.SQL;
import com.lumagaizen.minecraftshop.model.ProductTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionManager 
{
	private final String selProductTransactions="SELECT * FROM`"+SQL.TABLE_PRODUCT_TRANSACTIONS();
	private final String ptUpdateQStr = "UPDATE `" + SQL.TABLE_PRODUCT_TRANSACTIONS() + "` SET "
			+ "`user_id`=?, `product_id`=?, `action`=?, `token_change`=?, `in_cart`=?, `quantity`=?";
	private final String ptDeleteQStr = "DELETE FROM `" + SQL.TABLE_PRODUCT_TRANSACTIONS();
	
	
// --------------- I N S E R T S ----------------------------------------------	
	public boolean insertProductTransaction(ProductTransaction t)
	{
		String q = "INSERT INTO `" + SQL.TABLE_PRODUCT_TRANSACTIONS() + "` ("
				+ "`user_id`, `product_id`, `action`, `token_change`, `in_cart`, `quantity`) "
				+ "VALUES ( ? , ? , ? , ? , ? , ?); ";
		
		Connection conn = SQL.getConnection();
		try {
			if (conn == null)
			{
				return false;
			}
			PreparedStatement statement = conn.prepareStatement(q);
			int i = 1;
			statement.setInt(i++, t.getUserId());
			statement.setInt(i++, t.getProductId());
			statement.setString(i++, t.getAction());
			statement.setInt(i++, t.getTokenChange());
			statement.setBoolean(i++, t.getInCart());
			statement.setInt(i++, t.getQuantity());
			int numRowsAffected = statement.executeUpdate();
			return numRowsAffected > 0;
		} catch (SQLException ex) {
			Logger.getLogger(TransactionManager.class.getName()).log(Level.SEVERE,null,ex);
		}
		SQL.returnConnection(conn);
		return false;
	}

	
// --------------- G E T S ----------------------------------------------
	
	public ArrayList<ProductTransaction> getAllProductTransactions()
	{
		ArrayList<ProductTransaction> prodTrans = new ArrayList<>();
		String q = selProductTransactions;
		Connection conn = SQL.getConnection();
		try
		{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(q);
			while (rs.next())
			{
				ProductTransaction prodTran = readProductTransaction(rs);
				if ( prodTran != null )
				{
					prodTrans.add(prodTran);
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
		return prodTrans;
	}
	
	public ProductTransaction getProductTransactionByTransactionId(int transactionId) 
	{
		Connection conn = SQL.getConnection();
		ProductTransaction pt = null;
		try {
			String q = selProductTransactions + "`WHERE `transaction_id` = " + transactionId;
			PreparedStatement statement = conn.prepareStatement(q);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				pt = readProductTransaction(rs);
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return pt;
	}
	
	public ArrayList<ProductTransaction> getProductTransactionByUserId(int userId)
	{
		ArrayList<ProductTransaction> output = new ArrayList<ProductTransaction>();
		Connection conn = SQL.getConnection();
		try 
		{
			if ( conn == null ) 
			{
				return null;
			}
			String q = selProductTransactions + "`WHERE `user_id` = ?";
			PreparedStatement statement = conn.prepareStatement(q);
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			SQL.getInstance().returnCon(conn);
			while (rs.next())
			{
				ProductTransaction pt = readProductTransaction(rs);
				if (pt != null)
				{
					output.add(pt);
				}
			} 
		} 
		catch (SQLException ex) 
		{
				Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
				SQL.getInstance().returnCon(conn);
		}
		SQL.returnConnection(conn);
		return output;
	}
	
	public ArrayList<ProductTransaction> getProductTransactionByProductId(int productId)
	{
		ArrayList<ProductTransaction> output = new ArrayList<ProductTransaction>();
		Connection conn = SQL.getConnection();
		try 
		{
			if ( conn == null ) 
			{
				return null;
			}
			String q = selProductTransactions + "`WHERE `product_id` = ?";
			PreparedStatement statement = conn.prepareStatement(q);
			statement.setInt(1, productId);
			ResultSet rs = statement.executeQuery();
			SQL.getInstance().returnCon(conn);
			while (rs.next())
			{
				ProductTransaction pt = readProductTransaction(rs);
				if (pt != null)
				{
					output.add(pt);
				}
			} 
		} 
		catch (SQLException ex) 
		{
				Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
				SQL.getInstance().returnCon(conn);
		}
		SQL.returnConnection(conn);
		return output;
	}

	
	
// --------------- U P D A T E S ----------------------------------------------	
		
	public boolean updateProductTransactionByTransactionId(ProductTransaction pt, int key)
	{
		boolean output = false;
		Connection conn = SQL.getConnection();
		try
		{
			if (conn != null)
			{
				PreparedStatement statement = conn.prepareStatement(ptUpdateQStr + " WHERE `transaction_id` = " + key);
				int i = 1;
				//statement.setInt(i++, pt.getTransactionId());
				statement.setObject(i++, pt.getUserId());
				statement.setObject(i++, pt.getProductId());
				statement.setString(i++, pt.getAction());
				statement.setInt(i++, pt.getTokenChange());
				statement.setBoolean(i++, pt.getInCart());
				statement.setInt(i++, pt.getQuantity());
				output = statement.executeUpdate() > 0;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		
		return output;
	}
	
	public boolean updateProductTransactionByUserId(ProductTransaction pt, int key)
	{
		boolean output = false;
		Connection conn = SQL.getConnection();
		try
		{
			if (conn != null)
			{
				PreparedStatement statement = conn.prepareStatement(ptUpdateQStr + " WHERE `user_id` = ?");
				int i = 1;
				//statement.setInt(i++, pt.getTransactionId());
				statement.setObject(i++, pt.getUserId());
				statement.setObject(i++, pt.getProductId());
				statement.setString(i++, pt.getAction());
				statement.setInt(i++, pt.getTokenChange());
				statement.setBoolean(i++, pt.getInCart());
				statement.setInt(i++, pt.getQuantity());
				// Key to find
				statement.setInt(i++, key);
				output = statement.executeUpdate() > 0;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		SQL.returnConnection(conn);
		return output;
	}

// --------------- D E L E T E S ----------------------------------------------	
	
	public boolean deleteProductTransactionByTransactionId(int transactionId)
	{
		boolean output = false;
		SQL sql = SQL.getInstance();
		Connection conn = sql.getCon();
		try {
			if (conn != null)
			{
				String q = ptDeleteQStr + "`WHERE `transaction_id` = ?";
				PreparedStatement statement = conn.prepareStatement(q);
				statement.setInt(1, transactionId);
				output = statement.executeUpdate() > 0;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		sql.returnCon(conn);
		return output;
	}
	
// ---------- P R O T E C T E D   S T U F F -----------------------------------	
	
	protected ProductTransaction readProductTransaction(ResultSet rs)
	{
		try {
			ProductTransaction pt = new ProductTransaction();
			pt.setTransactionId(rs.getInt("transaction_id"));
			pt.setUserId(rs.getInt("user_id"));
			pt.setProductId(rs.getInt("product_id"));
			pt.setAction(rs.getString("action"));
			pt.setTokenChange(rs.getInt("token_change"));
			pt.setTime(rs.getTimestamp("time"));
			pt.setInCart(rs.getInt("in_cart") == 1);
			pt.setQuantity(rs.getInt("quantity"));
			return pt;
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
}
