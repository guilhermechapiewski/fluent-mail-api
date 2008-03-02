package com.guilhermechapiewski.fluentmail.email;

public interface EmailBuilder {

	EmailBuilder from(String address);

	EmailBuilder to(String address);

	EmailBuilder cc(String address);

	EmailBuilder bcc(String address);

	EmailBuilder withSubject(String subject);

	EmailBuilder withBody(String body);

	void send();
}