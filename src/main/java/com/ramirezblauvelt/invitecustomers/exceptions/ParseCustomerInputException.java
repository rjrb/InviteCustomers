package com.ramirezblauvelt.invitecustomers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ParseCustomerInputException extends ResponseStatusException {

	public ParseCustomerInputException(Throwable cause) {
		super(HttpStatus.UNPROCESSABLE_ENTITY, "Error parsing customer's GPS location", cause);
	}

}
