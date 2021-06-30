package com.traacAuthApprentissage.algoAuth;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.Obsel;
import com.ignition_factory.ktbs.bean.Trace;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

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
	public static  void createTraceRessourceActionSifnatureTrust (Base base, Trace primarytrace) throws Exception {
		
	          
		           // Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/user8540069828419911681/");

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
	            Trace primarytraceForumInteractionAction = ktbsService.createPrimaryTrace (base,"modelprimary","ForumInteractionAction",  primarytrace.get_origin());
	            
	            
	            
	            String filterparamQuiz = "bgp= {?obs m:component \"mod_quiz\"}UNION {?obs m:component \"mod_choice\"}";
	            String[] parametreQuiz = {filterparamQuiz};
	            ktbsService.createTransformedTrace(base, "QuizEvent","filter" , tracesource, parametreQuiz);
	            String[] tracesources5 = {"AllLogEvent/", "QuizEvent/"} ;
	            ktbsService.createTransformedTrace(base, "QUIZEventAndLog", "fusion", tracesources5, parametres4);     
	            Trace primarytraceQUIZInteractionAction = ktbsService.createPrimaryTrace (base,"modelprimary","QUIZInteractionAction",  primarytrace.get_origin());
	            
	            String filterparamText = "bgp={?obs m:component \"mod_page\"} UNION {?obs m:component \"mod_lesson\"}UNION {?obs m:component \"mod_book\"}UNION {?obs m:component \"mod_wiki\"}";
	            String[] parametreText = {filterparamText};
	            ktbsService.createTransformedTrace(base, "TextEvent","filter" , tracesource, parametreText);
	            String[] tracesources6 = {"AllLogEvent/", "TextEvent/"} ;
	            ktbsService.createTransformedTrace(base, "TextEventAndLog", "fusion", tracesources6, parametres4);     
	            Trace primarytraceTextInteractionAction = ktbsService.createPrimaryTrace (base,"modelprimary","TextInteractionAction",  primarytrace.get_origin());
	            
	            
	            /**CREATE TRACE RESSOURCE INTERACTION Profil and Signature **/
	            
		        String[] tracesources7 = {AllLogEvent.get_Name()+"/", primarytraceTextInteractionAction.get_Name()+"/", primarytraceQUIZInteractionAction.get_Name()+"/", primarytraceForumInteractionAction.get_Name()+"/"} ;
		        ktbsService.createTransformedTrace(base, "RessourceInteractionAction", "fusion", tracesources7, parametres4);     
		            
	            Trace RessourceInteractionActionSignature = ktbsService.createPrimaryTrace (base,"modelprimary","RessourceInteractionActionSignature",  primarytrace.get_origin());

	            /** FORUM INTERACTION **/
	           
	            
	            System.out.println ("##########################  FORUM INTERACTION #########################");
	            int compt =0 ;
	           Collection<Obsel> listObselForum = ktbsService.getListObsel(base,null, null,"ForumEventAndLog" );
	            int j =0 ; 
	            
	            while (j<listObselForum.size()) {
	            	System.out.println ("index  = " + j + " taille liste"+ listObselForum.size());
	            	//System.out.println (  ((Obsel) listObselForum.toArray()[j]).get_id() );
	            	if (((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("loggedin")) {
	            		ktbsService.createObsel(base.get_Name(),primarytraceForumInteractionAction.get_Name(),((Obsel) listObselForum.toArray()[j]).get_jsonObsel());
	            		j++;
	            	}else if (((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("loggedout")) {
	            		ktbsService.createObsel(base.get_Name(),primarytraceForumInteractionAction.get_Name(),((Obsel) listObselForum.toArray()[j]).get_jsonObsel());
	            		j++;
	            	}
	            	
	            	else	 {
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
		            		System.out.println ("j"+j);
		            	if (j<listObselForum.size()) {
		            	System.out.println	(((Obsel) listObselForum.toArray()[j]).get_attribute("contextid").getValue());
		            	System.out.println (((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId()) ;
		            	System.out.println(((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId()) ;
		            	System.out.println(((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId());
		            		
		            	
		            	
		            	while (j<listObselForum.size() && (((Obsel) listObselForum.toArray()[j]).get_attribute("contextid").getValue().equals(contextid))|| (! ((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("viewed")) && (!((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("loggedout")) &&(! ((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("loggedin"))) {
			            		
		            			 if  ((! ((Obsel) listObselForum.toArray()[j]).get_obsel_type().getTypeId().equals("viewed"))) {nbInteraction ++ ;};
		            			
		            			j++ ;
		            			System.out.println ("j while "+ j);
		            			if (j>=listObselForum.size()) {break;}
		            		}}
		            		Integer end = (Integer) ((Obsel) listObselForum.toArray()[j-1]).get_begin();
		            		ObselForumInteraction.put ("m:duration",end-begin);
		            		ObselForumInteraction.put ("end",((Obsel) listObselForum.toArray()[j-1]).get_begin());
		            		ObselForumInteraction.put ("m:nbInteraction",nbInteraction);
		            		ktbsService.createObsel(base.get_Name(),primarytraceForumInteractionAction.get_Name(),ObselForumInteraction);
		            		compt ++ ;
		    	           // Collection<Obsel> listObsel = ktbsService.getListObsel(base,null, null,primarytraceForumInteractionAction.get_Name() );
		            		System.out.println ("compt  ==================================== " + compt);
		            		if (compt==2) {
		            			System.out.println (" =============create signature forum======================================= " );
                            	Trace SignForum = createSignatureForumInteractionSignature(base);
                	            Collection<Obsel> listObselForumSign = ktbsService.getListObsel(base,null, null,SignForum.get_Name() );
                	            ktbsService.createObsel (base.get_Name(),RessourceInteractionActionSignature.get_Name(), ((Obsel) listObselForumSign.toArray()[0]).get_jsonObsel());
                            }
		            		else {
		            			// TODO CALCUL TRUST
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
	            	System.out.println ("index  = " + k + " ID " + ((Obsel) listObselQUIZ.toArray()[k]).get_id());
	            	
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
			            		ObselTextInteraction.put ("end",end);}
		            		
		            		ktbsService.createObsel(base.get_Name(),primarytraceQUIZInteractionAction.get_Name(),ObselTextInteraction);
		            		compt ++;
		            		 Collection<Obsel> listObsel = ktbsService.getListObsel(base,null, null,primarytraceQUIZInteractionAction.get_Name() );
	                            if (compt==2) {
	                            	Trace SignQuiz = createSignatureExerciceInteractionSignature(base);
	                	            Collection<Obsel> listObselForumSign = ktbsService.getListObsel(base,null, null,SignQuiz.get_Name() );
	                	            //RessourceInteractionActionSignature.
	                	            ktbsService.createObsel (base.get_Name(),RessourceInteractionActionSignature.get_Name(), ((Obsel) listObselForumSign.toArray()[0]).get_jsonObsel());
	                            }else if (listObsel.size()>2) {
	                            	
	                            	//calculer trust TODO
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
	            	System.out.println ("index  = " + k);
	            	System.out.println (  ((Obsel)  listObselText.toArray()[k]).get_id() );
	            	
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
		            		while (k<listObselText.size() && (((Obsel) listObselText.toArray()[k]).get_attribute("contextid").getValue().equals(contextid))|| (! ((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("viewed")) && (!((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("loggedout")) &&(! ((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("loggedin"))) {
		            			if  ((! ((Obsel) listObselText.toArray()[k]).get_obsel_type().getTypeId().equals("viewed"))) {nbInteraction ++ ;};
		            			
		            			k++ ;
		            			if (k>=listObselText.size()) {break;}
		            			
		            		}
		            		Integer end = (Integer) ((Obsel) listObselText.toArray()[k-1]).get_begin();
		            		ObselTextInteraction.put ("m:duration",end-begin);
		            		ObselTextInteraction.put ("end",((Obsel) listObselText.toArray()[k-1]).get_begin());
		            		
		            		
		            		
		            		ObselTextInteraction.put ("m:nbInteraction",nbInteraction);
		            		ktbsService.createObsel(base.get_Name(),primarytraceTextInteractionAction.get_Name(),ObselTextInteraction);
		            		
		            		Collection<Obsel> listObsel = ktbsService.getListObsel(base,null, null,primarytraceTextInteractionAction.get_Name() );
                            compt++ ;
		            		if (compt==2) {
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
	
	public static void createTraceSessionFormationBehaviorsSignatureTrust(Base base) throws Exception {
		
		 /** CREATE TRACE SESSION BEHAVIOR**/
        
        Trace FormationSessionInteractionBehaviors = ktbsService.createPrimaryTrace (base,"modelprimary","FormationSessionInteractionBehaviors",null);
        Trace FormationSessionInteractionBehaviorsSignature = ktbsService.createPrimaryTrace (base,"modelprimary","FormationSessionInteractionBehaviorsSignature",null);

        Collection<Obsel> listObselRessourceInterraction = ktbsService.getListObsel(base,null, null,"RessourceInteractionAction" );
        
        int m = 0 ;
         int SessionIndex = 1 ;
        while (m<listObselRessourceInterraction.size()) {
        	 //System.out.println (((Obsel) listObselRessourceInterraction.toArray()[m]).get_obsel_type().getTypeId());
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
        	//	System.out.println("index = "+m +"id =" + ((Obsel) listObselRessourceInterraction.toArray()[m]).get_id());
        		
        	//Attributes.put ("@id","FormationSessionIneraction"+((Obsel) listObselRessourceInterraction.toArray()[m]).get_id());
        		Attributes.put ("@id" , "Session"+SessionIndex );	
        		
        		Integer begin = (Integer) ((Obsel) listObselRessourceInterraction.toArray()[m]).get_begin();
        		Attributes.put ("begin",((Obsel) listObselRessourceInterraction.toArray()[m]).get_begin());
        		
        		Attributes.put ("@type","m:FormationSessionIneraction");
        		JSONObject SessionTrustJson = new JSONObject();
        		//Attributes.put ("m:sessionID",openCours.get_attribute("sessionID").getValue());
        		m++;
        		float trustAction =0;
        		 JSONArray ActionArray = new JSONArray() ;
        		
        		 while (m<listObselRessourceInterraction .size()  && (!((Obsel) listObselRessourceInterraction .toArray()[m]).get_obsel_type().getTypeId().equals("loggedout")) &&(! ((Obsel) listObselRessourceInterraction.toArray()[m]).get_obsel_type().getTypeId().equals("loggedin"))) {
        			
            	//	System.out.println("index while 2 = "+m +"id =" + ((Obsel) listObselRessourceInterraction.toArray()[m]).get_id());

        			Obsel action = (Obsel) listObselRessourceInterraction .toArray()[m];
        			JSONObject actionTrustJson = new JSONObject();
        			if ((action.get_obsel_type().getTypeId()).equals("ExerciceIneraction")) {
        				
        				dureInExercice += Double.valueOf(action.get_attribute("duration").getValue());
        				DurationTotalAllRessource+=Double.valueOf(action.get_attribute("duration").getValue());
        				
        				nbConsultationAllRessources++ ;
        				nbConsultationExercice++ ;
        				
        				if ((action.get_attribute("reussit").getValue()).equals("true")) {nbExerciceReussit++;}
        				if ((action.get_attribute("reussit").getValue()).equals ("false")) {nbExerciceFailed ++;}
        				if ((action.get_attribute("break").getValue()).equals ("true")) {nbExerciceAbandant ++;}
        				
        				actionTrustJson = saveTrustAction (base , action , trustAction);
        				
        				
        				
        			}
        			
        			if ((action.get_obsel_type().getTypeId()).equals("TextIneraction")) {
        				dureInText += Double.valueOf(action.get_attribute("duration").getValue());
        				DurationTotalAllRessource+=Double.valueOf(action.get_attribute("duration").getValue());
        				
        				nbConsultationAllRessources++ ;
        				nbConsultationText++;
        				nbAInteractionTextTotal+= Double.valueOf(action.get_attribute("nbInteraction").getValue());
        				actionTrustJson = saveTrustAction (base , action , trustAction);
        			}
        			
        			if ((action.get_obsel_type().getTypeId()).equals("ForumIneraction")) {
        				dureForum += Double.valueOf(action.get_attribute("duration").getValue());
        				DurationTotalAllRessource+=Double.valueOf(action.get_attribute("duration").getValue());
        				
        				nbConsultationAllRessources++ ;
        				nbConsultationForum++;
        				nbInteractionForum = nbInteractionForum + Double.valueOf(action.get_attribute("nbInteraction").getValue()) ;
        				actionTrustJson = saveTrustAction (base , action , trustAction);
        			}
        			ActionArray.put(actionTrustJson);
        			m++ ;
        		}
        		if ( m<listObselRessourceInterraction .size()  && ((Obsel) listObselRessourceInterraction.toArray()[m]).get_obsel_type().getTypeId().equals("loggedout")) {
        			Attributes.put ("end",((Obsel) listObselRessourceInterraction.toArray()[m]).get_begin());
        			Integer end = (Integer) ((Obsel) listObselRessourceInterraction.toArray()[m]).get_begin();
        			Attributes.put("m:duration", end - begin);
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
        		
        		System.out.println ("Attributes    :   "+ Attributes);
        		Obsel lastSession = new Obsel (FormationSessionInteractionBehaviors.get_uri(),FormationSessionInteractionBehaviors.get_model().get_uri(),Attributes);
        		Collection<Obsel> obselSignature = ktbsService.getListObsel(base,null, null,FormationSessionInteractionBehaviorsSignature.get_Name() );
        		
        		   
        		   // traitement pour First session 
        		//   SessionTrustJson.put("sessionID","FormationSessionIneraction"+((Obsel) listObselRessourceInterraction.toArray()[m]).get_id());
        		SessionTrustJson.put("sessionID","Session"+SessionIndex);
        		
        		SessionTrustJson.put("listTrustActions",ActionArray);
        		  
        		   
        		   if (obselSignature.isEmpty()) {
        			   
        			JSONObject FirstclusterBArycentre = calculbarycentre (null, lastSession,(int)obselSignature.size()+1,(double)0);
        			ktbsService.createObsel(base.get_Name() , FormationSessionInteractionBehaviorsSignature.get_Name(),FirstclusterBArycentre);
        			 
        			SessionTrustJson.put("cluster", "Premier cluster ");		   
        			SessionTrustJson.put("clusterName", FirstclusterBArycentre.get("m:name"));
                   
       	           
                   }// Autre session 
        		   else  {
                   	
        			    double [] distance = new double [100] ;
        				double [] Poid = new double [100] ;
        				Double score =0.0;
        				Double[] weigth = {0.3,0.2,0.2,0.1,0.1,0.1} ;
        				Double  k = 0.5 ;
        				  float trust =1;
        				  //def nouveauTrust
        				JSONArray detailsDistances = new JSONArray ();
        				/** Calculer distance par rapport tous les clusters dans trace Signature **/
        				for (int j=0; j< obselSignature.size(); j++) {
        					
        					double Simularite = calculSimularitySession (lastSession.get_jsonObsel(),((Obsel) obselSignature.toArray()[j]).get_jsonObsel(),weigth);
        					//System.out.println ("distance entre " + C.get("Name") + " et " + sessionActuel.get("Name") + " est = "+ dist ) ;
        					distance [j] = Simularite ;
        					Poid [j] = Double.parseDouble ((String) ((Obsel) obselSignature.toArray()[j]).get_attribute("poid").getValue()) ;
        					/*/** calculer score  for trust **/
        					score = score + Poid [j]*Simularite;
        					// Objet distance details
        					System.out.println ("comparaison cluster " + ((Obsel) obselSignature.toArray()[j]).get_id() +"le poid du cluster est "+ Poid [j]+ " distance est : " + Simularite);
        					JSONObject ObjdetailsDistances = new JSONObject();
        					ObjdetailsDistances.put("clusterID ", ((Obsel) obselSignature.toArray()[j]).get_id());
        					
        					ObjdetailsDistances.put("Simularité", Simularite);
        					
        					detailsDistances.put(ObjdetailsDistances);
        				}
        				System.out.println ("##################### Détailles Distance ############################");
        				System.out.println (detailsDistances.toString());
        				
        				Double SimMax = MaxTableau (distance);
        				int indexMax =  MaxIndex (distance) ;
        				System.out.println("SimMax =" + SimMax) ;
        				System.out.println("indexMax =" + indexMax) ;
        				System.out.println("score =" +  score) ;
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
        						SessionTrustJson.put("trust", trust);
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
                   }
        				SessionIndex ++;
        		   }
        		   System.out.println ("----------session trust json--------------------------------------------------");           		   
           		   System.out.println (SessionTrustJson.toString());
     				// TODO TODO Save SessiontrustJson in file 
           		try {
           	       FileOutputStream fileOut = new FileOutputStream("test");
           	       ObjectOutputStream out = new ObjectOutputStream(fileOut);
           	       out.writeObject(SessionTrustJson);
           	       out.close();
           	       fileOut.close();
           	       System.out.println("\nSerialisation terminée avec succès...\n");
           	 
           	     } catch (FileNotFoundException e) {
           	       e.printStackTrace();
           	     } catch (IOException e) {
           	       e.printStackTrace();
           	     }
        	}
        		  

        	
        	}}
			}
	

		
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
	    
	
	public static String ExerciceBehavior(int nbConsultationExercice , int nbExerciceReussit,int nbExerciceFailed,int nbExerciceAbandant) {
		String behavior = ""; 
		
		if (nbConsultationExercice==0) behavior ="Absent";
		else if (nbExerciceReussit>nbExerciceFailed && nbExerciceAbandant < nbExerciceReussit+nbExerciceFailed ) behavior ="Reussit-Completing";
		else if (nbExerciceReussit<nbExerciceFailed && nbExerciceAbandant < nbExerciceReussit+nbExerciceFailed)		behavior ="Failed-Completing";
		else if (nbExerciceAbandant > nbExerciceReussit+nbExerciceFailed)				behavior ="Incompleting" ;
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
	
     public static JSONObject calculbarycentre (Obsel AncientBarycentre, Obsel NouveauSession,int index, Double similarité) throws JSONException {
		
		JSONObject barycenter = new JSONObject();
		
		barycenter.put ("@id","Cluster"+index);
		barycenter.put ("begin",NouveauSession.get_begin());
		barycenter.put ("end",NouveauSession.get_begin());
		barycenter.put ("@type","m:cluster");
		barycenter.put ("m:name","cluster"+index);
		//barycenter.put ("m:poid","1")
		
		if (AncientBarycentre != null) { 
			Double NouveauPoid =  Double.parseDouble (AncientBarycentre.get_attribute("poid").getValue()) + similarité ;
			barycenter.put ("m:poid",NouveauPoid);
			}
		else {barycenter.put ("m:poid","1");}
		
		//List<String> listSession = new ArrayList<String>();
		JSONArray listSession =new JSONArray () ;
		listSession.put("");
		//barycenter.put("m:listSession", listSession) ;
		if (AncientBarycentre != null) {listSession=new JSONArray (AncientBarycentre.get_attribute("listSession").getValue());}
		listSession.put(NouveauSession.get_uri());
		//println (listSession) ;
		barycenter.put("m:listSession", listSession) ;
		
		JSONArray listExerciceBehavior =new JSONArray ();
		
		listExerciceBehavior.put("");
		if (AncientBarycentre != null) {listExerciceBehavior = new JSONArray (AncientBarycentre.get_attribute("ExerciceBehavior").getValue());}
		if (!listExerciceBehavior.toString().contains((String) NouveauSession.get_attribute("ExerciceBehavior").getValue())) {listExerciceBehavior.put ((String) NouveauSession.get_attribute("ExerciceBehavior").getValue());}
		barycenter.put("m:listExerciceBehavior", listExerciceBehavior) ;
		
		JSONArray listTextBehavior =new JSONArray ();
		listTextBehavior.put("");
		if (AncientBarycentre != null) { listTextBehavior = new JSONArray (AncientBarycentre.get_attribute("TextBehavior").getValue());}
		if (!listTextBehavior.toString().contains((String) NouveauSession.get_attribute("TextBehavior").getValue())) {listTextBehavior.put ((String) NouveauSession.get_attribute("TextBehavior").getValue()); }
		
	    barycenter.put("m:listTextBehavior", listTextBehavior) ;
	  
		
	    JSONArray listForumBehavior =new JSONArray ();
	    listForumBehavior.put("");
		if (AncientBarycentre != null) { listForumBehavior= new JSONArray (AncientBarycentre.get_attribute("ForumBehavior").getValue());}
		if (!listForumBehavior.toString().contains((String) NouveauSession.get_attribute("ForumBehavior").getValue())) { listForumBehavior.put ((String) NouveauSession.get_attribute("ForumBehavior").getValue());}
		barycenter.put("m:listForumBehavior", listForumBehavior) ;
		
		if (AncientBarycentre != null) {
		double nbConsultationAllRessources  = moyenne ( Double.parseDouble ((String) AncientBarycentre.get_attribute("nbConsultationAllRessources").getValue()),Double.parseDouble ((String) NouveauSession.get_attribute("nbConsultationAllRessources").getValue()));
		barycenter.put("m:AveragenbConsultationAllRessources", Double.toString (nbConsultationAllRessources)) ;
		} else barycenter.put("m:AveragenbConsultationAllRessources", NouveauSession.get_attribute("nbConsultationAllRessources").getValue()) ;
		
		if (AncientBarycentre != null) {
		double duration  = moyenneGeometric (Double.parseDouble ((String) AncientBarycentre.get_attribute("duration").getValue()),Double.parseDouble ((String) NouveauSession.get_attribute("duration").getValue()));
		barycenter.put("m:duration", Double.toString (duration)) ;}
		else barycenter.put("m:Averageduration", NouveauSession.get_attribute("duration").getValue()) ;
		
		if (AncientBarycentre != null) {
			double DurationTotalAllRessource  = moyenneGeometric (Double.parseDouble ((String) AncientBarycentre.get_attribute("DurationTotalAllRessource").getValue()),Double.parseDouble ((String) NouveauSession.get_attribute("DurationTotalAllRessource").getValue()));
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

		  double d = min (D1,D2) /  (double) (max(D1,D2)) ;
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
 						if (deviationnbInteraction==0 || deviationDuration ==0) {distance =0;}
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
 						
 						if (deviationnbInteraction==0 || deviationDuration ==0) {distance =0;}
 						else {distance = (Math.abs ((Duration - meanDuration)/deviationDuration )+ Math.abs ((nbInteraction - meannbInteraction)/deviationnbInteraction ))/2;
 						}
 						}
 		}
 		return distance ;
     }
     
     public static float  calculateCoefficient(float distance, Obsel obs, float previousCalculatedTrust, String urlBaseSignature) {
			float trust = previousCalculatedTrust;
			
			float Dthreshold =(float) 1.5 ;
			float Dnoise = 17 ;
			if (distance <Dnoise){
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
        	if(distance >= 0){
        		trustAction = calculateCoefficient(distance, action, trustAction, base.get_uri()+"RessourceInteractionActionSignature/");
						
						Obj.put("id_action", action.get_id());
						Obj.put("type_action", action.get_obsel_type().getTypeId());
						Obj.put("begin", action.get_begin());
						Obj.put("distance", distance);
						Obj.put ("end",action.get_end());
						Obj.put("trust", trustAction);
						//items.put(Obj);
        		
        	}
        	System.out.println (" save trust Action Obj "+ Obj.toString());
        	return Obj;
     }
     
     
     public static void main(String[] args) throws Exception {
		
		String jsonFilePath = "/Users/derbelfatma/eclipse-workspace/AuthComportementApprentissage/src/DataMoodleJSON/mdl_user.json";
		// try(JsonReader jsonReader = new JsonReader(
	    //        new InputStreamReader(
	    //        new FileInputStream(jsonFilePath), StandardCharsets.UTF_8))) {
         //      Gson gson = new GsonBuilder().create();

	     //       jsonReader.beginArray(); //start of json array
	    //        while (jsonReader.hasNext()){ //next json array element
	            	
	      //      logstore logstoreI = gson.fromJson(jsonReader, logstore.class);
	     //       Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/" + logstoreI.username +"/");
	      //      Trace primarytrace = new Trace ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/" + logstoreI.username +"/"+"PrimarytraceLog/") ;

		  Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle"+ "/user7067006821688410113/");
		 //user8540069828419911681
		  
		  //user135857572283416577
		  Trace  primarytrace = new Trace ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/user7067006821688410113/PrimarytraceLog/") ;

	        //  createTraceRessourceActionSifnatureTrust (base,primarytrace);
	            createTraceSessionFormationBehaviorsSignatureTrust(base) ;
		 
	          
	       //     jsonReader.endArray();
	            
	      //  }
	 //}
	     //   catch (UnsupportedEncodingException e) {
	     //       e.printStackTrace();
	     //   } catch (FileNotFoundException e) {
	     //       e.printStackTrace();
	     //   } catch (IOException e) {
	     //       e.printStackTrace();
	     //   }  
	            
		
	}
	
	
}
