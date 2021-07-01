package com.algo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ignition_factory.ktbs.bean.*;
import com.model.User;


public class ParseJson {
	
	 public   JSONArray ParseFile (String Path) {
	    	JSONParser parser = new JSONParser();
	    	try {
	            Object obj = parser.parse(new FileReader(Path));
	            JSONArray ArraySession = (JSONArray ) obj;
	            return ArraySession;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null ;
	        }
	    }
	 
	
	  @SuppressWarnings({ "deprecation", "null" })
	public static void parse(String jsonFilePath) throws Exception{

		FileInputStream  file = new FileInputStream(jsonFilePath) ;
		Reader reader =   new InputStreamReader(file,StandardCharsets.UTF_8);
		 
		 
		  
		  //create JsonReader object and pass it the json file,json source or json text.
	        try( JsonReader jsonReader = new JsonReader(reader)) {

	            Gson gson = new GsonBuilder().create();

	            jsonReader.beginArray(); //start of json array
	            int numberOfRecords = 0;
	            
	            SessionFactory factory=HibernateUtil.getFactory();
        		Session session=factory.openSession();
        		 
        		try { 
	            while (jsonReader.hasNext()){ //next json array element
	            	
	            	logstore logstoreI = gson.fromJson(jsonReader, logstore.class);
	            	numberOfRecords++;
	                System.out.println("Total Records effectttt : "+numberOfRecords);
	             if (numberOfRecords>1888) { 
	            		//  && numberOfRecords!=1888 && numberOfRecords!=1888) {
	                
	               User user = new User (logstoreI.username,"https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/" + logstoreI.username +"/","https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/" + logstoreI.username +"/"+"PrimarytraceLog/") ;
	               session.beginTransaction();
	               session.save(user);
		    	   session.getTransaction().commit();
		    	   System.out.println("transaction commitée");
		    		}
		    		}
	            jsonReader.endArray();
	            System.out.println("Total Records Found : "+numberOfRecords);
        		} catch (Exception ex) {
    				System.out.println("erreur : " + ex.getMessage());
    				session.getTransaction().rollback();
    			} finally {
    				
    				session.close();
    				factory.close(); 
    			}
	        }
	        catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	public static void main(String[] args) throws Exception   {
		
		parse ("/Users/derbelfatma/eclipse-workspace/traacAuthApprentissage/src/main/java/com/traacAuthApprentissage/algoAuth/mdl_user.json");		 
	
	}
}
