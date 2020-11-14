package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import org.springframework.stereotype.Service;

@Service
public class ValidateCoordinates {

	public GpsLocationDegrees validate(GpsLocationDegrees gpsLocationDegrees) {
		if(Math.abs(gpsLocationDegrees.getLatitude()) > 90) {
			throw new IllegalArgumentException("Latitudes can only range between -90 to 90 degrees");
		}

		if(Math.abs(gpsLocationDegrees.getLongitude()) > 180) {
			throw new IllegalArgumentException("Longitudes can only range between -180 to 180 degrees");
		}

		return gpsLocationDegrees;
	}

}
