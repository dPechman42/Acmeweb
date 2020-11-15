package com.acme.statusmgr.beans;

import com.acme.servermgr.ServerManager;

/**
 * This class adds details about memory
 */
public class MemoryDecorator extends DecoratorServerStatus{

	private final ServerStatus serverStatus;

	public MemoryDecorator(ServerStatus serverStatus){
		super(serverStatus);
		this.serverStatus = serverStatus;
	}

	/**
	 * Adds memory details to the status
	 * @return the status with memory added
	 */
	public String getStatusDesc() {
		return serverStatus.getStatusDesc() +
				", and its " +
				ServerManager.getMemoryStatus();
	}
}
