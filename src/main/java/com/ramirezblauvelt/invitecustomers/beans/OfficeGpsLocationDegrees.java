package com.ramirezblauvelt.invitecustomers.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfficeGpsLocationDegrees extends GpsLocationDegrees {

	@Value("${application.office-location.latitude}")
	private double officeLatitude;

	@Value("${application.office-location.longitude}")
	private double officeLongitude;

	@Bean("officeLocation")
	public OfficeGpsLocationDegrees getOfficeLocation() {
		setLatitude(officeLatitude);
		setLongitude(officeLongitude);
		return this;
	}

}
