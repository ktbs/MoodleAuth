package com.ignition_factory.ktbs.bean;

import java.util.Collection;

public class RelationType extends Resource  {

	private Model model;
	private Collection <RelationType> supertypes;
	private ObselType origin;
	private ObselType destination;
	
	public RelationType() {
		// TODO Auto-generated constructor stub
	}

	public Model get_model() {
		
		return this.model;
	}

	public Collection <RelationType> list_supertypes (boolean include_indirect) {
		
		return this.supertypes;
	}

	public ObselType get_origin() {
		
		return this.origin;
	}

	public void set_origin(ObselType ot) {
		
	}
	
	public ObselType get_destination() {
		
		return this.destination;
	}

	public void set_destination(ObselType ot) {
		
	}
	
	public void add_supertype (RelationType rt) {
		
	}
	
	public void remove_supertype (RelationType rt) {
		
	}
}
