package com.ignition_factory.ktbs.bean;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;
/**
 * Each resource in KTBS is identified by an URI and accessed through HTTP operations to that URI. 
 * This class implements these operations to ensure the connection with the KTBS
 * @author DERBEL Fatma
 *
 */

public class HttpConnectionKtbs {
	
	
	/**
	 * this method send get request to KTBS
	 * @param url
	 * @return response code 
	 * @throws Exception
	 */
	
	public int GetCodeResponse(String url) throws Exception {
		 
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setConnectTimeout(100000);
		
		int responseCode = con.getResponseCode();
		
	//	System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		return responseCode;
 
	}
	/**
	 * this method send get request to ktbs to get the value of etag
	 * @param url
	 * @return value of etag
	 * @throws Exception
	 */
	
	public String GetEtag(String url) throws Exception {
		 
		URL obj = new URL(url+".json");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
        
		URLConnection conn = obj.openConnection();
		Map<String, List<String>> map = conn.getHeaderFields();
		//System.out.println("Printing Response Header...\n");

//		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
//			System.out.println("Key : " + entry.getKey()
//	                           + " ,Value : " + entry.getValue());
//		}
		
		String Etag = con.getHeaderField("ETag");
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Etag : " + Etag);
		return Etag;
 
	}
	/**
	 * this method send get request to ktbs to get the data of a resource in json
	 * @param url
	 * @return Return a representation of the resource.
	 * @throws Exception
	 */
	public  JSONObject GetJsonResponse(String url) throws Exception {
		//System.out.println ("GetJsonResponse") ;
		boolean get = false ;
		while (!get) {
		
		 try {
			 get = true ;
		URL obj = new URL(url+".json");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setConnectTimeout(100000);
		//con.setRequestMethod("GET");
		 int responseCode = con.getResponseCode();
		 
	//	 System.out.println("\nSending 'GET' request to URL : " + url);
		//	System.out.println("Response Code : " + responseCode);
			
			if (responseCode==200 || responseCode==303) {
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		 
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				JSONObject ResponseJson = new JSONObject(new JSONTokener(response.toString()));
				return ResponseJson;
			}
			else if (responseCode==404) return null;
			else 	get = false ;
			//	return null;
			 
			 
		 }catch (final java.net.SocketTimeoutException e) {
			// System.out.println ("catch exeption") ;
			 get = false ; 
		 } catch (java.io.IOException e) {
			 get = false ; 
		}
		}
		return null;
		
		
		
	}
	/**
	 * this method send get request to ktbs to get the data of a resource in json
	 * @param url
	 * @return Return a representation of the resource.
	 * @throws Exception
	 * HERE
	 */
	public  JSONObject GetJsonResponse(String url, Boolean params) throws Exception {
		boolean get = false ;
		while (!get) {
			try {
					URL obj = new URL(url);
					HttpURLConnection con = (HttpURLConnection) obj.openConnection();
					con.setConnectTimeout(100000);
					con.setRequestMethod("GET");
					int responseCode = con.getResponseCode();
		
	
					if (responseCode==200 || responseCode==303) {
						BufferedReader in = new BufferedReader(
							new InputStreamReader(con.getInputStream()));
						String inputLine;
						StringBuffer response = new StringBuffer();
	 
						while ((inputLine = in.readLine()) != null) {
							response.append(inputLine);
						}
			
						in.close();
			
						JSONObject ResponseJson = new JSONObject(new JSONTokener(response.toString()));
						return ResponseJson;
					}
					else if (responseCode==409) {
						return this.GetJsonResponse (url) ;
					} else  return null;
			}catch (final java.net.SocketTimeoutException e) {
				 get = false ; 
			 } catch (java.io.IOException e) {
				 get = false ; 
			}
					}
		return null;
	}
	/**
	 * Send http post request  to create a new resource by submitting its representation to a “parent” resource.
	 * @param url
	 * @param JsonToPost
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	public int PostJsonObject (String url, JSONObject JsonToPost )throws Exception {
	
		boolean get = false ;
		while (!get) {
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setConnectTimeout(100000);

				con.setRequestMethod("POST");
		
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
				con.setRequestProperty("Content-Type", "application/json");
		
				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(JsonToPost.toString());
				wr.flush();
				wr.close();
 
		       int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Date to Post : " +JsonToPost.toString());
		//if (JsonToPost.has("@id")) System.out.println("id : " +JsonToPost.getString("@id"));
		//System.out.println("Json To post : " + JsonToPost.toString());
		//System.out.println("Response Code : " + responseCode);
		      return responseCode ;
			}catch (final java.net.SocketTimeoutException e) {
					 get = false ; 
				 } catch (java.io.IOException e) {
					 get = false ; 
				}
		}
			return (Integer) null;
	}
	/**
	 * send delete request 
	 * @param url
	 * @throws Exception
	 */
	public boolean DeleteResource (String url)throws Exception {
		
		
		boolean get = false ;
		while (!get) {
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setConnectTimeout(100000);
				con.setDoOutput(true);
				con.setRequestProperty(
		    "Content-Type", "application/x-www-form-urlencoded" );
				con.setRequestMethod("DELETE");
				int responseCode = con.getResponseCode();
		
				System.out.println("\nSending 'Delet' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);
				con.connect();
				if (responseCode==204) return true;
				
				else  return false;
			}catch (final java.net.SocketTimeoutException e) {
				 get = false ; 
			 } catch (java.io.IOException e) {
				 get = false ; 
			}
	}
		return false;
	}
	/**
	 * Put request to Alter a resource by submitting a new representation.
	 * @param url
	 * @param JsonToPut
	 * @throws Exception
	 */
	public void PutJsonObject (String url, JSONObject JsonToPut) throws Exception{
		
		boolean get = false ;
		while (!get) {
			try {
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setDoOutput(true);
				String s = this.GetEtag(url);
				con.setRequestMethod("PUT");
				con.setRequestProperty("Content-Type", "application/json");
				con.setRequestProperty("if-match", s);
		
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(JsonToPut.toString());
				wr.flush();
				wr.close();
		
		int responseCode = con.getResponseCode();
		String SS = con.getResponseMessage();
		//System.out.println(con.getHeaderFields());
		//System.out.println("\nSending 'PuT' request to URL : " + url);
		//System.out.println("Date to Put : " +JsonToPut.toString());
		//System.out.println("Response Code : " + responseCode + SS);
			}catch (final java.net.SocketTimeoutException e) {
				 get = false ; 
			 } catch (java.io.IOException e) {
				 get = false ; 
			}
	}
	}
}
