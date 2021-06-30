package com.ignition_factory.ktbs.bean;

import java.net.URL;
import java.util.*;

import org.json.*;

public class Model extends Resource{

	private Base Base;
	private String unit ;
	private Collection<Model> parents;
	private Collection<AttributeType> attribute_types;
	private Collection<RelationType> relation_types;
	private Collection<ObselType> obsel_types;
	
	public Model(String URL) {
		
		super (URL);

	}
	
	public void init () throws Exception{
		this.force_state_refresh();
		JSONArray graphArray = this.DataJson.getJSONArray("@graph");
		for (int i = 0; i < graphArray.length(); i++){
			JSONObject OBJ = graphArray.getJSONObject(i);
			if (new String (OBJ.getString("@type")).equals("TraceModel")) {
				this.uri=OBJ.getString("@id");
				this.Type=OBJ.getString("@type");
				URL URLBASE = new URL (new URL (this.uri),OBJ.getString("inBase"));
				this.Base = new Base (URLBASE.toString());
				this.unit = OBJ.getString("hasUnit");
			}
			else if (new String (OBJ.getString("@type")).equals("ObselType")){
				JSONArray subtype =null;
				JSONArray relation_types =null;
				if (OBJ.has("hasSuperObselType")) {subtype = OBJ.getJSONArray("hasSuperObselType");};
				ObselType obstype = new ObselType (this.uri,OBJ.getString("@id"), subtype,relation_types);
				this.obsel_types = new HashSet<ObselType>();
				this.obsel_types.add(obstype);
			}
			
			else if (new String (OBJ.getString("@type")).equals("AttributeType")){
				ObselType type = null;
				for (ObselType T : this.obsel_types) {
				    if (T.getTypeId().equals(OBJ.getString("hasAttributeObselType"))){
				    	type = T;
				    }
				}
				AttributeType Att = new AttributeType(this.uri, OBJ.getString("@id"), OBJ.getString("hasAttributeDatatype"),OBJ.getString("label"),type);
				type.add_attribute_type(Att);
				this.attribute_types = new HashSet<AttributeType>();
				this.attribute_types.add(Att);
			}
		}
		
	
		
	}
	/**
	 * 
	 * @return Base Class that contains the model
	 */
	public Base get_base() {
		
		return this.Base;
		
	}
	/**
	 * 
	 * @return "hasunit" attribute of the model
	 */
	public String get_unit() {
		
		return this.unit;
		
	}
	 //TODO
	public void set_unitn(String unit) {
	        this.unit = unit;
	    }
	/**
	 * 
	 * @param include_indirect
	 * @return
	 */
	public Collection<ObselType> list_obsel_types(boolean include_indirect) {
		
		return this.obsel_types;
	}
	
	public  Collection<AttributeType> list_attribute_types(boolean include_indirect) {
		
		return this.attribute_types;
	}
	 public Collection<Model> list_parents(boolean include_indirect) {
			
			return this.parents;
	}
	public Collection<RelationType> list_relation_types(boolean include_indirect) {
		
		return this.relation_types;
	}
	
	

	public void add_parent(Model m){
			
		}
	public void remove_parent(Model m){
			
		}	
		
	public void create_obsel_type(String IdType, ObselType supertypes, String label) throws Exception{
		JSONObject JsonObselType = new JSONObject();
		JSONArray arrayGraphe = this.DataJson.getJSONArray("@graph");
		JSONObject NewJson = this.DataJson;
		
		JsonObselType.put("@id", "#"+IdType);
		JsonObselType.put("@type", "ObselType");
		
		arrayGraphe.put(JsonObselType);
		
		 HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
	     htppKtbs.PutJsonObject (this.uri,NewJson);
	}
		
	public void create_attribute_type(String AttributeId, String typeId, boolean value_is_list,  String dataType,String label) throws Exception{
		JSONObject JsonObselType = new JSONObject();
		JSONArray arrayGraphe = this.DataJson.getJSONArray("@graph");
		JSONObject NewJson = this.DataJson;
		
		JsonObselType.put("@id", "#"+AttributeId);
		JsonObselType.put("@type", "AttributeType");
		JsonObselType.put("hasAttributeObselType","#"+ typeId);
		JsonObselType.put("hasAttributeObselType","#"+ typeId);
		
		arrayGraphe.put(JsonObselType);
		
		 HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
	     htppKtbs.PutJsonObject (this.uri,NewJson);
		}
		
	public void create_relation_type(String uri, ObselType origin,ObselType destination, RelationType supertypes, String label){
			
		}
}
