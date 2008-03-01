package com.guilhermechapiewski.fluentmail.email;

public class InvalidEmailAddressException extends Exception {

	private static final long serialVersionUID = 7521502257697884074L;

	public InvalidEmailAddressException(String message) {
		super(message);
	}
}
