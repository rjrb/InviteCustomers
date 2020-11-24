package com.ramirezblauvelt.invitecustomers.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Validated
@ConfigurationProperties(prefix = "application")
@Data
public class ApplicationProperties {

	@NotEmpty
	private String customerList;

	@Positive
	private Double rangeWithinKm;

	@Min(value = 6357)
	@Max(value = 6378)
	private double earthRadiusKm;

	@Valid
	private OfficeLocationProperties officeLocation;

}
