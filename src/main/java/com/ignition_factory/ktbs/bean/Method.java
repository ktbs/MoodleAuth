package com.ignition_factory.ktbs.bean;

import java.util.ArrayList;

public class Method extends Resource{

	private Base Base;
	private Method parent;
	private ArrayList<String> parameters;
	
	
	public Method(String URL) {
		// TODO Auto-generated constructor stub
	}
	
	public Base get_base() {
		
		return this.Base;
		
	}

	public Method get_parent() {
		
		return this.parent;
		
	}
	
	public void set_parent (Method P){
		
		this.parent = P;
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
}
