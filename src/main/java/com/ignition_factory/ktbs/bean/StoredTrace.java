package com.ignition_factory.ktbs.bean;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class StoredTrace extends Trace {

	private String Subject;
	
	/**
	 * Constructor from URI
	 * @param URI
	 * @throws Exception
	 */
	public StoredTrace(String URI) throws Exception {
		super (URI);
	}
	/**
	 * init all attribute
	 */
	public void init () throws Exception {
		super.init();
		this.Subject= this.DataJson.getString("defaultSubject");
	}
	/**
	 * 
	 * @param model
	 * @throws Exception 
	 */
	public void set_model(Model model) throws Exception {
		
		this.DataJson.put("hasModel", model.get_uri());
    	HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
		htppKtbs.PutJsonObject(this.uri, this.DataJson);
	    this.Model = model;    
	    }
	/**
	 * 
	 * @param origin
	 * @throws Exception 
	 */
	 
	 public void set_origin(String origin) throws Exception {
		 	this.DataJson.put("origin",origin);
	    	HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
			htppKtbs.PutJsonObject(this.uri, this.DataJson);
	        this.Origin = origin;
	    }
	 /**
	  * 
	  * @return
	  */
	 
	 public String get_default_subject() {
	       return this.Subject;
	    }
	 /**
	  * 
	  * @param subject
	 * @throws Exception 
	  */
	 public void set_default_subject (String subject) throws Exception {
		 	this.DataJson.put("defaultSubject",subject);
	    	HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
			htppKtbs.PutJsonObject(this.uri, this.DataJson);
	        this.Subject = subject;
	 
	 }
	
	/**
	 * Send Obsel To KTBS 
	 * @param type
	 * @param begin
	 * @param end
	 * @param subject
	 * @param Attributes
	 * @param Label
	 * @throws Exception
	 */
	 public void create_obsel ( String id , String type, String begin, String end, String subject , JSONObject Attributes, String Label  ) throws Exception{
		 JSONObject json_obsel = new JSONObject();
		 JSONArray arrayContext = new JSONArray();
		 JSONObject json_Context = new JSONObject();
		 JSONObject json_Model = new JSONObject();
		 json_Model.put("m:", this.Model.get_uri()+"#");
		 json_Context.put("http://liris.cnrs.fr/silex/2011/ktbs-jsonld-context", json_Model);
		 arrayContext.put(json_Context);
		// json_obsel.put("@context", arrayContext);
		 json_obsel.put("@type", "m:"+type);
		 json_obsel.put("@id", id);
		 json_obsel.put("subject", subject);
		 if (begin != null)  json_obsel.put("begin", begin);
		 if (end != null)  json_obsel.put("end", end);
		 Iterator<?> keys = Attributes.keys();
		 while( keys.hasNext() ){
	            String key = (String)keys.next();
	            json_obsel.put("m:"+key,Attributes.get(key));
	     }
		 
		 HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
	     htppKtbs.PostJsonObject (this.uri,json_obsel);
	    
		 
	 }

}
