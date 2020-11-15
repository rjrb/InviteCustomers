package com.ramirezblauvelt.invitecustomers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GpsLatitudeException extends ResponseStatusException {

	public GpsLatitudeException(double value) {
		super(HttpStatus.UNPROCESSABLE_ENTITY, "Latitudes can only range between -90 to 90 degrees. Input: " + value);
	}

}
