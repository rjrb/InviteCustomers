package com.ramirezblauvelt.invitecustomers.unit;

import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.exceptions.GpsLatitudeException;
import com.ramirezblauvelt.invitecustomers.exceptions.GpsLongitudeException;
import com.ramirezblauvelt.invitecustomers.services.ValidateCoordinates;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestValidateCoordinates {

	private final ValidateCoordinates validateCoordinates;

	@Autowired
	public TestValidateCoordinates(ValidateCoordinates validateCoordinates) {
		this.validateCoordinates = validateCoordinates;
	}

	@Test
	void testValidateCoordinates() {
		final GpsLocationDegrees london = new GpsLocationDegrees();
		london.setLatitude(51.500153);
		london.setLongitude(-0.126236);

		Assertions
			.assertThat(validateCoordinates.validate(london))
				.isEqualTo(london)
		;
	}

	@Test
	void testValidateCoordinatesExceptions() {
		final GpsLocationDegrees badLatitude = new GpsLocationDegrees();
		badLatitude.setLatitude(100);
		badLatitude.setLongitude(0);

		Assertions
			.assertThatExceptionOfType(GpsLatitudeException.class)
			.isThrownBy(() -> validateCoordinates.validate(badLatitude))
			.withMessageContaining("Latitudes can only range between -90 to 90 degrees")
		;

		final GpsLocationDegrees badLongitude = new GpsLocationDegrees();
		badLongitude.setLatitude(0);
		badLongitude.setLongitude(200);

		Assertions
			.assertThatExceptionOfType(GpsLongitudeException.class)
			.isThrownBy(() -> validateCoordinates.validate(badLongitude))
			.withMessageContaining("Longitudes can only range between -180 to 180 degrees")
		;
	}

}
