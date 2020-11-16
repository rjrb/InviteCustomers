package com.ramirezblauvelt.invitecustomers.unit;

import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileEmptyException;
import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileNotFoundException;
import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileReadException;
import com.ramirezblauvelt.invitecustomers.services.ReadFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestReadFile {

	@MockBean
	private ReadFile readFile;

	@Test
	void testFileLoad() {
		final String testCustomer = "{\"user_id\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.2348022\", \"longitude\": \"-75.5787825\"}";

		BDDMockito
			.given(readFile.readFile(Paths.get("")))
			.willReturn(List.of(testCustomer))
		;

		Assertions
			.assertThat(readFile.readFile(Paths.get("")))
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
			.given(readFile.readFile(fileNotFound))
			.willThrow(new CustomerFileNotFoundException(fileNotFound))
		;

		Assertions
			.assertThatThrownBy(() -> readFile.readFile(fileNotFound))
				.isInstanceOf(CustomerFileNotFoundException.class)
				.hasMessageContaining("not found")
		;

		final Path fileEmpty = Paths.get("fe");
		BDDMockito
			.given(readFile.readFile(fileEmpty))
			.willThrow(new CustomerFileEmptyException())
		;

		Assertions
			.assertThatThrownBy(() -> readFile.readFile(fileEmpty))
				.isInstanceOf(CustomerFileEmptyException.class)
				.hasMessageContaining("empty")
		;

		final Path fileError = Paths.get("ferr");
		BDDMockito
			.given(readFile.readFile(fileError))
			.willThrow(new CustomerFileReadException(new IOException("I'm a dummy error")))
		;

		Assertions
			.assertThatThrownBy(() -> readFile.readFile(fileError))
			.isInstanceOf(CustomerFileReadException.class)
			.hasMessageContaining("reading")
		;
	}

}
