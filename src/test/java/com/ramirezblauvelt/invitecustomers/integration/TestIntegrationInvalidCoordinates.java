package com.ramirezblauvelt.invitecustomers.integration;

import com.ramirezblauvelt.invitecustomers.exceptions.GpsLatitudeException;
import com.ramirezblauvelt.invitecustomers.exceptions.ParseCustomerInputException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = {
		"application.customer-list.path=src/test/resources/test-customers-invalid-latitude.txt",
		"application.earth-radius-km=6371",
		"application.range-within-km=100",
		"application.office-location.latitude=53.339428",
		"application.office-location.longitude=-6.257664"
	}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class TestIntegrationInvalidCoordinates {

	private final MockMvc mvc;

	@Autowired
	public TestIntegrationInvalidCoordinates(MockMvc mvc) {
		this.mvc = mvc;
	}

	@Test
	void testIntegrationInvalidJsonStructure() throws Exception {
		final Exception exceptionGet = mvc.perform(get("/invite"))
			.andExpect(status().isUnprocessableEntity())
			.andReturn().getResolvedException()
		;

		Assertions
			.assertThat(exceptionGet)
				.isInstanceOf(GpsLatitudeException.class)
				.hasMessageContaining("Latitudes can only range between -90 to 90 degrees")
		;
	}

}
