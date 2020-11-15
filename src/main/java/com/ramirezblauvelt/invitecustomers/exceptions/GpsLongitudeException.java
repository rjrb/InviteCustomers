package com.ramirezblauvelt.invitecustomers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GpsLongitudeException extends ResponseStatusException {

	public GpsLongitudeException(double value) {
		super(HttpStatus.UNPROCESSABLE_ENTITY, "Longitudes can only range between -180 to 180 degrees. Input: " + value);
	}

}
