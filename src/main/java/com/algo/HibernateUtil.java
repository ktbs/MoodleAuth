package com.algo;

/**
*
* @author fderbel
*
*/
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	
	private static SessionFactory sessionfactory=null;
	private HibernateUtil(){}
	public static SessionFactory getFactory(){
	 if (sessionfactory==null){
	 StandardServiceRegistry registre= new StandardServiceRegistryBuilder().configure().build();
	 sessionfactory=new MetadataSources(registre).buildMetadata().buildSessionFactory();
	 }
	return sessionfactory;
	}
}
