package com.ignition_factory.ktbs.bean;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.json.JSONTokener;


 
public class HttpURLConnectionExample {
 
	private final String USER_AGENT = "Mozilla/5.0";
 
	public static void main(String[] args) throws Exception {
 
		HttpURLConnectionExample http = new HttpURLConnectionExample();
 
		System.out.println("Testing 1 - Send Http GET request");
		//http.sendGet();
		System.out.println("Testing 1 - Send Http Delete request");
		http.delete();
		System.out.println("\nTesting 2 - Send Http POST request");
		//http.sendPost();
 
	}
 
	// HTTP GET request
	private void sendGet() throws Exception {
 
		String url = "http://localhost:8001/base2/.json";
 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// optional default is GET
		con.setRequestMethod("GET");
		
		//add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Content-Type", "application/json");
		
		int responseCode = con.getResponseCode();
	

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		
        BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		//JSONObject myObject = new JSONObject(response);
		JSONObject myObject = new JSONObject(new JSONTokener(response.toString()));
	//	System.out.println (response);
	//	System.out.println(myObject.getString("@type"));
 
	}
 
	// HTTP POST request
	private void sendPost() throws Exception {
 
		String url = "http://localhost:8001/";
		
		
		JSONObject cred = new JSONObject();
		
		
		
		cred.put("label", "My new base");
		cred.put("@type", "Base");
		cred.put("@id","base2/");
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json");
		//String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		//wr.writeBytes(urlParameters);
		String JsonData ="{ \"@id \":  \"base1/ \",  \"@type \":  \"Base \",  \"label \":  \"My new base \" }";
		wr.writeBytes(cred.toString());
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " +cred.toString());
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());
 
	}
 
	
	private void delete () throws Exception {
		
		URL url = new URL("http://localhost:8001/base2/");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestProperty(
		    "Content-Type", "application/x-www-form-urlencoded" );
		httpCon.setRequestMethod("DELETE");
		int responseCode = httpCon.getResponseCode();
		

		System.out.println("\nSending 'Delet' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		httpCon.connect();
		
	}
	
	private void put () throws Exception {
		
		URL url = new URL("http://www.example.com/resource");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		OutputStreamWriter out = new OutputStreamWriter(
		    httpCon.getOutputStream());
		out.write("Resource content");
		out.close();
		httpCon.getInputStream();
		
	}
}