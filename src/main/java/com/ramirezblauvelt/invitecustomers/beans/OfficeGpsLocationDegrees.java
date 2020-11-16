package com.ramirezblauvelt.invitecustomers.beans;

import com.ramirezblauvelt.invitecustomers.services.ValidateCoordinates;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EqualsAndHashCode(callSuper = false)
public class OfficeGpsLocationDegrees extends GpsLocationDegrees {

	@Value("${application.office-location.latitude}")
	private double officeLatitude;

	@Value("${application.office-location.longitude}")
	private double officeLongitude;

	@Autowired
	private ValidateCoordinates validateCoordinates;

	@Bean("officeLocation")
	public OfficeGpsLocationDegrees getOfficeLocation() {
		validateCoordinates.validate(new GpsLocationDegrees(officeLatitude, officeLongitude));
		setLatitude(officeLatitude);
		setLongitude(officeLongitude);
		return this;
	}

}
