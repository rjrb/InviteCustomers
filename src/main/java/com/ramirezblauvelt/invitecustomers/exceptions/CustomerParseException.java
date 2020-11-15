package com.ramirezblauvelt.invitecustomers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerParseException extends ResponseStatusException {

	public CustomerParseException(Throwable cause) {
		super(HttpStatus.BAD_REQUEST, "Error parsing JSON input string", cause);
	}

}
