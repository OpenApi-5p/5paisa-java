package com.FivePaisa.api;

import com.FivePaisa.service.ApiCalls;
import com.FivePaisa.service.Properties;
import com.FivePaisa.service.WssCall;

import com.FivePaisa.config.AppConfig;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class RestClient {

	private String loginRequest_V3 = "V3/LoginRequestMobileNewbyEmail";
	private String loginRequest_V4 = "V4/LoginRequestMobileNewbyEmail";
	private String holding = "V2/Holding";
	private String marketFeed = "MarketFeed";
	private String orderRequest = "V1/OrderRequest";
	private String orderStatus = "OrderStatus";
	private String tradeInformation = "TradeInformation";
	private String margin = "V3/Margin";
	private String orderBook = "V2/OrderBook";
	private String netPositionNetWise = "V1/NetPositionNetWise";
	private String smoOrderRequest = "SMOOrderRequest";
	private String modifySmoOrder = "5PSModMOOrd";
	private String loginCheck = "LoginCheck";
	
	
	JSONParser parser = new JSONParser();
	ApiCalls ac = new ApiCalls();
	Properties pr = new Properties();
	AppConfig config;
	
	public RestClient(AppConfig config){this.config=config;}

	public String loginCheck(JSONObject requestBody) throws IOException, ParseException {
		return ac.callCheckLogin(requestBody, loginCheck, pr.requestCodeLoginCheck,config);
	}

	public Response smoOrderRequest(JSONObject requestBody) throws IOException, ParseException {
		return ac.callWithCookies(requestBody, smoOrderRequest, pr.requestCodeSmoOrderReq,config);
	}
	public Response modifySmoOrder(JSONObject requestBody) throws IOException, ParseException {
		return ac.callWithCookies(requestBody, modifySmoOrder, pr.requestCodeModifySmoOrder,config);
	}
	public Response LoginRequestV2(JSONObject requestBody) throws IOException, ParseException {
		return ac.callLogin(requestBody, loginRequest_V3,pr.requestCodeLoginv3,config);
	}
	public Response LoginRequestV4(JSONObject requestBody) throws IOException, ParseException {
		return ac.callLogin(requestBody, loginRequest_V4,pr.requestCodeLoginv4,config);
	}

	public Response OrderRequest(JSONObject requestBody) throws IOException, ParseException {
		return ac.OrdReqCall(requestBody, orderRequest, pr.orderRequest,config);
	}

	public Response HoldingV2(JSONObject requestBody) throws IOException, ParseException {
		return ac.callWithCookies(requestBody, holding, pr.holding,config);
	}

	public Response MarketFeed(JSONObject requestBody) throws IOException, ParseException {
		return ac.callWithCookies((requestBody), marketFeed, pr.marketFeed,config);
	}

	public Response OrderStatus(JSONObject requestBody) throws IOException, ParseException {
		return ac.callWithCookies(requestBody, orderStatus, pr.orderStatus,config);
	}

	public Response TradeInformation(JSONObject requestBody) throws IOException, ParseException {
		return ac.callWithCookies(requestBody, tradeInformation, pr.tradeInformation,config);
	}

	public Response MarginV3(JSONObject requestBody) throws IOException, ParseException {
		return ac.callWithCookies(requestBody, margin, pr.margin,config);
	}

	public Response OrderBookV2(JSONObject requestBody) throws IOException, ParseException {
		return ac.callWithCookies(requestBody, orderBook, pr.orderBook,config);
	}

	public Response NetPositionNetWiseV1(JSONObject requestBody) throws IOException, ParseException {
		return ac.callWithCookies(requestBody, netPositionNetWise, pr.netPositionNetWise,config);
	}

}
