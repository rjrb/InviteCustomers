package com.ramirezblauvelt.invitecustomers;

import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileEmpty;
import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileNotFoundException;
import com.ramirezblauvelt.invitecustomers.services.ReadCustomerFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestReadCustomerFile {

	@MockBean
	private ReadCustomerFile readCustomerFile;

	@Test
	void testFileLoad() {
		final String testCustomer = "{\"user_id\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.2348022\", \"longitude\": \"-75.5787825\"}";

		BDDMockito
			.given(readCustomerFile.readFile(Paths.get("")))
			.willReturn(List.of(testCustomer))
		;

		Assertions
			.assertThat(readCustomerFile.readFile(Paths.get("")))
			.withFailMessage("Failed to load file data")
				.isNotEmpty()
				.hasSize(1)
				.containsOnly(testCustomer)
		;
	}

	@Test
	void testFileLoadExceptions() {
		final Path fileNotFound = Paths.get("fnf");
		BDDMockito
			.given(readCustomerFile.readFile(fileNotFound))
			.willThrow(new CustomerFileNotFoundException(fileNotFound))
		;

		Assertions
			.assertThatThrownBy(() -> readCustomerFile.readFile(fileNotFound))
				.isInstanceOf(CustomerFileNotFoundException.class)
				.hasMessageContaining("not found")
		;

		final Path fileEmpty = Paths.get("fe");
		BDDMockito
			.given(readCustomerFile.readFile(fileEmpty))
			.willThrow(new CustomerFileEmpty())
		;

		Assertions
			.assertThatThrownBy(() -> readCustomerFile.readFile(fileEmpty))
				.isInstanceOf(CustomerFileEmpty.class)
				.hasMessageContaining("empty")
		;
	}

}
