package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadCustomerFile {

	@Value("${application.customer-list.path:customers.txt}")
	private String filePath;

	private final Logger logger = LoggerFactory.getLogger(ReadCustomerFile.class);
	private final ReadFile readFile;
	private final ParseCustomerData parseCustomerData;

	public ReadCustomerFile(ReadFile readFile, ParseCustomerData parseCustomerData) {
		this.readFile = readFile;
		this.parseCustomerData = parseCustomerData;
	}

	public List<CustomerInput> readCustomerFile() {
		return readFile.readFile(Paths.get(filePath))
			.stream()
			.map(parseCustomerData::parseInputData)
			.collect(Collectors.toList())
		;
	}

}
