package com.algo;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Clusters{
	public static Double  weigth [] = {0.2,0.2,0.2,0.1,0.1,0.1,0.1};
  
public static boolean contains (JSONArray arr , String str) throws JSONException {
	  boolean contain = false ; 
	  for (int i=0; i<arr.length();i++) {
		  if ((arr.get(i)).equals(str)) return true ;
	  }
	  
	  return contain ;
  }
@SuppressWarnings("m:unchecked")
public static JSONObject calculbarycentre (JSONObject AncientBarycentre, JSONObject NouveauSession) throws JSONException {
	
	  
	 JSONObject barycenter = new JSONObject();
	
	 JSONArray TextBehavior = new JSONArray();
	
	if (AncientBarycentre != null) {TextBehavior.put((JSONArray) AncientBarycentre.get("m:TextBehavior"));}
	if (!contains(TextBehavior,(String) NouveauSession.get("m:TextBehavior"))) {TextBehavior.put ((String) NouveauSession.get("m:TextBehavior"));}
	barycenter.put("m:TextBehavior", TextBehavior) ;
	
	JSONArray VideoBehavior = new JSONArray();
	if (AncientBarycentre != null) { VideoBehavior.put((JSONArray) AncientBarycentre.get("m:VideoBehavior"));}
	if (!contains(VideoBehavior,(String) NouveauSession.get("m:VideoBehavior"))) {VideoBehavior.put((String) NouveauSession.get("m:VideoBehavior")); }
	
   barycenter.put("m:VideoBehavior", VideoBehavior) ;
	
   JSONArray ForumBehavior = new JSONArray();
	if (AncientBarycentre != null) { ForumBehavior.put ((JSONArray) AncientBarycentre.get("m:ForumBehavior"));}
	if (!contains(ForumBehavior,(String) NouveauSession.get("m:ForumBehavior"))) { ForumBehavior.put ((String) NouveauSession.get("m:ForumBehavior"));}
	barycenter.put("m:ForumBehavior", ForumBehavior) ;
	
	JSONArray ExerciceBehavior = new JSONArray();
	if (AncientBarycentre != null) { ExerciceBehavior.put ((JSONArray) AncientBarycentre.get("m:ExerciceBehavior"));}
	if (!contains(ExerciceBehavior,(String) NouveauSession.get("m:ExerciceBehavior"))) { ExerciceBehavior.put((String) NouveauSession.get("m:ExerciceBehavior"));}
	barycenter.put("m:ExerciceBehavior", ExerciceBehavior) ;
	
	if (AncientBarycentre != null) {
	double DurationTotalAllRessource  = similaritie.moyenne ( Double.parseDouble ((String) AncientBarycentre.get("m:DurationTotalAllRessource")),Double.parseDouble ((String) NouveauSession.get("m:DurationTotalAllRessource")));
	barycenter.put("m:DurationTotalAllRessource", Double.toString (DurationTotalAllRessource)) ;
	} else barycenter.put("m:DurationTotalAllRessource", NouveauSession.get("m:DurationTotalAllRessource")) ;
	
	if (AncientBarycentre != null) {
	double duration  = similaritie.moyenneGeometric (Double.parseDouble ((String) AncientBarycentre.get("m:duration")),Double.parseDouble ((String) NouveauSession.get("m:duration")));
	barycenter.put("m:duration", Double.toString (duration)) ;}
	else barycenter.put("m:duration", NouveauSession.get("m:duration")) ;
	
	if (AncientBarycentre != null) {
	double nbConsultationAllRessources  = similaritie.moyenneGeometric (Double.parseDouble ((String) AncientBarycentre.get("m:nbConsultationAllRessources")),Double.parseDouble ((String) NouveauSession.get("m:nbConsultationAllRessources")));
	barycenter.put("m:nbConsultationAllRessources", Double.toString (nbConsultationAllRessources)) ;}
	else barycenter.put("m:nbConsultationAllRessources", NouveauSession.get("m:nbConsultationAllRessources")) ;
	
	
	
	return barycenter; 
}
  /**
 * @throws JSONException 
 * @throws NumberFormatException 
   * 
   */
  
public static double calculdistanceSession (JSONObject Session, JSONObject barycentre) throws NumberFormatException, JSONException {

	
	  double d = (double)
	(
	weigth [6] * similaritie.simulaireString ((String) Session.get("m:TextBehavior"),(String) barycentre.get("m:TextBehavior"))
	+weigth [5] * similaritie.simulaireString ((String) Session.get("m:VideoBehavior"),(String) barycentre.get("m:VideoBehavior"))
	+weigth [4] * similaritie.simulaireString ((String) Session.get("m:ForumBehavior"),(String) barycentre.get("m:ForumBehavior"))
	+weigth [3] * similaritie.simulaireString ((String) Session.get("m:ExerciceBehavior"),(String) barycentre.get("m:ExerciceBehavior"))
	+ weigth [2] * similaritie.simulairDuration (Double.parseDouble ((String) Session.get("m:nbConsultationAllRessources")),Double.parseDouble ((String) barycentre.get("m:nbConsultationAllRessources")))
	+ weigth [1] * similaritie.simulairDuration (Double.parseDouble ((String) Session.get("m:DurationTotalAllRessource")),Double.parseDouble ((String) barycentre.get("m:DurationTotalAllRessource")))
	+ weigth [0] * similaritie.simulairDuration (Double.parseDouble ((String) Session.get("m:duration")),Double.parseDouble ((String) barycentre.get("m:duration"))));
			             
	  return d ;
  }
  /**
   * 
   * @param Session
   * @param barycentre
   * @return
 * @throws JSONException 
 * @throws NumberFormatException 
   */
  public static double calculdistanceBarycentre (JSONObject Session, JSONObject barycentre) throws NumberFormatException, JSONException {
	  double d = (double)
				(
				weigth [6] * similaritie.simulaiListString ((List<String>) Session.get("m:TextBehavior"),(List<String>) barycentre.get("m:TextBehavior"))
				+weigth [5] * similaritie.simulaiListString ((List<String>) Session.get("m:VideoBehavior"),(List<String>) barycentre.get("m:VideoBehavior"))
				+weigth [4] * similaritie.simulaiListString ((List<String>) Session.get("m:ForumBehavior"),(List<String>) barycentre.get("m:ForumBehavior"))
				+weigth [3] * similaritie.simulaiListString ((List<String>) Session.get("m:ExerciceBehavior"),(List<String>) barycentre.get("m:ExerciceBehavior"))
				+ weigth [2] * similaritie.simulairDuration (Double.parseDouble ((String) Session.get("m:nbConsultationAllRessources")),Double.parseDouble ((String) barycentre.get("m:nbConsultationAllRessources")))
				+ weigth [1] * similaritie.simulairDuration (Double.parseDouble ((String) Session.get("m:DurationTotalAllRessource")),Double.parseDouble ((String) barycentre.get("m:DurationTotalAllRessource")))
				+ weigth [0] * similaritie.simulairDuration (Double.parseDouble ((String) Session.get("m:duration")),Double.parseDouble ((String) barycentre.get("m:duration"))));
						             
	 
	 
	  return d ;
	  
  }
  
  @SuppressWarnings("m:unchecked")
public static double calculdistance (JSONObject Session, JSONObject barycentre) throws NumberFormatException, JSONException {

	  double d = (double)
				(
				weigth [6] * similaritie.simulairString ((String) Session.get("m:TextBehavior"),(JSONArray) barycentre.get("m:TextBehavior"))
				+weigth [5] * similaritie.simulairString ((String) Session.get("m:VideoBehavior"),(JSONArray) barycentre.get("m:VideoBehavior"))
				+weigth [4] * similaritie.simulairString ((String) Session.get("m:ForumBehavior"),(JSONArray) barycentre.get("m:ForumBehavior"))
				+weigth [3] * similaritie.simulairString ((String) Session.get("m:ExerciceBehavior"),(JSONArray) barycentre.get("m:ExerciceBehavior"))
				+ weigth [2] * similaritie.simulairDuration (Double.parseDouble ((String) Session.get("m:nbConsultationAllRessources")),Double.parseDouble ((String) barycentre.get("m:nbConsultationAllRessources")))
				+ weigth [1] * similaritie.simulairDuration (Double.parseDouble ((String) Session.get("m:DurationTotalAllRessource")),Double.parseDouble ((String) barycentre.get("m:DurationTotalAllRessource")))
				+ weigth [0] * similaritie.simulairDuration (Double.parseDouble ((String) Session.get("m:duration")),Double.parseDouble ((String) barycentre.get("m:duration"))));
						     
	  return d ;
  }
 
  
/*public static void regroupement(JSONArray ArraySession) {
	  JSONArray ArrayCluster = new JSONArray() ;
	  double trust = 1;
	  double AAtrust = 0;
	  double Atrust = 0;
	  double [] T = new double [201] ;
	  double [] AT = new double [201] ;
	  double [] D = new double [201] ;
	  double [] S = new double [203] ;
	  double [] AS = new double [203] ;
	  
	  JSONObject graph = new JSONObject();
	  JSONArray nodes = new JSONArray() ;
	  JSONArray links = new JSONArray() ;
	  
	  
	  for (int i = 0; i < ArraySession.size() ; i++) {
		    System.out.println("m:************************************************************") ;
		  	JSONObject sessionActuel = (JSONObject) ArraySession.get(i);
		  	
		  	*//** Initialization First Cluster **//*
		  	
		  	if (i==0) {
		  		JSONObject cluster = new JSONObject();
		  		*//** put Barycentre **//*
		  		//cluster.put("m:barycentre", sessionActuel) ;
		  		JSONObject NouveauBarycentre = calculbarycentre (null ,sessionActuel ) ;
		  		cluster.put("m:barycentre", NouveauBarycentre) ;
		  		
		  		*//** put list of list **//*
		  		List<String> SessionCluster = new ArrayList<String>();
		  		SessionCluster.add ((String) sessionActuel.get("m:Name"));
		  		cluster.put("m:Session", SessionCluster) ;
		  		*//** put Name **//*
		  		int index = ArrayCluster.size() +1 ;
		  		cluster.put("m:Name", "C"+index) ;
		  		cluster.put("m:Poid", "1") ;
		  		*//** add to arrayCluster **//*
		  		ArrayCluster.add(cluster);
		  		T[i] = trust;
		  		
		  	}
		  	
		  	*//** Other Cluster **//*
		  	else {
		  		double [] distance = new double [100] ;
		  		double [] Poid = new double [100] ;
		  		*//** Calculer distance par rapport tous les clusters **//*
		  		for (int j=0; j< ArrayCluster.size(); j++) {
		  			JSONObject C = (JSONObject) ArrayCluster.get(j);
		  			double dist = calculdistance (sessionActuel,(JSONObject) C.get("m:barycentre")); 
		  			System.out.println ("m:distance entre " + C.get("m:Name") + " et " + sessionActuel.get("m:Name") + " est = "+ dist ) ;
		  		    distance [j] = dist ;
		  		    Poid [j] = Double.parseDouble ((String) C.get("m:Poid")) ;
		  		}
		  		
		  		*//** calculer score  for trust **//*
		  		Double s =0.0;
		  		for (int k=0; k<ArrayCluster.size(); k++) {
		  			s = s + Poid[k]*distance[k] ;
		  		}
		  		Double P=0.0;
		  		for (int k=0; k<Poid.length ; k++) {
		  			P = P + Poid[k] ;
		  		}
		  		Double Ascore = s/P ;
		  		System.out.println("m:score  =" + Ascore) ;
		  		
		  		
		  		Double distanceMax = MaxTableau (distance) ;
		  		int indexMax =  MaxIndex (distance) ;
		  		*//** calculer Trust 
		  		 *//*
		  		if (distanceMax > 0.53) {
		  			//AAtrust = AAtrust + distanceMax ;
		  			//Atrust = Atrust + Ascore ;
		  			trust = trust + Ascore ;
		  			System.out.println("m:trust  ="+ trust);
		  		}else {
		  			//trust = trust - (ArrayCluster.size()-1-score) ;
		  			//AAtrust = AAtrust - (ArrayCluster.size()-1-distanceMax) ;
		  			//Atrust = Atrust - (ArrayCluster.size()-1-Ascore) ;
		  			//trust = trust - Math.abs(trust*(1-Ascore)) ;
		  			trust = trust*Ascore ;
		  			System.out.println("m:trust  ="+ trust);
		  		}
		  		//System.out.println("m:distanceMax = "+ distanceMax);
		  		*//** regroupement cluster **//*
		  		// ICIIIIIIIII
		  		if (distanceMax > 0.53) {
		  			*//**  ajouter session actuel au cluster **//*
		  			
		  			JSONObject C = (JSONObject) ArrayCluster.get(indexMax);
		  			
		  			System.out.println ("m:mise à jour cluster"+C.get("m:Name"));
		  			
		  			List<String> SessionCluster = (List<String>) C.get("m:Session") ;
		  			SessionCluster.add ((String) sessionActuel.get("m:Name"));
		  			C.put("m:Session", SessionCluster) ;
		  			*//** calculer le nouveau barycentre **//*
		  			JSONObject NouveauBarycentre = calculbarycentre ((JSONObject) C.get("m:barycentre"),sessionActuel ) ;
		  			C.put("m:barycentre", NouveauBarycentre) ;
		  			
		  			//trust = trust + score ;
		  			//trust = trust + Math.abs(trust /(1-score)) ;
		  			//System.out.println(trust);
		  			*//** mettre a jour le poid **//*
		  			Double nouveauPoid = Double.parseDouble ((String) C.get("m:Poid")) + distanceMax ;
		  			C.put("m:Poid", Double.toString (nouveauPoid)) ;
		  		    *//** Create nouveau cluster **//*
		  		}else {
		  			
		  			JSONObject cluster = new JSONObject();
			  		*//** put Barycentre **//*
			  		JSONObject NouveauBarycentre = calculbarycentre (null ,sessionActuel ) ;
			  		cluster.put("m:barycentre", NouveauBarycentre) ;
			  		
			  		*//** put list of list **//*
			  		List<String> SessionCluster = new ArrayList<String>();
			  		SessionCluster.add ((String) sessionActuel.get("m:Name"));
			  		cluster.put("m:Session", SessionCluster) ;
			  		*//** put Name **//*
			  		int index = ArrayCluster.size()+1 ;
			  		cluster.put("m:Name", "C"+index) ;
			  		cluster.put("m:Poid", "0.1") ;
			  		*//** add to arrayCluster **//*
			  		ArrayCluster.add(cluster);
	
			  		
		  		}
		  	
		  		T[i] = trust ; 
				AT[i] = Atrust ;
				//S[i]  = score ;
				AS[i]= Ascore ;
				D[i] = distanceMax ;
		  	}
	
			 
	  }
	  	

	  System.out.println("m:Nouveau trust " );
	  for (int k=0; k<T.length;k++) {
		  System.out.println (T[k]) ;
	  }

	  
	  

	  System.out.println("m:La lister des cluster a itération " );
	  for (int k=0; k<ArrayCluster.size();k++) {
		  System.out.println (ArrayCluster.get(k)) ;
	  }
	  System.out.println("m:fin liste cluster " );

	  
	  JSONArray allinks = new JSONArray() ;
	  JSONArray linksnoeud = new JSONArray() ;
	  JSONArray linkscluster = new JSONArray() ;
	  
	  for (int k=0; k<ArrayCluster.size();k++) {
		  JSONObject cluster = (JSONObject)ArrayCluster.get(k);
		  JSONObject C = (JSONObject) ArrayCluster.get(k);
		  
		  List<String> sessionArray = (List<String>)   cluster.get("m:Session") ;
		
		  
		  JSONObject node = new JSONObject() ;
		   node.put("m:id","barycentre"+k);  node.put("m:group",k);node.put("m:type","centroide");
		   nodes.add(node) ;
	
		   
		   for (int j=0; j< sessionArray.size() ; j++) {
			   JSONObject nodee = new JSONObject() ;
			   JSONObject link = new JSONObject() ;
			   nodee.put("m:id",sessionArray.get(j));  nodee.put("m:group",k); nodee.put("m:type","point");
			   link.put("m:source","barycentre"+k);  
			   link.put("m:target",sessionArray.get(j));
			   link.put("m:value",5) ;
			   link.put("m:distance",100 - 100*calculdistance ( getsession (ArraySession ,(String) sessionArray.get(j)),(JSONObject)C.get("m:barycentre")));
			   nodes.add(nodee) ;
			   allinks.add(link);
			   linkscluster.add(link);
		   }
		 }
	  // link pour tous les barycentre 
	  for (int k=0; k<ArrayCluster.size();k++) {
		  JSONObject C = (JSONObject) ArrayCluster.get(k);
		  for (int j=k+1; j<ArrayCluster.size() ;j++) {
              JSONObject Ci = (JSONObject) ArrayCluster.get(j);
			  
			  JSONObject linke = new JSONObject() ;
			  linke.put("m:source","barycentre"+k);  
			  linke.put("m:target","barycentre"+j);
			  linke.put("m:value",1) ;
			  
			 linke.put("m:distance",100-100*calculdistanceBarycentre ( (JSONObject)Ci.get("m:barycentre") ,(JSONObject)C.get("m:barycentre")));
			   
			 //  System.out.println (calculdistanceBarycentre ( (JSONObject)Ci.get("m:barycentre") ,(JSONObject)C.get("m:barycentre"))) ;
			  
			 allinks.add(linke);
			 linkscluster.add(linke);
			  
		  }
	  }
	  // link pour tous les sessions 
		  for (int p=0; p<ArraySession.size();p++) {
			  JSONObject Se = (JSONObject) ArraySession.get(p);
			  for (int j=p+1; j<ArraySession.size();j++) {
	              JSONObject Ci = (JSONObject) ArraySession.get(j);
				  
				  JSONObject linke = new JSONObject() ;
				  linke.put("m:source",Se.get("m:Name"));  
				  linke.put("m:target",Ci.get("m:Name"));
				  linke.put("m:value",0) ;
				  
				 linke.put("m:distance",100-100*calculdistanceSession  ( Se ,Ci));
				 allinks.add(linke);
				 linksnoeud.add(linke);
			  }
	  }
	
	// System.out.println("m:links :" + linksnoeud);
	// System.out.println("m:nodes :"+ nodes+",");
	// System.out.println("m:links :" + allinks);
	// System.out.println("m:links :" + linkscluster);

		  
		  ////// calculer indicateur 
		  Double SommeIndicteur= 0.0;
		  int nbIndicatur = 0;
		 Double SommeBow = 0.0 ;
		 for (int k=0; k<ArrayCluster.size();k++) {
			  Double sommeDistance2 =0.0 ;
			  JSONObject currentcluster = (JSONObject)ArrayCluster.get(k);

			  List<String> sessionlist = (List<String>) currentcluster.get("m:Session") ;
			  for (int j=0; j< sessionlist.size() ; j++) {
					sommeDistance2 += Math.pow(1- calculdistance (getsession (ArraySession ,(String) sessionlist.get(j)),(JSONObject)currentcluster.get("m:barycentre")), 2);

			  }
			  SommeBow+= sommeDistance2 ;	  
		 }
		 System.out.println("m:La distance pow est : "+ SommeBow);
		  for (int k=0; k<ArrayCluster.size();k++) {
			  System.out.println  ("m:============================================================================");
			  System.out.println ("m:Liste indicateur du cluster : " + ArrayCluster.get(k) ) ;
			  JSONObject cluster = (JSONObject)ArrayCluster.get(k);
			  List<String> sessionArray = (List<String>) cluster.get("m:Session") ;
			  // trouver le cluster le plus proche 
			  Double min = 1.0 ;
			  JSONObject clusterProche = (JSONObject)ArrayCluster.get(0) ;
			  
			  for (int c=0; c<ArrayCluster.size();c++) {
				 if(c!=k) {
					 System.out.println ("m:Distance barycentre entre  " + cluster.get("m:Name")+"et"+ (((JSONObject)ArrayCluster.get(c)).get("m:Name"))+ "=" + calculdistanceBarycentre ( (JSONObject)cluster.get("m:barycentre") ,(JSONObject)((JSONObject)ArrayCluster.get(c)).get("m:barycentre")));
				  if ( min>1-calculdistanceBarycentre ( (JSONObject)cluster.get("m:barycentre") ,(JSONObject)((JSONObject)ArrayCluster.get(c)).get("m:barycentre"))) 
					  clusterProche=(JSONObject)((JSONObject)ArrayCluster.get(c)) ;
				      min = 1-calculdistanceBarycentre ( (JSONObject)cluster.get("m:barycentre") ,(JSONObject)((JSONObject)ArrayCluster.get(c)).get("m:barycentre"));
				  }
			  }
			  List<String> sessionArrayClusterProche = (List<String>) clusterProche.get("m:Session") ;
			  System.out.println  ("m:cluster proche" + clusterProche) ;
			  // Parser les sessions de ce cluster
			 
			  for (int j=0; j< sessionArray.size() ; j++) {
				  // distance entre session et son barycentre 

				  // pour chaque session calculer ai
				  Double sommeDistance = 0.0 ;
				  for (int p=0; p< sessionArray.size() ; p++) {
				sommeDistance+= 1-calculdistanceSession (getsession (ArraySession ,(String) sessionArray.get(j)), getsession (ArraySession ,(String) sessionArray.get(p)));
			//	System.out.println ("m:distance entre " + sessionArray.get(j) +" & " + sessionArray.get(p) + ":" +calculdistanceSession (getsession (ArraySession ,(String) sessionArray.get(j)), getsession (ArraySession ,(String) sessionArray.get(p))));
				 
				  
				  }
				  Double a = (Double)sommeDistance/ sessionArray.size() ;
				  sommeDistance = 0.0 ;
				  // pour chaque session calculer b
				//  System.out.println (clusterProche) ;
				  for (int p1=0; p1< sessionArrayClusterProche.size() ; p1++) {
					  sommeDistance += 1-calculdistanceSession (getsession (ArraySession ,(String) sessionArray.get(j)), getsession (ArraySession ,(String) sessionArrayClusterProche.get(p1)));
				  }
				  Double b = (Double)sommeDistance/ sessionArrayClusterProche.size() ;
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
		//  System.out.println ("m:indicateur moyen pour cluster est " + SommeIndicteur/nbIndicatur );
	  
  }*/
 
 

}


