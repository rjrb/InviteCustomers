package com.ramirezblauvelt.invitecustomers;

import com.ramirezblauvelt.invitecustomers.beans.Customer;
import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.services.ReadCustomerFile;
import com.ramirezblauvelt.invitecustomers.services.ReadCustomers;
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
public class TestReadCustomers {

	@MockBean
	private ReadCustomerFile readCustomerFile;

	private final ReadCustomers readCustomers;

	@Autowired
	public TestReadCustomers(ReadCustomers readCustomers) {
		this.readCustomers = readCustomers;
	}

	@Test
	void testReadCustomers() {
		final List<String> testCustomers = List.of(
			"{\"user_id\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.2348022\", \"longitude\": \"-75.5787825\"}",
			"{\"user_id\": 2, \"name\": \"UPB\", \"latitude\": \"6.245377\", \"longitude\": \"-75.5928549\"}"
		);

		BDDMockito
			.given(readCustomerFile.readFile(ArgumentMatchers.any(Path.class)))
			.willReturn(testCustomers)
		;

		final Customer expectedCustomer1 = new Customer();
		expectedCustomer1.setUserID(1);
		expectedCustomer1.setName("Cerro Nutibara");
		expectedCustomer1.setGpsLocationDegrees(new GpsLocationDegrees(6.2348022, -75.5787825));

		final Customer expectedCustomer2 = new Customer();
		expectedCustomer2.setUserID(2);
		expectedCustomer2.setName("UPB");
		expectedCustomer2.setGpsLocationDegrees(new GpsLocationDegrees(6.245377, -75.5928549));

		Assertions
			.assertThat(readCustomers.readCustomers())
				.isNotEmpty()
				.hasSize(2)
				.containsExactlyInAnyOrder(expectedCustomer1, expectedCustomer2)
		;
	}

}
