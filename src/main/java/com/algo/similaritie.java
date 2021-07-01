package com.algo;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class similaritie {

	 
	// function calcul similaritie
	
	public static double simulaiListString (List<String> Liststr1, List<String> Liststr2) {
	    	
	    	
	    	int cpt=0;
	    	int cpt2=0;
	    	
	    	for (String s : Liststr1 ) {
	    	if (Liststr2.contains(s)) cpt++;
	    	}
	    	for (String s : Liststr2 ) {
	        	if (Liststr1.contains(s)) cpt2++;
	        }
	    	return Math.max(cpt, cpt2) / Math.max(Liststr1.size(), Liststr2.size()) ;
	    }
	    
	    public static double simulaireString (String str, String str2) {
	    	if (str.equals(str2)) return 1 ;
	    	else  return 0;
	    	
	    }
	    
	  public static double simulairString (String str, JSONArray jsonArray) throws JSONException {
		  for  (int i=0; i<jsonArray.length();i++ ) {
			  if ((jsonArray.get(i)).equals(str)) return 1 ; 
		  }
		  
		  return 0;
		  
	  }
	 
	
	 
	  public static double simulairDuration (double D1, double D2) {
			 
		  
		  if ( max (D1,D2) !=0 ) {double d = min (D1,D2) /  (double) (max(D1,D2)) ;
		  return d ;}
		  else return 0 ;
	  }
	  
	
	
	//// auther function
	  public static double min (double a, double b) {
		  if (a<b) return a; 
		  else return b; 
		  
	  }
	  public static double max (double a, double b) {
	    	if (a>b) return a; 
	    	else return b; 
			  
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
	    static int longPrefixIp(String ip1,String ip2)
	    {
	  	String[] ip1_tab = ip1.split("\\.");
	  	String[] ip2_tab = ip2.split("\\.");

	  	int seq_long=0;


	  	for (int i=0;i<ip1_tab.length;i++)
	  	{

	  		if( ip2_tab[i].equals(ip1_tab[i]))
	  			seq_long++;

	  	}

	  	return seq_long;

	  }


}
