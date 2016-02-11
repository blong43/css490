/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumagaizen.minecraftshop.model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Taylor
 */
public class Product
{
	private int productId;
	private int categoryId = -1;
	private DesignMeta designMeta = new DesignMeta();
	private String productMinecraftName = "";
	private String productWebsiteName = "";
	private int priceTokens = Integer.MAX_VALUE;
	private boolean isActive = true;
	private String description = "";
	
	private boolean isLimitedEdition = false;
	private Timestamp availableUntilTime;
	
	private boolean isLimitedUses = false;
	private int numUses = 0;
	
	private boolean isAbleToExpire = false;
	private long timeBeforeExpiringMs = 0;
	private String commandsOnUse = "";
	private String commandsOnExpire = "";

	public String getCommandsOnUse()
	{
		return commandsOnUse;
	}

	public void setCommandsOnUse(String commandsOnUse)
	{
		this.commandsOnUse = commandsOnUse;
	}

	public String getCommandsOnExpire()
	{
		return commandsOnExpire;
	}

	public void setCommandsOnExpire(String commandsOnExpire)
	{
		this.commandsOnExpire = commandsOnExpire;
	}

	public void setProductId(int productId)
	{
		this.productId = productId;
	}

	public void setProductMinecraftName(String productMinecraftName)
	{
		this.productMinecraftName = productMinecraftName;
	}

	public void setProductWebsiteName(String productWebsiteName)
	{
		this.productWebsiteName = productWebsiteName;
	}

	public void setPriceTokens(int priceTokens)
	{
		this.priceTokens = priceTokens;
	}

	public void setIsActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public void setDescription(String lore)
	{
		this.description = lore;
	}

	public void setAvailableUntilTime(Timestamp availableUntilTime)
	{
		this.availableUntilTime = availableUntilTime;
	}

	public void setNumUses(int numUses)
	{
		this.numUses = numUses;
	}

	public void setTimeBeforeExpiringMs(long timeBeforeExpiring)
	{
		this.timeBeforeExpiringMs = timeBeforeExpiring;
	}
	
	public int getProductId()
	{
		return productId;
	}

	public int getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}
	
	public String getProductMinecraftName()
	{
		return productMinecraftName;
	}

	public String getProductWebsiteName()
	{
		return productWebsiteName;
	}

	public int getPriceTokens()
	{
		return priceTokens;
	}

	public boolean isIsActive()
	{
		return isActive;
	}

	public String getDescription()
	{
		return description;
	}

	public boolean isLimitedEdition()
	{
		return isLimitedEdition;
	}

	public Timestamp getAvailableUntilTime()
	{
		return availableUntilTime;
	}

	public boolean isLimitedUses()
	{
		return isLimitedUses;
	}

	public int getNumUses()
	{
		return numUses;
	}

	public boolean isAbleToExpire()
	{
		return isAbleToExpire;
	}

	public long getTimeBeforeExpiringMillis()
	{
		return timeBeforeExpiringMs;
	}

	public DesignMeta getDesignMeta()
	{
		return designMeta;
	}

	public void setDesignMeta(DesignMeta designMeta)
	{
		this.designMeta = designMeta;
	}

	public boolean isIsLimitedEdition()
	{
		return isLimitedEdition;
	}

	public void setIsLimitedEdition(boolean isLimitedEdition)
	{
		this.isLimitedEdition = isLimitedEdition;
	}

	public boolean isIsLimitedUses()
	{
		return isLimitedUses;
	}

	public void setIsLimitedUses(boolean isLimitedUses)
	{
		this.isLimitedUses = isLimitedUses;
	}

	public boolean isIsAbleToExpire()
	{
		return isAbleToExpire;
	}

	public void setIsAbleToExpire(boolean isAbleToExpire)
	{
		this.isAbleToExpire = isAbleToExpire;
	}
	
}
