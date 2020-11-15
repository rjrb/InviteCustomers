package com.ramirezblauvelt.invitecustomers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerFileReadException extends ResponseStatusException {

	public CustomerFileReadException(Throwable cause) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading the customer file", cause);
	}

}
