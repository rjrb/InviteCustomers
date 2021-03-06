package com.ramirezblauvelt.invitecustomers.unit;

import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import com.ramirezblauvelt.invitecustomers.services.LoadCustomerFile;
import com.ramirezblauvelt.invitecustomers.services.ReadAndParseCustomerFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Path;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestReadAndParseCustomerFile {

	@MockBean
	private LoadCustomerFile loadCustomerFile;

	@Autowired
	private ReadAndParseCustomerFile readAndParseCustomerFile;

	@Test
	void testReadCustomerFile() {
		final List<String> testCustomers = List.of(
			"{\"user_id\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.2348022\", \"longitude\": \"-75.5787825\"}",
			"{\"user_id\": 2, \"name\": \"UPB\", \"latitude\": \"6.245377\", \"longitude\": \"-75.5928549\"}"
		);

		BDDMockito
			.given(loadCustomerFile.readFile(ArgumentMatchers.any(Path.class)))
			.willReturn(testCustomers)
		;

		final CustomerInput expectedCustomer1 = new CustomerInput();
		expectedCustomer1.setUserID(1);
		expectedCustomer1.setName("Cerro Nutibara");
		expectedCustomer1.setLatitude("6.2348022");
		expectedCustomer1.setLongitude("-75.5787825");

		final CustomerInput expectedCustomer2 = new CustomerInput();
		expectedCustomer2.setUserID(2);
		expectedCustomer2.setName("UPB");
		expectedCustomer2.setLatitude("6.245377");
		expectedCustomer2.setLongitude("-75.5928549");

		Assertions
			.assertThat(readAndParseCustomerFile.readCustomerFile())
				.isNotEmpty()
				.hasSize(2)
				.containsExactlyInAnyOrder(expectedCustomer1, expectedCustomer2)
		;
	}

}
