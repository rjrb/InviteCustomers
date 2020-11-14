package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReadCustomers {

	@Value("${application.customer-list.path:customers.txt}")
	private String filePath;

	private final Logger logger = LoggerFactory.getLogger(ReadCustomers.class);
	private final LoadFile loadFile;
	private final ParseCustomerData parseCustomerData;

	public ReadCustomers(LoadFile loadFile, ParseCustomerData parseCustomerData) {
		this.loadFile = loadFile;
		this.parseCustomerData = parseCustomerData;
	}

	public List<CustomerInput> readCustomers() throws IOException {
		logger.info("Reading file {}", filePath);
		return loadFile.readFile(Paths.get(filePath))
			.stream()
			.peek(logger::trace)
			.map(parseCustomerData::parseInputData)
			.peek(customer -> logger.trace("{}", customer))
			.filter(Objects::nonNull)
			.collect(Collectors.toList())
		;
	}

}
