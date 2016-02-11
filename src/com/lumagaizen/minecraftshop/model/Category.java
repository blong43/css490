/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumagaizen.minecraftshop.model;

/**
 *
 * @author Taylor
 */
public class Category
{
	private int categoryId = -1;
	private String categoryWebsiteName = "";
	private String categoryMinecraftName = "";
	private DesignMeta designMeta = new DesignMeta();
	private String lore = "";

	public int getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(int categoryId)
	{
		this.categoryId = categoryId;
	}

	public String getCategoryWebsiteName()
	{
		return categoryWebsiteName;
	}

	public void setCategoryWebsiteName(String categoryWebsiteName)
	{
		this.categoryWebsiteName = categoryWebsiteName;
	}

	public String getCategoryMinecraftName()
	{
		return categoryMinecraftName;
	}

	public void setCategoryMinecraftName(String categoryMinecraftName)
	{
		this.categoryMinecraftName = categoryMinecraftName;
	}

	public DesignMeta getDesignMeta()
	{
		return designMeta;
	}

	public void setDesignMeta(DesignMeta designMeta)
	{
		this.designMeta = designMeta;
	}

	public String getLore()
	{
		return lore;
	}

	public void setLore(String lore)
	{
		this.lore = lore;
	}
	
}
