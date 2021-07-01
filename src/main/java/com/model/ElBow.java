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
@Table(name = "elbow")
public class ElBow {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false,name ="id")
	private Integer id ;
	
	@Column (name = "SimSeuil")
	private double SimSeuil;
	@Column 
	private double distance ; 
	@Column 
	private String  userName;
	
	public ElBow () {};
	
	public ElBow (double SimSeuil,double distance,String  userName) {
		
		this.SimSeuil= SimSeuil; 
		this.distance = distance; 
		this.userName = userName;
	};
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getSimSeuil() {
		return SimSeuil;
	}
	public void setSimSeuil(Double simSeuil) {
		SimSeuil = simSeuil;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
	
}
