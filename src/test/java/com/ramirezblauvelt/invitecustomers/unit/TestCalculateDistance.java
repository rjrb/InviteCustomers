package com.ramirezblauvelt.invitecustomers.unit;

import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.services.CalculateDistance;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestCalculateDistance {

	/** Default radius for testing purposes (earth median radius) */
	private static final double EARTH_RADIUS = 6371;

	@Autowired
	private CalculateDistance calculateDistance;

	@Test
	void testCalculateDistance() {
		final SoftAssertions softAssertions = new SoftAssertions();

		final GpsLocationDegrees london = new GpsLocationDegrees();
		london.setLatitude(51.500153);
		london.setLongitude(-0.126236);

		final GpsLocationDegrees dublin = new GpsLocationDegrees();
		dublin.setLatitude(53.344105);
		dublin.setLongitude(-6.267494);

		softAssertions
			.assertThat(calculateDistance.calculateDistance(london, dublin, EARTH_RADIUS))
				.withFailMessage("Failed to calculate the distance from London to Dublin")
				.isCloseTo(464, Percentage.withPercentage(0.5))
		;

		final GpsLocationDegrees newYork = new GpsLocationDegrees();
		newYork.setLatitude(40.714270);
		newYork.setLongitude(-74.005970);

		final GpsLocationDegrees sanFrancisco = new GpsLocationDegrees();
		sanFrancisco.setLatitude(37.774930);
		sanFrancisco.setLongitude(-122.419420);

		softAssertions
			.assertThat(calculateDistance.calculateDistance(newYork, sanFrancisco, EARTH_RADIUS))
				.withFailMessage("Failed to calculate the distance from New York to San Francisco")
				.isCloseTo(4130, Percentage.withPercentage(0.5))
		;

		softAssertions.assertAll();
	}

}
