package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.beans.GpsLocationRadians;
import org.springframework.stereotype.Service;

@Service
public class CalculateDistance {

	private final ValidateCoordinates validateCoordinates;

	public CalculateDistance(ValidateCoordinates validateCoordinates) {
		this.validateCoordinates = validateCoordinates;
	}

	public double calculateDistance(GpsLocationDegrees origin, GpsLocationDegrees destination, double earthRadius) {
		final GpsLocationRadians originInRadians = toRadians(validateCoordinates.validate(origin));
		final GpsLocationRadians destinationInRadians = toRadians(validateCoordinates.validate(destination));

		final double angleDelta = Math.acos(
			Math.sin(originInRadians.getLatitude()) * Math.sin(destinationInRadians.getLatitude())
			+ Math.cos(originInRadians.getLatitude()) * Math.cos(destinationInRadians.getLatitude()) * Math.cos(originInRadians.getLongitude() - destinationInRadians.getLongitude())
		);

		return earthRadius * angleDelta;
	}

	private GpsLocationRadians toRadians(GpsLocationDegrees gpsLocationDegrees) {
		return new GpsLocationRadians(
			Math.toRadians(gpsLocationDegrees.getLatitude()),
			Math.toRadians(gpsLocationDegrees.getLongitude())
		);
	}


}
