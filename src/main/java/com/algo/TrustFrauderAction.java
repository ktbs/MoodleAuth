package com.algo;

import java.util.Collection;
import java.util.List;
import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.Obsel;
import com.ignition_factory.ktbs.bean.Trace;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ignition_factory.ktbs.bean.Base;
import com.ignition_factory.ktbs.bean.Obsel;

public class TrustFrauderAction {
	public static void main(String[] args) throws Exception {
		SessionFactory factory=HibernateUtil.getFactory();
	   	 Session session=factory.openSession();
	   	 
		
		List list = session.createSQLQuery("SELECT user_name from authtraac.Sessions group by user_name HAVING  count(*) >20").list();	
	Base base = new Base("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/user1038703339065311233/");
	Base base23 = new Base("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/user1002410981378228225/");
	Collection<Obsel> obselSignature = ktbsService.getListObsel(base,null, null,"RessourceInteractionActionSignature");
	Collection<Obsel> listAction = ktbsService.getListObsel(base23,null,null, "RessourceInteractionAction");
	Integer begin= (Integer)((Obsel) listAction).get_begin();
	Integer end =(Integer) ((Obsel)listAction).get_end();
	System.out.println(begin);
	
	
	//for (Object  userName : list ) {
		int i=0;
	//Base base = new Base ("https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/"+userName);
	//Collection<Obsel> obselSignature = ktbsService.getListObsel(base,null, null,"FormationSessionInteractionBehaviorsSignature");
//	float trustAction1=(float) 0.568;
//	while(listAction.size()>i){
//	Obsel action = (Obsel) listAction.toArray()[i];
//	
//	float distance = AuthentificationComportementApprentissage.calculDistance (action, obselSignature);
//	float trustAction = AuthentificationComportementApprentissage.calculateCoefficient(distance, action,trustAction1, base23.get_uri()+"RessourceInteractionActionSignature/");
//	trustAction1=trustAction;
//	System.out.println(trustAction);
//	i++;
//	}

	
	
	
	
	
	
	
	
	
	}}
