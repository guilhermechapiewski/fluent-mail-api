package com.guilhermechapiewski.fluentmail;

import java.util.HashSet;
import java.util.Set;


public class EmailMessage implements Email {

	private static EmailAddressValidator emailAddressValidator = new EmailAddressValidator();

	private Set<String> fromAddresses = new HashSet<String>();
	private Set<String> toAddresses = new HashSet<String>();
	private String subject;
	private String body;
	
	@Override
	public void send() {
		validateRequiredInfo();
		sendUsingMailAPI();
	}

	protected void validateRequiredInfo() {
		if (fromAddresses.isEmpty()) {
			throw new IncompleteEmailException(
					"Email should have at least one from address");
		}
		if (toAddresses.isEmpty()) {
			throw new IncompleteEmailException(
					"Email should have at least one to address");
		}
		if (subject == null) {
			throw new IncompleteEmailException("Subject cannot be null");
		}
		if (body == null) {
			throw new IncompleteEmailException("Body cannot be null");
		}
	}

	protected void sendUsingMailAPI() {
		// TODO
	}

	@Override
	public Email from(String address) {
		this.fromAddresses.add(address);
		return this;
	}

	@Override
	public Email to(String address) {
		this.toAddresses.add(address);
		return this;
	}

	@Override
	public Email withSubject(String subject) {
		this.subject = subject;
		return this;
	}

	@Override
	public Email withBody(String body) {
		this.body = body;
		return this;
	}

	public Set<String> getFromAddresses() {
		return fromAddresses;
	}

	public Set<String> getToAddresses() {
		return toAddresses;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	@Override
	public Email validateAddresses() {
		for (String email : fromAddresses) {
			if (!emailAddressValidator.validate(email)) {
				throw new InvalidEmailAddressException(email);
			}
		}

		for (String email : toAddresses) {
			if (!emailAddressValidator.validate(email)) {
				throw new InvalidEmailAddressException(email);
			}
		}

		return this;
	}
	
	public static void setEmailAddressValidator(
			EmailAddressValidator emailAddressValidator) {
		EmailMessage.emailAddressValidator = emailAddressValidator;
	}
}
