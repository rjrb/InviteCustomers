package com.ramirezblauvelt.invitecustomers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Path;

public class CustomerFileNotFoundException extends ResponseStatusException {

	public CustomerFileNotFoundException(Path filePath) {
		super(HttpStatus.INTERNAL_SERVER_ERROR , "Customer file not found in '" + filePath + "'");
	}

}
