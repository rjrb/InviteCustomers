package com.ramirezblauvelt.invitecustomers.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class BaseException extends RuntimeException {

	private final int errorCode;
	private final String errorMessage;

}
