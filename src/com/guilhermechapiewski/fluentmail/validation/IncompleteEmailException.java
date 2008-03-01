package com.guilhermechapiewski.fluentmail.validation;

public class IncompleteEmailException extends RuntimeException {

	private static final long serialVersionUID = -5356669884453957632L;

	public IncompleteEmailException(String message) {
		super(message);
	}

}
