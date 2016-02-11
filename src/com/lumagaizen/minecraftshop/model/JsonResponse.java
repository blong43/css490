/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lumagaizen.minecraftshop.model;

import com.google.gson.Gson;
import java.io.Serializable;

/**
 *
 * @author Taylor
 */
public class JsonResponse implements Serializable
{

	private boolean success;
	private String message;
	private Object payload;

	/**
	 * Default constructor. Null payload, no error message, success = true.
	 */
	public JsonResponse()
	{
		this(null, true, "");
	}

	/**
	 * payload = whatever object you've passed in. success = true, message
 = ""
	 *
	 * @param payload
	 */
	public JsonResponse(Object payload)
	{
		this(payload, true, "");
	}

	/**
	 * The best constructor.
	 *
	 * @param p_payload object to be serialized and sent back
	 * @param p_success true if this is not an error response.
	 * @param p_Message the error message to go with the error response.
	 * Value can be an empty string or null.
	 */
	public JsonResponse(Object p_payload, boolean p_success, String p_Message)
	{
		this.success = p_success;
		this.payload = p_payload;
		this.message = p_Message == null ? "" : p_Message;
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String p_Message)
	{
		this.message = p_Message;
	}

	public Object getPayload()
	{
		return payload;
	}

	public void setPayload(Object payload)
	{
		this.payload = payload;
	}

	public String toJson()
	{
		Gson gson = new Gson();
		String jsonStr = gson.toJson(this);
		return jsonStr;
	}

}
