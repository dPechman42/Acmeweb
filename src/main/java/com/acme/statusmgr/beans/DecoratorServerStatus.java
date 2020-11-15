package com.acme.statusmgr.beans;


public abstract class DecoratorServerStatus extends ServerStatus{

	private final ServerStatus serverStatus;

	public DecoratorServerStatus(ServerStatus serverStatus){
		this.serverStatus = serverStatus;
	}

	public long getId() {
		return serverStatus.getId();
	}

	public String getContentHeader() {

		return serverStatus.getContentHeader();
	}

	public String getStatusDesc() {
		return serverStatus.getStatusDesc();
	}

}
