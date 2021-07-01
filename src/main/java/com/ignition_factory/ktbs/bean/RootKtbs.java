package com.ignition_factory.ktbs.bean;
/**
 * 
 */

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class RootKtbs extends Resource {

	
	private Collection<Method> builtin_method;
	private Collection<Base> Bases;

	/**
	 * Constructor with URI
	 * @param URL
	 * @throws Exception
	 */
	public RootKtbs(String URL) throws Exception {
		super (URL);
		
	}
	
	/**
	 * allows to initialize all attributes
	 * @throws Exception
	 */
	public void init () throws Exception{
		this.force_state_refresh();
		JSONArray BAseNAme = this.DataJson.getJSONArray("hasBase") ;
		for (int i = 0; i < BAseNAme.length(); i++) {  
			Base base = new Base (this.uri+BAseNAme.getString(i));
			Bases.add(base);
		}
		
		JSONArray MethodeNAme = this.DataJson.getJSONArray("hasBuiltinMethod") ;
		for (int i = 0; i < MethodeNAme.length(); i++) {  
			Method method = new Method (MethodeNAme.getString(i));
			builtin_method.add(method);
		}
		
	}
	
	/**
	 *Create a new base in Ktbs
	 * @param BaseName
	 * @param Label
	 * @return ResponseCode of request Post
	 * @throws Exception
	 */
	public Base create_base(String BaseName, String Label) throws Exception{
		JSONObject BaseJson = new JSONObject();
		BaseJson.put("@id",BaseName+"/");
		BaseJson.put("label", Label);
		BaseJson.put("@type", "Base");
		HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
    	int response = htppKtbs.PostJsonObject (this.uri,BaseJson);
    	if (response==201) 
    	{
    		Base B = new Base (this.uri+BaseName+"/");
    		return B;
    	}
    	else 
    		return null;

	}
	/**
	 * 
	 * @return list of Object base 
	 */
	public  Collection<Base> list_bases(){
		return this.Bases ;
		
	}
	/**
	 * 
	 * @param uri
	 * @return Base object
	 * @throws Exception
	 */
	public Base get_base(String uri) throws Exception {
		Base B =  null;
		 for (Base obj : this.Bases) {
	         if (obj.get_uri() == uri) 
	         B=obj;         
	    } 
		return B ;
	}
	
	
	
	public Collection<Method> list_builtin_methods() {
		return this.builtin_method;
	}
	
	// TODO
	public String get_builtin_method(String uri){
		
		return "value";
	}
	
}
