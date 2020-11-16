package com.ramirezblauvelt.invitecustomers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoCustomersToInvite extends ResponseStatusException {

	public NoCustomersToInvite() {
		super(HttpStatus.NOT_FOUND, "No customers to invite found within the range");
	}

}
