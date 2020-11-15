package com.ramirezblauvelt.invitecustomers;

import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import com.ramirezblauvelt.invitecustomers.beans.CustomerToInvite;
import com.ramirezblauvelt.invitecustomers.services.InviteCustomers;
import com.ramirezblauvelt.invitecustomers.services.ReadFile;
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
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = {
		"application.customer-list.path=customers.txt",
		"application.earth-radius-km=6371",
		"application.range-within-km=100",
		"application.office-location.latitude=53.339428",
		"application.office-location.longitude=-6.257664"
	}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestInviteCustomers {

	@MockBean
	private ReadFile readFile;

	@Autowired
	private InviteCustomers inviteCustomers;

	@Test
	void testInviteCustomersFromFile() {
		final List<String> testCustomers = List.of(
			"{\"user_id\": 1, \"name\": \"Somewhere in Dublin\", \"latitude\": \"53.344105\", \"longitude\": \"-6.267494\"}",
			"{\"user_id\": 2, \"name\": \"UPB\", \"latitude\": \"6.245377\", \"longitude\": \"-75.5928549\"}"
		);

		BDDMockito
			.given(readFile.readFile(ArgumentMatchers.any(Path.class)))
			.willReturn(testCustomers)
		;

		final CustomerToInvite expectedCustomer = new CustomerToInvite(
			1,
			"Somewhere in Dublin"
		);

		Assertions
			.assertThat(inviteCustomers.inviteCustomersFromFile())
				.hasSize(1)
				.containsExactly(expectedCustomer)
		;
	}

	@Test
	void testInviteCustomersFromList() {
		final CustomerInput customerInput1 = new CustomerInput();
		customerInput1.setUserID(98);
		customerInput1.setName("Somewhere near the office");
		customerInput1.setLatitude("53.732321");
		customerInput1.setLongitude("-6.842092");

		final CustomerInput customerInput2 = new CustomerInput();
		customerInput2.setUserID(99);
		customerInput2.setName("Somewhere far from the office");
		customerInput2.setLatitude("59.344105");
		customerInput2.setLongitude("-9.267494");

		final List<CustomerInput> customerInputList = List.of(
			customerInput1,
			customerInput2
		);

		final CustomerToInvite expectedCustomer = new CustomerToInvite(
			98,
			"Somewhere near the office"
		);

		Assertions
			.assertThat(inviteCustomers.inviteCustomersFromList(customerInputList))
			.hasSize(1)
			.containsExactly(expectedCustomer)
		;
	}
}
