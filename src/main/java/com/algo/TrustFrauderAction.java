package com.algo;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.*;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.HttpConnectionKtbs;
import com.ignition_factory.ktbs.bean.Obsel;
import com.ignition_factory.ktbs.bean.Trace;
import org.hibernate.Session;
import org.hibernate.SessionFactory;



import com.model.EvalDiagno;


public class TrustFrauderAction {
	public static void main(String[] args) throws Exception {
		 SessionFactory factory=HibernateUtil.getFactory();
    	 Session sessionHibernate=factory.openSession();
	
    	 CriteriaBuilder cb = sessionHibernate.getCriteriaBuilder();
    	 CriteriaQuery<EvalDiagno> cq = cb.createQuery(EvalDiagno.class);
    	 Root<EvalDiagno> rootEntry = cq.from(EvalDiagno.class);
    	 CriteriaQuery<EvalDiagno> all = cq.select(rootEntry);
    	 TypedQuery<EvalDiagno> allQuery = sessionHibernate.createQuery(all);
    	 List<EvalDiagno> SessionList = allQuery.getResultList();
    	 
    	 

		Base base23 = new Base("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/user1594673103047229441/");
		Collection<Obsel> listObselSession1= ktbsService.getListObsel(base23,null, null,"FormationSessionInteractionBehaviors" );
		Obsel obselFrauder = (Obsel) listObselSession1.toArray()[6];
		
		
		

	Collection<Obsel> listAction = ktbsService.getListObsel(base23, null,null, "RessourceInteractionAction");
Integer begin = (Integer)obselFrauder.get_begin();
Integer end =  (Integer) obselFrauder.get_end();

List<Obsel> listAction1=new ArrayList<>();
for (int j=0;j<listAction.size();j++) {
if ((((Obsel) listAction.toArray()[j]).get_begin()>=begin)&& (end >=(Integer)((Obsel)listAction.toArray()[j]).get_end())) {
listAction1.add((Obsel) listAction.toArray()[j]);
}

}



	

int i=0;	
for (EvalDiagno evalDiagno : SessionList ) {
	sessionHibernate.beginTransaction();
	SessionFactory factory1=HibernateUtil.getFactory();
  	 Session session1=factory1.openSession();

	
	Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/"+evalDiagno.getUserName());
	Collection<Obsel> obselSignature = ktbsService.getListObsel(base,null, null,"RessourceInteractionActionSignature");
	
	Query query=session1.createSQLQuery("SELECT trust_action_final from authtraac.sessions where user_name=:name and session_index=:index");
	query.setParameter("name", evalDiagno.getUserName());
	query.setParameter("index", "session10");
	float trustAction100 = (float)(double) query.getSingleResult();
	
	
	 evalDiagno.setTrustActionUserS10(trustAction100);
	
	Query query2=session1.createSQLQuery("SELECT trust_action_final from authtraac.sessions where user_name=:name and session_index=:index");
	query2.setParameter("name", evalDiagno.getUserName());
	query2.setParameter("index", "session5");
	float trustAction50 = (float)(double) query2.getSingleResult();

	evalDiagno.setTrustActionUserS5(trustAction50);
	
	Query query3=session1.createSQLQuery("SELECT trust_action_final from authtraac.sessions where user_name=:name and session_index=:index");
	query3.setParameter("name", evalDiagno.getUserName());
	query3.setParameter("index", "session15");
	float trustAction150 = (float)(double) query3.getSingleResult();
	
	evalDiagno.setTrustActionUserS15(trustAction150);
	
	Query query4=session1.createSQLQuery("SELECT trust_action_final from authtraac.sessions where user_name=:name and session_index=:index");
	query4.setParameter("name", evalDiagno.getUserName());
	query4.setParameter("index", "session9");
	float trustAction1 = (float)(double) query4.getSingleResult();



	Query query5=session1.createSQLQuery("SELECT trust_action_final from authtraac.sessions where user_name=:name and session_index=:index");
	query5.setParameter("name", evalDiagno.getUserName());
	query5.setParameter("index", "session4");
	float trustAction2 = (float)(double) query5.getSingleResult();
	


	Query query6=session1.createSQLQuery("SELECT trust_action_final from authtraac.sessions where user_name=:name and session_index=:index");
	query6.setParameter("name", evalDiagno.getUserName());
	query6.setParameter("index", "session14");
	float trustAction3 = (float)(double) query6.getSingleResult();
	

	double difftrustactionuserS10= trustAction100-trustAction1;
	double difftrustactionuserS5= trustAction50-trustAction2;
	double difftrustactionuserS15= trustAction150-trustAction3;
	evalDiagno.setDifftrustactionuserS5(difftrustactionuserS5);
	evalDiagno.setDifftrustactionuserS10(difftrustactionuserS10);
	evalDiagno.setDifftrustactionuserS15(difftrustactionuserS15);

	
	
	
	float trustAction10=0;
	float trustAction5=0;
	float trustAction15=0;


	 
for (int j=0 ; j<listAction1.size(); j++) {
	
Obsel action = (Obsel) listAction1.toArray()[j];


float distance = AuthentificationComportementApprentissage.calculDistance (action, obselSignature);
System.out.println(distance);
trustAction10 = AuthentificationComportementApprentissage.calculateCoefficient(distance, action,trustAction1, base.get_uri()+"RessourceInteractionActionSignature/");
 trustAction5=AuthentificationComportementApprentissage.calculateCoefficient(distance, action,trustAction2, base.get_uri()+"RessourceInteractionActionSignature/");
 trustAction15=AuthentificationComportementApprentissage.calculateCoefficient(distance, action,trustAction3, base.get_uri()+"RessourceInteractionActionSignature/");
trustAction1=trustAction10;
trustAction2=trustAction5;
trustAction3=trustAction15;
}
evalDiagno.setTrustActionFrauderS10(trustAction10);
evalDiagno.setTrustActionFrauderS5(trustAction5);
evalDiagno.setTrustActionFrauderS15(trustAction15);

double difftrustactionfrauderS10= trustAction10-trustAction1;
double difftrustactionfrauderS5= trustAction5-trustAction2;
double difftrustactionfrauderS15= trustAction15-trustAction3;
evalDiagno.setDifftrustactionfrauderS5(difftrustactionfrauderS5);
evalDiagno.setDifftrustactionfrauderS10(difftrustactionfrauderS10);
evalDiagno.setDifftrustactionfrauderS15(difftrustactionfrauderS15);
sessionHibernate.update(evalDiagno);
sessionHibernate.getTransaction().commit();
System.out.println ("update sessions " + i);
i++;



}}}
