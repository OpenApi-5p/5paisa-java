package com.FivePaisa.service;

import com.FivePaisa.config.AppConfig;
import com.FivePaisa.util.AES;
import com.FivePaisa.util.ServerDetails;
import okhttp3.*;
//import okhttp3.ws.WebSocket;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.Buffer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ApiCalls {
	Properties pr = new Properties();
	public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	String Fivepaisacookie;
	String jwtToken;
	String urls = "https://Openapi.5paisa.com";
	String apiUrl = "https://Openapi.5paisa.com/VendorsAPI/Service1.svc/";
	String loginCheckUrl ="https://openfeed.5paisa.com/Feeds/api/UserActivity/";
	String wssUrl ="wss://openfeed.5paisa.com/Feeds/api/chat?Value1=";
	String ASPXAUTH_Cookie;
    final String FIVE_PAISA_COOKIE="5paisacookie";
	final String JWT_TOKEN="JwtToken";
	
	
	JSONParser parser = new JSONParser();
	Properties properties = new Properties();

	
	public String callCheckLogin(JSONObject reqbody, String suburl, String rc,AppConfig config) throws IOException, ParseException {

	//	String cookie = readFile();
		//System.out.println("cookie:====="+cookie);
		OkHttpClient client = new OkHttpClient();
		String url = loginCheckUrl + suburl;
		System.out.println("Url >>====="+url);
		JSONObject payload = new JSONObject();
	    JSONObject requestHead = new JSONObject();
		requestHead = headerWss(rc, config.getUserId(),config);
		payload.put("body", reqbody);
		payload.put("head", requestHead);
		RequestBody body = RequestBody.create(JSON, payload.toJSONString());
		System.out.println("\n Request >> " + payload);
		
		Request request = new Request.Builder().url(url)
				.post(body).build();
		
		Call call = client.newCall(request);
		Response response = call.execute();
		//System.out.println("Response Json>>"+response.body().string());	
		System.out.println("\n Response >> " + response.body().string());
		 ASPXAUTH_Cookie = (response.headers().get("set-cookie").split(";", 2)[0]).split("=", 2)[1];
			
		 System.out.println("\n Cookies ASPXAUTH_Cookie: "+response.headers().get("set-cookie"));
	//	 System.out.println("@@Cookies ASPXAUTH_Cookie: "+ASPXAUTH_Cookie);
		//whiteFile(ASPXAUTH_Cookie);
		if (!response.isSuccessful())
			throw new IOException("\n  Unexpected code " + response);
	
		return ASPXAUTH_Cookie;
		
	}
	
	public Response callWithEncrytion(JSONObject requestBody, String url, String rc,AppConfig config)
			throws IOException, ParseException {
		final JSONObject body = new JSONObject();
		requestBody = ipConfig(requestBody);
		JSONObject requestHead = new JSONObject();
		requestHead = header(rc, AES.encrypt(config.getUserId()), AES.encrypt(config.getPassword()),config);
		body.put("body", requestBody);
		body.put("head", requestHead);
		Response ressonse = apiCall(url, body, rc,config);
		return ressonse;
	}

	public Response OrdReqCall(JSONObject requestBody, String url, String rc,AppConfig config) throws IOException, ParseException {
	
		Response response = callWithCookies(requestBody,url,rc,config);
		return response;
	}

	public Response callWithCookies(JSONObject requestBody, String url, String rc,AppConfig config) throws IOException, ParseException {
		JSONObject body = new JSONObject();
		JSONObject OrderStatusJson = (JSONObject) requestBody;
		requestBody = ipConfig(OrderStatusJson);
		JSONObject requestHead = new JSONObject();
		requestHead = header(rc, config.getUserId(), config.getPassword(),config);
		body.put("body", requestBody);
		body.put("head", requestHead);
		Response resonse = apiCallWithCookies(url, body);
		return resonse;
	}

	public Response callLogin(JSONObject requestBody, String url, String rc,AppConfig config) throws IOException, ParseException {
         
		requestBody.put("Email_id",AES.encrypt(properties.getEmailId()));
		requestBody.put("My2PIN", AES.encrypt(properties.my2Pin));
		requestBody.put("Password", AES.encrypt(properties.Password));
		Response resonse = apiCall(url, requestBody,rc,config);
		return resonse;
	}

	public Response apiCall(String suburl, JSONObject reqbody, String rc,AppConfig config) throws IOException, ParseException {
		OkHttpClient client = new OkHttpClient();
		String url = apiUrl + suburl;
		System.out.println("\n Url >> " + url);

		//
		JSONObject payload = new JSONObject();
		reqbody = ipConfig(reqbody);
	    JSONObject requestHead = new JSONObject();
		requestHead = header(rc, config.getUserId(), config.getPassword(),config);//change
		payload.put("body", reqbody);
		payload.put("head", requestHead);
		//
		RequestBody body = RequestBody.create(JSON, payload.toJSONString());
		System.out.println("\n Request >> " + payload);
		Request request = new Request.Builder().url(url)
				.post(body).build();
		Call call = client.newCall(request);
		Response response = call.execute();

//	String	headercookie = response.headers().get("set-cookie");//.split(";", 2)[1]).split("=", 2)[1];
		 for (String header_cookie : response.headers("Set-Cookie")) {
			 if(header_cookie.contains(FIVE_PAISA_COOKIE)){
				 Fivepaisacookie=header_cookie.split(";", 2)[0].split("=", 2)[1];
				 System.out.println("\nCookies 5piasa:::"+header_cookie);
			     System.out.println("\n FIVE_PAISA_COOKIE Cookies>>"+Fivepaisacookie);
			 }
			 if(header_cookie.contains(JWT_TOKEN)){
				 jwtToken =header_cookie.split(";", 2)[0].split("=", 2)[1];
				 System.out.println("\nCookies JWT TOKEN:::"+header_cookie);
			     System.out.println("\n JWT_TOKEN Cookies >>"+jwtToken);
			 }
           }
		System.out.println("\nCookies name:::"+response.headers().get("set-cookie"));
		//System.out.println("Cookies in Login"+Fivepaisacookie);
		writeFile(Fivepaisacookie);
		if (!response.isSuccessful())
			throw new IOException("Unexpected code " + response);
		return response;
	}

	public Response apiCallWithCookies(String suburl, JSONObject reqbody) throws IOException, ParseException {
		String cookie = readFile();
		System.out.println("cookie:====="+cookie);
		OkHttpClient client = new OkHttpClient();
		String url = apiUrl + suburl;
		System.out.println("\n Url >> " + url);
		System.out.println("\n Request >> " + reqbody);
		RequestBody body = RequestBody.create(JSON, reqbody.toJSONString());
		Request request = new Request.Builder().url(url)
				.addHeader("Cookie", "5paisacookie=" + cookie).post(body).build();
		Call call = client.newCall(request);
		Response response = call.execute();
		if (!response.isSuccessful())
			throw new IOException("\n  Unexpected code " + response);
		return response;
	}

	public JSONObject header(String rc, String ui, String pw,AppConfig config) throws IOException, ParseException {
		JSONObject requestHead = new JSONObject();
		requestHead.put("requestCode", rc);
		requestHead.put("key", config.getKey());
		requestHead.put("appVer", config.getAppVer());
		requestHead.put("appName", config.getAppName());
		requestHead.put("osName", config.getOsName());
		requestHead.put("userId", ui);
		requestHead.put("password",pw);
	//	System.out.println(AES.encrypt(ui)+"ENcrypted Password=====:"+AES.encrypt(pw));
		return requestHead;
	}
	public JSONObject headerWss(String rc, String ui,AppConfig config) throws IOException, ParseException {
		JSONObject requestHead = new JSONObject();
		requestHead.put("requestCode", rc);
		requestHead.put("key", config.getKey());
		requestHead.put("appVer", config.getAppVer());
		requestHead.put("appName", config.getAppName());
		requestHead.put("osName", config.getOsName());
		requestHead.put("LoginId", config.getLogiId());
		//requestHead.put("password",pw);
		return requestHead;
	}
	public JSONObject ipConfig(JSONObject requestBody) throws IOException, ParseException {
		JSONObject ipDetails = ServerDetails.GetIP();
		requestBody.put("LocalIP", ipDetails.get("publicIp"));
		requestBody.put("PublicIP", ipDetails.get("privateIp"));
		return requestBody;
	}

	public void writeFile(String tx) throws IOException {
		FileWriter myWriter = new FileWriter("cookie.txt");
		myWriter.write(tx);
		myWriter.close();

	}

	public String readFile() throws IOException {
		FileReader fr = new FileReader("cookie.txt");
		int i;
		String cookie = "";
		while ((i = fr.read()) != -1)
			cookie += (char) i;
		fr.close();
		return cookie;
	}
}
