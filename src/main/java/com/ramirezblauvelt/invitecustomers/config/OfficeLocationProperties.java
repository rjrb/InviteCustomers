package com.ramirezblauvelt.invitecustomers.config;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class OfficeLocationProperties {

	@Min(value = -90)
	@Max(value =  90)
	private double latitude;

	@Min(value = -180)
	@Max(value =  180)
	private double longitude;

}
