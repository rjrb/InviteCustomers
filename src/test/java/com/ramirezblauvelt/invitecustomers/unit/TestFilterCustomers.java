package com.ramirezblauvelt.invitecustomers.unit;

import com.ramirezblauvelt.invitecustomers.beans.Customer;
import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.services.FilterCustomers;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = {
		"application.earth-radius-km=6371",
		"application.range-within-km=100",
		"application.office-location.latitude=53.339428",
		"application.office-location.longitude=-6.257664"
	}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestFilterCustomers {

	@Autowired
	private FilterCustomers filterCustomers;

	@Test
	void testFilterCustomer() {
		final Customer customerFarAway = new Customer();
		customerFarAway.setUserID(1);
		customerFarAway.setName("Cerro Nutibara");
		customerFarAway.setGpsLocationDegrees(new GpsLocationDegrees(6.2348022, -75.5787825));

		Assertions
			.assertThat(filterCustomers.filterCustomersByDistanceToOffice(customerFarAway))
				.isFalse()
		;

		final Customer customerCloseBy = new Customer();
		customerCloseBy.setUserID(2);
		customerCloseBy.setName("Somewhere in Dublin");
		customerCloseBy.setGpsLocationDegrees(new GpsLocationDegrees(53.344105, -6.267494));

		Assertions
			.assertThat(filterCustomers.filterCustomersByDistanceToOffice(customerCloseBy))
				.isTrue()
		;
	}

}
