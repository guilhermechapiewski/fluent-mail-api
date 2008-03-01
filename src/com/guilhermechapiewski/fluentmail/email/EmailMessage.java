package com.guilhermechapiewski.fluentmail.email;

import java.util.HashSet;
import java.util.Set;

import com.guilhermechapiewski.fluentmail.api.Email;

public class EmailMessage implements Email {

	Set<String> fromAddresses = new HashSet<String>();
	Set<String> toAddresses = new HashSet<String>();
	String subject;
	String body;

	@Override
	public void send() {
		validateRequiredInfo();
		sendUsingMailAPI();
	}

	private void validateRequiredInfo() {
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
		// TODO
		return null;
	}
}
