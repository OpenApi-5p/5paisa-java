
import com.FivePaisa.api.RestClient;
import com.FivePaisa.api.WebSocketObservable;
import com.FivePaisa.api.WssClient;
import com.FivePaisa.config.AppConfig;
import com.FivePaisa.service.Properties;
import com.FivePaisa.util.AES;

import okhttp3.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestWebSocket {

	AppConfig config = new AppConfig();
	RestClient apis = new RestClient(config);
	JSONParser parser = new JSONParser();
	Properties properties = new Properties();
	WebSocketObservable wssObserv = new WebSocketObservable();
	WssClient wssClient = new WssClient(wssObserv);
	public void setConfig()
	{
		
		AppConfig config = new AppConfig();
		config.setAppName("5P57201044");
		config.setAppVer("1.0");
		config.setOsName("WEB");
		config.setEncryptKey("huYC5GA05MzDAJVJpSbtinpkJXIpMzCS");
		config.setKey("mid3KQJFM6YFQ3SixJ3ooUxvInSLjY01");
		config.setLoginId("56565401");
		config.setUserId("GeUcdt8KE51");
		config.setPassword("lrBDcHCp2MN");
		
	}
	@Test
		public void wssFeed() throws IOException, ParseException {
		setConfig();
		System.out.println(" \n ************* LoginRequestV4  ************* \n");
		JSONObject obj2 = new JSONObject();
		//obj2.put("Email_id", AES.encrypt(properties.emailId));
		//obj2.put("My2PIN", AES.encrypt(properties.my2Pin));
		//obj2.put("Email_id", AES.encrypt(properties.emailId));
		//obj2.put("Password", AES.encrypt(properties.Password));
		//obj2.put("My2PIN", AES.encrypt(properties.my2Pin));
		obj2.put("HDSerialNumber", "1.0.16.0");
		obj2.put("MACAddress", "1.0.16.0");
		obj2.put("RequestNo", 1);
		obj2.put("ConnectionType", 1);
		obj2.put("MachineID","039377");
		obj2.put("VersionNo", "1.0");
		//System.out.println("\n JSON OBJ >> " + obj2.toJSONString());
		Response response = apis.LoginRequestV4(obj2);
		String resp=response.body().string();
		System.out.println("\n Response >> " + resp);
		String jwtToken ="";
		try{
			
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(resp);
			//System.out.println("Response Json"+jsonObject.get("body"));
			JSONParser bodyparser = new JSONParser();
			JSONObject bodyjsonObject = (JSONObject) bodyparser.parse(jsonObject.get("body").toString());
			jwtToken=(String)bodyjsonObject.get("JWTToken");
			System.out.println("\n JWT Token :  "+jwtToken);
			}catch(Exception e){e.printStackTrace();;}
		String ASPXAUTH_Cookie =checkLogin(jwtToken);	
		
		JSONObject markfeed = new JSONObject();
    	JSONObject markfeeddata = new JSONObject();
    	markfeeddata.put("Exch","N");
    	markfeeddata.put("ExchType","C");
    	markfeeddata.put("ScripCode",15083);
    	
    	JSONObject markfeeddata1 = new JSONObject();
    	markfeeddata1.put("Exch","B");
    	markfeeddata1.put("ExchType","C");
    	markfeeddata1.put("ScripCode",999901);
    	
    	JSONObject markfeeddata2 = new JSONObject();
    	markfeeddata2.put("Exch","N");
    	markfeeddata2.put("ExchType","C");
    	markfeeddata2.put("ScripCode",22);
    	
    	JSONArray markfeeddatarray = new JSONArray();
    	markfeeddatarray.add(markfeeddata);
    	markfeeddatarray.add(markfeeddata1);
    	markfeeddatarray.add(markfeeddata2);
    	markfeed.put("Method","MarketFeedV3");
    	markfeed.put("Operation","Subscribe");
    	markfeed.put("ClientCode",properties.clientcode);
    	markfeed.put("MarketFeedData",markfeeddatarray);
    	wssClient.wssfeed(markfeed,ASPXAUTH_Cookie,jwtToken,properties.clientcode);
		assertTrue(response.isSuccessful());
		System.out.println("\n Connection established and Message send "+wssObserv.isMesgSend());
		System.out.println("\n Message Received on Socket "+wssObserv.isMesgReceived());
		assertTrue(wssObserv.isMesgSend());
		assertTrue(wssObserv.isMesgReceived());
		
	}


	public String checkLogin(String JWTToken) throws IOException, ParseException {
	System.out.println(" \n ************* checkLogin  ************* \n");
	JSONObject obj2 = new JSONObject();
	obj2.put("RegistrationID", JWTToken);
	
	String  axpauth_cookie = apis.loginCheck(obj2);
	return axpauth_cookie;
	//System.out.println("\n Response >> " + response.body().string());
	//assertTrue(response.isSuccessful());
    }
  
}