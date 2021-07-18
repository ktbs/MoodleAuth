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