package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.exceptions.GpsLatitudeException;
import com.ramirezblauvelt.invitecustomers.exceptions.GpsLongitudeException;
import org.springframework.stereotype.Service;

@Service
public class ValidateCoordinates {

	public GpsLocationDegrees validate(GpsLocationDegrees gpsLocationDegrees) {
		if(Math.abs(gpsLocationDegrees.getLatitude()) > 90) {
			throw new GpsLatitudeException(gpsLocationDegrees.getLatitude());
		}

		if(Math.abs(gpsLocationDegrees.getLongitude()) > 180) {
			throw new GpsLongitudeException(gpsLocationDegrees.getLongitude());
		}

		return gpsLocationDegrees;
	}

}
