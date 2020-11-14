package com.acme.statusmgr;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid details option: junkERROR")
public class HandleBadRequest extends RuntimeException{

	public HandleBadRequest(String message){
		super(message);
	}

}
