package com.ignition_factory.ktbs.bean;

public class AttributeType extends Resource {

	private Model model;
	private ObselType obsel_type;
	private String data_type;
	private String Value;
	private String label;
	private String NameAttribute;
	public AttributeType() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Construct from Obsel Class 
	 * @param URI
	 * @param Value
	 * @param type
	 */
	public AttributeType(String URIModel, String NameAttribute, Object Value, ObselType type) {
		this.uri = URIModel+NameAttribute;
		this.model = new Model (URIModel);
		this.Value= Value.toString();
		this.obsel_type= type;
		this.NameAttribute = NameAttribute;
	}
	/**
	 * Constructor called from Model Class at init method 
	 * @param URIModel
	 * @param NameAttribute
	 * @param DataType
	 * @param label
	 * @param type
	 */
	public AttributeType(String URIModel, String NameAttribute,String DataType,String label, ObselType type) {
		this.uri = URIModel+NameAttribute;
		this.model = new Model (URIModel);
		this.data_type =DataType;
		this.label = label;
		this.obsel_type= type;
	}

	public void init () {
		
		
	}
	public Model get_model() {
		
		return this.model;
	}

	public ObselType get_obsel_type() {
		
		return this.obsel_type;
	}

	public String get_data_type() {
		
		return this.data_type;
	}

	public void set_data_type(String uri, boolean is_list) {
		
		
	}
	public String getValue() {
		return Value;
	}
	public String getName() {
		return NameAttribute;
	}
	public void setValue(String value) {
		Value = value;
	}

}
