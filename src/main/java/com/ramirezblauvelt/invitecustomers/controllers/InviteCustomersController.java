package com.ramirezblauvelt.invitecustomers.controllers;

import com.ramirezblauvelt.invitecustomers.beans.CustomerInput;
import com.ramirezblauvelt.invitecustomers.beans.CustomerToInvite;
import com.ramirezblauvelt.invitecustomers.services.InviteCustomers;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invite")
public class InviteCustomersController {

	private final InviteCustomers inviteCustomers;

	public InviteCustomersController(InviteCustomers inviteCustomers) {
		this.inviteCustomers = inviteCustomers;
	}

	@GetMapping("")
	public List<CustomerToInvite> getCustomersToInviteFromFile() {
		return inviteCustomers.inviteCustomersFromFile();
	}

	@PostMapping("")
	public List<CustomerToInvite> getCustomersToInviteFromList(@Validated @RequestBody List<CustomerInput> inputList) {
		return inviteCustomers.inviteCustomersFromList(inputList);
	}

}
