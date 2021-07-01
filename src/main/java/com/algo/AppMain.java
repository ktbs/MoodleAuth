package com.algo;



import org.hibernate.*;

import com.model.User;

public class AppMain {
	 static User userObj;
	    static Session sessionObj;
	    static SessionFactory sessionFactoryObj;
	
	public static void main(String[] args) {
		
		
		
		SessionFactory factory=HibernateUtil.getFactory();
		Session session=factory.openSession();
		session.beginTransaction(); 
			
			try {
				User user = new User ("username","base","trace");

				session.save(user);
				session.getTransaction().commit();
				System.out.println("transaction commitée");
			} catch (Exception ex) {
				System.out.println("erreur : " + ex.getMessage());
				
				session.getTransaction().rollback();
			} finally {
				
				session.close();
				factory.close(); 
			}
		}

}