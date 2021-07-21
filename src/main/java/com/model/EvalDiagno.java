package com.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EvalDiagno")

public class EvalDiagno {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false, name="id") private Integer id ;
	
	@Column(name ="user_name")
	private String userName;
	
	@Column(name="TrustUserS5") private double  TrustUserS5; 
	@Column(name="TrustFrauderS5") private double  TrustFrauderS5; 
	@Column(name="diffTrustUserS5") private double  diffTrustUserS5;//trust 10 - trust 9 vrai user 
	@Column (name="diffTrustFrauderS5")private double  diffTrustFrauderS5; //trust10frauder-trust 9 ancien
	
	@Column(name="TrustUserS10") private double  TrustUserS10; 
	@Column(name="TrustFrauderS10") private double  TrustFrauderS10; 
	@Column(name="diffTrustUserS10") private double  diffTrustUserS10;//trust 10 - trust 9 vrai user 
	@Column (name="diffTrustFrauderS10")private double  diffTrustFrauderS10; //trust10frauder-trust 9 ancien
	
	@Column(name="TrustUserS15") private double  TrustUserS15; 
	@Column(name="TrustFrauderS15") private double  TrustFrauderS15; 
	@Column(name="diffTrustUserS15") private double  diffTrustUserS15;//trust 10 - trust 9 vrai user 
	@Column (name="diffTrustFrauderS15")private double  diffTrustFrauderS15; //trust10frauder-trust 9 ancien
@Column(name="TrustActionUserS5",columnDefinition = "float default 0") private float TrustActionUserS5;
@Column(name="TrustActionUserS10" ,columnDefinition = "float default 0") private float TrustActionUserS10;
@Column(name="TrustActionUserS15",columnDefinition = "float default 0") private float TrustActionUserS15;
@Column(name="TrustActionFrauderS5",columnDefinition = "float default 0") private float TrustActionFrauderS5 ;
@Column(name="TrustActionFrauderS10",columnDefinition = "float default 0") private float TrustActionFrauderS10 ;
public double getDifftrustactionuserS5() {
	return difftrustactionuserS5;
}

public void setDifftrustactionuserS5(double difftrustactionuserS5) {
	this.difftrustactionuserS5 = difftrustactionuserS5;
}

public double getDifftrustactionfrauderS5() {
	return difftrustactionfrauderS5;
}

public void setDifftrustactionfrauderS5(double difftrustactionfrauderS5) {
	this.difftrustactionfrauderS5 = difftrustactionfrauderS5;
}

public double getDifftrustactionuserS10() {
	return difftrustactionuserS10;
}

public void setDifftrustactionuserS10(double difftrustactionuserS10) {
	this.difftrustactionuserS10 = difftrustactionuserS10;
}

public double getDifftrustactionfrauderS10() {
	return difftrustactionfrauderS10;
}

public void setDifftrustactionfrauderS10(double difftrustactionfrauderS10) {
	this.difftrustactionfrauderS10 = difftrustactionfrauderS10;
}

public double getDifftrustactionuserS15() {
	return difftrustactionuserS15;
}

public void setDifftrustactionuserS15(double difftrustactionuserS15) {
	this.difftrustactionuserS15 = difftrustactionuserS15;
}

public double getDifftrustactionfrauderS15() {
	return difftrustactionfrauderS15;
}

public void setDifftrustactionfrauderS15(double difftrustactionfrauderS15) {
	this.difftrustactionfrauderS15 = difftrustactionfrauderS15;
}

@Column(name="TrustActionFrauderS15",columnDefinition = "float default 0") private float TrustActionFrauderS15 ;
@Column(name="ScoreUserS5") private double ScoreUserS5;
@Column(name="ScoreUserS10") private double ScoreUserS10;
@Column(name="ScoreUserS15") private double ScoreUserS15;
@Column(name="ScoreFrauderS5") private double ScoreFrauderS5;
@Column(name="ScoreFrauderS10") private double ScoreFrauderS10;
@Column(name="ScoreFrauderS15") private double ScoreFrauderS15;
@Column(name="difftrustactionuserS5") private double difftrustactionuserS5;
@Column(name="difftrustactionfrauderS5") private double difftrustactionfrauderS5;
@Column(name="difftrustactionuserS10") private double difftrustactionuserS10;
@Column(name="difftrustactionfrauderS10") private double difftrustactionfrauderS10;
@Column(name="difftrustactionuserS15") private double difftrustactionuserS15;
@Column(name="difftrustactionfrauderS15") private double difftrustactionfrauderS15;

public double getScoreUserS5() {
	return ScoreUserS5;
}

public void setScoreUserS5(double scoreUserS5) {
	ScoreUserS5 = scoreUserS5;
}

public double getScoreUserS10() {
	return ScoreUserS10;
}

public void setScoreUserS10(double scoreUserS10) {
	ScoreUserS10 = scoreUserS10;
}

public double getScoreUserS15() {
	return ScoreUserS15;
}

public void setScoreUserS15(double scoreUserS15) {
	ScoreUserS15 = scoreUserS15;
}

public double getScoreFrauderS5() {
	return ScoreFrauderS5;
}

public void setScoreFrauderS5(double scoreFrauderS5) {
	ScoreFrauderS5 = scoreFrauderS5;
}

public double getScoreFrauderS10() {
	return ScoreFrauderS10;
}

public void setScoreFrauderS10(double scoreFrauderS10) {
	ScoreFrauderS10 = scoreFrauderS10;
}

public double getScoreFrauderS15() {
	return ScoreFrauderS15;
}

public void setScoreFrauderS15(double scoreFrauderS15) {
	ScoreFrauderS15 = scoreFrauderS15;
}

public EvalDiagno() {}

public EvalDiagno(String userName,double TrustUserS5,double TrustFrauderS5,double diffTrustUserS5,double diffTrustFrauderS5,double TrustUserS10,double TrustFrauderS10,double diffTrustUserS10,double diffTrustFrauderS10,double TrustUserS15,double TrustFrauderS15,double diffTrustUserS15,double diffTrustFrauderS15) {
this.userName=userName;
this.TrustUserS5=TrustUserS5;
this.TrustFrauderS5=TrustFrauderS5;
this.diffTrustUserS5=diffTrustUserS5;
this.diffTrustFrauderS5=diffTrustFrauderS5;

this.TrustUserS10=TrustUserS10;
this.TrustFrauderS10=TrustFrauderS10;
this.diffTrustUserS10=diffTrustUserS10;
this.diffTrustFrauderS10=diffTrustFrauderS10;

this.TrustUserS15=TrustUserS15;
this.TrustFrauderS15=TrustFrauderS15;
this.diffTrustUserS15=diffTrustUserS15;
this.diffTrustFrauderS15=diffTrustFrauderS15;


}

public float getTrustActionUserS5() {
	return TrustActionUserS5;
}

public void setTrustActionUserS5(float trustActionUserS5) {
	TrustActionUserS5 = trustActionUserS5;
}

public float getTrustActionUserS10() {
	return TrustActionUserS10;
}

public void setTrustActionUserS10(float trustActionUserS10) {
	TrustActionUserS10 = trustActionUserS10;
}

public float getTrustActionUserS15() {
	return TrustActionUserS15;
}

public void setTrustActionUserS15(float trustActionUserS15) {
	TrustActionUserS15 = trustActionUserS15;
}

public float getTrustActionFrauderS5() {
	return TrustActionFrauderS5;
}

public void setTrustActionFrauderS5(float trustActionFrauderS5) {
	TrustActionFrauderS5 = trustActionFrauderS5;
}

public float getTrustActionFrauderS10() {
	return TrustActionFrauderS10;
}

public void setTrustActionFrauderS10(float trustActionFrauderS10) {
	TrustActionFrauderS10 = trustActionFrauderS10;
}

public float getTrustActionFrauderS15() {
	return TrustActionFrauderS15;
}

public void setTrustActionFrauderS15(float trustActionFrauderS15) {
	TrustActionFrauderS15 = trustActionFrauderS15;
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public double getTrustUserS5() {
	return TrustUserS5;
}

public void setTrustUserS5(double trustUserS5) {
	TrustUserS5 = trustUserS5;
}

public double getTrustFrauderS5() {
	return TrustFrauderS5;
}

public void setTrustFrauderS5(double trustFrauderS5) {
	TrustFrauderS5 = trustFrauderS5;
}

public double getDiffTrustUserS5() {
	return diffTrustUserS5;
}

public void setDiffTrustUserS5(double diffTrustUserS5) {
	this.diffTrustUserS5 = diffTrustUserS5;
}

public double getDiffTrustFrauderS5() {
	return diffTrustFrauderS5;
}

public void setDiffTrustFrauderS5(double diffTrustFrauderS5) {
	this.diffTrustFrauderS5 = diffTrustFrauderS5;
}

public double getTrustUserS10() {
	return TrustUserS10;
}

public void setTrustUserS10(double trustUserS10) {
	TrustUserS10 = trustUserS10;
}

public double getTrustFrauderS10() {
	return TrustFrauderS10;
}

public void setTrustFrauderS10(double trustFrauderS10) {
	TrustFrauderS10 = trustFrauderS10;
}

public double getDiffTrustUserS10() {
	return diffTrustUserS10;
}

public void setDiffTrustUserS10(double diffTrustUserS10) {
	this.diffTrustUserS10 = diffTrustUserS10;
}

public double getDiffTrustFrauderS10() {
	return diffTrustFrauderS10;
}

public void setDiffTrustFrauderS10(double diffTrustFrauderS10) {
	this.diffTrustFrauderS10 = diffTrustFrauderS10;
}

public double getTrustUserS15() {
	return TrustUserS15;
}

public void setTrustUserS15(double trustUserS15) {
	TrustUserS15 = trustUserS15;
}

public double getTrustFrauderS15() {
	return TrustFrauderS15;
}

public void setTrustFrauderS15(double trustFrauderS15) {
	TrustFrauderS15 = trustFrauderS15;
}

public double getDiffTrustUserS15() {
	return diffTrustUserS15;
}

public void setDiffTrustUserS15(double diffTrustUserS15) {
	this.diffTrustUserS15 = diffTrustUserS15;
}

public double getDiffTrustFrauderS15() {
	return diffTrustFrauderS15;
}

public void setDiffTrustFrauderS15(double diffTrustFrauderS15) {
	this.diffTrustFrauderS15 = diffTrustFrauderS15;
}









}