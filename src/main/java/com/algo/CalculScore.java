package com.algo;


import java.util.Collection;

import java.util.List;
import java.util.*;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.ignition_factory.ktbs.bean.Base;

import com.ignition_factory.ktbs.bean.Obsel;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

import com.model.EvalDiagno;
import com.model.Sessions;
import com.sun.istack.logging.Logger;

public class CalculScore {
	public static void main(String[] args) throws Exception {
		
		
		SessionFactory factory=HibernateUtil.getFactory();
   	 Session sessionHibernate=factory.openSession();
	
   	 CriteriaBuilder cb = sessionHibernate.getCriteriaBuilder();
   	 CriteriaQuery<EvalDiagno> cq = cb.createQuery(EvalDiagno.class);
   	 Root<EvalDiagno> rootEntry = cq.from(EvalDiagno.class);
   	 CriteriaQuery<EvalDiagno> all = cq.select(rootEntry);
   	 TypedQuery<EvalDiagno> allQuery = sessionHibernate.createQuery(all);
   	 List<EvalDiagno> SessionList = allQuery.getResultList();
  
   	  
  
   	 for (EvalDiagno EvalDiagno1 : SessionList) {
   		
   	
   		SessionFactory factory1=HibernateUtil.getFactory();
     	 Session session1=factory.openSession();

   	
    Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/"+EvalDiagno1.getUserName());
   	Collection<Obsel> obselSignature = ktbsService.getListObsel(base,null, null,"RessourceInteractionActionSignature");
   	

   	Double normatTrustsAppS100 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDifftrustactionuserS10());
   	Double normalDiffTrustS100 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDiffTrustUserS10());
   System.out.println(EvalDiagno1.getDifftrustactionuserS10());
   	System.out.println(normatTrustsAppS100);
   	System.out.println("--------");
    double ScoreUserS10=DiagnosticService.DegreConfTotal(normatTrustsAppS100, normalDiffTrustS100);
   	
   	Double normatTrustsAppS10 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDifftrustactionfrauderS10());
   	Double normalDiffTrustS10 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDiffTrustFrauderS10());
 
   	
    double ScoreFrauderS10=DiagnosticService.DegreConfTotal(normatTrustsAppS10, normalDiffTrustS10);

    
    
    
 	Double normatTrustsAppS50 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDifftrustactionuserS5());
   	Double normalDiffTrustS50 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDiffTrustUserS5());
   
    double ScoreUserS5=DiagnosticService.DegreConfTotal(normatTrustsAppS50, normalDiffTrustS50);
   
   	
   	
   	Double normatTrustsAppS5 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDifftrustactionfrauderS5());
   	Double normalDiffTrustS5 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDiffTrustFrauderS5());
   	
    double ScoreFrauderS5=DiagnosticService.DegreConfTotal(normatTrustsAppS5, normalDiffTrustS5);
  
    
  
    Double normatTrustsAppS150 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDifftrustactionuserS15());
   	Double normalDiffTrustS150 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDiffTrustUserS15());
   
    double ScoreUserS15=DiagnosticService.DegreConfTotal(normatTrustsAppS150, normalDiffTrustS150);
   	
   	
   	Double normatTrustsAppS15 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDifftrustactionfrauderS15());
   	Double normalDiffTrustS15 = DiagnosticService.NormalTrustCompoAppr(EvalDiagno1.getDiffTrustFrauderS15());
   	
    double ScoreFrauderS15=DiagnosticService.DegreConfTotal(normatTrustsAppS15, normalDiffTrustS15);
   
 	sessionHibernate.beginTransaction();
    ((com.model.EvalDiagno)EvalDiagno1).setScoreUserS5(ScoreUserS5);
    ((com.model.EvalDiagno)EvalDiagno1).setScoreUserS10(ScoreUserS10);
    ((com.model.EvalDiagno)EvalDiagno1).setScoreUserS15(ScoreUserS15);
    
    EvalDiagno1.setScoreFrauderS15(ScoreFrauderS15);
    EvalDiagno1.setScoreFrauderS5(ScoreFrauderS5);
    EvalDiagno1.setScoreFrauderS10(ScoreFrauderS10);
    sessionHibernate.update(EvalDiagno1);
    sessionHibernate.getTransaction().commit();
   		 
   		 
   		 
   		 
   		 
   		 
   		 
   		 
   		 
   	 }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}}

