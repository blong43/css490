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
public class JsonErrorResponse extends JsonResponse
{
	public JsonErrorResponse(){
		super(null,false,"UNKNOWN ERROR");
	}
	public JsonErrorResponse(String errorMessage){
		super(null,false,errorMessage);
	}
}
