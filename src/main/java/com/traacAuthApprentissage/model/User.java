package com.traacAuthApprentissage.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Users")
public class User {
	@Id
	private String userName;
	@Column(name = "baseUri")
	private String baseUri;
	
	@Column(name = "primarytraceUri")
	private String primarytraceUri;
	
	@Column(name = "profilSessionUri")
	private String profilSessionUri;
	
	@Column(name = "signatureSessioneUri")
	private String signatureSessioneUri;
	
	@Column(name = "profilActionUri")
	private String profilActionUri;
	
	@Column(name = "signatureActionUri")
	private String signatureActionUri;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBaseUri() {
		return baseUri;
	}

	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}

	public String getPrimarytraceUri() {
		return primarytraceUri;
	}

	public  User (String UserName, String BaseUri, String PrimarytraceUri) {
		
		this.userName = UserName; 
		this.baseUri = BaseUri; 
		this.primarytraceUri = PrimarytraceUri;
		
	}
	public void setPrimarytraceUri(String primarytraceUri) {
		this.primarytraceUri = primarytraceUri;
	}

	public String getProfilSessionUri() {
		return profilSessionUri;
	}

	public void setProfilSessionUri(String profilSessionUri) {
		this.profilSessionUri = profilSessionUri;
	}

	public String getSignatureSessioneUri() {
		return signatureSessioneUri;
	}

	public void setSignatureSessioneUri(String signatureSessioneUri) {
		this.signatureSessioneUri = signatureSessioneUri;
	}

	public String getProfilActionUri() {
		return profilActionUri;
	}

	public void setProfilActionUri(String profilActionUri) {
		this.profilActionUri = profilActionUri;
	}

	public String getSignatureActionUri() {
		return signatureActionUri;
	}

	public void setSignatureActionUri(String signatureActionUri) {
		this.signatureActionUri = signatureActionUri;
	}
	
}
            