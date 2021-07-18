package com.algo;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ignition_factory.ktbs.bean.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AlgoKmeans {

	//public static Double  weigth [] = {0.3,0.3,0.1,0.1,0.2};
	//static double [] poid = new double [100] ;
	static List <Double> poid= new ArrayList<>(); 
	static int nbCluster =0 ;
	
	static JSONArray ArrayCluster = new JSONArray() ;
	static double trust = 1;
	
	static List <Double> T = new ArrayList<>(); 
	static List <Double> D = new ArrayList<>();
	static List <Double> S= new ArrayList<>();
	 
	 static JSONObject graph = new JSONObject();
	 static JSONArray nodes = new JSONArray() ;
	 static JSONArray links = new JSONArray() ;
	  
	
	 
	 
	 public static JSONObject getsession (JSONArray ArraySession , String Name) throws JSONException {
		  for (int j=0 ; j<ArraySession.length(); j++) {
			  JSONObject session = (JSONObject)ArraySession.get(j) ;
			  if (session.get("@id").equals(Name)) return (JSONObject) ArraySession.get(j);
		  }
		  return null;
	  }
	 
	 @SuppressWarnings("unchecked")
	 public static void algoKmeans(JSONArray ArraySession, double SimSeuil) throws NumberFormatException, JSONException {
	 	  
	 	  
	 	  
	 	  for (int i = 0; i < ArraySession.length() ; i++) {
	 		    System.out.println("************************************************************") ;
	 		  	JSONObject sessionActuel = (JSONObject) ArraySession.get(i);
	 		  	
	 		  	/** Initialization First Cluster **/
	 		  	
	 		  	if (i==0) {
	 		  		JSONObject cluster = new JSONObject();
	 		  		/** put Barycentre **/
	 		  		//cluster.put("barycentre", sessionActuel) ;
	 		  		JSONObject NouveauBarycentre = Clusters.calculbarycentre (null ,sessionActuel ) ;
	 		  		cluster.put("barycentre", NouveauBarycentre) ;
	 		  		
	 		  		/** put list of list **/
	 		  		List<String> SessionCluster = new ArrayList<String>();
	 		  		SessionCluster.add ((String) sessionActuel.get("@id"));
	 		  		cluster.put("Session", SessionCluster) ;
	 		  		/** put Name **/
	 		  		int index = ArrayCluster.length() +1 ;
	 		  		cluster.put("Name", "C"+index) ;
	 		  		cluster.put("Poid", "1") ;
	 		  		/** add to arrayCluster **/
	 		  		ArrayCluster.put(cluster);
	 		  		T.add(trust)  ;
	 		  		
	 		  	}
	 		  	
	 		  	/** Other Cluster **/
	 		  	else {
	 		  		double [] distance = new double [100] ;
	 		  		double [] Poid = new double [100] ;
	 		  		/** Calculer distance par rapport tous les clusters **/
	 		  		for (int j=0; j< ArrayCluster.length(); j++) {
	 		  			JSONObject C = (JSONObject) ArrayCluster.get(j);
	 		  			double dist = Clusters.calculdistance (sessionActuel,(JSONObject) C.get("barycentre")); 
	 		  			System.out.println ("distance entre " + C.get("Name") + " et " + sessionActuel.get("@id") + " est = "+ dist ) ;
	 		  		    distance [j] = dist ;
	 		  		    Poid [j] = Double.parseDouble ((String) C.get("Poid")) ;
	 		  		}
	 		  		
	 		  		/** calculer score  for trust **/
	 		  		Double s =0.0;
	 		  		for (int k=0; k<ArrayCluster.length(); k++) {
	 		  			s = s + Poid[k]*distance[k] ;
	 		  		}
	 		  		Double P=0.0;
	 		  		for (int k=0; k<Poid.length ; k++) {
	 		  			P = P + Poid[k] ;
	 		  		}
	 		  		Double score = s/P ;
	 		  		System.out.println("score  =" + score) ;
	 		  		
	 		  		
	 		  		Double distanceMax = similaritie.MaxTableau (distance) ;
	 		  		int indexMax =  similaritie.MaxIndex (distance) ;
	 		  		/** calculer Trust 
	 		  		 */
	 		  		if (distanceMax > SimSeuil) {
	 		  			trust = trust + score ;
	 		  			System.out.println("trust  ="+ trust);
	 		  		}else {
	 		  			
	 		  			trust = trust*score ;
	 		  			System.out.println("trust  ="+ trust);
	 		  		}
	 		  		//System.out.println("distanceMax = "+ distanceMax);
	 		  		/** regroupement cluster **/
	 		  		
	 		  		if (distanceMax > SimSeuil) {
	 		  			/**  ajouter session actuel au cluster **/
	 		  			
	 		  			JSONObject C = (JSONObject) ArrayCluster.get(indexMax);
	 		  			
	 		  			System.out.println ("mise à jour cluster"+C.get("Name"));
	 		  			
	 		  			JSONArray SessionCluster = (JSONArray) C.get("Session") ;
	 		  			SessionCluster.put ((String) sessionActuel.get("@id"));
	 		  			C.put("Session", SessionCluster) ;
	 		  			/** calculer le nouveau barycentre **/
	 		  			JSONObject NouveauBarycentre = Clusters.calculbarycentre ((JSONObject) C.get("barycentre"),sessionActuel ) ;
	 		  			C.put("barycentre", NouveauBarycentre) ;
	 		  			
	 		  			/** mettre a jour le poid **/
	 		  			Double nouveauPoid = Double.parseDouble ((String) C.get("Poid")) + distanceMax ;
	 		  			C.put("Poid", Double.toString (nouveauPoid)) ;
	 		  		    /** Create nouveau cluster **/
	 		  		}else {
	 		  			
	 		  			JSONObject cluster = new JSONObject();
	 			  		/** put Barycentre **/
	 			  		JSONObject NouveauBarycentre = Clusters.calculbarycentre (null ,sessionActuel ) ;
	 			  		cluster.put("barycentre", NouveauBarycentre) ;
	 			  		
	 			  		/** put list of list **/
	 			  		List<String> SessionCluster = new ArrayList<String>();
	 			  		SessionCluster.add ((String) sessionActuel.get("Name"));
	 			  		cluster.put("Session", SessionCluster) ;
	 			  		/** put Name **/
	 			  		int index = ArrayCluster.length()+1 ;
	 			  		cluster.put("Name", "C"+index) ;
	 			  		cluster.put("Poid", "0.1") ;
	 			  		/** add to arrayCluster **/
	 			  		ArrayCluster.put(cluster);
	 	
	 			  		
	 		  		}
	 		  	
	 		  		T.add (trust ); 
	 				S.add (score );
	 				D.add(distanceMax) ;
	 		  	}
	 	
	 			 }
	 	  	
} 
	 
	
	 
	 
	 @SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		  //JSONArray ArraySession = ParseFile ("/Users/derbelfatma/eclipse-workspace/Auth/src/SynthetiqueSession.json") ;
	Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/jamila2jamila2cff4529c-3654-473c-905e-03d025a9a719/");
	//Trace trace = ktbsService.createPrimaryTrace(base, "modelprimary", "Formations-Sessions-Interaction-Behaviors", null) ;
	Collection<Obsel> listObsel = ktbsService.getListObsel(base,null, null,"Formations-Sessions-Interaction-Behaviors" );
	
	JSONArray ArraySession = new JSONArray();
    for (Obsel obs: listObsel) {
    	ArraySession.put(obs.get_jsonObsel());
    }
    System.out.println(ArraySession);
		  
		  algoKmeans (ArraySession,0.6) ;
		 
		  System.out.println("Liste des trusts " );
	 	  for (int k=0; k<T.size();k++) {
	 		  System.out.println (T.get(k)) ;
	 	  }
	 	  
	 	 System.out.println("Liste des score " );
	 	  for (int k=0; k<S.size();k++) {
	 		  System.out.println (S.get(k)) ;
	 	  }

	 	  System.out.println("La lister des clusters " );
	 	  for (int k=0; k<ArrayCluster.length();k++) {
	 		  System.out.println (ArrayCluster.get(k)) ;
	 	  }
	 	  System.out.println("fin liste cluster " );

	 	  // la liste des noeud et link noeud 
	 	  JSONArray allinks = new JSONArray() ;
	 	  JSONArray linksnoeud = new JSONArray() ;
	 	  JSONArray linkscluster = new JSONArray() ;
	 	  for (int k=0; k<ArrayCluster.length();k++) {
	 		   JSONObject cluster = (JSONObject)ArrayCluster.get(k);
	 		   JSONObject C = (JSONObject) ArrayCluster.get(k);
	 		  JSONArray sessionArray = (JSONArray) cluster.get("Session") ;
	 		   JSONObject node = new JSONObject() ;
	 		   node.put("id","barycentre"+k);  node.put("group",k);node.put("type","centroide");
	 		   nodes.put(node) ;
	 		   for (int j=0; j< sessionArray.length() ; j++) {
	 			   JSONObject nodee = new JSONObject() ;
	 			   JSONObject link = new JSONObject() ;
	 			   nodee.put("id",sessionArray.get(j));  nodee.put("group",k); nodee.put("type","point");
	 			   link.put("source","barycentre"+k);  
	 			   link.put("target",sessionArray.get(j));
	 			   link.put("value",5) ;
	 			   link.put("distance",100 - 100*Clusters.calculdistance ( getsession (ArraySession ,(String) sessionArray.get(j)),(JSONObject)C.get("barycentre")));
	 			   nodes.put(nodee) ;
	 			   allinks.put(link);
	 			   linkscluster.put(link);
	 		   }
	 		 }
	 	  // link pour tous les barycentre 
	 	  for (int k=0; k<ArrayCluster.length();k++) {
	 		  JSONObject C = (JSONObject) ArrayCluster.get(k);
	 		  for (int j=k+1; j<ArrayCluster.length() ;j++) {
	               JSONObject Ci = (JSONObject) ArrayCluster.get(j);
	 			  
	 			  JSONObject linke = new JSONObject() ;
	 			  linke.put("source","barycentre"+k);  
	 			  linke.put("target","barycentre"+j);
	 			  linke.put("value",1) ;
	 			  
	 			 linke.put("distance",100-100*Clusters.calculdistanceBarycentre ( (JSONObject)Ci.get("barycentre") ,(JSONObject)C.get("barycentre")));
	 			 //  System.out.println (calculdistanceBarycentre ( (JSONObject)Ci.get("barycentre") ,(JSONObject)C.get("barycentre"))) ;
	 			 allinks.put(linke);
	 			 linkscluster.put(linke);
	 			  
	 		  }
	 	  }
	 	  // link pour tous les sessions 
	 		  for (int p=0; p<ArraySession.length();p++) {
	 			  JSONObject Se = (JSONObject) ArraySession.get(p);
	 			  for (int j=p+1; j<ArraySession.length();j++) {
	 	              JSONObject Ci = (JSONObject) ArraySession.get(j);
	 				  JSONObject linke = new JSONObject() ;
	 				  linke.put("source",Se.get("@id"));  
	 				  linke.put("target",Ci.get("@id"));
	 				  linke.put("value",0) ;
	 				  
	 				 linke.put("distance",100-100*Clusters.calculdistanceSession  ( Se ,Ci));
	 				 allinks.put(linke);
	 				 linksnoeud.put(linke);
	 			  }
	 	  }
	 	
	 	 System.out.println("links :" + linksnoeud);
	 	 System.out.println("nodes :"+ nodes+",");
	 	// System.out.println("links :" + allinks);
	 	// System.out.println("links :" + linkscluster);

	 		  
	 		  ////// calculer indicateur pow
	 		Double SommeIndicteur= 0.0;
	 		int nbIndicatur = 0;
	 		Double SommeBow = 0.0 ;
	 		 for (int k=0; k<ArrayCluster.length();k++) {
	 			  Double sommeDistance2 =0.0 ;
	 			  JSONObject currentcluster = (JSONObject)ArrayCluster.get(k);

	 			 JSONArray sessionlist = (JSONArray) currentcluster.get("Session") ;
	 			  for (int j=0; j< sessionlist.length() ; j++) {
	 					sommeDistance2 += Math.pow(1- Clusters.calculdistance (getsession (ArraySession ,(String) sessionlist.get(j)),(JSONObject)currentcluster.get("barycentre")), 2);

	 			  }
	 			  SommeBow+= sommeDistance2 ;	  
	 		 }
	 		 System.out.println("La distance pow est : "+ SommeBow);
	 		  
	 		 // calcul Silhoette
	 		 for (int k=0; k<ArrayCluster.length();k++) {
	 			  System.out.println  ("============================================================================");
	 			  System.out.println ("Liste indicateur du cluster : " + ArrayCluster.get(k) ) ;
	 			  JSONObject cluster = (JSONObject)ArrayCluster.get(k);
	 			 JSONArray sessionArray = (JSONArray) cluster.get("Session") ;
	 			  // trouver le cluster le plus proche 
	 			  Double min = 1.0 ;
	 			  JSONObject clusterProche = (JSONObject)ArrayCluster.get(0) ;
	 			  
	 			  for (int c=0; c<ArrayCluster.length();c++) {
	 				 if(c!=k) {
	 					 System.out.println ("Distance barycentre entre  " + cluster.get("Name")+"et"+ (((JSONObject)ArrayCluster.get(c)).get("Name"))+ "=" + Clusters.calculdistanceBarycentre ( (JSONObject)cluster.get("barycentre") ,(JSONObject)((JSONObject)ArrayCluster.get(c)).get("barycentre")));
	 				  if ( min>1-Clusters.calculdistanceBarycentre ( (JSONObject)cluster.get("barycentre") ,(JSONObject)((JSONObject)ArrayCluster.get(c)).get("barycentre"))) 
	 					  clusterProche=(JSONObject)((JSONObject)ArrayCluster.get(c)) ;
	 				      min = 1-Clusters.calculdistanceBarycentre ( (JSONObject)cluster.get("barycentre") ,(JSONObject)((JSONObject)ArrayCluster.get(c)).get("barycentre"));
	 				  }
	 			  }
	 			 JSONArray sessionArrayClusterProche = (JSONArray) clusterProche.get("Session") ;
	 			  System.out.println  ("cluster proche" + clusterProche) ;
	 			  // Parser les sessions de ce cluster
	 			 
	 			  for (int j=0; j< sessionArray.length() ; j++) {
	 				  // distance entre session et son barycentre 

	 				  // pour chaque session calculer ai
	 				  Double sommeDistance = 0.0 ; // SFSSQ
	 				  for (int p=0; p< sessionArray.length() ; p++) {
	 				sommeDistance+= 1-Clusters.calculdistanceSession (getsession (ArraySession ,(String) sessionArray.get(j)), getsession (ArraySession ,(String) sessionArray.get(p)));
	 			//	System.out.println ("distance entre " + sessionArray.get(j) +" & " + sessionArray.get(p) + ":" +calculdistanceSession (getsession (ArraySession ,(String) sessionArray.get(j)), getsession (ArraySession ,(String) sessionArray.get(p))));
	 				 
	 				  
	 				  }
	 				  Double a = (Double)sommeDistance/ sessionArray.length() ;
	 				  sommeDistance = 0.0 ;
	 				  // pour chaque session calculer b
	 				//  System.out.println (clusterProche) ;
	 				  for (int p1=0; p1< sessionArrayClusterProche.length() ; p1++) {
	 					  sommeDistance += 1-Clusters.calculdistanceSession (getsession (ArraySession ,(String) sessionArray.get(j)), getsession (ArraySession ,(String) sessionArrayClusterProche.get(p1)));
	 				  }
	 				  Double b = (Double)sommeDistance/ sessionArrayClusterProche.length() ;
	 				  // calculer indicateur
	 				  Double indicateur = (Double)(b-a)/(Double)Math.max(b, a);
	 				  // afficher indicateur pour chaque session 
	 				 // System.out.println(sessionArray.get(j) +" :" + a +" "+ b);
	 				 // System.out.println(sessionArray.get(j) +" :" + indicateur);
	 				 System.out.println(indicateur);
	 				  SommeIndicteur+= indicateur ;
	 				  nbIndicatur ++ ;
	 			  }
	 			  
	 		  }
	 		  System.out.println ("indicateur moyen pour cluster est " + SommeIndicteur/nbIndicatur );
	}

}
