package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileEmptyException;
import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileNotFoundException;
import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileReadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Slf4j
public class LoadCustomerFile {

	public List<String> readFile(Path filePath) {
		if(Files.notExists(filePath)) {
			log.error("File not found: {}", filePath.toAbsolutePath());
			throw new CustomerFileNotFoundException(filePath);
		}

		try {
			if(Files.size(filePath) == 0) {
				log.error("File empty: {}", filePath.toAbsolutePath());
				throw new CustomerFileEmptyException();
			}
			log.info("Reading file {}", filePath.toAbsolutePath().normalize());
			return Files.readAllLines(filePath);
		} catch (IOException ioException) {
			log.error("Error reading file: {}", filePath);
			throw new CustomerFileReadException(ioException);
		}
	}

}
