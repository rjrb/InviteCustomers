package com.ramirezblauvelt.invitecustomers.beans;

import com.ramirezblauvelt.invitecustomers.config.ApplicationProperties;
import lombok.EqualsAndHashCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EqualsAndHashCode(callSuper = false)
public class OfficeGpsLocationDegrees extends GpsLocationDegrees {

	@Bean("officeLocation")
	public OfficeGpsLocationDegrees getOfficeLocation(ApplicationProperties applicationProperties) {
		setLatitude(applicationProperties.getOfficeLocation().getLatitude());
		setLongitude(applicationProperties.getOfficeLocation().getLongitude());
		return this;
	}

}
