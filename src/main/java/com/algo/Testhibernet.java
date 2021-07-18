package com.algo;

import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;



import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.Obsel;
import com.model.EvalDiagno;


public class Testhibernet extends ExperienceApprentissage {

	public static void main(String[] args) throws Exception {
		
		SessionFactory factory=HibernateUtil.getFactory();
   	 Session session=factory.openSession();
   	 

List list = session.createSQLQuery("SELECT user_name from authtraac.Sessions group by user_name HAVING  count(*) >20").list();

Base base23 = new Base("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/user1594673103047229441/");
Collection<Obsel> listObselSession1= ktbsService.getListObsel(base23,null, null,"FormationSessionInteractionBehaviors" );
Obsel obselFrauder = (Obsel) listObselSession1.toArray()[5];








for (Object  userName : list ) {

	

	Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/"+userName);


Collection<Obsel> listObselSession= ktbsService.getListObsel(base,null, null,"FormationSessionInteractionBehaviors" );
Collection<Obsel> obselSignature = ktbsService.getListObsel(base,null, null,"FormationSessionInteractionBehaviorsSignature");

Query query = session.createSQLQuery("SELECT trust from authtraac.Sessions where user_name=:name and session_index=:index");
query.setParameter("name", userName);
query.setParameter("index", "session5");

Query query2 = session.createSQLQuery("SELECT trust from authtraac.Sessions where user_name=:name and session_index=:index");
query2.setParameter("name", userName);
query2.setParameter("index", "session10");

Query query3 = session.createSQLQuery("SELECT trust from authtraac.Sessions where user_name=:name and session_index=:index");
query3.setParameter("name", userName);
query3.setParameter("index", "session15");

double TrustS5 = (double) query.getSingleResult();

double TrustS10 = (double) query2.getSingleResult();

double TrustS15 = (double) query3.getSingleResult();







if (listObselSession.size()>20) {

Query query4 = session.createSQLQuery("SELECT trust from authtraac.Sessions where user_name=:name and session_index=:index");
query4.setParameter("name", userName);
query4.setParameter("index", "session4");
double TrustS4 = (double) query4.getSingleResult();

Query query5 = session.createSQLQuery("SELECT trust from authtraac.Sessions where user_name=:name and session_index=:index");
query5.setParameter("name", userName);
query5.setParameter("index", "session9");
double TrustS9 = (double) query5.getSingleResult();

Query query6 = session.createSQLQuery("SELECT trust from authtraac.Sessions where user_name=:name and session_index=:index");
query6.setParameter("name", userName);
query6.setParameter("index", "session14");
double TrustS14 = (double) query6.getSingleResult();


double TrustFrauderS5=CalculTrust(obselSignature,obselFrauder,TrustS4);
double diffTrustUserS5=TrustS5-TrustS4;
double diffTrustFrauderS5= TrustFrauderS5-TrustS4;

double TrustFrauderS10=CalculTrust(obselSignature,obselFrauder,TrustS9);
double diffTrustUserS10=TrustS10-TrustS9;
double diffTrustFrauderS10= TrustFrauderS10-TrustS9;


double TrustFrauderS15=CalculTrust(obselSignature,obselFrauder,TrustS14);
double diffTrustUserS15=TrustS15-TrustS14;
double diffTrustFrauderS15= TrustFrauderS15-TrustS14;



session.beginTransaction();
EvalDiagno Test= new EvalDiagno((String) userName,TrustS5,TrustFrauderS5,diffTrustUserS5,diffTrustFrauderS5,TrustS10,TrustFrauderS10,diffTrustUserS10,diffTrustFrauderS10,TrustS15,TrustFrauderS15,diffTrustUserS15,diffTrustFrauderS15);
session.save(Test);
session.getTransaction().commit();
System.out.println("done");

}





   		 
   	 
	
  	 
   		
	
   	 
   	
	}}}
