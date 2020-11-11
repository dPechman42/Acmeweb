package com.acme.statusmgr;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.acme.statusmgr.beans.ExtensionsDecorator;
import com.acme.statusmgr.beans.MemoryDecorator;
import com.acme.statusmgr.beans.OperatingDecorator;
import com.acme.statusmgr.beans.ServerStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for all web/REST requests about the status of servers
 *
 * For initial school project - just handles info about this server
 * Syntax for URLS:
 *    All start with /server
 *    /status  will give back status of server
 *    a param of 'name' specifies a requestor name to appear in response
 *
 * Examples:
 *    http://localhost:8080/server/status
 *
 *    http://localhost:8080/server/status?name=Noach
 *
 *
 *
 */
@RestController
@RequestMapping("/server")
public class StatusController {

    protected static final String template = "Server Status requested by %s";
    protected final AtomicLong counter = new AtomicLong();


    
    @RequestMapping("/status")
    public ServerStatus getServerStatus(@RequestParam(value="name", defaultValue="Anonymous") String name,
            @RequestParam(required = false) List<String> details) {
        System.out.println("*** DEBUG INFO *** " + details);
        return new ServerStatus(counter.incrementAndGet(),
                            String.format(template, name));
    }

    /**
     * gets details of the server
     * @param name the name of the person requesting the data
     * @param details the details the user wants to know
     * @return a detailed server status
     */
    @RequestMapping("/status/detailed")
    public ServerStatus getDetailedServerStatus(@RequestParam(value="name", defaultValue="Anonymous") String name,
            @RequestParam List<String> details) {
        ServerStatus serverStatus = new ServerStatus(counter.incrementAndGet(), String.format(template, name));
        for (String detail : details){
            serverStatus = decorate(serverStatus, detail);
        }
        return serverStatus;
    }

    public ServerStatus decorate(ServerStatus serverStatus, String detail){
        switch (detail){
            case "extensions":
                return new ExtensionsDecorator(serverStatus);
            case "memory":
                return new MemoryDecorator(serverStatus);
            case "operations":
                return new OperatingDecorator(serverStatus);
            default:
                throw new HandleBadRequest();
        }
    }
}
