package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@Column(name ="user_name")
	private String userName;
	@Column(name ="base_uri")
	private String baseUri;
	@Column(name ="primarytrace_uri")
	private String primarytraceUri;
	@Column(name ="profil_session_uri")
	private String profilSessionUri;
	@Column(name ="signature_session_uri")
	private String signatureSessionUri;
	@Column(name ="profil_action_uri")
	private String profilActionUri;
	@Column(name ="signature_action_uri")
	private String signatureActionUri;
	@Column(name ="trust_final_session")
	private double trustFinalSession  ;
	@Column(name ="trust_final_action")
	private float trustFinalAction ;
	
	@OneToMany(mappedBy = "user")
	private List<Sessions> sessions = new ArrayList<>();

	public User () {};
	public  User (String UserName, String BaseUri, String PrimarytraceUri) {
		
		this.userName = UserName; 
		this.baseUri = BaseUri; 
		this.primarytraceUri = PrimarytraceUri;
		
	}
	
	public double getTrustFinalSession() {
		return trustFinalSession;
	}
	public void setTrustFinalSession(double trustFinalSession) {
		this.trustFinalSession = trustFinalSession;
	}
	public double getTrustFinalAction() {
		return trustFinalAction;
	}
	public void setTrustFinalAction(float trustFinalAction) {
		this.trustFinalAction = trustFinalAction;
	}

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
		return signatureSessionUri;
	}

	public void setSignatureSessioneUri(String signatureSessioneUri) {
		this.signatureSessionUri = signatureSessioneUri;
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
	
	public void addSession(Sessions session) {
		sessions.add( session );
		session.setUser( this );}
	public void removePhone(Sessions session) {
		sessions.remove( session );
        session.setUser( null );}

}
            