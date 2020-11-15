package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.exceptions.CustomerFileEmpty;
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
public class ReadFile {

	private final Logger logger = LoggerFactory.getLogger(ReadFile.class);

	public List<String> readFile(Path filePath) {
		if(Files.notExists(filePath)) {
			logger.error("File not found: {}", filePath);
			throw new CustomerFileNotFoundException(filePath);
		}

		try {
			if(Files.size(filePath) == 0) {
				logger.error("File empty: {}", filePath);
				throw new CustomerFileEmpty();
			}
			logger.info("Reading file {}", filePath);
			return Files.readAllLines(filePath);
		} catch (IOException ioException) {
			logger.error("Error reading file: {}", filePath);
			throw new CustomerFileReadException(ioException);
		}
	}

}
