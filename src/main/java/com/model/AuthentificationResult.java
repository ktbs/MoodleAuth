package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Authentification_result")
public class AuthentificationResult {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false, name="id_authentification_result")
	private Integer idAuthentificationResult;
	
	@Column(name="user_profil_name")
	private String UserProfilName;
	
	@Column(name="user_signature_name")
	private String UserSignatureName;
	
	@Column(name="trust")
	private double trust;
	
	@Column(name="dthreshold")
	private double dthreshold;
	
	@Column(name="nb_action")
	private double nbAction;
	
		
		public AuthentificationResult (String UserProfilName,String UserSignatureName, double trust,double dthreshold,int nbAction) {
			
			this.UserProfilName =UserProfilName ;
			this.UserSignatureName = UserSignatureName;
			this.trust = trust ;
			this.dthreshold = dthreshold;
			this.nbAction = nbAction;
		};
		

	public Integer getId() {
		return idAuthentificationResult;
	}

	public void setId(Integer id) {
		this.idAuthentificationResult = id;
	}

	public String getUserProfilName() {
		return UserProfilName;
	}

	public void setUserProfilName(String userProfilName) {
		UserProfilName = userProfilName;
	}

	public String getUserSignatureName() {
		return UserSignatureName;
	}

	public void setUserSignatureName(String userSignatureName) {
		UserSignatureName = userSignatureName;
	}

	public double getTrust() {
		return trust;
	}

	public void setTrust(double trust) {
		this.trust = trust;
	}

	public Integer getIdAuthentificationResult() {
		return idAuthentificationResult;
	}

	public void setIdAuthentificationResult(Integer idAuthentificationResult) {
		this.idAuthentificationResult = idAuthentificationResult;
	}

	public double getDthreshold() {
		return dthreshold;
	}

	public void setDthreshold(double dthreshold) {
		this.dthreshold = dthreshold;
	}

	public double getNbAction() {
		return nbAction;
	}

	public void setNbAction(double nbAction) {
		this.nbAction = nbAction;
	}
	
	

}
