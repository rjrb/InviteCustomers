package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.beans.GpsLocationRadians;
import org.springframework.stereotype.Service;

@Service
public class ConvertGpsLocation {

	public GpsLocationRadians toRadians(GpsLocationDegrees gpsLocationDegrees) {
		return new GpsLocationRadians(
			Math.toRadians(gpsLocationDegrees.getLatitude()),
			Math.toRadians(gpsLocationDegrees.getLongitude())
		);
	}

}
