package com.algo;

import java.util.Collection;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONObject;

import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.Obsel;
import com.ignition_factory.ktbs.bean.Trace;
import com.model.ActionTrust;
import com.model.AuthentificationResult;
import com.model.User;


public class ExperienceDSEUIL {
	
	 static SessionFactory factory=HibernateUtil.getFactory();
   	 static Session sessionHibernate=factory.openSession();
	
   	 
   	 
	public static void main(String[] args) throws Exception {
		
		// List list = sessionHibernate.createSQLQuery("select distinct (user_name ) from AuthTraac.sessions \n" + 
		// 		"								where id_session in ( select id_session from AuthTraac.action_trust group by id_session having count(*)>30);").list();
		 List list = sessionHibernate.createSQLQuery ("select user_profil_name from AuthTraac.Authentification_result where user_profil_name = user_signature_name and dthreshold=1").list();
		 
		 for (Object  userNameProfil : list ) {		
			 
			  
			 
			Base baseProfil = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/"+userNameProfil);
			Trace RessourcesInteractionActions = new Trace (baseProfil.get_uri()+"RessourceInteractionAction/") ;
			Collection<Obsel> listObselRessourceInterraction = ktbsService.getListObsel(baseProfil,null, null,"RessourceInteractionAction" );
			
			for (Object  userNameSignature : list ) {
				sessionHibernate.beginTransaction() ;
				float trustAction = 0;
					Base baseSignature = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/"+userNameSignature);
					Trace RessourceInteractionActionSignature = new Trace (baseSignature.get_uri()+"RessourceInteractionActionSignature/") ;
					Collection<Obsel> obselsignature = ktbsService.getListObsel(baseSignature,null, null,"RessourceInteractionActionSignature" );
					System.out.println ("user profil     :  "+(String)userNameProfil + "  userSignature    :  "+ (String)userNameSignature);
					
					AuthentificationResult authentificationResult = new AuthentificationResult((String)userNameProfil,(String)userNameSignature,trustAction,2,listObselRessourceInterraction.size());
					for (Obsel action : listObselRessourceInterraction ) {

						float distance = AuthentificationComportementApprentissage.calculDistance (action, obselsignature);
						trustAction = AuthentificationComportementApprentissage.calculateCoefficient(distance, action, trustAction, baseSignature.get_uri()+"RessourceInteractionActionSignature/");
						JSONObject actionTrustJson = new JSONObject();
						actionTrustJson.put("id_action", action.get_id());
				        actionTrustJson.put("type_action", action.get_obsel_type().getTypeId());
				        actionTrustJson.put("begin", action.get_begin());
				        actionTrustJson.put("distance", distance);
				        actionTrustJson.put ("end",action.get_end());
				        actionTrustJson.put("trust", trustAction);
				        ActionTrust actionTrust = new ActionTrust ((String) actionTrustJson.get("id_action"),(String) actionTrustJson.get("type_action") , (Integer) actionTrustJson.get("begin") , (Integer) actionTrustJson.get("end"), (double)actionTrustJson.get("distance") ,(double) actionTrustJson.get("trust"),authentificationResult );
				        sessionHibernate.save(actionTrust);
					}
					authentificationResult.setTrust(trustAction);
					sessionHibernate.save(authentificationResult);
					 sessionHibernate.getTransaction().commit();
			}
			
		 }
	}

}
