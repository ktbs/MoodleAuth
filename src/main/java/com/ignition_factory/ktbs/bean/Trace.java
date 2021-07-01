package com.ignition_factory.ktbs.bean;

import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;





import org.json.JSONArray;
import org.json.JSONObject;

public class Trace extends Resource{

	protected Base Base;
	protected Model Model;
	protected String Origin;
	protected Collection<Obsel> ListObsels;
	protected Collection<Trace> SourceTraces;
	protected Collection<Trace> TransformedTraces;
	public Trace()  {
		
	}
	
	public Trace(String URI)  {
		super (URI);
	}
	/**
	 * allows to initialize all attributes { object ktbsRoot, list Traces , list Model}
	 * @throws Exception
	 */
	public void init () throws Exception{
		this.force_state_refresh();
	//	System.out.print("data   :"+this.DataJson);
		// origin 
		if (this.DataJson.getString("origin") != null )this.Origin = this.DataJson.getString("origin");
		// Base
		URL URL_Base = new URL (new URL (this.uri),this.DataJson.getString("inBase"));
		this.Base = new Base(URL_Base.toString());
		// Model
		URL URL_Model = new URL (new URL (this.uri),this.DataJson.getString("hasModel"));
		this.Model = new Model(URL_Model.toString());
		// list Obsel
//		HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
//		JSONObject JSONDATA = htppKtbs.GetJsonResponse (this.uri+"@obsels");
//		JSONArray ArrayObsels = 	JSONDATA.getJSONArray("obsels");
//		for (int i = 0; i < ArrayObsels.length(); i++){
//			Obsel ObjObsel = new Obsel (this.uri,JSONDATA.getJSONArray("@context").getJSONObject(1).getString("m"),ArrayObsels.getJSONObject(i));
//			this.ListObsels = new HashSet<Obsel>();
//					ListObsels.add(ObjObsel);
//		}
		// list computed traces for stored Trace
		if (this.DataJson.has("isSourceOf")) {
			JSONArray ArrayTraceTransf = 	this.DataJson.getJSONArray("isSourceOf");
			for (int i = 0; i <ArrayTraceTransf.length(); i++){
			URL URL_TraceSource = new URL (new URL (this.uri),ArrayTraceTransf.getString(i));
			Trace T = new Trace (URL_TraceSource.toString());
			this.TransformedTraces = new HashSet<Trace>();
			this.TransformedTraces.add(T);
			}
		}
		// list source traces for computed Trace
		
		if (this.DataJson.has("hasSource")) {
			JSONArray ArrayTraceSource = 	this.DataJson.getJSONArray("hasSource");
			for (int i = 0; i <ArrayTraceSource.length(); i++){
			URL URL_TraceSource = new URL (new URL (this.uri),ArrayTraceSource.getString(i));
			Trace T = new Trace (URL_TraceSource.toString());
			this.SourceTraces = new HashSet<Trace>();
			this.SourceTraces.add(T);
			}
		}
		
		
	}
	/**
	 * 
	 * @return
	 */
	public Base get_base() {
		
		return this.Base;
		
	}
	/**
	 * 
	 * @return
	 */
	public Model get_model() {
		return this.Model;
		
	}
	/**
	 * 
	 * @return
	 */
	public String get_origin() {
		
		return this.Origin;
	}
	/**
	 * 
	 * @return
	 */
	
	public Collection<Trace> list_source_traces() {
		
		return this.SourceTraces;
	}
	/**
	 * 
	 * @return
	 */
	public Collection<Trace> list_transformed_traces() {
	
		return this.TransformedTraces;
	}
	/**
	 * 
	 * @param begin
	 * @param end
	 * @param reverse
	 * @return
	 * @throws Exception 
	 */
	public Collection<Obsel> list_obsels(Integer begin , Integer end,Boolean reverse) throws Exception {
		HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
		String  url = this.uri+"@obsels.json" ;
		if ((begin != null)&& (end != null)) {
			url = url +"?minb="+begin + "?maxb="+end ;
		} else if (begin != null) {url = url +"?minb="+begin;}
		else if (end != null) {url = url +"?maxb="+end ;}
		
		JSONObject jsonData = htppKtbs.GetJsonResponse(url,true) ;
		JSONArray arrayObsels = (jsonData != null) ? jsonData.getJSONArray("obsels") : null;
		this.ListObsels = new LinkedHashSet<Obsel>();
		
		if(arrayObsels != null) {
			for (int i = 0; i < arrayObsels.length(); i++){
				//System.out.println (arrayObsels.getJSONObject(i));
				Obsel objObsel = new Obsel (this.uri,jsonData.getJSONArray("@context").getJSONObject(1).getString("m"),arrayObsels.getJSONObject(i));
				
						ListObsels.add(objObsel);
						
			}
		}
		return this.ListObsels;
	}
	/**
	 * 
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public Obsel get_obsel(String uri) throws Exception {
		Obsel O =  null;
		 for (Obsel obj : this.ListObsels) {
	         if (obj.get_uri() == uri) 
	         O=obj;         
	    } 
		return O ;
		
	}
	public void PostObsel (Obsel obs) throws Exception{
		HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
    	htppKtbs.PostJsonObject (this.uri, obs.get_jsonObsel());
	}
	public void CleanListObsel () throws Exception{
		
    	
    	JSONObject TraceJson = new JSONObject();
    	JSONArray arrayContext = new JSONArray(); 
    	arrayContext.put("http://liris.cnrs.fr/silex/2011/ktbs-jsonld-context");
    	JSONObject ModelJson = new JSONObject();
    	ModelJson.put("m", this.Model.get_uri());
    	arrayContext.put(ModelJson) ;
    	
    	TraceJson.put("@context",arrayContext);
		TraceJson.put("@id", "./");
		JSONObject ObselJson = new JSONObject();
		ObselJson.put("@id", "") ;
		ObselJson.put("@type", "StoredTraceObsels") ;
		TraceJson.put("hasObselList", ObselJson);
		
		JSONArray arrayObsel = new JSONArray(); 
		TraceJson.put("obsels", arrayObsel);
		
		HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
    	htppKtbs.PutJsonObject (this.uri+"@obsels",TraceJson);
	}
}
