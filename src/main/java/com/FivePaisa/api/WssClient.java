package com.FivePaisa.api;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.FivePaisa.service.WssCall;

public class WssClient {

	WebSocketObservable wssObser;

	public WssClient(WebSocketObservable wssObser) {

		this.wssObser = wssObser;
	}

	// Properties pr = new Properties();
	WssCall wss = new WssCall();

	public void wssfeed(JSONObject requestBody, String cookie_ASPXAUTH, String jwtToken, String clientcode)
			throws IOException, ParseException {
		wss.wssCall(requestBody, cookie_ASPXAUTH, jwtToken, clientcode, wssObser);
	}

}
