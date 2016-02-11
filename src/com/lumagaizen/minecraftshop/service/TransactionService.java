package com.lumagaizen.minecraftshop.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.lumagaizen.minecraftshop.SQL;
import com.lumagaizen.minecraftshop.model.MoneyTransaction;
import com.lumagaizen.minecraftshop.model.ProductTransaction;
import com.lumagaizen.minecraftshop.model.ShopUser;
import com.lumagaizen.minecraftshop.model.TransferTransaction;
import com.lumagaizen.minecraftshop.manager.TransactionManager;
import com.lumagaizen.minecraftshop.manager.UserManager;


// ProductTransaction Completed
// May not get to the Transfer or Money Transactions
// Functions left in this class still need to be implemented later

public class TransactionService {
	
	private final TransactionManager repo;
	
	public TransactionService(){
		this.repo = new TransactionManager();
	}
	
	public boolean insertTransferTransaction(TransferTransaction t){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public boolean insertMoneyTransaction(MoneyTransaction t){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public TransferTransaction getTransferTransactionById(int id){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public MoneyTransaction getMoneyTransactionByUserIdFrom(int id){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public MoneyTransaction getMoneyTransactionByUserIdTo(int id){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public TransferTransaction updateTransferTransactionById(int id){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public MoneyTransaction updateMoneyTransactionByUserIdFrom(int id){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public MoneyTransaction updateMoneyTransactionByUserIdTo(int id){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public TransferTransaction deleteTransferTransactionById(int id){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public MoneyTransaction deleteMoneyTransactionByUserIdFrom(int id){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	public MoneyTransaction deleteMoneyTransactionByUserIdTo(int id){
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
/*	
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
	
    public boolean deleteProductTransactionByProductId(int productId)
	{
		boolean output = false;
		SQL sql = SQL.getInstance();
		Connection conn = sql.getCon();
		try {
			if (conn != null)
			{
				String q = ptDeleteQStr + "`WHERE `product_id` = ?";
				PreparedStatement statement = conn.prepareStatement(q);
				statement.setInt(1, productId);
				output = statement.executeUpdate() > 0;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
		}
		sql.returnCon(conn);
		return output;
	}
	
	public boolean updateProductTransactionByProductId(ProductTransaction pt, int key)
	{
		boolean output = false;
		Connection conn = SQL.getConnection();
		try
		{
			if (conn != null)
			{
				PreparedStatement statement = conn.prepareStatement(ptUpdateQStr + " WHERE `product_id` = ?");
				int i = 1;
				statement.setInt(i++, pt.getTransactionId());
				statement.setObject(i++, pt.getUserId());
				statement.setObject(i++, pt.getProductId());
				statement.setString(i++, pt.getAction());
				statement.setInt(i++, pt.getTokenChange());
				
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
*/	
}
