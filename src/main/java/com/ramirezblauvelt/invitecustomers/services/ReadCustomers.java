package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReadCustomers {

	@Value("${application.customer-list.path:customers.txt}")
	private String filePath;

	private final Logger logger = LoggerFactory.getLogger(ReadCustomers.class);
	private final ReadCustomerFile readCustomerFile;
	private final ParseCustomerData parseCustomerData;

	public ReadCustomers(ReadCustomerFile readCustomerFile, ParseCustomerData parseCustomerData) {
		this.readCustomerFile = readCustomerFile;
		this.parseCustomerData = parseCustomerData;
	}

	public List<Customer> readCustomers() {
		logger.info("Reading file {}", filePath);
		return readCustomerFile.readFile(Paths.get(filePath))
			.stream()
			.peek(logger::trace)
			.map(parseCustomerData::parseInputData)
			.peek(customerInput -> logger.trace("{}", customerInput))
			.map(parseCustomerData::parseCustomerInput)
			.peek(customer -> logger.trace("{}", customer))
			.filter(Objects::nonNull)
			.collect(Collectors.toList())
		;
	}

}
