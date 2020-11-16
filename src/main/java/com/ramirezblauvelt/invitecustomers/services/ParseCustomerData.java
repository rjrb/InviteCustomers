package com.ramirezblauvelt.invitecustomers.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramirezblauvelt.invitecustomers.beans.Customer;
import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.exceptions.CustomerParseException;
import com.ramirezblauvelt.invitecustomers.exceptions.ParseCustomerInputException;
import org.springframework.stereotype.Service;

@Service
public class ParseCustomerData {

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
			throw new CustomerParseException(jsonProcessingException);
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
		} catch (NullPointerException | NumberFormatException parseCustomerException) {
			throw new ParseCustomerInputException(parseCustomerException);
		}
		return customer;
	}

}
