/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumagaizen.minecraftshop.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author Taylor
 */
public class ProductTransaction
{
	private int transactionId = 0;
	private int userId = 0;
	private int productId = 0;
	private String action = "purchased";
	private int tokenChange = 0;
	private Timestamp time = null;
	private boolean inCart = false;
	private int quantity = 0;
	
	public int getTransactionId()
	{
		return transactionId;
	}
	
	public int getUserId()
	{
		return userId;
	}
	
	public int getProductId()
	{
		return productId;
	}
	
	public String getAction()
	{
		return action;
	}
	
	public int getTokenChange()
	{
		return tokenChange;
	}
	
	public Timestamp getTime()
	{
		return time;
	}
	
	public int getQuantity()
	{
		return quantity;
	}
	
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	
	public boolean getInCart()
	{
		return inCart;
	}
	
	public void setInCart(boolean inCart)
	{
		this.inCart = inCart;
	}
	
	public void setTransactionId(int id)
	{
		transactionId = id;
	}
	
	public void setUserId(int userId)
	{
		this.userId = userId;
	}
	
	public void setProductId(int productId)
	{
		this.productId = productId;
	}
	
	public void setAction(String s)
	{
		action = s;
	}
	
	public void setTokenChange(int change)
	{
		tokenChange = change;
	}
	
	public void setTime(Timestamp t)
	{
		time = t;
	}
}
