package com.ramirezblauvelt.invitecustomers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import com.ramirezblauvelt.invitecustomers.beans.CustomerToInvite;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = {
		"application.customer-list.path=src/test/resources/test-customers.txt",
		"application.earth-radius-km=6371",
		"application.range-within-km=100",
		"application.office-location.latitude=53.339428",
		"application.office-location.longitude=-6.257664"
	}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IntegrationTestInviteCustomers {

	private final MockMvc mvc;
	private final ObjectMapper objectMapper;

	@Autowired
	public IntegrationTestInviteCustomers(MockMvc mvc) {
		this.mvc = mvc;

		objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
	}

	@Test
	void testIntegrationInviteCustomersFromFile() throws Exception {
		final List<CustomerToInvite> responseGet = objectMapper.readValue(
			mvc.perform(get("/invite"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("[0].userID").exists())
				.andExpect(jsonPath("[0].name").exists())
				.andDo(print())
				.andReturn().getResponse().getContentAsString()
			, new TypeReference<List<CustomerToInvite>>(){}
		);

		final CustomerToInvite expectedCustomer1 = new CustomerToInvite(4, "Ian Kehoe");
		final CustomerToInvite expectedCustomer2 = new CustomerToInvite(5, "Nora Dempsey");
		final CustomerToInvite expectedCustomer3 = new CustomerToInvite(6, "Theresa Enright");

		Assertions
			.assertThat(responseGet)
				.hasSize(3)
				.containsExactly(
					expectedCustomer1,
					expectedCustomer2,
					expectedCustomer3
				)
		;
	}

	@Test
	void testIntegrationInviteCustomersFromList() throws Exception {
		final List<CustomerInput> inputCustomerList = List.of(
			new CustomerInput(5, "Nora Dempsey", "53.1302756", "-6.2397222"),
			new CustomerInput(1, "Alice Cahill", "51.9289300", "-10.27699"),
			new CustomerInput(6, "Theresa Enright", "53.1229599", "-6.2705202"),
			new CustomerInput(2, "Ian McArdle", "51.8856167", "-10.4240951"),
			new CustomerInput(4, "Ian Kehoe", "53.2451022", "-6.238335"),
			new CustomerInput(3, "Jack Enright", "52.3191841", "-8.5072391")
		);

		final List<CustomerToInvite> responseGet = objectMapper.readValue(
			mvc.perform(
					post("/invite")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(inputCustomerList))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("[0].userID").exists())
				.andExpect(jsonPath("[0].name").exists())
				.andDo(print())
				.andReturn().getResponse().getContentAsString()
			, new TypeReference<List<CustomerToInvite>>(){}
		);

		final CustomerToInvite expectedCustomer1 = new CustomerToInvite(4, "Ian Kehoe");
		final CustomerToInvite expectedCustomer2 = new CustomerToInvite(5, "Nora Dempsey");
		final CustomerToInvite expectedCustomer3 = new CustomerToInvite(6, "Theresa Enright");

		Assertions
			.assertThat(responseGet)
			.hasSize(3)
			.containsExactly(
				expectedCustomer1,
				expectedCustomer2,
				expectedCustomer3
			)
		;
	}

}
