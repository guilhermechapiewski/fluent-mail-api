package com.guilhermechapiewski.fluentmail;

public interface Email {

	Email from(String address);

	Email to(String address);

	// Email cc(String address);

	// Email bcc(String address);

	Email withSubject(String subject);

	Email withBody(String body);

	Email validateAddresses();

	// Email ignoreInvalidAddresses();

	void send();
}
