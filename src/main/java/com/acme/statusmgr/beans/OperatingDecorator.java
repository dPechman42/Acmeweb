package com.acme.statusmgr.beans;

import com.acme.servermgr.ServerManager;

/**
 * this class adds details about the operations
 */
public class OperatingDecorator extends DecoratorServerStatus{

	private final ServerStatus serverStatus;

	public OperatingDecorator(ServerStatus serverStatus){
		super(serverStatus);
		this.serverStatus = serverStatus;
	}

	public String getStatusDesc() {
		String operations;
		if (ServerManager.isOperatingNormally()){
			operations = ", and is operating normally";
		} else {
			operations = ", and is not operating normally";
		}
		return serverStatus.getStatusDesc() + operations;
	}
}
