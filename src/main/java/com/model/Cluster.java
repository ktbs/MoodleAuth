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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cluster")

public class Cluster {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false,name ="id_cluster")
	private Integer idCluster ;
	@Column (name = "cluster_name")
	private String clusterName;
	@Column 
	private double poid ; 
	@Column (name ="average_nb_consultation_all_ressources")
	private String averageNbConsultationAllRessources;
	@Column (name = "average_duration_total_ressource")
	private String averageDurationTotalRessource;
	@Column (name = "average_duration")
	private String averageDuration;
	@Column (name ="list_exercice_behavior")
	private String listExerciceBehavior;
	@Column (name = "list_text_behavior")
	private String listTextBehavior;
	
	@Column
	private String list_forum_behavior;
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_name")
	private User user;
	
	@OneToMany(mappedBy = "cluster")
	private List<Sessions> sessions = new ArrayList<>();
	
	public Cluster () {};
    public Cluster ( String clusterName) {
		this.clusterName = clusterName ;}
	
	public String getList_text_behavior() {
		return listTextBehavior;
	}
	public void setList_text_behavior(String list_text_behavior) {
		this.listTextBehavior = list_text_behavior;
	}
	
	public String getList_exercice_behavior() {
		return listExerciceBehavior;
	}
	public void setList_exercice_behavior(String list_exercice_behavior) {
		this.listExerciceBehavior = list_exercice_behavior;
	}
	public String getList_forum_behavior() {
		return list_forum_behavior;
	}
	public void setList_forum_behavior(String list_forum_behavior) {
		this.list_forum_behavior = list_forum_behavior;
	}
	
	
	public Integer getId() {
		return idCluster;
	}
	public void setId(Integer id) {
		this.idCluster = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public double getpoid() {
		return poid;
	}
	public void setPoid(double d) {
		this.poid= d;
	}
	public String getAveragenbConsultationAllRessources() {
		return averageNbConsultationAllRessources;
	}
	public void setAveragenbConsultationAllRessources(String string) {
		averageNbConsultationAllRessources = string;
	}
	public String getAveragedurationTotalRessource() {
		return  averageDurationTotalRessource;
	}
	public void setAveragedurationTotalRessource(String string) {
		 averageDurationTotalRessource = string;
	}
	public String getAverageduration() {
		return averageDuration;
	}
	public void setAverageduration(String string) {
		averageDuration = string;
	}
	
	public List<Sessions> getSessions() {
		return sessions;
	}
	public void setSessions(List<Sessions> sessions) {
		this.sessions = sessions;
	}
	
	
	 public void addSession(Sessions session) {
		 sessions.add( session );
		 session.setCluster( this );}
 	public void removeActionTrust(Sessions session) {
 		sessions.remove( session );
 		session.setCluster( null );}
}
