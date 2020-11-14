package com.ramirezblauvelt.invitecustomers.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramirezblauvelt.invitecustomers.beans.Customer;
import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ParseCustomerData {

	private final Logger logger = LoggerFactory.getLogger(ParseCustomerData.class);
	private final ObjectMapper objectMapper;
	private final ValidateCoordinates validateCoordinates;

	public ParseCustomerData(ValidateCoordinates validateCoordinates) {
		this.validateCoordinates = validateCoordinates;

		objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
	}

	public CustomerInput parseInputData(String customerData) {
		try {
			return objectMapper.readValue(customerData, CustomerInput.class);
		} catch (IllegalArgumentException | JsonProcessingException jsonProcessingException) {
			logger.error("Error parsing JSON input string", jsonProcessingException);
			throw new RuntimeException("Error parsing JSON input string", jsonProcessingException);
		}
	}

	public Customer parseCustomerInput(CustomerInput customerInput) {
		final Customer customer = new Customer();
		customer.setUserID(customerInput.getUserID());
		customer.setName(customerInput.getName());
		try {
			final GpsLocationDegrees gpsLocationDegrees = new GpsLocationDegrees();
			gpsLocationDegrees.setLatitude(Double.parseDouble(customerInput.getLatitude()));
			gpsLocationDegrees.setLongitude(Double.parseDouble(customerInput.getLongitude()));
			customer.setGpsLocationDegrees(validateCoordinates.validate(gpsLocationDegrees));
		} catch (NumberFormatException numberFormatException) {
			logger.error("Error parsing customer's GPS location", numberFormatException);
			throw new RuntimeException("Error parsing customer's GPS location", numberFormatException);
		} catch (IllegalArgumentException illegalArgumentException) {
			logger.error("Error validating customer's GPS location", illegalArgumentException);
			throw new RuntimeException("Error validating customer's GPS location", illegalArgumentException);
		}
		return customer;
	}

}
