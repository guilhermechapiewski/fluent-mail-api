package com.guilhermechapiewski.fluentmail.email;

public interface Email {

	Email from(String address);

	Email to(String address);

	// Email cc(String address);

	// Email bcc(String address);

	Email withSubject(String subject);

	Email withBody(String body);

	void send();
}
