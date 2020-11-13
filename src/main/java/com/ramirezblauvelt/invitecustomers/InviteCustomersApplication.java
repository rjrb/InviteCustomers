package com.ramirezblauvelt.invitecustomers;

import com.ramirezblauvelt.invitecustomers.services.ReadCustomers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InviteCustomersApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(InviteCustomersApplication.class, args);
	}

	@Autowired
	private ReadCustomers readCustomers;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(readCustomers.readCustomers());
	}

}
