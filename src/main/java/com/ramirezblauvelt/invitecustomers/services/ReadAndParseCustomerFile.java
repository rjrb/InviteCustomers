package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadAndParseCustomerFile {

	@Value("${application.customer-list:customers.txt}")
	private String filePath;

	private final LoadCustomerFile loadCustomerFile;
	private final ParseCustomerData parseCustomerData;

	public ReadAndParseCustomerFile(LoadCustomerFile loadCustomerFile, ParseCustomerData parseCustomerData) {
		this.loadCustomerFile = loadCustomerFile;
		this.parseCustomerData = parseCustomerData;
	}

	public List<CustomerInput> readCustomerFile() {
		return loadCustomerFile.readFile(Paths.get(filePath))
			.stream()
			.map(parseCustomerData::parseInputData)
			.collect(Collectors.toList())
		;
	}

}
