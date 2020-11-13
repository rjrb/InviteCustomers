package com.ramirezblauvelt.invitecustomers.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramirezblauvelt.invitecustomers.beans.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ParseData {

	private final Logger logger = LoggerFactory.getLogger(ParseData.class);
	private final ObjectMapper objectMapper;

	public ParseData() {
		objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
	}

	public Customer parseData(String customerData) {
		try {
			return objectMapper.readValue(customerData, Customer.class);
		} catch (IllegalArgumentException | JsonProcessingException jsonProcessingException) {
			logger.error("Error parsing JSON input string", jsonProcessingException);
			throw new RuntimeException("Error parsing JSON input string", jsonProcessingException);
		}
	}

}
