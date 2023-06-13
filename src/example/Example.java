
import org.json.simple.JSONObject;

import com.FivePaisa.api.RestClient;
import com.FivePaisa.config.AppConfig;
import com.FivePaisa.service.Properties;

import okhttp3.Response;

class Test {

    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        config.setAppName("Your app name");// eg 5P12345678
        config.setAppVer("1.0");
        config.setOsName("WEB");
        config.setEncryptKey("Your encryption key"); // eg. ABCDEFGHIJKLMNOPQURSTUVWXY
        config.setKey("Your user key");// eg ABCDEFGHIJKLMNOPQURSTUVWXYZANCDEF
        config.setUserId("Your user id");
        config.setPassword("Your password");// eg. ABCDEFGHIJK
        config.setLoginId("Your client code");// eg 12345678

        Properties properties = new Properties();
        properties.setClientcode("Your client Code");// eg 12345678

        RestClient apis = new RestClient(config, properties);
        try {
            String response = apis.getTotpSession(properties.getClientcode(),
                    "Your TOTP code", "Your PIN");

            System.out.println("\n Response >> " + response);

            System.out.println(" \n ************* OrderRequest  ************* \n");

            orderRequest(config, properties, apis);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void getOrderBook(AppConfig config, Properties properties, RestClient apis) throws Exception {
        System.out.println(" \n ************* OrderBookV2  ************* \n");
        JSONObject obj3 = new JSONObject();
        obj3.put("ClientCode", properties.clientcode);
        Response response = apis.orderBookV2(obj3);
        System.out.println("\n Response >> " + response.body().string());
    }

    public static void orderRequest(AppConfig config, Properties properties, RestClient apis) throws Exception {
        JSONObject obj3 = new JSONObject();
        obj3.put("ClientCode", properties.clientcode);
        obj3.put("OrderFor", "P");// P - New Order placed, M - Modify, C- Cancel
        obj3.put("Exchange", "N");// N- NSE, B-BSE, M - MCX
        obj3.put("ExchangeType", "D");// D - Derivative, C - Cash, U - Currency
        obj3.put("ScripCode", 35019);
        obj3.put("Price", 0);// 0 - For market order
        obj3.put("OrderID", 2);
        obj3.put("OrderType", "BUY");// BUY/SELL
        obj3.put("Qty", 50);// For NSE FUT Qty=Lotsize*Lot ie for 1lot Qty=50
        obj3.put("OrderDateTime", "/Date(1563857357612)/");// Current time
        obj3.put("AtMarket", true);
        obj3.put("RemoteOrderID", "s0002201907231019172");
        obj3.put("ExchOrderID", 0);// 0 - for new order, Actual value for modify and cancel
        obj3.put("DisQty", 0);// Disclosed quantity
        obj3.put("IsStopLossOrder", false);
        obj3.put("StopLossPrice", 0);// 0 - For at market stoploss price
        obj3.put("IsVTD", false);
        obj3.put("IOCOrder", false);
        obj3.put("IsIntraday", false);
        obj3.put("PublicIP", "192.168.84.215");
        obj3.put("AHPlaced", "N");// Y - For After hour orders
        obj3.put("ValidTillDate", "/Date(1600248018615)/");
        obj3.put("iOrderValidity", 0);
        obj3.put("OrderRequesterCode", properties.clientcode);
        obj3.put("TradedQty", 0);
        obj3.put("AppSource", properties.AppSource);
        Response response = apis.placeOrderRequest(obj3);
        String resp = response.body().string();
        System.out.println("\n Response =========>> " + resp);
    }

}
