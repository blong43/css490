/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumagaizen.minecraftshop;

import com.lumagaizen.minecraftshop.model.ShopUser;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains non-web-related static functions that help with the 
 * program. Hash methods are common here.
 * @author Taylor
 */
public class STATIC
{

	//<editor-fold defaultstate="collapsed" desc="Sha256">

	public static String Sha256(String plainText)
	{
		if (plainText == null)
		{
			plainText = "";
		}
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(plainText.getBytes("UTF-8")); // Change this to "UTF-16" if needed
			byte[] digest = md.digest();
			return bytesToHex(digest);
		}
		catch (UnsupportedEncodingException ex)
		{
			Logger.getLogger(ShopUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			Logger.getLogger(ShopUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="md5">
	public static String md5(String plainText)
	{
		if (plainText == null)
		{
			plainText = "";
		}
		try
		{
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(plainText.getBytes("UTF-8")); // Change this to "UTF-16" if needed
			byte[] digest = md.digest();
			return bytesToHex(digest);
		}
		catch (UnsupportedEncodingException ex)
		{
			Logger.getLogger(ShopUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (NoSuchAlgorithmException ex)
		{
			Logger.getLogger(ShopUser.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="bytesToHex">
	public static String bytesToHex(byte[] oBytes)
	{
		StringBuilder sb = new StringBuilder();
		for (byte b : oBytes)
		{
			sb.append(String.format("%02X", b));
		}
		return sb.toString();
	}
	//</editor-fold>

}
