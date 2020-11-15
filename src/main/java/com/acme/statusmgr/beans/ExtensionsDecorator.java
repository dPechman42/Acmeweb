package com.acme.statusmgr.beans;

import com.acme.servermgr.ServerManager;

/**
 * This class adds details about the extensions
 */
public class ExtensionsDecorator extends DecoratorServerStatus{

	private final ServerStatus serverStatus;

	public ExtensionsDecorator(ServerStatus serverStatus){
		super(serverStatus);
		this.serverStatus = serverStatus;
	}

	/**
	 * add extensions to the status
	 * @return the status with the extensions added
	 */
	public String getStatusDesc() {
		return serverStatus.getStatusDesc() +
				", and is using these extensions - " +
				ServerManager.getExtensions();
	}

}
