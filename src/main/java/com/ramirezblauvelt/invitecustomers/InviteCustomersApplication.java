package com.ramirezblauvelt.invitecustomers;

import com.ramirezblauvelt.invitecustomers.beans.CustomerToInvite;
import com.ramirezblauvelt.invitecustomers.services.FilterCustomers;
import com.ramirezblauvelt.invitecustomers.services.ReadCustomers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class InviteCustomersApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(InviteCustomersApplication.class, args);
	}

	@Autowired
	private ReadCustomers readCustomers;

	@Autowired
	private FilterCustomers filterCustomers;

	@Override
	public void run(String... args) {
		final List<CustomerToInvite> filtered = readCustomers.readCustomers().stream()
			.filter(customer -> filterCustomers.filterCustomersByDistanceToOffice(customer))
			.map(customer -> new CustomerToInvite(customer.getUserID(), customer.getName()))
			.sorted(Comparator.comparingInt(CustomerToInvite::getUserID))
			.collect(Collectors.toList())
		;
		System.out.println(filtered);
	}

}
