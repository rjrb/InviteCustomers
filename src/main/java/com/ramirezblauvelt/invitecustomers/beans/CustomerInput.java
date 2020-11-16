package com.ramirezblauvelt.invitecustomers.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInput {

	@JsonProperty("user_id")
	private Integer userID;
	private String name;
	private String latitude;
	private String longitude;

}
