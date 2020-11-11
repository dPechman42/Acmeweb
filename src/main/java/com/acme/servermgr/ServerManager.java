package com.acme.servermgr;

/**
 * Manage all servers (service providers) being tracked by the Acme server tracking system
 * For now just some simple static methods for use in school project.
 * Treat this as a 'black box' that gives back indicators like up, running etc for
 * various 'services' that are being managed.
 */
public class ServerManager {

    /**
     * Get the status of this server
     * @return a descriptive string about the servers status
     */
    static public String getCurrentServerStatus() {
        return "up";  // The server is up
    }

    /**
     * Find out if this server is operating normally
     * @return Boolean indicating if server is operating normally
     */
    static public Boolean isOperatingNormally() {
        return true;
    }

    /**
     * Find out the memory status
     * @return a string saying what the status is
     */
    static public String getMemoryStatus(){
        return "Memory is running low";
    }

    /**
     * Find out what extensions are in play
     * @return the extensions in play
     */
    static public String getExtensions(){
        return "[Hypervisor, Kubernetes, RAID-6]";
    }
}
