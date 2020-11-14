package com.ramirezblauvelt.invitecustomers;

import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.beans.GpsLocationRadians;
import com.ramirezblauvelt.invitecustomers.services.ConvertGpsLocation;
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
public class TestConvertGpsLocation {

	private final ConvertGpsLocation convertGpsLocation;

	@Autowired
	public TestConvertGpsLocation(ConvertGpsLocation convertGpsLocation) {
		this.convertGpsLocation = convertGpsLocation;
	}

	@Test
	void testConvertGpsLocation() {
		final GpsLocationDegrees londonInDegrees = new GpsLocationDegrees();
		londonInDegrees.setLatitude(51.500153);
		londonInDegrees.setLongitude(-0.126236);

		final GpsLocationRadians londonInRadians = new GpsLocationRadians();
		londonInRadians.setLatitude(0.8988472351308352);
		londonInRadians.setLongitude(-0.0022032338345475615);

		Assertions
			.assertThat(convertGpsLocation.toRadians(londonInDegrees))
				.isEqualTo(londonInRadians)
		;
	}

}
