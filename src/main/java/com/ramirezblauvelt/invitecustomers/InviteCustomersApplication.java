package com.ramirezblauvelt.invitecustomers;

import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import com.ramirezblauvelt.invitecustomers.beans.CustomerToInvite;
import com.ramirezblauvelt.invitecustomers.beans.OfficeGpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.services.CalculateDistance;
import com.ramirezblauvelt.invitecustomers.services.ParseCustomerData;
import com.ramirezblauvelt.invitecustomers.services.ReadCustomers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${application.earth-radius-km:6371}")
	private double earthRadius;

	@Value("${application.range-within-km:100}")
	private double rangeWithin;

	@Autowired
	private ReadCustomers readCustomers;

	@Autowired
	private ParseCustomerData parseCustomerData;

	@Autowired
	private CalculateDistance calculateDistance;

	@Autowired
	private OfficeGpsLocationDegrees officeGpsLocationDegrees;

	@Override
	public void run(String... args) throws Exception {
		final List<CustomerInput> customerInputs = readCustomers.readCustomers();
		System.out.println(customerInputs);
		final List<CustomerToInvite> filtered = customerInputs.stream()
			.map(parseCustomerData::parseCustomerInput)
			.map(customer -> {
				final double distance = calculateDistance.calculateDistance(officeGpsLocationDegrees, customer.getGpsLocationDegrees(), earthRadius);
				customer.setDistanceFromOffice(distance);
				return customer;
			})
			.filter(customer -> customer.getDistanceFromOffice() <= rangeWithin)
			.map(customer -> new CustomerToInvite(customer.getUserID(), customer.getName()))
			.sorted(Comparator.comparingInt(CustomerToInvite::getUserID))
			.collect(Collectors.toList())
		;
		System.out.println(filtered);
	}

}
