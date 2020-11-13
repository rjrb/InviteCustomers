package com.ramirezblauvelt.invitecustomers.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Customer {

	@JsonProperty("user_id")
	private Integer userID;
	private String name;
	private String latitude;
	private String longitude;

}
