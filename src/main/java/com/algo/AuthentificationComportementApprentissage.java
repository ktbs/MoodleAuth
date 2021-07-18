package com.algo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.Obsel;
import com.ignition_factory.ktbs.bean.Trace;
import com.model.User;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyStore.Entry.Attribute;

import com.model.ActionTrust;
import com.model.Cluster;
import com.model.Sessions;

public class AuthentificationComportementApprentissage {

	/***
	 * 
	 * @param base
	 * @param primarytrace
	 * @throws Exception
	 * Créer la trace Comportement Ressource Interaction Action 
	 * Créer la trace Signature Ressource Interaction Action Signature 
	 * Calculer Trust pour chaque Action après deux répétition 
	 */
	public static  void createTraceRessourceActionSifnatureTrust (SessionFactory factory,User user ,Base base, Trace primarytrace) throws Exception {
		
	          
	//Session sessionHibernate=factory.openSession();
	//sessionHibernate.beginTransaction();
		
	
	Trace TraceForumInteractionSignature = new Trace (base.get_uri()+"Trace-Forum-Interaction-Signature/") ; 
	if (TraceForumInteractionSignature.Exist() ) {
		TraceForumInteractionSignature.remove();}
	Trace  TraceTextInteractionSignature = new Trace (base.get_uri()+"Trace-Text-Interaction-Signature/") ; 
	 TraceTextInteractionSignature.remove();
	Trace  TraceExerciceInteractionSignature = new Trace (base.get_uri()+"Trace-Exercice-Interaction-Signature/") ; 
	TraceExerciceInteractionSignature.remove(); 
	Trace RessourceInteractionAction = new Trace (base.get_uri()+"RessourceInteractionAction/");
	RessourceInteractionAction.remove();
	
	String [] tracesource = {primarytrace.get_Name()+"/"};
	String modeUrl = ktbsService.getUrlModel(base,primarytrace);
	    /** filter loginlogaout **/
	
	String filterparamlogin = "otypes="+modeUrl+"loggedin "+modeUrl+"loggedout";
	String[] parametrelogin = {filterparamlogin};
	Trace AllLogEvent = ktbsService.createTransformedTrace(base, "AllLogEvent","filter" , tracesource, parametrelogin);
	
	String[] parametres4 = {} ;
	
	String filterparamForum = "bgp=?obs m:component \"mod_forum\"";
	String[] parametreDD4 = {filterparamForum};
	ktbsService.createTransformedTrace(base, "ForumEvent","filter" , tracesource, parametreDD4);
	String[] tracesources4 = {"AllLogEvent/", "ForumEvent/"} ;
	ktbsService.createTransformedTrace(base, "ForumEventAndLog", "fusion", tracesources4, parametres4);       
	Trace primarytraceForumInteractionAction = new Trace (base.get_uri()+"ForumInteractionAction/");
	primarytraceForumInteractionAction.remove();
	primarytraceForumInteractionAction = ktbsService.createPrimaryTrace (base,"modelprimary","ForumInteractionAction",  primarytrace.get_origin());
	
	String filterparamQuiz = "bgp= {?obs m:component \"mod_quiz\"}UNION {?obs m:component \"mod_choice\"}";
	String[] parametreQuiz = {filterparamQuiz};
	ktbsService.createTransformedTrace(base, "QuizEvent","filter" , tracesource, parametreQuiz);
	String[] tracesources5 = {"AllLogEvent/", "QuizEvent/"} ;
	ktbsService.createTransformedTrace(base, "QUIZEventAndLog", "fusion", tracesources5, parametres4);     
	
	Trace primarytraceQUIZInteractionAction = new Trace (base.get_uri()+"QUIZInteractionAction/");
	primarytraceQUIZInteractionAction.remove();
	primarytraceQUIZInteractionAction = ktbsService.createPrimaryTrace (base,"modelprimary","QUIZInteractionAction",  primarytrace.get_origin());
	
	String filterparamText = "bgp={?obs m:component \"mod_page\"} UNION {?obs m:component \"mod_lesson\"}UNION {?obs m:component \"mod_book\"}UNION {?obs m:component \"mod_wiki\"}";
	String[] parametreText = {filterparamText};
	ktbsService.createTransformedTrace(base, "TextEvent","filter" , tracesource, parametreText);
	String[] tracesources6 = {"AllLogEvent/", "TextEvent/"} ;
	ktbsService.createTransformedTrace(base, "TextEventAndLog", "fusion", tracesources6, parametres4);     
	Trace primarytraceTextInteractionAction = new Trace (base.get_uri()+"TextInteractionAction/");
	primarytraceTextInteractionAction.remove();
	primarytraceTextInteractionAction = ktbsService.createPrimaryTrace (base,"modelprimary","TextInteractionAction",  primarytrace.get_origin());
	
	
	/**CREATE TRACE RESSOURCE INTERACTION Profil and Signature **/
	
	String[] tracesources7 = {AllLogEvent.get_Name()+"/", primarytraceTextInteractionAction.get_Name()+"/", primarytraceQUIZInteractionAction.get_Name()+"/", primarytraceForumInteractionAction.get_Name()+"/"} ;
	
	
	
		
	RessourceInteractionAction = ktbsService.createTransformedTrace(base, "RessourceInteractionAction", "fusion", tracesources7, parametres4);     
	    

	
	Trace RessourceInteractionActionSignature = new Trace (base.get_uri()+"RessourceInteractionActionSignature/");
	RessourceInteractionActionSignature.remove();
	RessourceInteractionActionSignature = ktbsService.createPrimaryTrace (base,"modelprimary","RessourceInteractionActionSignature",  primarytrace.get_origin());

	//user.setSignatureActionUri(base.get_uri()+"RessourceInteractionActionSignature/");
	//user.setProfilActionUri(base.get_uri()+"RessourceInteractionAction/");
	//sessionHibernate.save(user);

	/** FORUM INTERACTION **/
	   
	System.out.println ("##########################  FORUM INTERACTION #########################");
	int compt =0 ;
	Collection<Obsel> listObselForum = ktbsService.getListObsel(base,null, null,"ForumEventAndLog" );
	int j =0 ; 
	
	while (j<listObselForum.size()) {
		//System.out.println ("index  = " + j + " taille liste"+ listObselForum.size());
		//System.out.println (  ((Obsel) listObselForum.toArray()[j]).get_id() );
		if (((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("loggedin")) {
			ktbsService.createObsel(base.get_Name(),primarytraceForumInteractionAction.get_Name(),((Obsel) listObselForum.toArray()[j]).get_jsonObsel());
			j++;
	}else if (((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("loggedout")) {
			ktbsService.createObsel(base.get_Name(),primarytraceForumInteractionAction.get_Name(),((Obsel) listObselForum.toArray()[j]).get_jsonObsel());
			j++;
	}
	
	else{
			int nbInteraction = 0 ;
			JSONObject ObselForumInteraction = new JSONObject();
			Integer begin = (Integer) ((Obsel) listObselForum.toArray()[j]).get_begin();
			ObselForumInteraction.put ("@id",((Obsel) listObselForum.toArray()[j]).get_id());
			ObselForumInteraction.put ("begin",((Obsel) listObselForum.toArray()[j]).get_begin());
			ObselForumInteraction.put ("@type","m:ForumIneraction");
			ObselForumInteraction.put ("m:ressourceType","Forum");
			ObselForumInteraction.put ("m:ressourceID",((Obsel) listObselForum.toArray()[j]).get_attribute("contextid").getValue());
			ObselForumInteraction.put ("m:courseID",((Obsel) listObselForum.toArray()[j]).get_attribute("courseid").getValue());
			String contextid = ((Obsel) listObselForum.toArray()[j]).get_attribute("contextid").getValue();
	
			j++ ;
			//System.out.println ("j"+j);
			if (j<listObselForum.size()) {
				//System.out.println	(((Obsel) listObselForum.toArray()[j]).get_attribute("contextid").getValue());
				//System.out.println (((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId()) ;
				//System.out.println(((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId()) ;
				//System.out.println(((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId());
		
				while (j<listObselForum.size() && (((Obsel) listObselForum.toArray()[j]).get_attribute("contextid").getValue().equals(contextid))|| (! ((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("viewed")) && (!((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("loggedout")) &&(! ((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("loggedin"))) {
	
					if  ((! ((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("viewed"))) {nbInteraction ++ ;};
	
					j++ ;
					//System.out.println ("j while "+ j);
					if (j>=listObselForum.size()) {break;}
				}
			}
			Integer end = (Integer) ((Obsel) listObselForum.toArray()[j-1]).get_begin();
			ObselForumInteraction.put ("m:duration",end-begin);
			ObselForumInteraction.put ("end",((Obsel) listObselForum.toArray()[j-1]).get_begin());
			ObselForumInteraction.put ("m:nbInteraction",nbInteraction);
			ktbsService.createObsel(base.get_Name(),primarytraceForumInteractionAction.get_Name(),ObselForumInteraction);
			compt ++ ;
			// Collection<Obsel> listObsel = ktbsService.getListObsel(base,null, null,primarytraceForumInteractionAction.get_Name() );
			//System.out.println ("compt  ==================================== " + compt);
			if (compt==5) {
				System.out.println (" =============create signature forum======================================= " );
				
						Trace SignForum = createSignatureForumInteractionSignature(base);
	    	    Collection<Obsel> listObselForumSign = ktbsService.getListObsel(base,null, null,SignForum.get_Name() );
	    	    ktbsService.createObsel (base.get_Name(),RessourceInteractionActionSignature.get_Name(), ((Obsel) listObselForumSign.toArray()[0]).get_jsonObsel());
	        }
	        
	    } 
	 }
	    
	 System.out.println ("##########################  END FORUM INTERACTION #########################");
	/** QUIZ INTERACTION **/
	System.out.println ("##########################  QUIZ INTERACTION #########################");
	       
	int k =0 ;
	Collection<Obsel> listObselQUIZ = ktbsService.getListObsel(base,null, null,"QUIZEventAndLog" );
	 
	compt =0;
	while (k<listObselQUIZ.size()) {
		//System.out.println ("index  = " + k + " ID " + ((Obsel) listObselQUIZ.toArray()[k]).get_id());
	
		if (((Obsel) listObselQUIZ.toArray()[k]).get_obsel_type().getTypeId().equals("loggedin")) {
	//	ktbsService.createObsel(base.get_Name(),primarytraceQUIZInteractionAction.get_Name(),((Obsel) listObselQUIZ.toArray()[k]).get_jsonObsel());
		k++;
		}else if (((Obsel) listObselQUIZ.toArray()[k]).get_obsel_type().getTypeId().equals("loggedout")) {
	//	ktbsService.createObsel(base.get_Name(),primarytraceQUIZInteractionAction.get_Name(),((Obsel) listObselQUIZ.toArray()[k]).get_jsonObsel());
		k++;
	}
		else	if (((Obsel) listObselQUIZ.toArray()[k]).get_obsel_type().getTypeId().equals("viewed")) {
	
			JSONObject ObselTextInteraction = new JSONObject();
			Integer begin = (Integer)((Obsel) listObselQUIZ.toArray()[k]).get_begin();
			ObselTextInteraction.put ("@id",((Obsel) listObselQUIZ.toArray()[k]).get_id());
			ObselTextInteraction.put ("begin",((Obsel) listObselQUIZ.toArray()[k]).get_begin());
			ObselTextInteraction.put ("@type","m:ExerciceIneraction");
			ObselTextInteraction.put ("m:ressourceType","QUIZ");
			ObselTextInteraction.put ("m:ressourceID",((Obsel) listObselQUIZ.toArray()[k]).get_attribute("contextid").getValue());
			ObselTextInteraction.put ("m:courseID",((Obsel) listObselQUIZ.toArray()[k]).get_attribute("courseid").getValue());
			k++ ;
	  
			while (k<listObselQUIZ.size() && (! ((Obsel) listObselQUIZ.toArray()[k]).get_obsel_type().getTypeId().equals("submitted")) && (! ((Obsel) listObselQUIZ.toArray()[k]).get_obsel_type().getTypeId().equals("viewed")) && (!((Obsel) listObselQUIZ.toArray()[k]).get_obsel_type().getTypeId().equals("loggedout")) &&(! ((Obsel) listObselQUIZ.toArray()[k]).get_obsel_type().getTypeId().equals("loggedin"))) {
		
				k++ ;
			}
			if (k<listObselQUIZ.size()) {
				if (((Obsel) listObselQUIZ.toArray()[k]).get_obsel_type().getTypeId().equals("submitted")) {
	
					ObselTextInteraction.put ("m:reussit","true");
					ObselTextInteraction.put ("m:break","false");
					Integer end = (Integer) ((Obsel) listObselQUIZ.toArray()[k]).get_begin();
					ObselTextInteraction.put ("m:duration",end-begin);
					ObselTextInteraction.put ("end",end);
					k++ ;
				} else {
					ObselTextInteraction.put ("m:reussit","false");
					ObselTextInteraction.put ("m:break","true");
					Integer end = (Integer) ((Obsel) listObselQUIZ.toArray()[k-1]).get_begin();
					ObselTextInteraction.put ("m:duration",end-begin);
					ObselTextInteraction.put ("end",end);
		
				}}else {
					ObselTextInteraction.put ("m:reussit","false");
					ObselTextInteraction.put ("m:break","true");
					Integer end = (Integer) ((Obsel) listObselQUIZ.toArray()[k-1]).get_begin();
					ObselTextInteraction.put ("m:duration",end-begin);
					ObselTextInteraction.put ("end",end);
					}
	
	ktbsService.createObsel(base.get_Name(),primarytraceQUIZInteractionAction.get_Name(),ObselTextInteraction);
	compt ++;
	Collection<Obsel> listObsel = ktbsService.getListObsel(base,null, null,primarytraceQUIZInteractionAction.get_Name() );
	    if (compt==5) {
	    	Trace SignQuiz = createSignatureExerciceInteractionSignature(base);
	        Collection<Obsel> listObselForumSign = ktbsService.getListObsel(base,null, null,SignQuiz.get_Name() );
	        //RessourceInteractionActionSignature.
	    	            ktbsService.createObsel (base.get_Name(),RessourceInteractionActionSignature.get_Name(), ((Obsel) listObselForumSign.toArray()[0]).get_jsonObsel());
	                }else if (listObsel.size()>2) {
	                	
	                	
	                }
	    	} else k++;
		}
		 
	System.out.println ("##########################  END QUIZ INTERACTION #########################");
	
	
	/** TEXT INTERACTION **/
	   
	System.out.println ("##########################  TEXT INTERACTION #########################");
	   
	    Collection<Obsel> listObselText = ktbsService.getListObsel(base,null, null,"TextEventAndLog" );
	 k =0 ; 
	 compt =0;
	
	while (k<listObselText.size()) {
		//System.out.println ("index  = " + k);
	//System.out.println (  ((Obsel)  listObselText.toArray()[k]).get_id() );
	
	if (((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("loggedin")) {
	//ktbsService.createObsel(base.get_Name(),primarytraceTextInteractionAction.get_Name(),((Obsel) listObselText.toArray()[k]).get_jsonObsel());
		k++;
	}else if (((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("loggedout")) {
	//ktbsService.createObsel(base.get_Name(),primarytraceTextInteractionAction.get_Name(),((Obsel) listObselText.toArray()[k]).get_jsonObsel());
		k++;
	}
	else	 {
		//if (((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("viewed"))
	int nbInteraction = 0 ;
	JSONObject ObselTextInteraction = new JSONObject();
	Integer begin = (Integer) ((Obsel) listObselText.toArray()[k]).get_begin();
	ObselTextInteraction.put ("@id",((Obsel) listObselText.toArray()[k]).get_id());
	ObselTextInteraction.put ("begin",((Obsel) listObselText.toArray()[k]).get_begin());
	ObselTextInteraction.put ("@type","m:TextIneraction");
	ObselTextInteraction.put ("m:ressourceType","Text");
	ObselTextInteraction.put ("m:ressourceID",((Obsel) listObselText.toArray()[k]).get_attribute("contextid").getValue());
	ObselTextInteraction.put ("m:courseID",((Obsel) listObselText.toArray()[k]).get_attribute("courseid").getValue());
	String contextid = ((Obsel) listObselText.toArray()[k]).get_attribute("contextid").getValue();
	
	
	k++ ;
	if (k>=listObselText.size()) {
		break;
		}
	while (k<listObselText.size() && (((Obsel) listObselText.toArray()[k]).get_attribute("contextid").getValue().equals(contextid))|| (! ((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("viewed")) && (!((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("loggedout")) &&(! ((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("loggedin"))) {
	if  ((! ((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("viewed"))) {nbInteraction ++ ;};
		
		k++ ;
		if (k>=listObselText.size()) {
			break;
			}
		
	}
	Integer end = (Integer) ((Obsel) listObselText.toArray()[k-1]).get_begin();
	ObselTextInteraction.put ("m:duration",end-begin);
	ObselTextInteraction.put ("end",((Obsel) listObselText.toArray()[k-1]).get_begin());
	
	
	
	ObselTextInteraction.put ("m:nbInteraction",nbInteraction);
	ktbsService.createObsel(base.get_Name(),primarytraceTextInteractionAction.get_Name(),ObselTextInteraction);
	
	Collection<Obsel> listObsel = ktbsService.getListObsel(base,null, null,primarytraceTextInteractionAction.get_Name() );
	compt++ ;
	if (compt==5) {
		Trace SignText = createSignatureTextInteractionSignature(base);
	    Collection<Obsel> listObselForumSign = ktbsService.getListObsel(base,null, null,SignText.get_Name() );
	    //RessourceInteractionActionSignature.
	    ktbsService.createObsel (base.get_Name(),RessourceInteractionActionSignature.get_Name(), ((Obsel) listObselForumSign.toArray()[0]).get_jsonObsel());
	}else if (listObsel.size()>2) {
		
		//calculer trust TODO
	            }
	    		
	    	} 
		}
	
	System.out.println ("##########################  END TEXT INTERACTION #########################");
	          }
	
	/***
	 * 
	 * @param base
	 * @throws Exception
	 * Créer ma trace comportement 
	 */
	
public static void createTraceSessionFormationBehaviorsSignatureTrust(SessionFactory factory,User user, Base base) throws Exception {
		
		
		

		/** CREATE TRACE SESSION BEHAVIOR**/
		Trace FormationSessionInteractionBehaviors = new Trace (base.get_uri()+"FormationSessionInteractionBehaviors/") ;
		FormationSessionInteractionBehaviors.remove();
		FormationSessionInteractionBehaviors = ktbsService.createPrimaryTrace (base,"modelprimary","FormationSessionInteractionBehaviors",null);
		Trace FormationSessionInteractionBehaviorsSignature = new Trace (base.get_uri()+"FormationSessionInteractionBehaviorsSignature/") ;
		FormationSessionInteractionBehaviorsSignature.remove();
		FormationSessionInteractionBehaviorsSignature = ktbsService.createPrimaryTrace (base,"modelprimary","FormationSessionInteractionBehaviorsSignature",null);

		Collection<Obsel> listObselRessourceInterraction = ktbsService.getListObsel(base,null, null,"RessourceInteractionAction" );
		
		 
		int m = 0 ;
		int SessionIndex = 0 ;
		float trust =1;
		float trustAction =0;


		while (m<listObselRessourceInterraction.size()) {
			//System.out.println (((Obsel) listObselRessourceInterraction.toArray()[m]).get_obsel_type().getTypeId());
			Session sessionHibernate=factory.openSession();
			sessionHibernate.beginTransaction();
			
			user =  (User) sessionHibernate.get(User.class, user.getUserName());
			
			user.setProfilActionUri (base.get_uri()+"RessourceInteractionAction/") ;
			user.setProfilSessionUri(FormationSessionInteractionBehaviors.get_uri());
			user.setSignatureSessioneUri(FormationSessionInteractionBehaviorsSignature.get_uri());
			
			user.setSignatureActionUri(base.get_uri()+"RessourceInteractionActionSignature/");
		    sessionHibernate.update( user);
			if (((Obsel) listObselRessourceInterraction.toArray()[m]).get_obsel_type().getTypeId().equals("loggedin")) {

				JSONObject Attributes = new JSONObject();
				double DurationTotalAllRessource = 0 ;
				int nbConsultationAllRessources = 0 ;
				double dureInExercice = 0 ;
				int nbConsultationExercice = 0 ;
				int nbExerciceReussit = 0 ;
				int nbExerciceFailed = 0 ;
				int nbExerciceAbandant = 0;
				int Sommescore = 0 ;
				double dureInText = 0 ;
				int nbConsultationText = 0 ;
				int nbAInteractionTextTotal = 0 ;
				double dureForum = 0 ;
				int nbConsultationForum = 0 ;
				double nbInteractionForum = 0 ;

				Attributes.put ("@id" , "Session"+ SessionIndex );	

				Integer begin = (Integer) ((Obsel) listObselRessourceInterraction.toArray()[m]).get_begin();
				Attributes.put ("begin",((Obsel) listObselRessourceInterraction.toArray()[m]).get_begin());
				Attributes.put ("@type","m:FormationSessionIneraction");
				JSONObject SessionTrustJson = new JSONObject();
				//Attributes.put ("m:sessionID",openCours.get_attribute("sessionID").getValue());
				
				m++;
				JSONArray ActionArray = new JSONArray() ;
				List<ActionTrust> actionTrusts = new ArrayList<>();
				while (m<listObselRessourceInterraction .size()  && (!((Obsel) listObselRessourceInterraction .toArray()[m]).get_obsel_type().getTypeId().equals("loggedout")) &&(! ((Obsel) listObselRessourceInterraction.toArray()[m]).get_obsel_type().getTypeId().equals("loggedin"))) {

					//	System.out.println("index while 2 = "+m +"id =" + ((Obsel) listObselRessourceInterraction.toArray()[m]).get_id());

					Obsel action = (Obsel) listObselRessourceInterraction .toArray()[m];
					//JSONObject actionTrustJson = new JSONObject();
					if ((action.get_obsel_type().getTypeId()).equals("ExerciceIneraction")) {

						dureInExercice += Double.valueOf(action.get_attribute("duration").getValue());
						DurationTotalAllRessource+=Double.valueOf(action.get_attribute("duration").getValue());
						nbConsultationAllRessources++ ;
						nbConsultationExercice++ ;

						if ((action.get_attribute("reussit").getValue()).equals("true")) {nbExerciceReussit++;}
						if ((action.get_attribute("reussit").getValue()).equals ("false")) {nbExerciceFailed ++;}
						if ((action.get_attribute("break").getValue()).equals ("true")) {nbExerciceAbandant ++;}

						Collection<Obsel> obselsignature = ktbsService.getListObsel(base,null, null,"RessourceInteractionActionSignature" );
				        float distance = calculDistance (action, obselsignature);
				        
				        JSONObject actionTrustJson = new JSONObject();
				        
				        trustAction = calculateCoefficient(distance, action, trustAction, base.get_uri()+"RessourceInteractionActionSignature/");
										
				        actionTrustJson.put("id_action", action.get_id());
				        actionTrustJson.put("type_action", action.get_obsel_type().getTypeId());
				        actionTrustJson.put("begin", action.get_begin());
				        actionTrustJson.put("distance", distance);
				        actionTrustJson.put ("end",action.get_end());
				        actionTrustJson.put("trust", trustAction);
				       
				        ActionTrust actionTrust = new ActionTrust ((String) actionTrustJson.get("id_action"),(String) actionTrustJson.get("type_action") , (Integer) actionTrustJson.get("begin") , (Integer) actionTrustJson.get("end"), (double)actionTrustJson.get("distance") ,(double) actionTrustJson.get("trust") );
						actionTrust.setTrust(trustAction);
						actionTrust.setDistance(distance);
						sessionHibernate.save(actionTrust);
						
						actionTrusts.add(actionTrust);
						ActionArray.put(actionTrustJson);
					}

					if ((action.get_obsel_type().getTypeId()).equals("TextIneraction")) {
						dureInText += Double.valueOf(action.get_attribute("duration").getValue());
						DurationTotalAllRessource+=Double.valueOf(action.get_attribute("duration").getValue());

						nbConsultationAllRessources++ ;
						nbConsultationText++;
						nbAInteractionTextTotal+= Double.valueOf(action.get_attribute("nbInteraction").getValue());
						Collection<Obsel> obselsignature = ktbsService.getListObsel(base,null, null,"RessourceInteractionActionSignature" );
				        float distance = calculDistance (action, obselsignature);
				        
				        JSONObject actionTrustJson = new JSONObject();
				        
				        trustAction = calculateCoefficient(distance, action, trustAction, base.get_uri()+"RessourceInteractionActionSignature/");
										
				        actionTrustJson.put("id_action", action.get_id());
				        actionTrustJson.put("type_action", action.get_obsel_type().getTypeId());
				        actionTrustJson.put("begin", action.get_begin());
				        actionTrustJson.put("distance", distance);
				        actionTrustJson.put ("end",action.get_end());
				        actionTrustJson.put("trust", trustAction);
				       
				        ActionTrust actionTrust = new ActionTrust ((String) actionTrustJson.get("id_action"),(String) actionTrustJson.get("type_action") , (Integer) actionTrustJson.get("begin") , (Integer) actionTrustJson.get("end"), (double)actionTrustJson.get("distance") ,(double) actionTrustJson.get("trust") );
						actionTrust.setTrust(trustAction);
						actionTrust.setDistance(distance);
						sessionHibernate.save(actionTrust);
						
						actionTrusts.add(actionTrust);
						ActionArray.put(actionTrustJson);

	
					}

					if ((action.get_obsel_type().getTypeId()).equals("ForumIneraction")) {
						dureForum += Double.valueOf(action.get_attribute("duration").getValue());
						DurationTotalAllRessource+=Double.valueOf(action.get_attribute("duration").getValue());

						nbConsultationAllRessources++ ;
						nbConsultationForum++;
						nbInteractionForum = nbInteractionForum + Double.valueOf(action.get_attribute("nbInteraction").getValue()) ;
						Collection<Obsel> obselsignature = ktbsService.getListObsel(base,null, null,"RessourceInteractionActionSignature" );
				        float distance = calculDistance (action, obselsignature);
				        
				        JSONObject actionTrustJson = new JSONObject();
				        
				        trustAction = calculateCoefficient(distance, action, trustAction, base.get_uri()+"RessourceInteractionActionSignature/");
										
				        actionTrustJson.put("id_action", action.get_id());
				        actionTrustJson.put("type_action", action.get_obsel_type().getTypeId());
				        actionTrustJson.put("begin", action.get_begin());
				        actionTrustJson.put("distance", distance);
				        actionTrustJson.put ("end",action.get_end());
				        actionTrustJson.put("trust", trustAction);
				       
				        ActionTrust actionTrust = new ActionTrust ((String) actionTrustJson.get("id_action"),(String) actionTrustJson.get("type_action") , (Integer) actionTrustJson.get("begin") , (Integer) actionTrustJson.get("end"), (double)actionTrustJson.get("distance") ,(double) actionTrustJson.get("trust") );
						actionTrust.setTrust(trustAction);
						actionTrust.setDistance(distance);
						sessionHibernate.save(actionTrust);
						
						actionTrusts.add(actionTrust);
						ActionArray.put(actionTrustJson);

					}
					    
						//Query query1 = sessionHibernate.createQuery("from ActionTrust where idAction=:id and begin=:beg");
						//query1.setParameter("id", action.get_id());
						//query1.setParameter("beg", action.get_begin());
						//ActionTrust actionTrust = (ActionTrust) query1.getSingleResult();
				   
						
						
					    m++ ;
	
				}
				user.setTrustFinalAction(trustAction);
			//	sessionHibernate.update(user);

			if ( m<listObselRessourceInterraction .size()  && ((Obsel) listObselRessourceInterraction.toArray()[m]).get_obsel_type().getTypeId().equals("loggedout")) {
				Attributes.put ("end",((Obsel) listObselRessourceInterraction.toArray()[m]).get_begin());
				Integer end = (Integer) ((Obsel) listObselRessourceInterraction.toArray()[m]).get_begin();
				Attributes.put("m:duration",  Double.toString (end - begin));
				m++;
			}else {
				int index;
				if(m==0) index =0;
				else index =m-1 ;
				Attributes.put ("end",((Obsel) listObselRessourceInterraction.toArray()[index]).get_begin());
				Integer end = (Integer) ((Obsel) listObselRessourceInterraction.toArray()[index]).get_end();
				Attributes.put("m:duration", Double.toString(end - begin));
            }
            if (! Attributes.get("m:duration").equals("0.0")) {

				Attributes.put("m:DurationTotalAllRessource", String.valueOf(DurationTotalAllRessource));
				Attributes.put("m:nbConsultationAllRessources", String.valueOf(nbConsultationAllRessources));
				Attributes.put("m:dureInExercice", dureInExercice);
				Attributes.put("m:nbConsultationExercice", nbConsultationExercice);
				Attributes.put("m:nbExerciceReussit", nbExerciceReussit);
				Attributes.put("m:nbExerciceFailed", nbExerciceFailed);
				Attributes.put("m:nbExerciceAbandant", nbExerciceAbandant);
				//Attributes.put("m:scoreMean", Sommescore/nbConsultationExercice);
				Attributes.put("m:ExerciceBehavior", ExerciceBehavior(nbConsultationExercice,nbExerciceReussit,nbExerciceFailed,nbExerciceAbandant));
				Attributes.put("m:dureInText", dureInText);
				Attributes.put("m:nbConsultationText", nbConsultationText);
				Attributes.put("m:nbAInteractionTextTotal", nbAInteractionTextTotal);
				Attributes.put("m:TextBehavior", TextBehavior(nbConsultationText,nbAInteractionTextTotal));
				Attributes.put("m:dureForum", dureForum);
				Attributes.put("m:nbConsultationForum", nbConsultationForum);
				Attributes.put("m:nbInteractionForum", nbInteractionForum);
				Attributes.put("m:ForumBehavior", ForumBehavior(nbConsultationForum,nbInteractionForum));
				ktbsService.createObsel(base.get_Name(),FormationSessionInteractionBehaviors.get_Name(),Attributes);

				System.out.println ("$$$$$$$$$$$$$$$$$$$Session numéro : "+SessionIndex +"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                Sessions session = new Sessions ((String)Attributes.get("@id"),(Integer)Attributes.get("begin"));
				sessionHibernate.save(session);
					
					/*Query query2 = sessionHibernate.createQuery("from Sessions where user_name=:name and begin=:beg and session_index=:index");
					query2.setParameter("name", user.getUserName());
					query2.setParameter("beg", Attributes.get("begin"));
					query2.setParameter("index", Attributes.get("@id"));
					Sessions session = (Sessions) query2.getSingleResult();*/
					//sessionHibernate.update(session);
					
				session.setAttributs(Attributes.toString());
				session.setEnd((Integer)Attributes.get("end"));
				session.setDuration((String)Attributes.get("m:duration"));
				session.setActionTrusts(actionTrusts);
				session.setTrustActionFinal(trustAction);
				user.addSession((Sessions)session);
				//sessionHibernate.update(user);
				sessionHibernate.update(session);
					
				Obsel lastSession = new Obsel (FormationSessionInteractionBehaviors.get_uri(),FormationSessionInteractionBehaviors.get_model().get_uri(),Attributes);
				Collection<Obsel> obselSignature = ktbsService.getListObsel(base,null, null,FormationSessionInteractionBehaviorsSignature.get_Name() );
                // traitement pour First session 
				SessionTrustJson.put("sessionID","Session"+SessionIndex);
				SessionTrustJson.put("listTrustActions",ActionArray);
                if (obselSignature.isEmpty()) {
	                JSONObject FirstclusterBArycentre = calculbarycentre (null, lastSession,(int)obselSignature.size()+1,(double)0);
					ktbsService.createObsel(base.get_Name() , FormationSessionInteractionBehaviorsSignature.get_Name(),FirstclusterBArycentre);
					SessionTrustJson.put("cluster", "Premier cluster ");		   
					SessionTrustJson.put("clusterName", FirstclusterBArycentre.get("m:name"));
                    Cluster cluster = new Cluster ( (String) FirstclusterBArycentre.get("m:name"));
					//Query query = sessionHibernate.createQuery("from Cluster where clusterName=:clName and user_name=:usName ");
					//query.setParameter("clName", (String) FirstclusterBArycentre.get("m:name"));
					//query.setParameter("usName", user.getUserName() );
					//Cluster cluster = (Cluster) query.getSingleResult();
					cluster.setAverageduration((String) FirstclusterBArycentre.get("m:Averageduration"));
					cluster.setAveragedurationTotalRessource((String) FirstclusterBArycentre.get("m:AveragedurationTotalRessource"));				
					cluster.setAveragenbConsultationAllRessources((String) FirstclusterBArycentre.get("m:AveragenbConsultationAllRessources"));	
					cluster.setPoid((double) FirstclusterBArycentre.get("m:poid")); 
					cluster.setList_exercice_behavior(FirstclusterBArycentre.get("m:listExerciceBehavior").toString());	
					cluster.setList_text_behavior((String) FirstclusterBArycentre.get("m:listTextBehavior").toString());	
					cluster.setList_forum_behavior((String) FirstclusterBArycentre.get("m:listForumBehavior").toString());	                    		
					cluster.addSession(session);
					cluster.setUser(user);
					sessionHibernate.save(cluster);
   
				}else  {
                // Autre session 
                    double [] distance = new double [100] ;
					double [] Poid = new double [100] ;
					Double score =0.0;
					Double[] weigth = {0.3,0.2,0.2,0.1,0.1,0.1} ;
					Double  k = 0.5 ;
					//float trust =1;
					JSONArray detailsDistances = new JSONArray ();
					/** Calculer distance par rapport tous les clusters dans trace Signature **/
					double sommePoid = 0 ;
					for (int j=0; j< obselSignature.size(); j++) {
						double Simularite = calculSimularitySession (lastSession.get_jsonObsel(),((Obsel) obselSignature.toArray()[j]).get_jsonObsel(),weigth);
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
					session.setDetailsDistances(detailsDistances.toString());
					Double SimMax = MaxTableau (distance);
					int indexMax =  MaxIndex (distance) ;
					// Clustering and Calcul Trust
                    SessionTrustJson.put("detailsDistances", detailsDistances);
					if (SimMax>k) {
						System.out.println ("mise à jour barycenter Signture");
						// Mise à jour l'obsel Cluster de la trace signature 
						Obsel AncienBarycenter = ((Obsel) obselSignature.toArray()[indexMax]);
						// Supprimer Obsel du trace Signature 
						Obsel obs = ((Obsel) obselSignature.toArray()[indexMax]);
						obs.remove() ;
						// create nouveau obsel ==> obsel de mise à jour 
						JSONObject NouveauclusterBArycentre = calculbarycentre (AncienBarycenter, lastSession,indexMax+1,SimMax);
						ktbsService.createObsel(base.get_Name() , FormationSessionInteractionBehaviorsSignature.get_Name(),NouveauclusterBArycentre);
						trust = (float) (trust + score) ;
						SessionTrustJson.put("cluster", "mise a jour cluster");
						SessionTrustJson.put("clusterName", NouveauclusterBArycentre.get("m:name"));
						SessionTrustJson.put("barycentre", NouveauclusterBArycentre);
		                Query query = sessionHibernate.createQuery("from Cluster where clusterName=:clName and user_name=:usName ");
						query.setParameter("clName", (String) NouveauclusterBArycentre.get("m:name"));
						query.setParameter("usName", user.getUserName() );
						Cluster cluster = (Cluster) query.getSingleResult();
                        cluster.setAverageduration((String) NouveauclusterBArycentre.get("m:Averageduration"));
						cluster.setAveragedurationTotalRessource((String) NouveauclusterBArycentre.get("m:AveragedurationTotalRessource"));				
						cluster.setAveragenbConsultationAllRessources((String) NouveauclusterBArycentre.get("m:AveragenbConsultationAllRessources"));	
						cluster.setPoid((double) NouveauclusterBArycentre.get("m:poid")); 
						cluster.setList_exercice_behavior(NouveauclusterBArycentre.get("m:listExerciceBehavior").toString());	
						cluster.setList_text_behavior(NouveauclusterBArycentre.get("m:listTextBehavior").toString());	
						cluster.setList_forum_behavior(NouveauclusterBArycentre.get("m:listForumBehavior").toString());	                    		
						cluster.addSession(session);
						sessionHibernate.update(cluster);
						SessionTrustJson.put("trust", trust);
						session.setTrust(trust);
						sessionHibernate.update(cluster);
						sessionHibernate.update(session);
					}else {
						System.out.println ("creer nouveau cluster");
						// create un nouveau Cluster ==> un nouveau obsel dans signature 
						JSONObject NouveauclusterBArycentre = calculbarycentre (null, lastSession,(int)obselSignature.size()+1,SimMax);
						ktbsService.createObsel(base.get_Name() ,  FormationSessionInteractionBehaviorsSignature.get_Name(), NouveauclusterBArycentre);
						trust = (float) (trust * score) ;
						SessionTrustJson.put("cluster", "Nouveau cluster");
						SessionTrustJson.put("clusterName", NouveauclusterBArycentre.get("m:name"));
						SessionTrustJson.put("barycentre", NouveauclusterBArycentre);
						SessionTrustJson.put("trust", trust);
	                    Cluster cluster =new Cluster ((String) NouveauclusterBArycentre.get("m:name"));
						sessionHibernate.save(cluster);	
						//	Query query = sessionHibernate.createQuery("from Cluster where clusterName=:clName and user_name=:usName ");
						//	query.setParameter("clName", (String) NouveauclusterBArycentre.get("m:name"));
						//	query.setParameter("usName", user.getUserName() );
						//	Cluster cluster = (Cluster) query.getSingleResult();
						cluster.setAverageduration((String) NouveauclusterBArycentre.get("m:Averageduration"));
						cluster.setAveragedurationTotalRessource((String) NouveauclusterBArycentre.get("m:AveragedurationTotalRessource"));				
						cluster.setAveragenbConsultationAllRessources((String) NouveauclusterBArycentre.get("m:AveragenbConsultationAllRessources"));	
						cluster.setPoid((double) NouveauclusterBArycentre.get("m:poid")); 
						cluster.setList_exercice_behavior(NouveauclusterBArycentre.get("m:listExerciceBehavior").toString());	
						cluster.setList_text_behavior(NouveauclusterBArycentre.get("m:listTextBehavior").toString());	
						cluster.setList_forum_behavior( NouveauclusterBArycentre.get("m:listForumBehavior").toString());	                    		
						cluster.addSession(session);
						session.setTrust(trust);
						cluster.setUser(user);
						sessionHibernate.update(cluster);
					}
						
                    
					
				}
                // GEt SESSION PRECEDENTE 
                if (SessionIndex >=1 ) {
						Query query33 = sessionHibernate.createQuery("from Sessions where user_name=:n and session_index=:i");
						query33.setParameter("n", user.getUserName() );
						int s = SessionIndex-1;
						query33.setParameter("i", "Session"+s);
						Sessions sessionPrec = (Sessions) query33.getSingleResult();
	                    session.setDiffTrustActionFinal(session.getTrustActionFinal()-sessionPrec.getTrustActionFinal());
	                    session.setDiffTrust(session.getTrust()-sessionPrec.getTrust());
						sessionHibernate.update(session);
                } else {    
                        session.setDiffTrustActionFinal(0);
                        session.setDiffTrust(0);
                }
                SessionIndex ++;
				System.out.println ("----------session trust json--------------------------------------------------");           		   
				System.out.println (SessionTrustJson.toString());
				user.setTrustFinalSession(trust);
				sessionHibernate.update(user);
				sessionHibernate.getTransaction().commit();

				}
			} else {
			m++;
			}
        }
	
}


		/***
		 * 
		 * @param base
		 * @return
		 * @throws Exception
		 */
		public static Trace createSignatureExerciceInteractionSignature (Base base) throws Exception {
			
		Trace traceRessourceInteraction = new Trace (base.get_uri()+"RessourceInteractionAction/");
		String modeUrl = ktbsService.getUrlModel(base,traceRessourceInteraction) ;
		
		// Exercice-Interaction-Signature
		String [] tracesourceExercice = {traceRessourceInteraction.get_Name()+"/"};
		
		
		String sparlExercice =  "sparql=    prefix : <http://liris.cnrs.fr/silex/2009/ktbs#>\nprefix model: <"+modeUrl+">\n\nCONSTRUCT {\n  [ a ?ctyp ;\n    model:obstyp ?typ ;\n    model:meanDuration ?meanDuration;\n    model:deviationDuration ?deviationDuration;\n\n   \n\n    model:nbOccurence ?count;\n  \n    :hasTrace <%(__destination__)s> ;\n    :hasBegin ?begin ;\n    :hasEnd ?end ;\n  ]\n}\nwhere {\n\n    SELECT  ?typ\n           (SAMPLE(?ctyp2) as ?ctyp)\n           (SAMPLE(?count1) as ?count)\n           (SAMPLE(?meanDuration1) as ?meanDuration)\n            \n            \n           (SUM(ABS(?duration2 - ?meanDuration1)/(?count1 - 1)) as ?deviationDuration)\n \n           (MIN(?begin2) as ?begin)\n           (MAX(?end2) as ?end)\n    {\n\n        {\n            SELECT  ?typ\n                   (SAMPLE(?ctyp1) as ?ctyp2)\n                   (COUNT(?o1) as ?count1)\n                   (AVG(?duration1) as ?meanDuration1)\n                 \n                   \n            {\n                ?o1 a ?typ ;\n                    \n                    model:duration ?duration1 .\n                     \n\n                VALUES (?typ ?ctyp1) {\n                    (model:ExerciceIneraction model:ExerciceInteractionSignature)\n                }\n            }\n            GROUP BY  ?typ\n            HAVING (COUNT(?o1) >= 2)\n        }\n\n        # then aggregate obsels again, in order to compute deviation (see outer select).\n\n        ?o2 a ?typ ;\n            model:duration ?duration2 ;\n            \n            :hasBegin ?begin2 ;\n            :hasEnd ?end2 .\n\n    } GROUP BY  ?typ\n\n\n}\n\n" ;
		
		
		String[] parametreExercice = {sparlExercice};
		
		
		
		 
		 
	   Trace TraceExerciceInteractionSignature = ktbsService.createTransformedTrace(base, "Trace-Exercice-Interaction-Signature","sparql" , tracesourceExercice, parametreExercice) ;		   
		return TraceExerciceInteractionSignature;
		}
		/***
		 * 
		 * @param base
		 * @return
		 * @throws Exception
		 */
		public static Trace createSignatureTextInteractionSignature (Base base) throws Exception {
			
			Trace traceRessourceInteraction = new Trace (base.get_uri()+"RessourceInteractionAction/");
			String modeUrl = ktbsService.getUrlModel(base,traceRessourceInteraction) ;
	    // Text-Interaction-Signature
	    
	  
		String [] tracesourcesText = {traceRessourceInteraction.get_Name()+"/"};
		
		String sparlText = "sparql=    prefix : <http://liris.cnrs.fr/silex/2009/ktbs#>\n     prefix model: <"+modeUrl+"> \nCONSTRUCT {\n  [ a ?ctyp ;\n    model:obstyp ?typ ;\n    model:meanDuration ?meanDuration;\n    model:deviationDuration ?deviationDuration;\n\n    model:meannbInteraction ?meannbInteraction;\n    model:deviationnbInteraction ?deviationnbInteraction;\n\n    model:nbOccurence ?count;\n  \n    :hasTrace <%(__destination__)s> ;\n    :hasBegin ?begin ;\n    :hasEnd ?end ;\n  ]\n}\nwhere {\n\n    SELECT  ?typ\n           (SAMPLE(?ctyp2) as ?ctyp)\n           (SAMPLE(?count1) as ?count)\n           (SAMPLE(?meanDuration1) as ?meanDuration)\n            (SAMPLE(?meannbInteraction1) as ?meannbInteraction)\n            \n           (SUM(ABS(?duration2 - ?meanDuration1)/(?count1 - 1)) as ?deviationDuration)\n           (SUM(ABS(?nbInteraction2 - ?meannbInteraction1)/(?count1 - 1)) as ?deviationnbInteraction)\n           (MIN(?begin2) as ?begin)\n           (MAX(?end2) as ?end)\n    {\n\n        {\n            SELECT  ?typ\n                   (SAMPLE(?ctyp1) as ?ctyp2)\n                   (COUNT(?o1) as ?count1)\n                   (AVG(?duration1) as ?meanDuration1)\n                  (AVG(?nbInteraction1) as ?meannbInteraction1)\n                   \n            {\n                ?o1 a ?typ ;\n                    \n                    model:duration ?duration1 ;\n                     model:nbInteraction ?nbInteraction1 .\n\n                VALUES (?typ ?ctyp1) {\n                    (model:TextIneraction model:TextInteractionSignature)\n                }\n            }\n            GROUP BY  ?typ\n            HAVING (COUNT(?o1) >= 2)\n        }\n\n        # then aggregate obsels again, in order to compute deviation (see outer select).\n\n        ?o2 a ?typ ;\n            model:duration ?duration2 ;\n             model:nbInteraction ?nbInteraction2;\n            :hasBegin ?begin2 ;\n            :hasEnd ?end2 .\n\n    } GROUP BY  ?typ\n\n}\n\n";

		String[] parametreText = {sparlText};
		
		
		
	    Trace TraceTextInteractionSignature = ktbsService.createTransformedTrace(base, "Trace-Text-Interaction-Signature","sparql" , tracesourcesText, parametreText) ;		   
	   
	    
	    
	    return TraceTextInteractionSignature;
		}
        /***
         * 
         * @param base
         * @return
         * @throws Exception
         */
        public static Trace createSignatureForumInteractionSignature (Base base) throws Exception {
			
			Trace traceRessourceInteraction = new Trace (base.get_uri()+"RessourceInteractionAction/");
			String modeUrl = ktbsService.getUrlModel(base,traceRessourceInteraction) ;
	    // Forum-Interaction-Signature
	    
	    String [] tracesourcesForum = {traceRessourceInteraction.get_Name()+"/"};
		
		String sparlForum =  "sparql=    prefix : <http://liris.cnrs.fr/silex/2009/ktbs#>\nprefix model: <"+modeUrl+"> \n\n \n\nCONSTRUCT {\n  [ a ?ctyp ;\n    model:obstyp ?typ ;\n    model:meanDuration ?meanDuration;\n    model:deviationDuration ?deviationDuration;\n\n    model:meannbInteraction ?meannbInteraction;\n    model:deviationnbInteraction ?deviationnbInteraction;\n\n   \n    model:nbOccurence ?count;\n  \n    :hasTrace <%(__destination__)s> ;\n    :hasBegin ?begin ;\n    :hasEnd ?end ;\n  ]\n}\nwhere {\n\n    SELECT  ?typ\n           (SAMPLE(?ctyp2) as ?ctyp)\n           (SAMPLE(?count1) as ?count)\n           (SAMPLE(?meanDuration1) as ?meanDuration)\n            (SAMPLE(?meannbInteraction1) as ?meannbInteraction)\n\n            \n           (SUM(ABS(?duration2 - ?meanDuration1)/(?count1 - 1)) as ?deviationDuration)\n           (SUM(ABS(?nbInteraction2 - ?meannbInteraction1)/(?count1 - 1)) as ?deviationnbInteraction)\n \n           (MIN(?begin2) as ?begin)\n           (MAX(?end2) as ?end)\n    {\n\n        {\n            SELECT  ?typ\n                   (SAMPLE(?ctyp1) as ?ctyp2)\n                   (COUNT(?o1) as ?count1)\n                   (AVG(?duration1) as ?meanDuration1)\n                  (AVG(?nbInteraction1) as ?meannbInteraction1)\n  \n                   \n            {\n                ?o1 a ?typ ;\n                    \n                    model:duration ?duration1 ;\n model:nbInteraction ?nbInteraction1 .\n                    \n\n                VALUES (?typ ?ctyp1) {\n                    (model:ForumIneraction model:ForumInteractionSignature)\n                }\n            }\n            GROUP BY  ?typ\n            HAVING (COUNT(?o1) >= 2)\n        }\n\n        # then aggregate obsels again, in order to compute deviation (see outer select).\n\n        ?o2 a ?typ ;\n            model:duration ?duration2 ;\n             model:nbInteraction ?nbInteraction2;\n            :hasBegin ?begin2 ;\n            :hasEnd ?end2 .\n\n    } GROUP BY  ?typ\n\n\n\n}\n\n";
		String[] parametreForum = {sparlForum};
		
		Trace TraceForumInteractionSignature = ktbsService.createTransformedTrace(base, "Trace-Forum-Interaction-Signature","sparql" , tracesourcesForum, parametreForum) ;		   
	    return TraceForumInteractionSignature ;
        }
	    //fusion
	    
	 /*   String[] parametres = {} ;
	    String[] tracesources = {TraceExerciceInteractionSignature.get_Name()+"/", TraceTextInteractionSignature.get_Name()+"/", TraceForumInteractionSignature.get_Name()+"/"} ;
        ktbsService.createTransformedTrace(base, "RessourceInteractionAction", "fusion", tracesources, parametres);     
            */
	    
	/***
	 * 
	 * @param nbConsultationExercice
	 * @param nbExerciceReussit
	 * @param nbExerciceFailed
	 * @param nbExerciceAbandant
	 * @return
	 */
	public static String ExerciceBehavior(int nbConsultationExercice , int nbExerciceReussit,int nbExerciceFailed,int nbExerciceAbandant) {
		String behavior = ""; 
		
		if (nbConsultationExercice==0) behavior ="Absent";
		else if (nbExerciceReussit>=nbExerciceFailed && nbExerciceAbandant <= nbExerciceReussit+nbExerciceFailed ) behavior ="Reussit-Completing";
		else if (nbExerciceReussit<=nbExerciceFailed && nbExerciceAbandant <= nbExerciceReussit+nbExerciceFailed)		behavior ="Failed-Completing";
		else if (nbExerciceAbandant >= nbExerciceReussit+nbExerciceFailed)				behavior ="Incompleting" ;
		return behavior ;
	}

	  public static String TextBehavior(int nbConsultationText,int nbActionclickTotal) {
		String behavior = ""; 
		if (nbConsultationText==0) behavior = "Absent" ;
		else if (nbActionclickTotal>2)	behavior = "Active ";
		else  		behavior = "Inactive";
		return behavior ;
	}
	
	public static String ForumBehavior(int nbConsultationForum,double nbInteractionForum) {
		String behavior = ""; 
		if (nbConsultationForum ==0 ) behavior = "Absent";
		else if (nbInteractionForum ==0)behavior = "Lecteur";
		else behavior = "Acteur";
		return behavior ;
		
	}
	
     public static JSONObject calculbarycentre (Obsel AncientBarycentre, Obsel NouveauSession,int index, Double similarit�) throws JSONException {
		
		JSONObject barycenter = new JSONObject();
		
		
		
		if (AncientBarycentre != null) {
			barycenter.put ("@id",AncientBarycentre.get_id());
			barycenter.put ("begin",AncientBarycentre.get_begin());
			barycenter.put ("end",AncientBarycentre.get_begin());
			barycenter.put ("@type","m:cluster");
			barycenter.put ("m:name",AncientBarycentre.get_id());
			
		}else {
			barycenter.put ("@id","Cluster"+index);
			barycenter.put ("begin",NouveauSession.get_begin());
			barycenter.put ("end",NouveauSession.get_begin());
			barycenter.put ("@type","m:cluster");
			barycenter.put ("m:name","cluster"+index);
		}
		
		//barycenter.put ("m:poid","1")
		
		if (AncientBarycentre != null) { 
			Double NouveauPoid =  Double.parseDouble (AncientBarycentre.get_attribute("poid").getValue()) + similarit� ;
			barycenter.put ("m:poid",NouveauPoid);
			}
		else {barycenter.put ("m:poid",1.0);}
		
		//List<String> listSession = new ArrayList<String>();
		JSONArray listSession =new JSONArray () ;
		listSession.put("");
		//barycenter.put("m:listSession", listSession) ;
		if (AncientBarycentre != null) {listSession=new JSONArray (AncientBarycentre.get_attribute("listSession").getValue());}
		listSession.put(NouveauSession.get_uri());
		//println (listSession) ;
		barycenter.put("m:listSession", listSession) ;
		
		JSONArray listExerciceBehavior =new JSONArray ();
		System.out.println (NouveauSession.get_attribute("ExerciceBehavior").getValue());
		listExerciceBehavior.put("");
		if (AncientBarycentre != null) {
			listExerciceBehavior = new JSONArray (AncientBarycentre.get_attribute("listExerciceBehavior").getValue());}
		if (!listExerciceBehavior.toString().contains((String) NouveauSession.get_attribute("ExerciceBehavior").getValue())) {listExerciceBehavior.put ((String) NouveauSession.get_attribute("ExerciceBehavior").getValue());}
		barycenter.put("m:listExerciceBehavior", listExerciceBehavior) ;
		
		JSONArray listTextBehavior =new JSONArray ();
		listTextBehavior.put("");
		if (AncientBarycentre != null) { listTextBehavior = new JSONArray (AncientBarycentre.get_attribute("listTextBehavior").getValue());}
		if (!listTextBehavior.toString().contains((String) NouveauSession.get_attribute("TextBehavior").getValue())) {listTextBehavior.put ((String) NouveauSession.get_attribute("TextBehavior").getValue()); }
		
	    barycenter.put("m:listTextBehavior", listTextBehavior) ;
	  
		
	    JSONArray listForumBehavior =new JSONArray ();
	    listForumBehavior.put("");
		if (AncientBarycentre != null) {
			listForumBehavior= new JSONArray (AncientBarycentre.get_attribute("listForumBehavior").getValue());}
		if (!listForumBehavior.toString().contains((String) NouveauSession.get_attribute("ForumBehavior").getValue())) { listForumBehavior.put ((String) NouveauSession.get_attribute("ForumBehavior").getValue());}
		barycenter.put("m:listForumBehavior", listForumBehavior) ;
		
		if (AncientBarycentre != null) {
		double nbConsultationAllRessources  = moyenne ( 
				Double.parseDouble ((String) AncientBarycentre.get_attribute("AveragenbConsultationAllRessources").getValue()),
				Double.parseDouble ((String) NouveauSession.get_attribute("nbConsultationAllRessources").getValue()));
		barycenter.put("m:AveragenbConsultationAllRessources", Double.toString (nbConsultationAllRessources)) ;
		} else barycenter.put("m:AveragenbConsultationAllRessources", NouveauSession.get_attribute("nbConsultationAllRessources").getValue()) ;
		
		if (AncientBarycentre != null) {
		double duration  = moyenneGeometric (
				Double.parseDouble ((String) AncientBarycentre.get_attribute("Averageduration").getValue()),
				Double.parseDouble ((String) NouveauSession.get_attribute("duration").getValue()));
		barycenter.put("m:Averageduration", Double.toString (duration)) ;}
		else barycenter.put("m:Averageduration", NouveauSession.get_attribute("duration").getValue()) ;
		
		if (AncientBarycentre != null) {
			double DurationTotalAllRessource  = moyenneGeometric (
					Double.parseDouble ((String) AncientBarycentre.get_attribute("AveragedurationTotalRessource").getValue()),
					Double.parseDouble ((String) NouveauSession.get_attribute("DurationTotalAllRessource").getValue()));
			barycenter.put("m:AveragedurationTotalRessource", Double.toString (DurationTotalAllRessource)) ;}
			else barycenter.put("m:AveragedurationTotalRessource", NouveauSession.get_attribute("DurationTotalAllRessource").getValue()) ;
			
		 
		return barycenter;
	}
     public static double calculSimularitySession (JSONObject Session, JSONObject barycentre, Double[] weigth) throws NumberFormatException, JSONException {
 		
		  double d = (double) (weigth [5] * simulairString ((String) Session.get("m:ExerciceBehavior"),(JSONArray)barycentre.get("m:listExerciceBehavior"))
				  + weigth [4] * simulairString ((String) Session.get("m:ForumBehavior"),(JSONArray)barycentre.get("m:listForumBehavior"))
				  + weigth [3] * simulairString ((String) Session.get("m:TextBehavior"),(JSONArray)barycentre.get("m:listTextBehavior"))
				  + weigth [1] * simulairDuration (Double.parseDouble ((String) Session.get("m:DurationTotalAllRessource")),Double.parseDouble( (String) barycentre.get("m:AveragedurationTotalRessource")))
				  + weigth [0] * simulairDuration (Double.parseDouble ( (String)( Session.get("m:duration"))),Double.parseDouble((String) barycentre.get("m:Averageduration")))
		          + weigth [2] * simulairDuration (Double.parseDouble ((String) ( Session.get("m:nbConsultationAllRessources"))),Double.parseDouble((String) barycentre.get("m:AveragenbConsultationAllRessources"))));
		 
		
		
		return d ;
		  }
     
     public static double simulairString (String str, JSONArray Liststr2) {
   	  
   	  if (Liststr2.toString().contains(str)) {return 1 ;}
   	  else return 0;
   	  
     }
     public static double min (double a, double b) {
 		if (a<b) return a;
 		else return b;
 		
 	}
     public static double max (double a, double b) {
   	  if (a>b) return a;
   	  else return b;
   		
   	}
     public static double simulairDuration (double D1, double D2) {
    	 double d;
		 if (D1==0  || D2==0) {d=0;}
    	 
		 else {d = min (D1,D2) /  (double) (max(D1,D2)) ;}
		  return d ;
	  }

     public static double moyenne (double a, double b) {
   	  
   	  double r = (double)(a+b)/2 ;
   	  //System.out.println ("moyenne "+ a +" et" + b + "=" + r);
   	   return   r ;
     }
     
     public static double moyenneGeometric (double a, double b) {
   	  
   	  double m =  (double) Math.sqrt( (double) a*b ) ;
   	  return m ;
     }
     public static double MaxTableau (double [] tab ){

   	  double max = 0;

   	  for (int i = 0; i < tab.length; i++) {
   		  if (tab[i] > max){
   			  max = tab[i];
   		  }
   	  }

   	  return max;
     }
     
     public static int MaxIndex (double [] tab ){

   	  double max = 0;
   	  int index = 0 ;

   	  for (int i = 0; i < tab.length; i++) {
   		  if (tab[i] > max){ index = i; max = tab[i]; }
   	  }

   	  return index;
     }
     
     public static float calculDistance (Obsel obs, Collection<Obsel> obselsignature ) throws JSONException {
 		
 		float distance = -1 ;
 		if (obs.get_obsel_type().getTypeId().equalsIgnoreCase("ExerciceIneraction")) {
 			JSONObject obAttribute = new JSONObject();
 				//	obAttribute.put(Constants.att_keySource,obs?.get_attribute (Constants.att_keySource)?.getValue())
 					Obsel obssignature = ktbsService.getObselWithType ("ExerciceIneraction",null,obselsignature);
 					if (obssignature!=null){
 						float mean = Float.parseFloat (obssignature.get_attribute ("meanDuration").getValue());
 						float deviation = Float.parseFloat(obssignature.get_attribute ("deviationDuration").getValue());
 						float time = Float.parseFloat (obs.get_attribute ("duration").getValue());
 						if (deviation==0 ) {distance =0;}
 						else {
 							distance = Math.abs ((time - mean)/deviation );
 						}
 						
 						
 						
 		           }
 		}
 		if (obs.get_obsel_type().getTypeId().equalsIgnoreCase("ForumIneraction")) {
 			JSONObject obAttribute = new JSONObject();
 				//	obAttribute.put(Constants.att_keySource,obs?.get_attribute (Constants.att_keySource)?.getValue())
 					Obsel obssignature = ktbsService.getObselWithType ("ForumInteractionSignature",null,obselsignature);
 					if (obssignature!=null){
 						float meanDuration = Float.parseFloat (obssignature.get_attribute ("meanDuration").getValue());
 						float deviationDuration = Float.parseFloat(obssignature.get_attribute ("deviationDuration").getValue());
 						float Duration = Float.parseFloat (obs.get_attribute ("duration").getValue());
 						
 						float meannbInteraction = Float.parseFloat (obssignature.get_attribute ("meannbInteraction").getValue());
 						float deviationnbInteraction = Float.parseFloat(obssignature.get_attribute ("deviationnbInteraction").getValue());
 						float nbInteraction = Float.parseFloat (obs.get_attribute ("nbInteraction").getValue());
 						
 						if (deviationnbInteraction==0 && deviationDuration !=0) {
 							distance = (Math.abs ((Duration - meanDuration)/deviationDuration ));
 						}
 						else if (deviationDuration ==0 && deviationnbInteraction!=0 ) {
 							distance = Math.abs ((nbInteraction - meannbInteraction)/deviationnbInteraction ); 
 						}
 						else if (deviationnbInteraction==0 && deviationDuration ==0) distance =0 ;
 						else {distance = (Math.abs ((Duration - meanDuration)/deviationDuration )+ Math.abs ((nbInteraction - meannbInteraction)/deviationnbInteraction ))/2;
 						}
 						
 						
 		           }
 		}
 		
 		if (obs.get_obsel_type().getTypeId().equalsIgnoreCase("TextIneraction")) {
 			JSONObject obAttribute = new JSONObject();
 				//	obAttribute.put(Constants.att_keySource,obs?.get_attribute (Constants.att_keySource)?.getValue())
 					Obsel obssignature = ktbsService.getObselWithType ("TextInteractionSignature",null,obselsignature);
 					if (obssignature!=null){
 						float meanDuration = Float.parseFloat (obssignature.get_attribute ("meanDuration").getValue());
 						float deviationDuration = Float.parseFloat(obssignature.get_attribute ("deviationDuration").getValue());
 						float Duration = Float.parseFloat (obs.get_attribute ("duration").getValue());
 						
 						float meannbInteraction = Float.parseFloat (obssignature.get_attribute ("meannbInteraction").getValue());
 						float deviationnbInteraction = Float.parseFloat(obssignature.get_attribute ("deviationnbInteraction").getValue());
 						float nbInteraction = Float.parseFloat (obs.get_attribute ("nbInteraction").getValue());
 						
 						if (deviationnbInteraction==0 && deviationDuration !=0) {
 							distance = (Math.abs ((Duration - meanDuration)/deviationDuration ));
 						}
 						else if (deviationDuration ==0 && deviationnbInteraction!=0 ) {
 							distance = Math.abs ((nbInteraction - meannbInteraction)/deviationnbInteraction ); 
 						}
 						else if (deviationnbInteraction==0 && deviationDuration ==0) distance =0 ;
 						else {distance = (Math.abs ((Duration - meanDuration)/deviationDuration )+ Math.abs ((nbInteraction - meannbInteraction)/deviationnbInteraction ))/2;
 						}
 						}
 		}
 		return distance ;
     }
     
     public static float  calculateCoefficient(float distance, Obsel obs, float previousCalculatedTrust, String urlBaseSignature) {
			float trust = previousCalculatedTrust;
			
			float Dthreshold =(float) 2 ;
			float Dnoise = 17 ;
			if (distance <Dnoise && distance >0){
				trust = (float) (trust + Dthreshold -distance);
			}
			else if(distance >=Dnoise)
			{
				//println (obs.get_id()+ ";" + obs.get_obsel_type().getTypeId()+ ";" + distance + ";" + trust + " DISTANCE TOO BIG")
			}
		
			
			return trust;
		}
     
     public static JSONObject saveTrustAction (Base base , Obsel action , float ancientrust) throws Exception {
    	 Collection<Obsel> obselsignature = ktbsService.getListObsel(base,null, null,"RessourceInteractionActionSignature" );
        float distance = calculDistance (action, obselsignature);
        float trustAction = 0;
        JSONObject Obj = new JSONObject();
        //	if(distance >= 0){
        		trustAction = calculateCoefficient(distance, action, trustAction, base.get_uri()+"RessourceInteractionActionSignature/");
						
						Obj.put("id_action", action.get_id());
						Obj.put("type_action", action.get_obsel_type().getTypeId());
						Obj.put("begin", action.get_begin());
						Obj.put("distance", distance);
						Obj.put ("end",action.get_end());
						Obj.put("trust", trustAction);
						//items.put(Obj);
        		
        //	}
        	System.out.println (" save trust Action Obj "+ Obj.toString());
        	return Obj;
     }
     public static void servicediagnostique () {
    	 SessionFactory factory=HibernateUtil.getFactory();
    	 Session sessionHibernate=factory.openSession();
	
    	 CriteriaBuilder cb = sessionHibernate.getCriteriaBuilder();
    	 CriteriaQuery<Sessions> cq = cb.createQuery(Sessions.class);
    	 Root<Sessions> rootEntry = cq.from(Sessions.class);
    	 CriteriaQuery<Sessions> all = cq.select(rootEntry);
	
    	 TypedQuery<Sessions> allQuery = sessionHibernate.createQuery(all);
    	 List<Sessions> SessionList = allQuery.getResultList();
    	 
    	 int i = 0; 
    		
    	 for (Sessions sessions : SessionList) {
    		 sessionHibernate.beginTransaction();
    		 Double normatTrustsApp = DiagnosticService.NormalTrustActionApp(sessions.getTrustActionFinal());
    		 Double normalDiffTrust = DiagnosticService.NormalTrustCompoAppr(sessions.getDiffTrust());
    		 sessions.setNormaldiffTrust(normalDiffTrust);
    		 sessions.setNormaltrustActionFinal(normatTrustsApp);
    	     sessions.setScoreFinal(DiagnosticService.DegreConfTotal(normatTrustsApp, normalDiffTrust));
    	     sessionHibernate.update(sessions);
    	     sessionHibernate.getTransaction().commit();
    	     System.out.println ("update sessions " + i);
    	     i++;
    	 }
    	 
     }
     public static void main(String[] args) throws Exception {
    	 servicediagnostique ();
			
//    	 SessionFactory factory=HibernateUtil.getFactory();
//    	 Session sessionHibernate=factory.openSession();
//	
//    	 CriteriaBuilder cb = sessionHibernate.getCriteriaBuilder();
//    	 CriteriaQuery<User> cq = cb.createQuery(User.class);
//    	 Root<User> rootEntry = cq.from(User.class);
//    	 CriteriaQuery<User> all = cq.select(rootEntry);
//	
//    	 TypedQuery<User> allQuery = sessionHibernate.createQuery(all);
//    	 List<User> userList = allQuery.getResultList();
//    	 
//    	
// 		
//    	 int i = 0; 
//	
//    	 for (User user : userList) {
//    		 i++;
//    	//	if ( i>2016) {
//    			//792
//    			
//    		if(user.getUserName().equals ("user8796387446511108097")) {
//    			 // user8796387446511108097
//    				 //("user1042190010696073217")) {
//    		 Base base = new Base (user.getBaseUri());
//    		 Trace  primarytrace = new Trace (user.getPrimarytraceUri());
//    		createTraceRessourceActionSifnatureTrust (factory,user,base,primarytrace);
//    		createTraceSessionFormationBehaviorsSignatureTrust(factory,user , base) ;
//    		
//    		
//    		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%  User Numéro   : "+i+"%%%%%%");
//    	 
//    		 }
//    	 }
//    	 System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%END%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//	

     }
}
