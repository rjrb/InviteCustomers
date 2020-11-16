package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import com.ramirezblauvelt.invitecustomers.beans.CustomerToInvite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InviteCustomers {

	private final Logger logger = LoggerFactory.getLogger(InviteCustomers.class);
	private final ReadCustomerFile readCustomerFile;
	private final ParseCustomerData parseCustomerData;
	private final FilterCustomers filterCustomers;

	public InviteCustomers(ReadCustomerFile readCustomerFile, ParseCustomerData parseCustomerData, FilterCustomers filterCustomers) {
		this.readCustomerFile = readCustomerFile;
		this.parseCustomerData = parseCustomerData;
		this.filterCustomers = filterCustomers;
	}

	public List<CustomerToInvite> inviteCustomersFromFile() {
		return inviteCustomersFromList(readCustomerFile.readCustomerFile());
	}

	public List<CustomerToInvite> inviteCustomersFromList(List<CustomerInput> inputList) {
		return inputList
			.stream()
			.peek(customerInput -> logger.trace("{}", customerInput))
			.map(parseCustomerData::parseCustomerInput)
			.peek(customer -> logger.trace("{}", customer))
			.filter(filterCustomers::filterCustomersByDistanceToOffice)
			.map(customer -> new CustomerToInvite(customer.getUserID(), customer.getName()))
			.sorted(Comparator.comparingInt(CustomerToInvite::getUserID))
			.peek(customerToInvite -> logger.trace("{}", customerToInvite))
			.collect(Collectors.toList())
		;
	}

}
