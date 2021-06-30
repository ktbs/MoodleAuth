package com.ignition_factory.ktbs.bean;

import org.json.JSONObject;

//import java.util.Collection;
//import java.util.Date;


public class main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		JSONObject Attributes = new JSONObject();
		Attributes.put ("a","valA");
		Attributes.put ("b","valB");
		Attributes.put ("c","valC");
		StoredTrace t= new StoredTrace ("http://localhost:8001/base2/t01/");
		t.init();
		//System.out.println (t.ListObsels);
		t.create_obsel ( "ob155","type", null, null, "subject" , Attributes, null );
	}

}
		
		// TODO Auto-generated method stub
		/*System.out.print("here");
		 Collection <classEssai> children = new HashSet<classEssai>() ;
		  classEssai c= new classEssai();
		   children.add(c);
		String uri;
		//RootKtbs ktbsRoot = new RootKtbs ("http://localhost:8001/");
		URL baseUrl = new URL("http://localhost:8001/base1/");
		URL url = new URL (baseUrl,"./model1");
		System.out.print(url.toString());
		
		
		JSONObject Attributes = new JSONObject();
		Attributes.put ("a","valA");
		Attributes.put ("b","valB");
		Attributes.put ("c","valC");
		StoredTrace t= new StoredTrace ("http://localhost:8001/base2/t01/");
		t.init();
		System.out.println (t.ListObsels);*/
		//t.create_obsel ( "ob155","type", null, null, "subject" , Attributes, null );
//		Base B = new Base ("http://localhost:8001/BaseTest/");
//		B.init();
//		B.set_label("label2");
//		B.init();
//		System.out.println(B.get_label());
		/*B.create_model("model6",null, null);
		Model M= new Model(B.get_uri()+"model6");
		M.init();
		M.create_obsel_type("typeTest2", null, null);
		String [] TraceSourceName = {"t01/"};
		String [] Parametres = {"after=1423845742006"};
		//B.create_computed_trace("filtered1", "filter", TraceSourceName, Parametres);
		String [] TraceSourceName1 = {"t01/"};
		String [] Parametres1 = {"sparql=    PREFIX : <http://liris.cnrs.fr/silex/2009/ktbs#>\nPREFIX m:  <http://liris.cnrs.fr/silex/2011/simple-trace-model/>\n\nCONSTRUCT {\n    [ a m:SimpleObsel ;\n      m:value ?value ;\n      :hasTrace <%(__destination__)s> ;\n      :hasSubject ?subject ;\n      :hasBegin ?begin ;\n      :hasEnd ?end ;\n      :hasSourceObsel ?o1, ?o2 ;\n    ] .\n} WHERE {\n    ?o2 :hasSubject ?subject ;\n        :hasEnd ?end ;\n        m:hasRelatedObsel ?o1 .\n    ?o1 :hasBegin ?begin .\n    OPTIONAL { ?o2 m:value ?value }\n}\n"};
		///B.create_computed_trace("joinRelated1", "sparql", TraceSourceName1, Parametres1);
		ComputedTrace T = new ComputedTrace ("http://localhost:8001/base2/filtered1/");
		T.init();*/
	//	String tracesourcename = "Trace_Profil" ;
	//	String UrlKTBSRoot = "https://liris-ktbs01.insa-lyon.fr:8000/fatma/" ;
	//	String basename = "vvvvfd7c3d65-7e5a-468e-bf90-59108c6f43ce" ;
//		String methode = "fsa" ;
//		String [] tracesource = {tracesourcename+"/"} ;	
//		String FsaTraceProfile = "fsa= {\n\u0009\"allow_overlap\": true,\n\u0009\"states\": {\n\u0009\u0009\"start\": {\n\u0009\u0009\u0009\"transitions\": [{\n\u0009\u0009\u0009\u0009\"condition\": \"#K_Press\",\n\u0009\u0009\u0009\u0009\"target\": \"pressedrelease\"\n\u0009\u0009\u0009}, {\n\u0009\u0009\u0009\u0009\"condition\": \"#K_Release\",\n\u0009\u0009\u0009\u0009\"target\": \"ReleaseRelease\"\n\u0009\u0009\u0009}, {\n\u0009\u0009\u0009\u0009\"condition\": \"#K_Press\",\n\u0009\u0009\u0009\u0009\"target\": \"presspress\"\n\u0009\u0009\u0009}, {\n\u0009\u0009\u0009\u0009\"condition\": \"#K_Release\",\n\u0009\u0009\u0009\u0009\"target\": \"releasepressed\"\n\u0009\u0009\u0009}]\n\u0009\u0009},\n\u0009\u0009\"releasepressed\": {\n                        \"max_duration\": 1000,\n\n\u0009\u0009\u0009\"transitions\": [{\n\u0009\u0009\u0009\u0009\"condition\": \"#K_Press\",\n\u0009\u0009\u0009\u0009\"target\": \"#K_RP\"\n\u0009\u0009\u0009}],\n\u0009\u0009\u0009\"default_transition\": {\n\u0009\u0009\u0009\u0009\"target\": \"releasepressed\",\n\u0009\u0009\u0009\u0009\"silent\": true\n\u0009\u0009\u0009}\n\u0009\u0009},\n\u0009\u0009\"presspress\": {\n                        \"max_duration\": 2000,\n\u0009\u0009\u0009\"transitions\": [{\n\u0009\u0009\u0009\u0009\"condition\": \"#K_Press\",\n\u0009\u0009\u0009\u0009\"target\": \"#K_PP\"\n\u0009\u0009\u0009}],\n\u0009\u0009\u0009\"default_transition\": {\n\u0009\u0009\u0009\u0009\"target\": \"presspress\",\n\u0009\u0009\u0009\u0009\"silent\": true\n\u0009\u0009\u0009}\n\u0009\u0009},\n\u0009\u0009\"pressedrelease\": {\n                        \"max_duration\": 2000,\n\u0009\u0009\u0009\"transitions\": [{\n\u0009\u0009\u0009\u0009\"condition\": \"?obs a m:K_Release ; m:keyCode ?k . ?pred m:keyCode ?k .\",\n\u0009\u0009\u0009\u0009\"matcher\": \"sparql-ask\",\n\u0009\u0009\u0009\u0009\"target\": \"#K_PR\"\n\u0009\u0009\u0009}],\n\u0009\u0009\u0009\"default_transition\": {\n\u0009\u0009\u0009\u0009\"target\": \"pressedrelease\",\n\u0009\u0009\u0009\u0009\"silent\": true\n\u0009\u0009\u0009}\n\u0009\u0009},\n\u0009\u0009\"ReleaseRelease\": {\n                        \"max_duration\": 2000,\n\u0009\u0009\u0009\"transitions\": [{\n\u0009\u0009\u0009\u0009\"condition\": \"#K_Release\",\n\u0009\u0009\u0009\u0009\"target\": \"#K_RR\"\n\u0009\u0009\u0009}],\n\u0009\u0009\u0009\"default_transition\": {\n\u0009\u0009\u0009\u0009\"target\": \"ReleaseRelease\",\n\u0009\u0009\u0009\u0009\"silent\": true\n\u0009\u0009\u0009}\n\u0009\u0009},\n\u0009\u0009\"#K_PP\": {\n\u0009\u0009\u0009\"terminal\": true,\n\u0009\u0009\u0009\"ktbs_attributes\": {\n\u0009\u0009\u0009\u0009\"#CharSource\": \"first #Character\",\n\u0009\u0009\u0009\u0009\"#keySource\": \"first #keyCode\",\n\u0009\u0009\u0009\u0009\"#CharDestination\": \"last #Character\",\n\u0009\u0009\u0009\u0009\"#keyDestination\": \"last #keyCode\",\n\u0009\u0009\u0009\u0009\"#time\": \"span http://liris.cnrs.fr/silex/2009/ktbs#hasEnd\"\n\u0009\u0009\u0009}\n\u0009\u0009},\n\u0009\u0009\"#K_RP\": {\n\u0009\u0009\u0009\"terminal\": true,\n\u0009\u0009\u0009\"ktbs_attributes\": {\n\u0009\u0009\u0009\u0009\"#CharSource\": \"first #Character\",\n\u0009\u0009\u0009\u0009\"#keySource\": \"first #keyCode\",\n\u0009\u0009\u0009\u0009\"#CharDestination\": \"last #Character\",\n\u0009\u0009\u0009\u0009\"#keyDestination\": \"last #keyCode\",\n\u0009\u0009\u0009\u0009\"#time\": \"span http://liris.cnrs.fr/silex/2009/ktbs#hasEnd\"\n\u0009\u0009\u0009}\n\u0009\u0009},\n\u0009\u0009\"#K_RR\": {\n\u0009\u0009\u0009\"terminal\": true,\n\u0009\u0009\u0009\"ktbs_attributes\": {\n\u0009\u0009\u0009\u0009\"#CharSource\": \"first #Character\",\n\u0009\u0009\u0009\u0009\"#keySource\": \"first #keyCode\",\n\u0009\u0009\u0009\u0009\"#CharDestination\": \"last #Character\",\n\u0009\u0009\u0009\u0009\"#keyDestination\": \"last #keyCode\",\n\u0009\u0009\u0009\u0009\"#time\": \"span http://liris.cnrs.fr/silex/2009/ktbs#hasEnd\"\n\u0009\u0009\u0009}\n\u0009\u0009},\n\u0009\u0009\"#K_PR\": {\n\u0009\u0009\u0009\"terminal\": true,\n\u0009\u0009\u0009\"ktbs_attributes\": {\n\u0009\u0009\u0009\u0009\"#CharSource\": \"#Character\",\n\u0009\u0009\u0009\u0009\"#keySource\": \"#keyCode\",\n\u0009\u0009\u0009\u0009\"#time\": \"span http://liris.cnrs.fr/silex/2009/ktbs#hasEnd\"\n\u0009\u0009\u0009}\n\u0009\u0009}\n\u0009}\n}";
//		String[] parametre = {FsaTraceProfile};
//		Base base = new Base (UrlKTBSRoot + basename + "/");
//		base.create_computed_trace ("Profil",methode,tracesource,parametre);
//		Trace tr = new Trace (UrlKTBSRoot+basename +"/"+ tracesourcename) ;
//		tr.init();
//		Collection<Obsel> obselprofil = tr.list_obsels (null, null, null);
	//	System.out.println(obselprofil.size());
		
//		for (Obsel obs : obselprofil) {
//			System.out.println(obs);
//			System.out.println (obs.get_obsel_type().getTypeId());
//			//System.out.println (obs.list_attribute_types().get_obsel_type());
//			System.out.println(obs.list_attribute_types());
//			for (AttributeType att : obs.list_attribute_types())
//				{
//				System.out.println (att.getName());
//					System.out.println (att.getValue());
//				}
//		}
//			System.out.println ("*************");
//			if (obs.get_attribute ("keySource") != null ){
//				System.out.println ( obs.get_attribute ("keySource").getValue()) ;
//			}
//		
			
			
			//for(Iterator key=json.keys();key.hasNext();) {
			    //JSONObject name = json.get(key.next());
			//	System.out.println (json.);
			     //now name contains the firstname, and so on... 
			//}
	//	}
		//Iterator it=obselprofil.iterator();
//	while (it.hasNext()) // tant que j'ai un element non parcouru
//		{
//		  Object o = it.next();
//		  System.out.println (o);
//		  //mes opérations
//	}
//		JSONObject json = new JSONObject();
//		json.put("keySource","key");
//		
//	    Iterator iter =json.keys();
//	    while(iter.hasNext()){
//	        String key = (String)iter.next();
//	        String value = json.getString(key);
//	        System.out.println(key + value);
//	    }
		
//		Trace signaturetrace = new Trace ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/bouattourghassenfc318f05-05cb-4040-b5b3-0307673e0d3b/Trace_Signature/");
//		Collection<Obsel> obsel = signaturetrace.list_obsels (null, null, null) ;
//		System.out.println (obsel) ;
		
//		RootKtbs ktbsRoot = new RootKtbs ("https://liris-ktbs01.insa-lyon.fr:8000/public/");
//		ktbsRoot.create_base("cc", "Label");
//		Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/public/cc");
//	    base.init();
//	    base.create_model("modelname", null, "modelprimary");
//		Model model = new Model (base.get_uri()+ "modelname");
//		base.init ();
//		Date date = new Date();
//		base.create_stored_trace("tracename", model, date.toString(), null, "primary trace");
//		Trace trace = new Trace ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/test15bugfe395c3d-6c8a-4ba5-826b-2dddbcc11504/primarytraceAuthentificationTrace_Profil/");
//		Trace trace2 = new Trace ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/bbbbce5f2ceb-4a75-42a5-a4b7-78bf67fc2ffb/primarytraceInscriptionTraceObselDynamic/");
//		Collection<Obsel> obselprofil = trace.list_obsels (null, null, null);
//		
//		trace2.init();
//		System.out.println(trace2.get_origin());
//		
//		for (Obsel obs : obselprofil){
//			System.out.println (obs.get_jsonObsel());
//			trace2.PostObsel(obs);
//		}
		//System.out.println ("ici");
	//	RootKtbs ktbsRoot = new RootKtbs ("http://176.31.62.95/ktbs/");
    	//ktbsRoot.create_base("cc", "Label");
		
//		for (Obsel obs : obselprofil){
//			System.out.println ("json" + obs.get_jsonObsel());
//			trace.PostObsel(obs);
//		}
//		JSONObject Attributes = new JSONObject();
//		Attributes.put ("a","valA");
//		Attributes.put ("b","valB");
//		Attributes.put ("c","valC");
//		StoredTrace t= new StoredTrace ("http://localhost:8001/base2/t01/");
//		t.init();
//		//System.out.println (t.ListObsels);
//		t.create_obsel ( "ob155","type", null, null, "subject" , Attributes, null );
//	}
//
//}
