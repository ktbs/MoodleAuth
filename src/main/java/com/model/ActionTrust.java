package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "action_trust")
public class ActionTrust {

	@Id
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false, name="id")
	private Integer id ;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="id_action")
	private String idAction;
	
	@Column (name = "type_action")
	private String typeAction ;
	
	@Column 
	private Integer begin ;
	
	@Column 
	private Integer end ;
	
	@Column 
	private double distance ;
	
	@Column 
	private double trust ;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_session")
	private Sessions session;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_authentification_result")
	private AuthentificationResult authentificationResult;
	
	
     public ActionTrust() {};
	
	public ActionTrust(String id_action,String type_action,Integer begin,Integer end,double distance, double trust) {
		
		
		this.idAction= id_action;
		this.typeAction = type_action ;
		this.begin = begin;
		this.end = end; 
		this.distance = distance ; 
		this.trust = trust ;
	};
	
public ActionTrust(String id_action,String type_action,Integer begin,Integer end,double distance, double trust,  AuthentificationResult authentificationResult) {
		
		
		this.idAction= id_action;
		this.typeAction = type_action ;
		this.begin = begin;
		this.end = end; 
		this.distance = distance ; 
		this.trust = trust ;
		this.authentificationResult = authentificationResult;
	};

	public Sessions getSession() {
		return session;
	}
	public void setSession(Sessions session) {
		this.session = session;
	}
	
	public String getId_action() {
		return idAction;
	}

	public void setId_action(String id_action) {
		this.idAction = id_action;
	}

	public String getType_action() {
		return typeAction;
	}

	public void setType_action(String type_action) {
		this.typeAction = type_action;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getTrust() {
		return trust;
	}

	public void setTrust(double trust) {
		this.trust = trust;
	}
	
	
}
