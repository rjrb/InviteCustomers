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
public class ReadCustomerFile {

	private final Logger logger = LoggerFactory.getLogger(ReadCustomerFile.class);

	public List<String> readFile(Path filePath) {
		logger.info("Reading file {}", filePath);
		if(Files.notExists(filePath)) {
			throw new CustomerFileNotFoundException(filePath);
		}

		try {
			if(Files.size(filePath) == 0) {
				throw new CustomerFileEmpty();
			}
			return Files.readAllLines(filePath);
		} catch (IOException ioException) {
			throw new CustomerFileReadException(ioException);
		}
	}

}
