package com.ramirezblauvelt.invitecustomers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerFileEmpty extends ResponseStatusException {

	public CustomerFileEmpty() {
		super(HttpStatus.INTERNAL_SERVER_ERROR, "Customer file is empty");
	}

}
