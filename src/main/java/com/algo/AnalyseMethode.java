package com.algo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.Obsel;
import com.ignition_factory.ktbs.bean.Trace;
import com.model.Cluster;
import com.model.ElBow;
import com.model.User;





public class AnalyseMethode {
	static Double[] weigth = {0.3,0.2,0.2,0.1,0.1,0.1} ;
	 static SessionFactory factory=HibernateUtil.getFactory();
   	 static Session sessionHibernate=factory.openSession();
	
	public static double calculdistance (Obsel Session, Obsel barycentre) throws NumberFormatException, JSONException {
		 
		
		double d = (double)
					(
					weigth [5] * simulairString (Session.getDataJson().getString("m:TextBehavior"),barycentre.get_jsonObsel().getJSONArray("m:listTextBehavior"))
					+ weigth [4] * simulairString (Session.getDataJson().getString("m:ForumBehavior"), barycentre.get_jsonObsel().getJSONArray("m:listForumBehavior"))
					+weigth [3] * simulairString (Session.getDataJson().getString("m:ExerciceBehavior"),barycentre.get_jsonObsel().getJSONArray("m:listExerciceBehavior"))
					+ weigth [1] * similaritie.simulairDuration (Double.parseDouble (Session.getDataJson().getString("m:DurationTotalAllRessource")),Double.parseDouble (barycentre.get_jsonObsel().getString("m:AveragedurationTotalRessource")))
					+ weigth [0] * similaritie.simulairDuration (Double.parseDouble (Session.getDataJson().getString("m:duration")),Double.parseDouble (barycentre.get_jsonObsel().getString("m:Averageduration")))
					+ weigth [2] * similaritie.simulairDuration (Double.parseDouble (Session.getDataJson().getString("m:nbConsultationAllRessources")),Double.parseDouble (barycentre.get_jsonObsel().getString("m:AveragenbConsultationAllRessources"))));
							             
		 
		 
		  return d ;
		  
	  }
	
	 public static double simulairString (String str,  JSONArray jsonArray ) throws JSONException {
		// JSONObject jsnobject = new JSONObject (str2);

		// JSONArray jsonArray = new JSONArray(str2);
		  for  (int i=0; i<jsonArray.length();i++ ) {
			  if ((jsonArray.get(i)).equals(str)) return 1 ; 
		  }
		  
		  return 0;
		  
	  }
	 
	public static void elbow (Collection<Obsel> ArrayCluster, Double SimSeuil , String userName ) throws Exception {
		Double SommeIndicteur= 0.0;
			int nbIndicatur = 0;
			Double SommeBow = 0.0 ;
			 for (int k=0; k<ArrayCluster.size();k++) {
				  Double sommeDistance2 =0.0 ;
				  Obsel currentcluster = ((Obsel)ArrayCluster.toArray()[k]);

				  String sessionlist = currentcluster.get_attribute("listSession").getValue();
					 JSONArray sessionlistjsonArray = new JSONArray(sessionlist);

				  for (int j=0; j< sessionlistjsonArray.length(); j++) {
					 if (!((String)sessionlistjsonArray.get(j)).equals("")) {
						 Obsel session = new Obsel ((String)sessionlistjsonArray.get(j));
						 session.force_state_refresh();
						sommeDistance2 += Math.pow(1- calculdistance (session,currentcluster), 2);
						//System.out.println ("distance entre "+currentcluster.get_jsonObsel().getString("m:name") +" et session :"+session.getDataJson().getString("@id"));
						System.out.println(sommeDistance2);
					 }

				  }
				  SommeBow+= sommeDistance2 ;	  
			 }
			 ElBow elbow = new ElBow (SimSeuil,SommeBow,userName);
			 sessionHibernate.beginTransaction() ;

			 sessionHibernate.save(elbow);
	    	   sessionHibernate.getTransaction().commit();
			 System.out.println("La distance pow est : "+ SommeBow);
		}
	
	 
	public static Trace clustering (Trace FormationSessionInteractionBehaviors, Base base) throws Exception {
   		
		
		Trace RessourceInteractionActionSignature = new Trace (base.get_uri()+"FormationSessionInteractionBehaviorsSignature03/");
		RessourceInteractionActionSignature.remove();
		Trace TraceSign = ktbsService.createPrimaryTrace (base,"modelprimary","FormationSessionInteractionBehaviorsSignature03",null);

   				
   				//new Trace (base.get_uri()+"FormationSessionInteractionBehaviorsSignature0.4/");
   		
		Collection<Obsel> listSession = ktbsService.getListObsel(base ,null, null,"FormationSessionInteractionBehaviors" );
		 for (int i=0; i<listSession.size();i++) {
			 
		Obsel lastSession = ((Obsel)listSession.toArray()[i]);
		Collection<Obsel> obselSignature = ktbsService.getListObsel(base,null, null,TraceSign.get_Name() );
		   // traitement pour First session 
        if (obselSignature.isEmpty()) {
            JSONObject FirstclusterBArycentre = AuthentificationComportementApprentissage.calculbarycentre (null, lastSession,(int)obselSignature.size()+1,(double)0);
			ktbsService.createObsel(base.get_Name(), TraceSign.get_Name(),FirstclusterBArycentre);
           

		}else  {
        // Autre session 
            double [] distance = new double [100] ;
			double [] Poid = new double [100] ;
			Double score =0.0;
			Double[] weigth = {0.3,0.2,0.2,0.1,0.1,0.1} ;
			Double  k = 0.3 ;
			//float trust =1;
			JSONArray detailsDistances = new JSONArray ();
			/** Calculer distance par rapport tous les clusters dans trace Signature **/
			double sommePoid = 0 ;
			for (int j=0; j< obselSignature.size(); j++) {
				double Simularite = AuthentificationComportementApprentissage.calculSimularitySession (lastSession.get_jsonObsel(),((Obsel) obselSignature.toArray()[j]).get_jsonObsel(),weigth);
				//System.out.println ("distance entre " + C.get("Name") + " et " + sessionActuel.get("Name") + " est = "+ dist ) ;
				distance [j] = Simularite ;
				Poid [j] = Double.parseDouble ((String) ((Obsel) obselSignature.toArray()[j]).get_attribute("poid").getValue()) ;
				/*/** calculer score  for trust **/
				score = score + Poid [j]*Simularite;
				sommePoid+=Poid [j];
				// Objet distance details
				//System.out.println ("comparaison cluster " + ((Obsel) obselSignature.toArray()[j]).get_id() +"le poid du cluster est "+ Poid [j]+ " distance est : " + Simularite);
				JSONObject ObjdetailsDistances = new JSONObject();
				ObjdetailsDistances.put("clusterID ", ((Obsel) obselSignature.toArray()[j]).get_id());
				ObjdetailsDistances.put("Simularité", Simularite);	
				ObjdetailsDistances.put("Poid", Poid [j]);	
				detailsDistances.put(ObjdetailsDistances);
			}
            System.out.println ("##################### Détailles Distance ############################");
			System.out.println (detailsDistances.toString());
            score = score/sommePoid;
			JSONObject Objscore = new JSONObject();
			Objscore.put("score ", score);
            detailsDistances.put(Objscore);
			Double SimMax = AuthentificationComportementApprentissage.MaxTableau (distance);
			int indexMax =  AuthentificationComportementApprentissage.MaxIndex (distance) ;
			// Clustering and Calcul Trust
			if (SimMax>k) {
				System.out.println ("mise à jour barycenter Signture");
				// Mise à jour l'obsel Cluster de la trace signature 
				Obsel AncienBarycenter = ((Obsel) obselSignature.toArray()[indexMax]);
				// Supprimer Obsel du trace Signature 
				Obsel obs = ((Obsel) obselSignature.toArray()[indexMax]);
				obs.remove() ;
				// create nouveau obsel ==> obsel de mise à jour 
				JSONObject NouveauclusterBArycentre = AuthentificationComportementApprentissage.calculbarycentre (AncienBarycenter, lastSession,indexMax+1,SimMax);
				ktbsService.createObsel(base.get_Name() , TraceSign.get_Name(),NouveauclusterBArycentre);
				//trust = (float) (trust + score) ;
				
			}else {
				System.out.println ("creer nouveau cluster");
				// create un nouveau Cluster ==> un nouveau obsel dans signature 
				JSONObject NouveauclusterBArycentre = AuthentificationComportementApprentissage.calculbarycentre (null, lastSession,(int)obselSignature.size()+1,SimMax);
				ktbsService.createObsel(base.get_Name() , TraceSign.get_Name(), NouveauclusterBArycentre);
			//	trust = (float) (trust * score) ;
			
			}
				
		 }
   		
   		
   	}
		 return TraceSign;
	}
	
	public static void main(String[] args) throws Exception {
			
   	
	 @SuppressWarnings("unchecked")
	 List list = sessionHibernate.createSQLQuery("SELECT user_name from AuthTraac.Sessions group by user_name HAVING  count(*) >5 and  min(diff_trust_action_final) >=0 ;").list();

	 double SimSeuil = 0.3 ;
		 for (Object  userName : list ) {
		
		Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/"+userName);
		Trace FormationSessionInteractionBehaviors = new Trace (base.get_uri()+"FormationSessionInteractionBehaviors/") ;
		
		Trace TraceSign = clustering (FormationSessionInteractionBehaviors, base);
				//new Trace (base.get_uri()+"FormationSessionInteractionBehaviorsSignature/"); 
			//	
				
				
				//clustering (FormationSessionInteractionBehaviors, base);
				//new Trace (base.get_uri()+"FormationSessionInteractionBehaviorsSignature/"); 
				
				//
				//
		
		Collection<Obsel> listCLsuter = ktbsService.getListObsel(base,null, null,TraceSign.get_Name());
		elbow (listCLsuter, SimSeuil, (String) userName);
		 
		 }
	 }
}
