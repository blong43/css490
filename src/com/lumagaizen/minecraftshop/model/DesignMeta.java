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
public class DesignMeta
{
	private int designMetaId = -1;
	private String material = "BARRIER";
	private byte dataValue = 0;
	private boolean isEnchanted = false;
	private String imageUrl = "";

	public int getDesignMetaId()
	{
		return designMetaId;
	}

	public void setDesignMetaId(int designMetaId)
	{
		this.designMetaId = designMetaId;
	}

	public String getMaterial()
	{
		return material;
	}

	public void setMaterial(String material)
	{
		this.material = material;
	}

	public byte getDataValue()
	{
		return dataValue;
	}

	public void setDataValue(byte dataValue)
	{
		this.dataValue = dataValue;
	}

	public boolean isEnchanted()
	{
		return isEnchanted;
	}

	public void setIsEnchanted(boolean isEnchanted)
	{
		this.isEnchanted = isEnchanted;
	}

	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}
	
	
}
