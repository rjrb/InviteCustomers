package com.ramirezblauvelt.invitecustomers.beans;

import lombok.Data;

@Data
public class Customer {

	private Integer userID;
	private String name;
	private GpsLocationDegrees gpsLocationDegrees;

}
