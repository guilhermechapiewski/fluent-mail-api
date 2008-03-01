package com.guilhermechapiewski.fluentmail.email;

import java.util.HashSet;
import java.util.Set;

import com.guilhermechapiewski.fluentmail.transport.EmailTransportException;
import com.guilhermechapiewski.fluentmail.transport.PostalService;
import com.guilhermechapiewski.fluentmail.validation.EmailAddressValidator;
import com.guilhermechapiewski.fluentmail.validation.IncompleteEmailException;
import com.guilhermechapiewski.fluentmail.validation.InvalidEmailAddressException;

public class EmailMessage implements EmailBuilder, Email {

	private static EmailAddressValidator emailAddressValidator = new EmailAddressValidator();

	private String fromAddress;
	private Set<String> toAddresses = new HashSet<String>();
	private String subject;
	private String body;

	@Override
	public void send() {
		validateRequiredInfo();
		validateAddresses();
		sendMessage();
	}

	protected void validateRequiredInfo() {
		if (fromAddress == null) {
			throw new IncompleteEmailException("From address cannot be null");
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

	protected void sendMessage() {
		try {
			new PostalService().send(this);
		} catch (Exception e) {
			throw new EmailTransportException("Email could not be sent: "
					+ e.getMessage(), e);
		}
	}

	@Override
	public EmailBuilder from(String address) {
		this.fromAddress = address;
		return this;
	}

	@Override
	public EmailBuilder to(String address) {
		this.toAddresses.add(address);
		return this;
	}

	@Override
	public EmailBuilder withSubject(String subject) {
		this.subject = subject;
		return this;
	}

	@Override
	public EmailBuilder withBody(String body) {
		this.body = body;
		return this;
	}

	public String getFromAddress() {
		return fromAddress;
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

	protected EmailBuilder validateAddresses() {
		if (!emailAddressValidator.validate(fromAddress)) {
			throw new InvalidEmailAddressException("From: " + fromAddress);
		}

		for (String email : toAddresses) {
			if (!emailAddressValidator.validate(email)) {
				throw new InvalidEmailAddressException("To: " + email);
			}
		}

		return this;
	}

	public static void setEmailAddressValidator(
			EmailAddressValidator emailAddressValidator) {
		EmailMessage.emailAddressValidator = emailAddressValidator;
	}
}
