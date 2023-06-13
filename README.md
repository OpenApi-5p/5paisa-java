5Paisa Open API's: Java

## Installation

Download the latest jar file from builds

Install the jar file in your Maven project
```
mvn install:install-file -Dfile=src/main/resources/FivePaisa-0.0.2-SNAPSHOT.jar -DgroupId=com.FivePaisa -DartifactId=FivePaisa -Dversion=0.0.2-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
```
Add the dependencies in pom: 
<dependency>
           <groupId>com.FivePaisa</groupId>
   	    		<artifactId>FivePaisa</artifactId>
           <version>0.0.2-SNAPSHOT</version>
</dependency>

## Usage


### Setting keys

```
AppConfig config = new AppConfig();
config.setAppName("Your app name");// eg 5P12345678
config.setAppVer("1.0");
config.setOsName("WEB");
config.setEncryptKey("Your encryption key"); // eg. ABCDEFGHIJKLMNOPQURSTUVWXY
config.setKey("Your user key");// eg ABCDEFGHIJKLMNOPQURSTUVWXYZANCDEF
config.setUserId("Your user id");
config.setPassword("Your password");// eg. ABCDEFGHIJK
config.setLoginId("Your client code");

Properties properties = new Properties();
properties.setClientcode("Your client Code");
```

### Authentication
Just call the below method and JWT token get stored in memory for all other API calls

```
String response = apis.getTotpSession(properties.getClientcode(), "Your TOTP code", "Your PIN");
```
### Placing orderrequest

```
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
```
### Sample code
Sample code is available in Example.java in example folder.