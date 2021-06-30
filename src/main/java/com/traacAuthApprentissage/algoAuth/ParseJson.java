package com.traacAuthApprentissage.algoAuth;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ignition_factory.ktbs.bean.*;
import com.traacAuthApprentissage.model.User;

import repository.UserRepository;

public class ParseJson {
	 @Autowired
    static UserRepository userRepository ;
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

	        //create JsonReader object and pass it the json file,json source or json text.
	        try(JsonReader jsonReader = new JsonReader(
	                new InputStreamReader(
	                        new FileInputStream(jsonFilePath), StandardCharsets.UTF_8))) {

	            Gson gson = new GsonBuilder().create();

	            jsonReader.beginArray(); //start of json array
	            int numberOfRecords = 0;
	            while (jsonReader.hasNext()){ //next json array element
	            	
	            	logstore logstoreI = gson.fromJson(jsonReader, logstore.class);
	            	numberOfRecords++;
	                System.out.println("Total Records effectttt : "+numberOfRecords);
	               
	               
	                
	                try {
		                User user = new User (logstoreI.username,"https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/" + logstoreI.username +"/","https://liris-ktbs01.insa-lyon.fr:8000/fatma/baseMoodle/" + logstoreI.username +"/"+"PrimarytraceLog/") ;

		                User _user = userRepository.save(user);

		                System.out.println (_user);
	        			
	        		} catch (Exception e) {
	        			System.out.println ( HttpStatus.INTERNAL_SERVER_ERROR);
	        		}
	            }
	            jsonReader.endArray();
	            System.out.println("Total Records Found : "+numberOfRecords);
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
