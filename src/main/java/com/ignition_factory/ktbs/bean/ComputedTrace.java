package com.ignition_factory.ktbs.bean;

import java.util.ArrayList;

public class ComputedTrace extends Trace {

	
	private Method method;
	private String methodName;
	private ArrayList<String> parameters;
	/**
	 * 
	 * @param URI
	 */
	public ComputedTrace (String URI){
		
		super (URI);
	}
	/**
	 * 
	 */
	public void init () throws Exception{
		super.init();
		this.methodName= this.DataJson.getString("hasMethod");
//		for (int i = 0; i <this.DataJson.getJSONArray("parameter").length(); i++){
//			this.parameters.add(this.DataJson.getJSONArray("parameter").getString(i));
//		}
	}
	/**
	 * 
	 * @return
	 */
	public Method get_method(){
		
		return this.method;
	}
	/**
	 * 
	 * @param method
	 * @throws Exception 
	 */
	public void set_method (String method) throws Exception {
		this.DataJson.put("hasMethod",method);
    	HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
		htppKtbs.PutJsonObject(this.uri, this.DataJson);
       
		 this.methodName = method;
	 }
	 
	 public ArrayList<String> list_parameters(boolean include_inherited){
			
			return this.parameters;
	}
		
	public String get_parameter(String key){
			
			return "value";
	}
		
	public void set_parameter(String key, String value){
			
			
	}
		
	public void del_parameter(String key){
			
			
		}
	public String getMethodName() {
			return methodName;
	}
	
	public void setMethodName(String methodName) {
			this.methodName = methodName;
	}
}
