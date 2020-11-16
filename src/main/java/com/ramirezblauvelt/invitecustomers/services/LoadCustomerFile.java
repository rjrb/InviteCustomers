package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileEmptyException;
import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileNotFoundException;
import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class LoadCustomerFile {

	private final Logger logger = LoggerFactory.getLogger(LoadCustomerFile.class);

	public List<String> readFile(Path filePath) {
		if(Files.notExists(filePath)) {
			logger.error("File not found: {}", filePath.toAbsolutePath());
			throw new CustomerFileNotFoundException(filePath);
		}

		try {
			if(Files.size(filePath) == 0) {
				logger.error("File empty: {}", filePath.toAbsolutePath());
				throw new CustomerFileEmptyException();
			}
			logger.info("Reading file {}", filePath.toAbsolutePath().normalize());
			return Files.readAllLines(filePath);
		} catch (IOException ioException) {
			logger.error("Error reading file: {}", filePath);
			throw new CustomerFileReadException(ioException);
		}
	}

}
