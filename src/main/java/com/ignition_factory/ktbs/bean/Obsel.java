package com.ignition_factory.ktbs.bean;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class Obsel extends Resource  {

	private Trace Trace;
	private Model model;
	private ObselType ObselType;
	private Object Begin;
	private Object End;
	private String id;
	private String subject;
	private Collection <Obsel> source_obsels;
	private Collection <AttributeType> attribute_types;
	private Collection <RelationType> relation_types;
	private Collection <Obsel> related_obsels;
	private Collection <RelationType> inverse_relation_types;
	private JSONObject jsonObsel ;
	public Obsel(String URI)  {
		//super (URI);
		this.uri= URI;
	}
	/**
	 * Constructor called from Trace class when load the list of obsel
	 * @param TraceURI
	 * @param ModelURI
	 * @param jsonObsel
	 * @throws JSONException
	 */
	public Obsel(String TraceURI,String ModelURI, JSONObject jsonObsel) throws JSONException  {
		this.Trace = new Trace(TraceURI);
		//this.setModel(new Model (ModelURI.substring(ModelURI.length()-1,ModelURI.length())));
		this.uri = TraceURI+ jsonObsel.getString("@id");
		this.id = jsonObsel.getString("@id") ;
		this.Begin = jsonObsel.get("begin");
		this.End = jsonObsel.get("end");
		this.jsonObsel = jsonObsel ;
		//this.setSubject(jsonObsel.getString("subject"));
		//System.out.println(jsonObsel.getString("@type").substring(2,jsonObsel.getString("@type").length()));
		
		this.ObselType = new ObselType (ModelURI,jsonObsel.getString("@type").substring(2,jsonObsel.getString("@type").length()),null,null);
		//this.ObselType = new ObselType (ModelURI+jsonObsel.getString("@type").substring(0,2));
		 Iterator<?> keys = jsonObsel.keys();
		 this.attribute_types = new HashSet<AttributeType>();
		 while( keys.hasNext() ){
	            String key = (String)keys.next();
	            if ((! new String (key).equals("@id")) && (! new String (key).equals("begin"))&& (! new String (key).equals("end"))&& (! new String (key).equals("@type"))&& (! new String (key).equals("subject"))&& (! new String (key).equals("hasSourceObsel")))
	            {
	            	AttributeType Att = new AttributeType (ModelURI,key.substring(2,key.length()),jsonObsel.get(key),this.ObselType);
	            	
	            	attribute_types.add(Att);
	            } 
	            	//json_obsel.put("m:"+key,Attributes.get(key));
	     }
	}
	 public void force_state_refresh() throws Exception{
		 super.force_state_refresh();
	 }
	public void init () {
		
	}
	public Trace get_trace(){
		return this.Trace;
		
	}
	public ObselType get_obsel_type(){
		return this.ObselType;
		
	}
	public JSONObject  get_jsonObsel(){
		return this.jsonObsel;
		
	}
	public int get_begin(){
		return (Integer) this.Begin;
		
	}
	public Object get_end(){
		return this.End;
		
	}
	public String get_id(){
		return this.id;
		
	}
	
	public Collection <Obsel> list_source_obsels(){
		return this.source_obsels;
		
	}
	public Collection <AttributeType> list_attribute_types(){
		return this.attribute_types;
		
	}
	public Collection <RelationType> list_relation_types(){
		return this.relation_types;
		
	}
	public Collection <RelationType> list_inverse_relation_types(){
		return this.inverse_relation_types;
		
	}
	public Collection <Obsel> list_related_obsels(RelationType rt ){
		return this.related_obsels;
		
	}
	public String get_attribute_value(AttributeType at ){
		
		return "value";
		
	}
	public AttributeType get_attribute (String attributeName) {
		AttributeType attR = null; 
		for (AttributeType att : this.list_attribute_types()){
			
			if (att.getName().equalsIgnoreCase(attributeName)){
				
				attR = att;
			}
		}
		return attR;
	}
	
	public void set_attribute_value (AttributeType at, String value){
		
		
	}
	public void del_attribute_value (AttributeType at){
		
		
	}
	public void add_related_obsel (AttributeType at, Obsel value){
		
		
	}
	public void del_related_obsel (AttributeType at, Obsel value){
		
		
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
}
