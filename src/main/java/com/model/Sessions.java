package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sessions")
public class Sessions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false, name="id_session")
	private Integer idSession ;
	@Column
	private String session_index;
	@Lob
	@Column( length = 100000,columnDefinition="LONGTEXT" ) 
	private String attributs ; 
	@Column
	private Integer begin;
	@Column
	private Integer end;
	@Column
	private String duration;
	@Column
	private double trust;
	@Column (name="trust_action_final")
	private double trustActionFinal;
	
	@Column (name="normal_trust_action_final")
	private double NormaltrustActionFinal;
	
	@Column (name="diff_trust_action_final")
	private double diffTrustActionFinal;
	
	@Column (name="diff_trust")
	private double diffTrust;
	
	@Column (name="normal_diff_trust")
	private double NormaldiffTrust;
	
	@Column (name="score_final")
	private double ScoreFinal;
	
	@Column (length = 100000, name="details_distances",columnDefinition="LONGTEXT")
	private String detailsDistances ;
	
	//@OneToMany(mappedBy = "session", fetch = FetchType.EAGER)
	//private List<ActionTrust> actionTrusts = new ArrayList<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_cluster")
	private Cluster cluster;

	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_name")
	private User user;

    public Sessions () {}
	
    public Sessions (String sessionIndex, Integer begin) {
		this.session_index= sessionIndex;
		this.begin = begin; 
    }
		
	
	/*public List<ActionTrust> getActionTrusts() {
		return actionTrusts;
	}*/

	public void setActionTrusts(List<ActionTrust> actionTrusts) {
		for (ActionTrust a: actionTrusts){
			a.setSession( this );
		}
		//this.actionTrusts = actionTrusts;
	}

	
	
	
	
	
	public String getDetailsDistances() {
		return detailsDistances;
	}

	public void setDetailsDistances(String detailsDistances) {
		this.detailsDistances = detailsDistances;
	}

	
	
   
	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	
     
//    public void addActionTrust(ActionTrust actionTrust) {
//    	 actionTrusts.add( actionTrust );
//    	 actionTrust.setSession( this );}
// 	public void removeActionTrust(ActionTrust actionTrust) {
// 		actionTrusts.remove( actionTrust );
// 		actionTrust.setSession( null );}
// 	
 	
	
	public String getSessionIndex() {
		return session_index;
	}
	public void setSessionIndex(String sessionIndex) {
		this.session_index = sessionIndex;
	}
	public String getAttributs() {
		return attributs;
	}
	public void setAttributs(String attributs) {
		this.attributs = attributs;
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String string) {
		this.duration = string;
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public double getTrust() {
		return trust;
	}

	public void setTrust(double trust) {
		this.trust = trust;
	}
	public Integer getIdSession() {
		return idSession;
	}

	public void setIdSession(Integer idSession) {
		this.idSession = idSession;
	}

	public String getSession_index() {
		return session_index;
	}

	public void setSession_index(String session_index) {
		this.session_index = session_index;
	}

	public double getTrustActionFinal() {
		return trustActionFinal;
	}

	public void setTrustActionFinal(double trustActionFinal) {
		this.trustActionFinal = trustActionFinal;
	}

	public double getDiffTrustActionFinal() {
		return diffTrustActionFinal;
	}

	public void setDiffTrustActionFinal(double diffTrustActionFinal) {
		this.diffTrustActionFinal = diffTrustActionFinal;
	}

	public double getDiffTrust() {
		return diffTrust;
	}

	public void setDiffTrust(double diffTrust) {
		this.diffTrust = diffTrust;
	}

	public double getNormaltrustActionFinal() {
		return NormaltrustActionFinal;
	}

	public void setNormaltrustActionFinal(double normaltrustActionFinal) {
		NormaltrustActionFinal = normaltrustActionFinal;
	}

	public double getNormaldiffTrust() {
		return NormaldiffTrust;
	}

	public void setNormaldiffTrust(double normaldiffTrust) {
		NormaldiffTrust = normaldiffTrust;
	}

	public double getScoreFinal() {
		return ScoreFinal;
	}

	public void setScoreFinal(double scoreFinal) {
		ScoreFinal = scoreFinal;
	}


}
