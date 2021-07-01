package com.algo;



import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.ComputedTrace ;
import com.ignition_factory.ktbs.bean.HttpConnectionKtbs;
import com.ignition_factory.ktbs.bean.Model;
import com.ignition_factory.ktbs.bean.Obsel;
import com.ignition_factory.ktbs.bean.RootKtbs;
import com.ignition_factory.ktbs.bean.Trace;


class ktbsService {

	static String urlKTBSRoot = "https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/";
//	static String urlKTBSRoot = "http://localhost:8001/";

	//def grailsApplication
	/**
	 * 
	 * @param basename
	 * @param label
	 * @return
	 * @throws Exception 
	 */
	static Base createBase (String basename, String label) throws Exception {
		

		RootKtbs ktbsRoot = new RootKtbs (urlKTBSRoot);
		ktbsRoot.create_base(basename,label);
		Base base = new Base (urlKTBSRoot + basename + "/");
		return base;
    }
	/**
	 * 
	 * @param basename
	 * @param label
	 * @return
	 * @throws Exception 
	 */
	static Base createSubBase (String basename , String Subasename) throws Exception {
		

		Base base = new Base (urlKTBSRoot+ basename + "/");
		Base subbase = base.create_Subbase(Subasename);
		//Base subbase = new Base (urlKTBSRoot + basename + "/");
		return subbase;
    }
/**
 * 
 * @param base
 * @param modelname
 * @param tracename
 * @param origin
 * @return
 * @throws Exception 
 */
	public static Trace createPrimaryTrace (Base base,String modelname, String tracename, String origin) throws Exception {
		
		String orgine;
	
		base.create_model(modelname, null, "modelprimary");
		Model model = new Model (base.get_uri()+ modelname);
		base.init ();
		if (origin != null) {
			orgine = origin ;
		} else {
		Date date = new Date() ;
		orgine = date.toString();
		}
		
		base.create_stored_trace(tracename, model, orgine , null, "primary trace");
		Trace trace = new Trace (base.get_uri()+ tracename+"/");
		trace.init();
		return trace ;
	}
	/**
	 * 
	 * @param basename
	 * @param tracename
	 * @param Attributes
	 * @return
	 * @throws Exception 
	 */
	
	static void createObsel2 (Base base , Trace trace , JSONObject Attributes) throws Exception 
	{
		
	//	Base base = new Base (urlKTBSRoot+basename+"/");
	//	Trace trace = new Trace (urlKTBSRoot+basename+"/"+tracename);
		
		trace.init();
		
		
		Obsel obs = new Obsel (trace.get_uri(),trace.get_model().get_uri(), Attributes);
		
		trace.PostObsel(obs);
		
		//System.out.println ("URI" + trace.get_uri());
		
		
	}
	
	static void createObsel (String basename , String tracename , JSONObject Attributes) throws Exception 
	{
		
		Base base = new Base (urlKTBSRoot+basename+"/");
		Trace trace = new Trace (urlKTBSRoot+basename+"/"+tracename);
		
		trace.init();
		
		
		Obsel obs = new Obsel (trace.get_uri(),trace.get_model().get_uri(), Attributes);
		
		trace.PostObsel(obs);
		
		//System.out.println ("URI" + trace.get_uri());
		
		
	}
	/**
	 * 
	 * @param basename
	 * @param tracename
	 * @param URI
	 * @param Attributes
	 * @return
	 * @throws Exception 
	 */
	
	static void updateObsel (String basename , String tracename , String URI , JSONObject Attributes) throws Exception
	{
		
		
		
		Base base = new Base (urlKTBSRoot+basename+"/");
		Trace trace = new Trace (urlKTBSRoot+basename+"/"+tracename);
		trace.init();
		
		Obsel obs = new Obsel (URI);
		trace.PostObsel(obs);
		
	}
	/**
	 * 
	 * @param base
	 * @param tracename
	 * @param methode
	 * @param tracesource
	 * @param parametre
	 * @return
	 * @throws Exception 
	 */
	
	static Trace createTransformedTrace (Base base,String tracename,String methode , String[] tracesource, String[] parametre) throws Exception {
		
		base.create_computed_trace (tracename,methode,tracesource,parametre);
		ComputedTrace computedtrace = new ComputedTrace(base.get_uri()+tracename);
		return computedtrace;
		
	}
	/**
	 * 
	 * @param base
	 * @param trace
	 * @return
	 * @throws Exception 
	 */
	
	static String getUrlModel (Base base,Trace trace) throws Exception{
		trace.init();
		Model model = trace.get_model();
		String url = model.get_uri().substring(0,model.get_uri().length()-1);
		//println ("url model" + url);
		return url;
	}
	/**
	 * 
	 * @param listobsel
	 * @return
	 */

	static int getIdLastObsel (Collection<Obsel> listobsel) {
		int beginmax = 0;
		int beginlastobsel = 0;
		for (Obsel obs : listobsel){
			
			if ((int)(obs.get_begin() )> beginmax) {
				beginmax = (int)obs.get_begin() ;
				beginlastobsel = (int)obs.get_begin();
			}
		}
		
		return beginlastobsel;
	}
	/**
	 * 
	 * @param base
	 * @param begin
	 * @param end
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	static Collection<Obsel> getListObsel (Base base, Object begin,Object end, String name) throws Exception {
		
		Trace profiltrace = new Trace (base.get_uri()+name);
		Collection<Obsel> ListObsels;
		// = profiltrace?.list_obsels (begin, end, null)
		HttpConnectionKtbs htppKtbs = new HttpConnectionKtbs();
		String  url = profiltrace.get_uri()+"/@obsels.json" ;
		if ((begin != null)&& (end != null)) {
			url = url +"?minb="+begin + "?maxb="+end ;
		} else if (begin != null) {url = url +"?minb="+begin;}
		else if (end != null) {url = url +"?maxb="+end ;}
		//System.out.println(url);
		JSONObject jsonData = htppKtbs.GetJsonResponse(url,true) ;
		JSONArray arrayObsels = (jsonData != null) ? jsonData.getJSONArray("obsels") : null;
		ListObsels = new LinkedHashSet<Obsel>();
		
		if(arrayObsels != null) {
			for (int i = 0; i < arrayObsels.length(); i++){
				//System.out.println (arrayObsels.getJSONObject(i));
				Obsel objObsel = new Obsel (profiltrace.get_uri(),jsonData.getJSONArray("@context").getJSONObject(1).getString("m"),arrayObsels.getJSONObject(i));
				
						ListObsels.add(objObsel);
						
			}
		}
		
		return ListObsels;
	}
	/**
	 * 
	 * @param listobsel
	 * @param order
	 * @return
	 */
	//Obsel getObselByOrder (Collection<Obsel> listobsel, int order) {
		
		//return listobsel.get(order) ;
	//}
	/**
	 * 
	 * @param type
	 * @param obAttribute
	 * @param obselsignature
	 * @return
	 * @throws JSONException 
	 */
	
	static Obsel getObselWithType (String type, JSONObject obAttribute, Collection<Obsel> obselsignature) throws JSONException {
		Obsel obsel = null;
		for (Obsel obs : obselsignature){
			
			if (obs.get_obsel_type().getTypeId().equalsIgnoreCase(type)){
				if (obAttribute != null) {
					Iterator iter =obAttribute.keys();
					if (obAttribute.length() ==1 ){
						String key = (String)iter.next();
						String value = obAttribute.getString(key);
						if (obs.get_attribute (key).getValue().equalsIgnoreCase(value)){
							return obs ;
							
						}
					} else if (obAttribute.length() ==2) {
						String key = (String)iter.next();
						String value = obAttribute.getString(key);
						
						//Iterator iter1 = iter.next()
						String key1 = (String)iter.next();
						String value1 = obAttribute.getString(key1);
						if ((obs.get_attribute (key).getValue().equalsIgnoreCase(value))&&(obs.get_attribute (key1).getValue().equalsIgnoreCase(value1))){
							return obs ;
							
					 }
					}

				}
				else
				{
					return obs;
				}
			}
			
		}
		return null;
		
	}
}
