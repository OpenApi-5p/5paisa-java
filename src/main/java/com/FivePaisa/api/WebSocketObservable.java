package com.FivePaisa.api;

public class WebSocketObservable {
	
	
	boolean mesgSend =false;
	boolean mesgReceived =false;
	public boolean isMesgSend() {
		return mesgSend;
	}
	public void setMesgSend(boolean mesgSend) {
		this.mesgSend = mesgSend;
	}
	public boolean isMesgReceived() {
		return mesgReceived;
	}
	public void setMesgReceived(boolean mesgReceived) {
		this.mesgReceived = mesgReceived;
	}
	
	

}
