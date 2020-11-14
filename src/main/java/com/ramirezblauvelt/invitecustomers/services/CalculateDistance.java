package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.beans.GpsLocationRadians;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CalculateDistance {

	private final Logger logger = LoggerFactory.getLogger(CalculateDistance.class);
	private final ConvertGpsLocation convertGpsLocation;
	private final ValidateCoordinates validateCoordinates;

	public CalculateDistance(ConvertGpsLocation convertGpsLocation, ValidateCoordinates validateCoordinates) {
		this.convertGpsLocation = convertGpsLocation;
		this.validateCoordinates = validateCoordinates;
	}

	public double calculateDistance(GpsLocationDegrees origin, GpsLocationDegrees destination, double earthRadius) {
		final GpsLocationRadians originInRadians = convertGpsLocation.toRadians(validateCoordinates.validate(origin));
		final GpsLocationRadians destinationInRadians = convertGpsLocation.toRadians(validateCoordinates.validate(destination));

		final double angleDelta = Math.acos(
			Math.sin(originInRadians.getLatitude()) * Math.sin(destinationInRadians.getLatitude())
			+ Math.cos(originInRadians.getLatitude()) * Math.cos(destinationInRadians.getLatitude()) * Math.cos(originInRadians.getLongitude() - destinationInRadians.getLongitude())
		);

		return earthRadius * angleDelta;
	}

}
