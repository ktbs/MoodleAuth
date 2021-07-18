package com.algo;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.Obsel;


public class ExperienceApprentissage {

	
	public static void main(String[] args)throws Exception {
//604
 
Base base = new Base("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/user1025316407040016385/");	

Collection<Obsel> listObselSession= ktbsService.getListObsel(base,null, null,"FormationSessionInteractionBehaviors" );
Collection<Obsel> obselSignature = ktbsService.getListObsel(base,null, null,"FormationSessionInteractionBehaviorsSignature");
for (int i = 0; i < listObselSession.size(); i++) {
	
	Obsel ob =(Obsel) listObselSession.toArray()[i];
	System.out.println(CalculTrust(obselSignature,ob,0));
	
}
	
	}
  public static double CalculTrust(Collection<Obsel> obselSignature,Obsel lastSession,double trustS9 ) throws JSONException {
double [] distance = new double [100] ;
double [] Poid = new double [100] ;
Double score =0.0;
Double[] weigth = {0.3,0.2,0.2,0.1,0.1,0.1} ;
Double  k = 0.55 ;
;
JSONArray detailsDistances = new JSONArray ();

double sommePoid = 0 ;
for (int j=0; j< obselSignature.size(); j++) {
	double Simularite = calculSimularitySession (lastSession.get_jsonObsel(),((Obsel) obselSignature.toArray()[j]).get_jsonObsel(),weigth);
	distance [j] = Simularite ;
	Poid [j] = Double.parseDouble ((String) ((Obsel) obselSignature.toArray()[j]).get_attribute("poid").getValue()) ;
	
	score = score + Poid [j]*Simularite;
	sommePoid+=Poid [j];
	JSONObject ObjdetailsDistances = new JSONObject();
	ObjdetailsDistances.put("clusterID ", ((Obsel) obselSignature.toArray()[j]).get_id());
	ObjdetailsDistances.put("Simularité", Simularite);	
	ObjdetailsDistances.put("Poid", Poid [j]);	
	detailsDistances.put(ObjdetailsDistances);
}
score = score/sommePoid;
JSONObject Objscore = new JSONObject();
Objscore.put("score ", score);
detailsDistances.put(Objscore);
Double SimMax = MaxTableau (distance);
double trust = trustS9;
System.out.println(SimMax+"ssssssssss");
if (SimMax>k)
	trust = (double) (trust + score) ;
else {
	trust = (double) (trust * score) ;
}
return  trust;
}

  public static double calculSimularitySession (JSONObject Session,JSONObject barycentre, Double[] weigth) throws NumberFormatException, JSONException {
		
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
		else return b;}
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
    
}