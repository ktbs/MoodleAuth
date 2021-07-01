package com.ignition_factory.ktbs.bean;

import java.util.Collection;
import java.util.HashSet;

import org.json.JSONArray;

public class ObselType extends Resource {

	private Model model;
	private String TypeId;
	private Collection <ObselType> supertypes;
	private Collection <ObselType> subtypes;
	private Collection <AttributeType> attribute_types;
	private Collection <RelationType> relation_types;
	private Collection <RelationType> inverse_relation_types;
	
	public ObselType(String URI) {
		super (URI);
	}
	/**
	 * Constructor called from model Class 
	 * @param URIModel
	 * @param idType
	 * @param subtypes
	 * @param relation_types
	 */
	public ObselType(String URIModel, String idType, JSONArray subtypes, JSONArray relation_types) {
		
		this.model = new Model (URIModel);
		this.TypeId = idType;
		this.uri = URIModel + idType;
		
	}
	
	public void init () {
		
	}
	/**
	 * 
	 * @return Class model of this obsel type
	 */
	public Model get_model() {
		return this.model;
	}
	/**
	 * 
	 * @return Name of obselType with #
	 */
	public String getTypeId() {
		return TypeId;
	}
	/**
	 * 
	 * @return collection of object AttributeType that describe the obsel type
	 */
	public Collection <AttributeType> list_attribute_types(){
		return this.attribute_types;
		
	}
	public void add_attribute_type (AttributeType Att){
		this.attribute_types = new HashSet<AttributeType>();
		this.attribute_types.add(Att);
		
	}
	public void create_attribute_type(String uri,String data_type, boolean value_is_list,String label){	
	}
	
	public void create_relation_type(String uri, ObselType destination, Collection <RelationType> supertypes,String label){
			
	}

	public Collection <RelationType> list_relation_types(){
		return this.relation_types;
	}
	public Collection <RelationType> list_inverse_relation_types(){
		return this.inverse_relation_types;
		
	}
	public Collection <ObselType> list_supertypes (boolean include_indirect) {
		
		return this.supertypes;
	}	
	public Collection <ObselType> list_subtypes (boolean include_indirect) {
		
		return this.subtypes;
	}
	public void add_supertype(ObselType ot){
		
	}
	public void remove_supertype(ObselType ot){
		
	}
	
}
