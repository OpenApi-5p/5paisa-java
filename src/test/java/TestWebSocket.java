
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import com.FivePaisa.api.RestClient;
import com.FivePaisa.api.WebSocketObservable;
import com.FivePaisa.api.WssClient;
import com.FivePaisa.config.AppConfig;
import com.FivePaisa.service.Properties;

import okhttp3.Response;

public class TestWebSocket {

    AppConfig config = new AppConfig();
    Properties properties = new Properties();

    RestClient apis = new RestClient(config, properties);
    JSONParser parser = new JSONParser();
    WebSocketObservable wssObserv = new WebSocketObservable();
    WssClient wssClient = new WssClient(wssObserv);

    public void setConfig() {

        config.setAppName("Your app name");// eg 5P12345678
        config.setAppVer("1.0");
        config.setOsName("WEB");
        config.setEncryptKey("Your encryption key"); // eg.ABCDEFGHIJKLMNOPQURSTUVWXYZANCDE
        config.setKey("Your user key");// eg ABCDEFGHIJKLMNOPQURSTUVWXYZANCDEF
        config.setUserId("Your user id");
        config.setPassword("Your password");// eg. ABCDEFGHIJK
        config.setLoginId("Your client code");// eg 12345678

        properties.setClientcode("Your client Code");// eg 12345678
    }

    @Test
    public void wssFeed() throws IOException, ParseException {
        setConfig();
        System.out.println(" \n ************* LoginRequestV4  ************* \n");
        JSONObject obj2 = new JSONObject();
        // obj2.put("Email_id", AES.encrypt(properties.emailId));
        // obj2.put("My2PIN", AES.encrypt(properties.my2Pin));
        // obj2.put("Email_id", AES.encrypt(properties.emailId));
        // obj2.put("Password", AES.encrypt(properties.Password));
        // obj2.put("My2PIN", AES.encrypt(properties.my2Pin));
        obj2.put("HDSerialNumber", "1.0.16.0");
        obj2.put("MACAddress", "1.0.16.0");
        obj2.put("RequestNo", 1);
        obj2.put("ConnectionType", 1);
        obj2.put("MachineID", "039377");
        obj2.put("VersionNo", "1.0");
        // System.out.println("\n JSON OBJ >> " + obj2.toJSONString());
        // Response response = apis.LoginRequestV4(obj2);
        String resp = apis.getTotpSession("Your client code", "Your TOTP", "Your PIN");
        System.out.println("\n Response >> " + resp);
        String jwtToken = "";
        try {

            JSONObject requestTokenObject = (JSONObject) JSONValue.parse(resp);
            requestTokenObject = (JSONObject) requestTokenObject.get("body");
            if ((Long) requestTokenObject.get("Status") == 0) {
                jwtToken = (String) requestTokenObject.get("AccessToken");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
        String ASPXAUTH_Cookie = "";
        // checkLogin(jwtToken);

        JSONObject markfeed = new JSONObject();
        JSONObject markfeeddata = new JSONObject();
        markfeeddata.put("Exch", "N");
        markfeeddata.put("ExchType", "C");
        markfeeddata.put("ScripCode", 15083);

        JSONObject markfeeddata1 = new JSONObject();
        markfeeddata1.put("Exch", "B");
        markfeeddata1.put("ExchType", "C");
        markfeeddata1.put("ScripCode", 999901);

        JSONObject markfeeddata2 = new JSONObject();
        markfeeddata2.put("Exch", "N");
        markfeeddata2.put("ExchType", "C");
        markfeeddata2.put("ScripCode", 22);

        JSONArray markfeeddatarray = new JSONArray();
        markfeeddatarray.add(markfeeddata);
        markfeeddatarray.add(markfeeddata1);
        markfeeddatarray.add(markfeeddata2);
        markfeed.put("Method", "MarketFeedV3");
        markfeed.put("Operation", "Subscribe");
        markfeed.put("ClientCode", properties.clientcode);
        markfeed.put("MarketFeedData", markfeeddatarray);
        wssClient.wssfeed(markfeed, ASPXAUTH_Cookie, jwtToken, properties.clientcode);
        // assertTrue(response.isSuccessful());
        System.out.println("\n Connection established and Message send " + wssObserv.isMesgSend());
        System.out.println("\n Message Received on Socket " + wssObserv.isMesgReceived());
        assertTrue(wssObserv.isMesgSend());
        assertTrue(wssObserv.isMesgReceived());

    }

    public String checkLogin(String JWTToken) throws IOException, ParseException {
        System.out.println(" \n ************* checkLogin  ************* \n");
        JSONObject obj2 = new JSONObject();
        obj2.put("RegistrationID", JWTToken);

        String axpauth_cookie = apis.loginCheck(obj2);
        return axpauth_cookie;
        // System.out.println("\n Response >> " + response.body().string());
        // assertTrue(response.isSuccessful());
    }

}
