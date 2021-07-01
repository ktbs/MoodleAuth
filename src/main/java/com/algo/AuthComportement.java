package com.algo;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.Obsel;
import com.ignition_factory.ktbs.bean.Trace;


public class AuthComportement {

	
	public static void createTraceFormationInteractionBehaviors() throws Exception {
		  
		Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/jamila2jamila2cff4529c-3654-473c-905e-03d025a9a719/");
		Trace trace = ktbsService.createPrimaryTrace(base, "modelprimary", "Formations-Sessions-Interaction-Behaviors", null) ;
		Collection<Obsel> listObsel = ktbsService.getListObsel(base,null, null,"Ressource-Interaction-Behaviors" );
		
		Obsel openCours = ktbsService.getObselWithType ("OpenCours",null,listObsel);
		Obsel closeCours = ktbsService.getObselWithType ("CloseCours",null,listObsel);
			
		Collection<Obsel> listObselSession = ktbsService.getListObsel(base,openCours.get_begin(),null,"Ressource-Interaction-Behaviors" );
		JSONObject Attributes = new JSONObject();
		Attributes.put("m:duration", String.valueOf( Double.valueOf(String.valueOf (closeCours.get_begin()))-Double.valueOf(String.valueOf( openCours.get_begin()))));
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
		int nbActionclickTotal = 0 ;
		
		double dureInVideo = 0 ;
		int nbConsultationVideo = 0 ;
		int nbCompletedVideo = 0 ;
		int nbVideoAbandant = 0 ;
		int nbActionTotal = 0 ;
		
		double dureForum = 0 ;
		int nbConsultationForum = 0 ;
		double nbInteractionForum = 0 ;
		
		for(Obsel action : listObselSession)  {
			
			if ((action.get_obsel_type().getTypeId()).equals("ExerciceInteraction")) {
				
				dureInExercice += Double.valueOf(action.get_attribute("duration").getValue());
				DurationTotalAllRessource+=Double.valueOf(action.get_attribute("duration").getValue());
				
				nbConsultationAllRessources++ ;
				nbConsultationExercice++ ;
				
				if ((action.get_attribute("reussit").getValue()).equals("true")) {nbExerciceReussit++;}
				if ((action.get_attribute("reussit").getValue()).equals ("false")) {nbExerciceFailed ++;}
				if ((action.get_attribute("break").getValue()).equals ("true")) {nbExerciceAbandant ++;}
				Sommescore += Double.valueOf(action.get_attribute("score").getValue());
				
			}
			
			if ((action.get_obsel_type().getTypeId()).equals("TextInteraction")) {
				dureInText += Double.valueOf(action.get_attribute("duration").getValue());
				DurationTotalAllRessource+=Double.valueOf(action.get_attribute("duration").getValue());
				
				nbConsultationAllRessources++ ;
				nbConsultationText++;
				nbActionclickTotal+= Double.valueOf(action.get_attribute("nbClick").getValue());
			}
			
			if ((action.get_obsel_type().getTypeId()).equals("VideoInteraction")) {
				dureInVideo += Double.valueOf(action.get_attribute("duration").getValue());
				DurationTotalAllRessource+=Double.valueOf(action.get_attribute("duration").getValue());
				
				nbConsultationAllRessources++ ;
				nbConsultationVideo ++;
				
				if ((action.get_attribute("break").getValue()).equals("false")) {nbCompletedVideo ++;}
				else {nbVideoAbandant ++;}
				//if ((action.get_attribute("completition").getValue()).equals("true")) {nbVideoAbandant ++;}
				nbActionTotal+= Double.valueOf(action.get_attribute("nbpause").getValue()) + Double.valueOf(action.get_attribute("nbjup").getValue());
			}
			
			if ((action.get_obsel_type().getTypeId()).equals("ForumInteraction")) {
				dureForum += Double.valueOf(action.get_attribute("duration").getValue());
				DurationTotalAllRessource+=Double.valueOf(action.get_attribute("duration").getValue());
				
				nbConsultationAllRessources++ ;
				nbConsultationForum++;
				System.out.println (action.get_attribute("nbpbsubmit").getValue());
				nbInteractionForum = nbInteractionForum + Double.valueOf(action.get_attribute("nbpbsubmit").getValue()) + Double.valueOf(action.get_attribute("nbreponsesubmit").getValue());
			}
			
			
			
			
		}
		Attributes.put ("@id","FormationSessionIneraction"+openCours.get_attribute("sessionID").getValue());
		Attributes.put ("begin",openCours.get_begin());
		Attributes.put ("end",closeCours.get_begin());
		Attributes.put ("@type","m:FormationSessionIneraction");
		Attributes.put ("m:sessionID",openCours.get_attribute("sessionID").getValue());
		
		
		
		Attributes.put("m:DurationTotalAllRessource", String.valueOf(DurationTotalAllRessource));
		Attributes.put("m:nbConsultationAllRessources", String.valueOf(nbConsultationAllRessources));
		
		Attributes.put("m:dureInExercice", dureInExercice);
		Attributes.put("m:nbConsultationExercice", nbConsultationExercice);
		Attributes.put("m:nbExerciceReussit", nbExerciceReussit);
		Attributes.put("m:nbExerciceFailed", nbExerciceFailed);
		Attributes.put("m:nbExerciceAbandant", nbExerciceAbandant);
		Attributes.put("m:scoreMean", Sommescore/nbConsultationExercice);
		Attributes.put("m:ExerciceBehavior", ExerciceBehavior(nbConsultationExercice,nbExerciceReussit,nbExerciceFailed,nbExerciceAbandant));
		
		Attributes.put("m:dureInText", dureInText);
		Attributes.put("m:nbConsultationText", nbConsultationText);
		Attributes.put("m:nbActionclickTotalText", nbActionclickTotal);
		Attributes.put("m:TextBehavior", TextBehavior(nbConsultationText,nbActionclickTotal));
		
		
		
		Attributes.put("m:dureInVideo", dureInVideo);
		Attributes.put("m:nbConsultationVideo", nbConsultationVideo);
		Attributes.put("m:nbCompletedVideo", nbCompletedVideo);
		Attributes.put("m:nbVideoAbandant", nbVideoAbandant);
		Attributes.put("m:nbActionTotalVideo", nbActionTotal);
		Attributes.put("m:VideoBehavior", VideoBehavior(nbConsultationVideo,nbCompletedVideo,nbVideoAbandant,nbActionTotal));
		
		Attributes.put("m:dureForum", dureForum);
		Attributes.put("m:nbConsultationForum", nbConsultationForum);
		Attributes.put("m:nbInteractionForum", nbInteractionForum);
		Attributes.put("m:ForumBehavior", ForumBehavior(nbConsultationForum,nbInteractionForum));
		
		
		ktbsService.createObsel(base.get_Name(),trace.get_Name(),Attributes);
	}
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
	public static String VideoBehavior(int nbConsultationVideo,int nbCompletedVideo,int nbVideoAbandant,int nbActionTotal) {
		String behavior = ""; 
		if (nbConsultationVideo ==0)behavior = "Absent" ;
		else if (nbCompletedVideo>nbVideoAbandant && nbActionTotal>2)behavior = "Active-Completing" ;
		else if (nbCompletedVideo>nbVideoAbandant && nbActionTotal<2)behavior = "Inactive-Completing" ;
		else if (nbCompletedVideo<nbVideoAbandant&& nbActionTotal>2)behavior = "Active-Incompleting" ;
		else if (nbCompletedVideo<nbVideoAbandant && nbActionTotal<2)behavior = "Inactive-Incompleting" ;
		
		return behavior ;
	}
	public static String ForumBehavior(int nbConsultationForum,double nbInteractionForum) {
		String behavior = ""; 
		if (nbConsultationForum ==0 ) behavior = "Absent";
		else if (nbInteractionForum ==0)behavior = "Lecteur";
		else behavior = "Acteur";
		return behavior ;
		
	}
	
	public static void createSignatureTraceRessourceInteraction (Base base) throws Exception {
		
		// Exercice-Interaction-Signature
		
		Trace traceRessourceInteraction = new Trace ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/jamila2jamila2cff4529c-3654-473c-905e-03d025a9a719/Ressource-Interaction-Behaviors");

		String modeUrl = ktbsService.getUrlModel(base,traceRessourceInteraction) ;
		String [] tracesourceExercice = {traceRessourceInteraction.get_Name()+"/"};
		String sparlExercice = "sparql=    prefix : <http://liris.cnrs.fr/silex/2009/ktbs#>\nprefix model: <"+modeUrl+"> \n\nCONSTRUCT {\n  [ a ?ctyp ;\n    model:obstyp ?typ ;\n    model:direction ?direction;\n    model:meanTime ?meanTime;\n    model:deviationTime ?deviationTime;\n    model:nbOccurence ?count;\n    \n    model:meanSpeed ?meanSpeed;\n    model:deviationSpeed ?deviationSpeed; \n\n    model:meantravelledDistance ?meantravelledDistance;\n    model:deviationtravelledDistance ?deviationtravelledDistance; \n\n    model:meancurveLength ?meancurveLength;\n    model:deviationcurveLength ?deviationcurveLength; \n\n    model:meanacceleration ?meanacceleration;\n    model:deviationacceleration ?deviationacceleration; \n\n    model:meancurveAcceleration ?meancurveAcceleration;\n    model:deviationcurveAcceleration ?deviationcurveAcceleration; \n\n    \n    :hasTrace <%(__destination__)s> ;\n    :hasBegin ?begin ;\n    :hasEnd ?end ;\n  ]\n}\nwhere {\n\n    SELECT ?direction ?typ\n           (SAMPLE(?ctyp2) as ?ctyp)\n           (SAMPLE(?count1) as ?count)\n           (SAMPLE(?meanTime1) as ?meanTime)\n           (SAMPLE(?meanSpeed1) as ?meanSpeed)\n           (SAMPLE(?meantravelledDistance1) as ?meantravelledDistance)\n           (SAMPLE(?meancurveLength1) as ?meancurveLength)\n            \n           (SAMPLE(?meanacceleration1) as ?meanacceleration)\n           (SAMPLE(?meancurveAcceleration1) as ?meancurveAcceleration)\n           \n           (SUM(ABS(?time2 - ?meanTime1)/(?count1 - 1)) as ?deviationTime)\n           (SUM(ABS(?Speed2 - ?meanSpeed1)/(?count1 - 1)) as ?deviationSpeed)\n\n           (SUM(ABS(?travelledDistance2 - ?meantravelledDistance1)/(?count1 - 1)) as ?deviationtravelledDistance)\n           (SUM(ABS(?curveLength2 - ?meancurveLength1)/(?count1 - 1)) as ?deviationcurveLength)\n           \n           (SUM(ABS(?acceleration2 - ?meanacceleration1)/(?count1 - 1)) as ?deviationacceleration)\n           (SUM(ABS(?curveAcceleration2 - ?meancurveAcceleration1)/(?count1 - 1)) as ?deviationcurveAcceleration)\n           \n           (MIN(?begin2) as ?begin)\n           (MAX(?end2) as ?end)\n    {\n\n        # first compute ?mean and ?count for each n-uple (?key1 ?key2 ?typ)\n        {\n            SELECT ?direction ?typ\n                   (SAMPLE(?ctyp1) as ?ctyp2)\n                   (COUNT(?o1) as ?count1)\n                   (AVG(?time1) as ?meanTime1)\n                   (AVG(?Speed1) as ?meanSpeed1)\n                   (AVG(?travelledDistance1) as ?meantravelledDistance1)\n                   (AVG(?curveLength1) as ?meancurveLength1)\n                   (AVG(?acceleration1) as ?meanacceleration1)\n                   (AVG(?curveAcceleration1) as ?meancurveAcceleration1)\n            {\n                ?o1 a ?typ ;\n                    model:direction ?direction ;\n                    model:actualSpeed ?Speed1 ;\n                    model:travelledDistance ?travelledDistance1 ;\n                    model:curveLength ?curveLength1 ;\n                    model:acceleration ?acceleration1 ;\n                    model:curveAcceleration ?curveAcceleration1 ;\n                    model:timeElapsed ?time1 .\n\n                VALUES (?typ ?ctyp1) {\n                    (model:M_MS model:M_MMS)\n                }\n            }\n            GROUP BY ?direction ?typ\n            HAVING (COUNT(?o1) >= 5)\n        }\n\n        # then aggregate obsels again, in order to compute deviation (see outer select).\n\n        ?o2 a ?typ ;\n            model:direction ?direction;\n            model:timeElapsed ?time2 ;\n            model:actualSpeed ?Speed2 ;\n            model:travelledDistance ?travelledDistance2 ;\n            model:curveLength ?curveLength2 ;\n            model:acceleration ?acceleration2 ;\n            model:curveAcceleration ?curveAcceleration2 ;\n            :hasBegin ?begin2 ;\n            :hasEnd ?end2 .\n\n    } GROUP BY ?direction ?typ\n\n}\n\n";
		String[] parametreExercice = {sparlExercice};
	    Trace TraceExerciceInteractionSignature = ktbsService.createTransformedTrace(base, "Trace-Exercice-Interaction-Signature","sparql" , tracesourceExercice, parametreExercice) ;		   
	
	    // Text-Interaction-Signature
	    
	  
		String [] tracesourcesText = {traceRessourceInteraction.get_Name()+"/"};
		String sparlText = "sparql=    prefix : <http://liris.cnrs.fr/silex/2009/ktbs#>\nprefix model: <"+modeUrl+"> \n\nCONSTRUCT {\n  [ a ?ctyp ;\n    model:obstyp ?typ ;\n    model:direction ?direction;\n    model:meanTime ?meanTime;\n    model:deviationTime ?deviationTime;\n    model:nbOccurence ?count;\n    \n    model:meanSpeed ?meanSpeed;\n    model:deviationSpeed ?deviationSpeed; \n\n    model:meantravelledDistance ?meantravelledDistance;\n    model:deviationtravelledDistance ?deviationtravelledDistance; \n\n    model:meancurveLength ?meancurveLength;\n    model:deviationcurveLength ?deviationcurveLength; \n\n    model:meanacceleration ?meanacceleration;\n    model:deviationacceleration ?deviationacceleration; \n\n    model:meancurveAcceleration ?meancurveAcceleration;\n    model:deviationcurveAcceleration ?deviationcurveAcceleration; \n\n    \n    :hasTrace <%(__destination__)s> ;\n    :hasBegin ?begin ;\n    :hasEnd ?end ;\n  ]\n}\nwhere {\n\n    SELECT ?direction ?typ\n           (SAMPLE(?ctyp2) as ?ctyp)\n           (SAMPLE(?count1) as ?count)\n           (SAMPLE(?meanTime1) as ?meanTime)\n           (SAMPLE(?meanSpeed1) as ?meanSpeed)\n           (SAMPLE(?meantravelledDistance1) as ?meantravelledDistance)\n           (SAMPLE(?meancurveLength1) as ?meancurveLength)\n            \n           (SAMPLE(?meanacceleration1) as ?meanacceleration)\n           (SAMPLE(?meancurveAcceleration1) as ?meancurveAcceleration)\n           \n           (SUM(ABS(?time2 - ?meanTime1)/(?count1 - 1)) as ?deviationTime)\n           (SUM(ABS(?Speed2 - ?meanSpeed1)/(?count1 - 1)) as ?deviationSpeed)\n\n           (SUM(ABS(?travelledDistance2 - ?meantravelledDistance1)/(?count1 - 1)) as ?deviationtravelledDistance)\n           (SUM(ABS(?curveLength2 - ?meancurveLength1)/(?count1 - 1)) as ?deviationcurveLength)\n           \n           (SUM(ABS(?acceleration2 - ?meanacceleration1)/(?count1 - 1)) as ?deviationacceleration)\n           (SUM(ABS(?curveAcceleration2 - ?meancurveAcceleration1)/(?count1 - 1)) as ?deviationcurveAcceleration)\n           \n           (MIN(?begin2) as ?begin)\n           (MAX(?end2) as ?end)\n    {\n\n        # first compute ?mean and ?count for each n-uple (?key1 ?key2 ?typ)\n        {\n            SELECT ?direction ?typ\n                   (SAMPLE(?ctyp1) as ?ctyp2)\n                   (COUNT(?o1) as ?count1)\n                   (AVG(?time1) as ?meanTime1)\n                   (AVG(?Speed1) as ?meanSpeed1)\n                   (AVG(?travelledDistance1) as ?meantravelledDistance1)\n                   (AVG(?curveLength1) as ?meancurveLength1)\n                   (AVG(?acceleration1) as ?meanacceleration1)\n                   (AVG(?curveAcceleration1) as ?meancurveAcceleration1)\n            {\n                ?o1 a ?typ ;\n                    model:direction ?direction ;\n                    model:actualSpeed ?Speed1 ;\n                    model:travelledDistance ?travelledDistance1 ;\n                    model:curveLength ?curveLength1 ;\n                    model:acceleration ?acceleration1 ;\n                    model:curveAcceleration ?curveAcceleration1 ;\n                    model:timeElapsed ?time1 .\n\n                VALUES (?typ ?ctyp1) {\n                    (model:M_MS model:M_MMS)\n                }\n            }\n            GROUP BY ?direction ?typ\n            HAVING (COUNT(?o1) >= 5)\n        }\n\n        # then aggregate obsels again, in order to compute deviation (see outer select).\n\n        ?o2 a ?typ ;\n            model:direction ?direction;\n            model:timeElapsed ?time2 ;\n            model:actualSpeed ?Speed2 ;\n            model:travelledDistance ?travelledDistance2 ;\n            model:curveLength ?curveLength2 ;\n            model:acceleration ?acceleration2 ;\n            model:curveAcceleration ?curveAcceleration2 ;\n            :hasBegin ?begin2 ;\n            :hasEnd ?end2 .\n\n    } GROUP BY ?direction ?typ\n\n}\n\n";
		String[] parametreText = {sparlText};
	    Trace TraceTextInteractionSignature = ktbsService.createTransformedTrace(base, "Trace-Text-Interaction-Signature","sparql" , tracesourcesText, parametreText) ;		   
	    
	    // Forum-Interaction-Signature
	    
	    String [] tracesourcesForum = {traceRessourceInteraction.get_Name()+"/"};
		String sparlForum = "sparql=    prefix : <http://liris.cnrs.fr/silex/2009/ktbs#>\nprefix model: <"+modeUrl+"> \n\nCONSTRUCT {\n  [ a ?ctyp ;\n    model:obstyp ?typ ;\n    model:direction ?direction;\n    model:meanTime ?meanTime;\n    model:deviationTime ?deviationTime;\n    model:nbOccurence ?count;\n    \n    model:meanSpeed ?meanSpeed;\n    model:deviationSpeed ?deviationSpeed; \n\n    model:meantravelledDistance ?meantravelledDistance;\n    model:deviationtravelledDistance ?deviationtravelledDistance; \n\n    model:meancurveLength ?meancurveLength;\n    model:deviationcurveLength ?deviationcurveLength; \n\n    model:meanacceleration ?meanacceleration;\n    model:deviationacceleration ?deviationacceleration; \n\n    model:meancurveAcceleration ?meancurveAcceleration;\n    model:deviationcurveAcceleration ?deviationcurveAcceleration; \n\n    \n    :hasTrace <%(__destination__)s> ;\n    :hasBegin ?begin ;\n    :hasEnd ?end ;\n  ]\n}\nwhere {\n\n    SELECT ?direction ?typ\n           (SAMPLE(?ctyp2) as ?ctyp)\n           (SAMPLE(?count1) as ?count)\n           (SAMPLE(?meanTime1) as ?meanTime)\n           (SAMPLE(?meanSpeed1) as ?meanSpeed)\n           (SAMPLE(?meantravelledDistance1) as ?meantravelledDistance)\n           (SAMPLE(?meancurveLength1) as ?meancurveLength)\n            \n           (SAMPLE(?meanacceleration1) as ?meanacceleration)\n           (SAMPLE(?meancurveAcceleration1) as ?meancurveAcceleration)\n           \n           (SUM(ABS(?time2 - ?meanTime1)/(?count1 - 1)) as ?deviationTime)\n           (SUM(ABS(?Speed2 - ?meanSpeed1)/(?count1 - 1)) as ?deviationSpeed)\n\n           (SUM(ABS(?travelledDistance2 - ?meantravelledDistance1)/(?count1 - 1)) as ?deviationtravelledDistance)\n           (SUM(ABS(?curveLength2 - ?meancurveLength1)/(?count1 - 1)) as ?deviationcurveLength)\n           \n           (SUM(ABS(?acceleration2 - ?meanacceleration1)/(?count1 - 1)) as ?deviationacceleration)\n           (SUM(ABS(?curveAcceleration2 - ?meancurveAcceleration1)/(?count1 - 1)) as ?deviationcurveAcceleration)\n           \n           (MIN(?begin2) as ?begin)\n           (MAX(?end2) as ?end)\n    {\n\n        # first compute ?mean and ?count for each n-uple (?key1 ?key2 ?typ)\n        {\n            SELECT ?direction ?typ\n                   (SAMPLE(?ctyp1) as ?ctyp2)\n                   (COUNT(?o1) as ?count1)\n                   (AVG(?time1) as ?meanTime1)\n                   (AVG(?Speed1) as ?meanSpeed1)\n                   (AVG(?travelledDistance1) as ?meantravelledDistance1)\n                   (AVG(?curveLength1) as ?meancurveLength1)\n                   (AVG(?acceleration1) as ?meanacceleration1)\n                   (AVG(?curveAcceleration1) as ?meancurveAcceleration1)\n            {\n                ?o1 a ?typ ;\n                    model:direction ?direction ;\n                    model:actualSpeed ?Speed1 ;\n                    model:travelledDistance ?travelledDistance1 ;\n                    model:curveLength ?curveLength1 ;\n                    model:acceleration ?acceleration1 ;\n                    model:curveAcceleration ?curveAcceleration1 ;\n                    model:timeElapsed ?time1 .\n\n                VALUES (?typ ?ctyp1) {\n                    (model:M_MS model:M_MMS)\n                }\n            }\n            GROUP BY ?direction ?typ\n            HAVING (COUNT(?o1) >= 5)\n        }\n\n        # then aggregate obsels again, in order to compute deviation (see outer select).\n\n        ?o2 a ?typ ;\n            model:direction ?direction;\n            model:timeElapsed ?time2 ;\n            model:actualSpeed ?Speed2 ;\n            model:travelledDistance ?travelledDistance2 ;\n            model:curveLength ?curveLength2 ;\n            model:acceleration ?acceleration2 ;\n            model:curveAcceleration ?curveAcceleration2 ;\n            :hasBegin ?begin2 ;\n            :hasEnd ?end2 .\n\n    } GROUP BY ?direction ?typ\n\n}\n\n";
		String[] parametreForum = {sparlForum};
	    Trace TraceForumInteractionSignature = ktbsService.createTransformedTrace(base, "Trace-Forum-Interaction-Signature","sparql" , tracesourcesForum, parametreForum) ;		   
	    
	    // Video-Interaction-Signature
	    String [] tracesourcesVideo = {traceRessourceInteraction.get_Name()+"/"};
		String sparlVideo = "sparql=    prefix : <http://liris.cnrs.fr/silex/2009/ktbs#>\nprefix model: <"+modeUrl+"> \n\nCONSTRUCT {\n  [ a ?ctyp ;\n    model:obstyp ?typ ;\n    model:direction ?direction;\n    model:meanTime ?meanTime;\n    model:deviationTime ?deviationTime;\n    model:nbOccurence ?count;\n    \n    model:meanSpeed ?meanSpeed;\n    model:deviationSpeed ?deviationSpeed; \n\n    model:meantravelledDistance ?meantravelledDistance;\n    model:deviationtravelledDistance ?deviationtravelledDistance; \n\n    model:meancurveLength ?meancurveLength;\n    model:deviationcurveLength ?deviationcurveLength; \n\n    model:meanacceleration ?meanacceleration;\n    model:deviationacceleration ?deviationacceleration; \n\n    model:meancurveAcceleration ?meancurveAcceleration;\n    model:deviationcurveAcceleration ?deviationcurveAcceleration; \n\n    \n    :hasTrace <%(__destination__)s> ;\n    :hasBegin ?begin ;\n    :hasEnd ?end ;\n  ]\n}\nwhere {\n\n    SELECT ?direction ?typ\n           (SAMPLE(?ctyp2) as ?ctyp)\n           (SAMPLE(?count1) as ?count)\n           (SAMPLE(?meanTime1) as ?meanTime)\n           (SAMPLE(?meanSpeed1) as ?meanSpeed)\n           (SAMPLE(?meantravelledDistance1) as ?meantravelledDistance)\n           (SAMPLE(?meancurveLength1) as ?meancurveLength)\n            \n           (SAMPLE(?meanacceleration1) as ?meanacceleration)\n           (SAMPLE(?meancurveAcceleration1) as ?meancurveAcceleration)\n           \n           (SUM(ABS(?time2 - ?meanTime1)/(?count1 - 1)) as ?deviationTime)\n           (SUM(ABS(?Speed2 - ?meanSpeed1)/(?count1 - 1)) as ?deviationSpeed)\n\n           (SUM(ABS(?travelledDistance2 - ?meantravelledDistance1)/(?count1 - 1)) as ?deviationtravelledDistance)\n           (SUM(ABS(?curveLength2 - ?meancurveLength1)/(?count1 - 1)) as ?deviationcurveLength)\n           \n           (SUM(ABS(?acceleration2 - ?meanacceleration1)/(?count1 - 1)) as ?deviationacceleration)\n           (SUM(ABS(?curveAcceleration2 - ?meancurveAcceleration1)/(?count1 - 1)) as ?deviationcurveAcceleration)\n           \n           (MIN(?begin2) as ?begin)\n           (MAX(?end2) as ?end)\n    {\n\n        # first compute ?mean and ?count for each n-uple (?key1 ?key2 ?typ)\n        {\n            SELECT ?direction ?typ\n                   (SAMPLE(?ctyp1) as ?ctyp2)\n                   (COUNT(?o1) as ?count1)\n                   (AVG(?time1) as ?meanTime1)\n                   (AVG(?Speed1) as ?meanSpeed1)\n                   (AVG(?travelledDistance1) as ?meantravelledDistance1)\n                   (AVG(?curveLength1) as ?meancurveLength1)\n                   (AVG(?acceleration1) as ?meanacceleration1)\n                   (AVG(?curveAcceleration1) as ?meancurveAcceleration1)\n            {\n                ?o1 a ?typ ;\n                    model:direction ?direction ;\n                    model:actualSpeed ?Speed1 ;\n                    model:travelledDistance ?travelledDistance1 ;\n                    model:curveLength ?curveLength1 ;\n                    model:acceleration ?acceleration1 ;\n                    model:curveAcceleration ?curveAcceleration1 ;\n                    model:timeElapsed ?time1 .\n\n                VALUES (?typ ?ctyp1) {\n                    (model:M_MS model:M_MMS)\n                }\n            }\n            GROUP BY ?direction ?typ\n            HAVING (COUNT(?o1) >= 5)\n        }\n\n        # then aggregate obsels again, in order to compute deviation (see outer select).\n\n        ?o2 a ?typ ;\n            model:direction ?direction;\n            model:timeElapsed ?time2 ;\n            model:actualSpeed ?Speed2 ;\n            model:travelledDistance ?travelledDistance2 ;\n            model:curveLength ?curveLength2 ;\n            model:acceleration ?acceleration2 ;\n            model:curveAcceleration ?curveAcceleration2 ;\n            :hasBegin ?begin2 ;\n            :hasEnd ?end2 .\n\n    } GROUP BY ?direction ?typ\n\n}\n\n";
		String[] parametreVideo = {sparlVideo};
	    Trace TraceVideoInteractionSignature = ktbsService.createTransformedTrace(base, "Trace-Video-Interaction-Signature","sparql" , tracesourcesVideo, parametreVideo) ;		   
	    
	    //fusion
	    
	}
	public static void main(String[] args) throws Exception {
		 createTraceFormationInteractionBehaviors() ;
		// createSignatureTraceRessourceInteraction ();
	}
}
