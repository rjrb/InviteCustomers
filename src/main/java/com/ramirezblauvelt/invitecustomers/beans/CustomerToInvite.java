package com.ramirezblauvelt.invitecustomers.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerToInvite {

	private Integer userID;
	private String name;

}
