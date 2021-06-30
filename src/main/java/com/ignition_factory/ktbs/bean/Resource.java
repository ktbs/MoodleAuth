package com.ignition_factory.ktbs.bean;

/**
*@author DERBEL Fatma
*@version 1.0.0
*@description  Resource Objects are KTBS objects. All resources have an uri, an id and an optional label
*/

import org.json.JSONObject;

public class Resource {
	
	protected String Name;
	protected String uri;
	protected String label;
	protected String Type;
	protected String version;
	protected String readonly; /*TODO*/
	protected String sync_status; /*TODO*/
	protected JSONObject DataJson;
    
	public Resource (){}
	/**
	 * Constructor with URI
	 * @param URL
	 */
	public Resource (String URL) {
		
		if (URL.substring(URL.length()-1).equals("/")) {this.uri= URL;	}
		else {this.uri= URL +"/";}
		String[] spliteURI = URL.split ("/");
		this.Name= spliteURI[spliteURI.length-1];
	}
	
    /**
     * Remove  resource from the kTBS.
     * @throws Exception
     */
    public void remove() throws Exception {
    	HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
    	htppKtbs.DeleteResource (this.uri);
    }
    /**
     * 
     * @return
     * @throws Exception
     */
    public Boolean Exist () throws Exception{
    	HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
		int Response = htppKtbs.GetCodeResponse(this.uri);
		if ((Response ==200) || (Response == 303)) {return true;}
		else return false;
	}
    /**
     * Forces the Resource to synchronize with the KTBS.
     * @throws Exception
     */
    public void force_state_refresh() throws Exception {
    	HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
		JSONObject JsonResponse = htppKtbs.GetJsonResponse (this.uri);
		if (JsonResponse!= null) {
			this.DataJson = JsonResponse;
			if (JsonResponse.has("label")) {this.label= JsonResponse.getString("label");}
			if (JsonResponse.has("@type")) {this.Type= JsonResponse.getString("@type");}
			if (JsonResponse.has("version")) {this.version= JsonResponse.getString("version");}
		}
		
    }
    /**
	 * 
	 * @return id of resource
	 */
	public String get_Name () {
        return this.Name;
    }
    /**
     * 
     * @return uri of resource
     */
    public String get_uri () {
        return this.uri;
    }
  
  /**
   * 
   * @return label
   */
    
    public String get_label () {
        return this.label;
    }
   /**
    * 
    * @param label
    * @throws Exception
    */
    public void set_label(String label) throws Exception {
    	this.DataJson.put("label", label);
    	HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
		htppKtbs.PutJsonObject(this.uri, this.DataJson);
		this.label=label;
    }
    /*TODO*/
    public void reset_label() {}
    
    //TODO
    public String get_readonly() {
        return this.readonly;
    }
    //TODO
    public String get_sync_status(){
        return this.sync_status ;
    }
}
