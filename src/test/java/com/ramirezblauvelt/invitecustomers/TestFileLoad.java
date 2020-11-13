package com.ramirezblauvelt.invitecustomers;

import com.ramirezblauvelt.invitecustomers.services.LoadFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestFileLoad {

	@MockBean
	private LoadFile loadFile;

	@Test
	void testFileLoad() throws IOException {
		final String testCustomer = "{\"user_id\": 1, \"name\": \"Cerro Nutibara\", \"latitude\": \"6.21685669257658\", \"longitude\": \"-75.55700248857622\"}";

		BDDMockito
			.given(loadFile.readFile(Paths.get("")))
			.willReturn(List.of(testCustomer))
		;

		Assertions
			.assertThat(loadFile.readFile(Paths.get("")))
			.withFailMessage("Failed to load file data")
				.isNotEmpty()
				.hasSize(1)
				.containsOnly(testCustomer)
		;
	}

	@Test
	void testFileLoadExceptions() throws IOException {
		BDDMockito
			.given(loadFile.readFile(Paths.get("fnf")))
			.willThrow(new IOException("File not found"))
		;

		Assertions
			.assertThatThrownBy(() -> loadFile.readFile(Paths.get("fnf")))
				.isInstanceOf(IOException.class)
				.hasMessageContaining("File not found")
		;

		BDDMockito
			.given(loadFile.readFile(Paths.get("fe")))
			.willThrow(new IOException("File empty"))
		;

		Assertions
			.assertThatThrownBy(() -> loadFile.readFile(Paths.get("fe")))
				.isInstanceOf(IOException.class)
				.hasMessageContaining("File empty")
		;
	}

}
