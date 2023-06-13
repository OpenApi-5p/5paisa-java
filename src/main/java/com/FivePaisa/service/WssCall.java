package com.FivePaisa.service;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.FivePaisa.api.WebSocketObservable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WssCall {
	String wssUrl = "wss://openfeed.5paisa.com/Feeds/api/chat?Value1=";
	String ASPXAUTH_Cookie;
	// Properties pr = new Properties();

	public void wssCall(JSONObject requestbody, String cookie_ASPXAUTH, String jwtToken, String clientcode,
			WebSocketObservable wssObser) throws IOException, ParseException {
		System.out.println(" \n ************* Web Socket Call Initiated  ************* \n");
		String wssurl_1 = wssUrl + jwtToken + "|" + clientcode;
		System.out.println("\n WSS URL>> " + wssurl_1);

		OkHttpClient client = null;

		Request request = new Request.Builder()
				.url(wssurl_1)
				// .addHeader("Cookie", ".ASPXAUTH=" + cookie_ASPXAUTH)
				.build();

		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		client = builder.build();

		WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {

			private static final int NORMAL_CLOSURE_STATUS = 1000;

			@Override
			public void onOpen(WebSocket webSocket, Response response) {
				System.out.println("\n WebSockets" + "Connection accepted!");

				System.out.println("\n Request Send>> " + requestbody);
				webSocket.send(requestbody.toJSONString());
				wssObser.setMesgSend(true);

			}

			@Override
			public void onMessage(WebSocket webSocket, String text) {
				System.out.println("\n onMessage Receiving >> :" + text);
				wssObser.setMesgReceived(true);
			}

			@Override
			public void onMessage(WebSocket webSocket, okio.ByteString bytes) {
				System.out.println("onMessage Receiving bytes>> :" + bytes.hex());

			}

			@Override
			public void onClosing(WebSocket webSocket, int code, String reason) {
				System.out.println("onClosing :" + code + "reason :" + reason);
				webSocket.close(NORMAL_CLOSURE_STATUS, null);

			}

			@Override
			public void onFailure(WebSocket webSocket, Throwable t, Response response) {
				try {
					System.out.println("onFailure >> :" + t.getMessage());
				} catch (Exception e) {

					e.printStackTrace();
				}

			}

			@Override
			public void onClosed(WebSocket webSocket, int arg0, String arg1) {

				System.out.println("onClosed :" + arg0 + ":" + arg1);
			}

		});

		try {
			Thread.sleep(Properties.threadTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.dispatcher().executorService().shutdown();
	}

}
