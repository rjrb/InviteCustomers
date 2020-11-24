package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import com.ramirezblauvelt.invitecustomers.beans.CustomerToInvite;
import com.ramirezblauvelt.invitecustomers.exceptions.NoCustomersToInvite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InviteCustomers {

	private final ReadAndParseCustomerFile readAndParseCustomerFile;
	private final ParseCustomerData parseCustomerData;
	private final FilterCustomers filterCustomers;

	public InviteCustomers(ReadAndParseCustomerFile readAndParseCustomerFile, ParseCustomerData parseCustomerData, FilterCustomers filterCustomers) {
		this.readAndParseCustomerFile = readAndParseCustomerFile;
		this.parseCustomerData = parseCustomerData;
		this.filterCustomers = filterCustomers;
	}

	public List<CustomerToInvite> inviteCustomersFromFile() {
		return inviteCustomersFromList(readAndParseCustomerFile.readCustomerFile());
	}

	public List<CustomerToInvite> inviteCustomersFromList(List<CustomerInput> inputList) {
		final List<CustomerToInvite> customerToInviteList = inputList
			.stream()
			.map(parseCustomerData::parseCustomerInput)
			.filter(filterCustomers::filterCustomersByDistanceToOffice)
			.map(customer -> new CustomerToInvite(customer.getUserID(), customer.getName()))
			.sorted(Comparator.comparingInt(CustomerToInvite::getUserID))
			.collect(Collectors.toList())
		;

		if(customerToInviteList.isEmpty()) {
			throw new NoCustomersToInvite();
		}

		log.info("Customers to invite: {}", customerToInviteList);
		return customerToInviteList;
	}

}
