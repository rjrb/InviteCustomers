package com.ramirezblauvelt.invitecustomers;

import com.ramirezblauvelt.invitecustomers.beans.Customer;
import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.services.ParseCustomerData;
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
public class TestParseCustomerData {

	private final ParseCustomerData parseCustomerData;

	@Autowired
	public TestParseCustomerData(ParseCustomerData parseCustomerData) {
		this.parseCustomerData = parseCustomerData;
	}

	@Test
	void testParseData() {
		final String testCustomer = "{\"user_id\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.2348022\", \"longitude\": \"-75.5787825\"}";

		final CustomerInput expectedCustomer = new CustomerInput();
		expectedCustomer.setUserID(1);
		expectedCustomer.setName("Cerro Nutibara");
		expectedCustomer.setLatitude("6.2348022");
		expectedCustomer.setLongitude("-75.5787825");

		Assertions
			.assertThat(parseCustomerData.parseInputData(testCustomer))
			.withFailMessage("Failed to parse customer data")
				.isNotNull()
				.hasFieldOrPropertyWithValue("userID", expectedCustomer.getUserID())
				.hasFieldOrPropertyWithValue("name", expectedCustomer.getName())
				.hasFieldOrPropertyWithValue("latitude", expectedCustomer.getLatitude())
				.hasFieldOrPropertyWithValue("longitude", expectedCustomer.getLongitude())
		;
	}

	@Test
	void testParseDataExceptions() {
		Assertions
			.assertThatThrownBy(() -> parseCustomerData.parseInputData(null))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Error parsing JSON input string")
		;

		Assertions
			.assertThatThrownBy(() -> parseCustomerData.parseInputData(""))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Error parsing JSON input string")
		;

		Assertions
			.assertThatThrownBy(() -> parseCustomerData.parseInputData("foo"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Error parsing JSON input string")
		;

		final String testBadProperty = "{\"user\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.2348022\", \"longitude\": \"-75.5787825\"}";
		Assertions
			.assertThatThrownBy(() -> parseCustomerData.parseInputData(testBadProperty))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Error parsing JSON input string")
		;

		final String testExtraProperty = "{\"foo\": \"foo\", \"user_id\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.2348022\", \"longitude\": \"-75.5787825\"}";
		Assertions
			.assertThatThrownBy(() -> parseCustomerData.parseInputData(testExtraProperty))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Error parsing JSON input string")
		;
	}

	@Test
	void testParseCustomerInput() {
		final CustomerInput customerInput = new CustomerInput();
		customerInput.setUserID(1);
		customerInput.setName("Cerro Nutibara");
		customerInput.setLatitude("6.2348022");
		customerInput.setLongitude("-75.5787825");

		final Customer expectedCustomer = new Customer();
		expectedCustomer.setUserID(1);
		expectedCustomer.setName("Cerro Nutibara");
		expectedCustomer.setGpsLocationDegrees(new GpsLocationDegrees(6.2348022, -75.5787825));

		Assertions
			.assertThat(parseCustomerData.parseCustomerInput(customerInput))
				.hasFieldOrPropertyWithValue("userID", expectedCustomer.getUserID())
				.hasFieldOrPropertyWithValue("name", expectedCustomer.getName())
				.hasFieldOrPropertyWithValue("gpsLocationDegrees", expectedCustomer.getGpsLocationDegrees())
		;
	}

	@Test
	void testParseCustomerInputExceptions() {
		final CustomerInput customerWithBadLocation = new CustomerInput();
		customerWithBadLocation.setUserID(99);
		customerWithBadLocation.setName("Anywhere");
		customerWithBadLocation.setLatitude("Some text");
		customerWithBadLocation.setLongitude("-75.5787825");

		Assertions
			.assertThatThrownBy(() -> parseCustomerData.parseCustomerInput(customerWithBadLocation))
				.hasMessageContaining("Error parsing customer's GPS location")
		;

		final CustomerInput customerWithIllegalLatitude = new CustomerInput();
		customerWithIllegalLatitude.setUserID(98);
		customerWithIllegalLatitude.setName("Anywhere");
		customerWithIllegalLatitude.setLatitude("91");
		customerWithIllegalLatitude.setLongitude("-75.5787825");

		Assertions
			.assertThatThrownBy(() -> parseCustomerData.parseCustomerInput(customerWithIllegalLatitude))
				.hasMessageContaining("Error validating customer's GPS location")
		;

		final CustomerInput customerWithIllegalLongitude = new CustomerInput();
		customerWithIllegalLongitude.setUserID(98);
		customerWithIllegalLongitude.setName("Anywhere");
		customerWithIllegalLongitude.setLatitude("0");
		customerWithIllegalLongitude.setLongitude("-181");

		Assertions
			.assertThatThrownBy(() -> parseCustomerData.parseCustomerInput(customerWithIllegalLongitude))
				.hasMessageContaining("Error validating customer's GPS location")
		;
	}

}
