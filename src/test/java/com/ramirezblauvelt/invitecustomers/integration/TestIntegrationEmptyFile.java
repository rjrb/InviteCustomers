package com.ramirezblauvelt.invitecustomers.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileEmptyException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
	properties = {
		"application.customer-list.path=src/test/resources/empty-customers.txt",
		"application.earth-radius-km=6371",
		"application.range-within-km=100",
		"application.office-location.latitude=53.339428",
		"application.office-location.longitude=-6.257664"
	}
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class TestIntegrationEmptyFile {

	private final MockMvc mvc;

	@Autowired
	public TestIntegrationEmptyFile(MockMvc mvc) {
		this.mvc = mvc;
	}

	@Test
	void testIntegrationEmptyFile() throws Exception {
		final Exception exceptionGet = mvc.perform(get("/invite"))
			.andExpect(status().isInternalServerError())
			.andReturn().getResolvedException()
		;

		Assertions
			.assertThat(exceptionGet)
				.isInstanceOf(CustomerFileEmptyException.class)
				.hasMessageContaining("Customer file is empty")
		;
	}

}
