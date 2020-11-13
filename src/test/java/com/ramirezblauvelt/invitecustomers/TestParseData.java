package com.ramirezblauvelt.invitecustomers;

import com.ramirezblauvelt.invitecustomers.beans.Customer;
import com.ramirezblauvelt.invitecustomers.services.ParseData;
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
public class TestParseData {

	private final ParseData parseData;

	@Autowired
	public TestParseData(ParseData parseData) {
		this.parseData = parseData;
	}

	@Test
	void testParseData() {
		final String testCustomer = "{\"user_id\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.21685669257658\", \"longitude\": \"-75.55700248857622\"}";

		final Customer expectedCustomer = new Customer();
		expectedCustomer.setUserID(1);
		expectedCustomer.setName("Cerro Nutibara");
		expectedCustomer.setLatitude("6.21685669257658");
		expectedCustomer.setLongitude("-75.55700248857622");

		Assertions
			.assertThat(parseData.parseData(testCustomer))
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
			.assertThatThrownBy(() -> parseData.parseData(null))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Error parsing JSON input string")
		;

		Assertions
			.assertThatThrownBy(() -> parseData.parseData(""))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Error parsing JSON input string")
		;

		Assertions
			.assertThatThrownBy(() -> parseData.parseData("foo"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Error parsing JSON input string")
		;

		final String testBadProperty = "{\"user\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.21685669257658\", \"longitude\": \"-75.55700248857622\"}";
		Assertions
			.assertThatThrownBy(() -> parseData.parseData(testBadProperty))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Error parsing JSON input string")
		;

		final String testExtraProperty = "{\"foo\": \"foo\", \"user_id\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.21685669257658\", \"longitude\": \"-75.55700248857622\"}";
		Assertions
			.assertThatThrownBy(() -> parseData.parseData(testExtraProperty))
			.isInstanceOf(RuntimeException.class)
			.hasMessageContaining("Error parsing JSON input string")
		;
	}

}
