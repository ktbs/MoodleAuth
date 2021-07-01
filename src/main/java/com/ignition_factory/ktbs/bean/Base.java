package com.ignition_factory.ktbs.bean;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Base extends Resource {
	
	private Collection<Trace> Traces;
	private Collection<Model> Models;
	private RootKtbs RootKtbs;
	
	/**
	 * Constructor with URI
	 * @param URL
	 */
	public Base(String URL) {
		super (URL);
		
	}
	/**
	 * allows to initialize all attributes { object ktbsRoot, list Traces , list Model}
	 * @throws Exception
	 */
	public void init () throws Exception{
		this.force_state_refresh();
		// Root
		URL URL_RootKtbs = new URL (new URL (this.uri),this.DataJson.getString("inRoot"));
		this.RootKtbs = new RootKtbs(URL_RootKtbs.toString());
		// list Model and Traces
		//JSONArray containsObj = this.DataJson.getJSONArray("contains") ;
		JSONObject containsObj = this.DataJson ;
//		for (int i = 0; i < containsObj.length(); i++) { 
//			if (containsObj.getJSONObject(i).getString("@type")=="StoredTrace")
//			{
//				URL urlRessource = new URL (new URL(this.uri),containsObj.getJSONObject(i).getString("@id"));
//				Trace trace = new Trace (urlRessource.toString());
//				Traces.add(trace);
//			}
//			else if (containsObj.getJSONObject(i).getString("@type")=="TraceModel"){
//				URL urlRessource = new URL (new URL(this.uri),containsObj.getJSONObject(i).getString("@id"));
//				Model model = new Model (urlRessource.toString());
//				Models.add(model);
//			}
//		}
			  Iterator iter =containsObj.keys();
			    while(iter.hasNext()){
			        String key = (String)iter.next();
                    if (key.equals("contains")) {
                    	JSONArray value = containsObj.getJSONArray(key);
                    	for (int i = 0; i < value.length(); i++) { 
                			if (value.getJSONObject(i).getString("@type")=="StoredTrace")
                			{
                				URL urlRessource = new URL (new URL(this.uri),value.getJSONObject(i).getString("@id"));
                				Trace trace = new Trace (urlRessource.toString());
                				Traces.add(trace);
                			}
                			else if (value.getJSONObject(i).getString("@type")=="TraceModel"){
                				URL urlRessource = new URL (new URL(this.uri),value.getJSONObject(i).getString("@id"));
                				Model model = new Model (urlRessource.toString());
                				Models.add(model);
                				}
                		}
                    }
			       
			       
			    }
			
				
	}
	/**
	 * Creates a stored trace in that base
	 * @param TraceName
	 * @param model
	 * @param origin
	 * @param default_subject
	 * @param label
	 * @return  ResponseCode of request Post
	 */
	public void create_stored_trace(String TraceName, Model model, String origin, String  default_subject, String label) throws Exception {
		
		JSONObject TraceJson = new JSONObject();
		TraceJson.put("@id",TraceName+"/");
		TraceJson.put("@type", "StoredTrace");
		String urlmodel = model.get_uri().substring(0,model.get_uri().length()-1) + "#" ;
		TraceJson.put("hasModel", urlmodel);
		TraceJson.put("origin", origin);
		TraceJson.put("defaultSubject", default_subject);
		TraceJson.put("label", label);
		
		HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
    	htppKtbs.PostJsonObject (this.uri,TraceJson);
    	
    	/*if (response == 201) { 
    		StoredTrace T = new StoredTrace(this.uri+TraceName);
    		return T;
    		}
    	else return null;*/
	}
	
	/**
	 * Create empty model in the base 
	 * @param ModelName
	 * @param parent
	 * @param label
	 * @return
	 * @throws Exception
	 */
	public void create_model (String ModelName, Collection <Model> parent, String label) throws Exception {
		 JSONObject json_Model = new JSONObject();
		 JSONArray arraygraphe = new JSONArray();
		 JSONObject json_graphe = new JSONObject();
		 json_Model.put("@context","http://liris.cnrs.fr/silex/2011/ktbs-jsonld-context");
		 json_graphe.put("@id", ModelName);
		 json_graphe.put("@type", "TraceModel");
		 json_graphe.put("inBase", "");
		 json_graphe.put("hasUnit", "millisecond");
		 arraygraphe.put(json_graphe);
		 json_Model.put("@context","http://liris.cnrs.fr/silex/2011/ktbs-jsonld-context");
		 json_Model.put ("@graph",arraygraphe);
		 
		 HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
		 htppKtbs.PostJsonObject (this.uri,json_Model);
		 /*if (response == 201) { 
	    		Model M = new Model(this.uri+ModelName);
	    		return M;
	    		}
	    	else return null;*/
	}
	/**
	 * 
	 *  Creates a computed trace in that base
	 *  TODO PARAM
	 *  create_computed_trace(id:uri?, method:Method, parameters:[str=>any]?, sources:[Trace]?, label:str?)
	 * @throws Exception 
	 */
	public void create_computed_trace(String TraceName, String MethodeName,String[] TraceSourceName,String[] Parametres ) throws Exception {
		
		JSONObject CompTraceJson = new JSONObject();
		CompTraceJson.put("@id",TraceName+"/");
		CompTraceJson.put("@type","ComputedTrace");
		CompTraceJson.put("hasMethod",MethodeName);
		CompTraceJson.put("hasSource",TraceSourceName);
		CompTraceJson.put("parameter",Parametres);
		
		HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
    	int response = htppKtbs.PostJsonObject (this.uri,CompTraceJson);
    	/*if (response == 201) { 
    		ComputedTrace TC = new ComputedTrace(this.uri+TraceName);
    		return TC;
    		}
    	else return null;*/
	}
	
	/**
	 * get object ktbs_root
	 * @return
	 */
	public RootKtbs get_RootKtbs() {
		
		return this.RootKtbs;
	}
	/**
	 * Gets the list of traces stored in the base.
	 * 
	 */
	public Collection<Trace> list_traces() {
		
		return this.Traces;
	}
	/**
	 * Gets the list of models stored in the base.
	 * 
	 */
	public Collection<Model> list_models() {
		
		return this.Models;
	}
	
	/*
	 * create_method(id:uri, parent:Method, parameters:[str=>any]?, label:str?)
	 */
	public void create_method (String uri) {
		
		
	}
	public Base create_Subbase(String subasename) throws Exception {
		JSONObject BaseJson = new JSONObject();
		BaseJson.put("@id",subasename+"/");
		BaseJson.put("@type", "Base");
		BaseJson.put("inBase", "");
		HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
    	int response = htppKtbs.PostJsonObject (this.uri,BaseJson);
    	if (response==201) 
    	{
    		Base B = new Base (this.uri+subasename+"/");
    		return B;
    	}
    	else 
    		return null;
		
	}

}
