package com.ramirezblauvelt.invitecustomers.services;

import com.ramirezblauvelt.invitecustomers.beans.Customer;
import com.ramirezblauvelt.invitecustomers.beans.GpsLocationDegrees;
import com.ramirezblauvelt.invitecustomers.beans.OfficeGpsLocationDegrees;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@DependsOn("officeLocation")
public class FilterCustomers {

	@Value("${application.earth-radius-km:6371}")
	private double earthRadius;

	@Value("${application.range-within-km:100}")
	private double rangeWithin;

	private final Logger logger = LoggerFactory.getLogger(FilterCustomers.class);
	private final CalculateDistance calculateDistance;
	private final OfficeGpsLocationDegrees officeGpsLocationDegrees;

	public FilterCustomers(CalculateDistance calculateDistance, OfficeGpsLocationDegrees officeGpsLocationDegrees) {
		this.calculateDistance = calculateDistance;
		this.officeGpsLocationDegrees = officeGpsLocationDegrees;
	}

	@PostConstruct
	private void logInfo() {
		logger.info("Using earth radius: {}km", earthRadius);
		logger.info("Using office location: {}", officeGpsLocationDegrees);
		logger.info("Filtering users by range within {}km from office location", rangeWithin);
	}

	public boolean filterCustomersByDistanceToOffice(Customer customer) {
		final double customerDistanceToOffice = getDistanceBetweenPoints(customer.getGpsLocationDegrees(), officeGpsLocationDegrees, earthRadius);
		logger.info("{} ({}) is {}km away from office", customer.getName(), customer.getUserID(), Math.round(customerDistanceToOffice));
		return customerDistanceToOffice <= rangeWithin;
	}

	private double getDistanceBetweenPoints(GpsLocationDegrees customerLocation, GpsLocationDegrees officeLocation, double earthRadius) {
		return calculateDistance.calculateDistance(
			customerLocation,
			officeLocation,
			earthRadius
		);
	}

}
