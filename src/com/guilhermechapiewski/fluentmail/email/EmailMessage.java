package com.guilhermechapiewski.fluentmail.email;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.guilhermechapiewski.fluentmail.transport.EmailTransportConfiguration;
import com.guilhermechapiewski.fluentmail.transport.EmailTransportException;
import com.guilhermechapiewski.fluentmail.validation.EmailAddressValidator;
import com.guilhermechapiewski.fluentmail.validation.IncompleteEmailException;
import com.guilhermechapiewski.fluentmail.validation.InvalidEmailAddressException;
import com.sun.mail.smtp.SMTPTransport;

public class EmailMessage implements Email {

	private static EmailAddressValidator emailAddressValidator = new EmailAddressValidator();
	private static EmailTransportConfiguration emailTransportConfig = new EmailTransportConfiguration();

	private String fromAddress;
	private Set<String> toAddresses = new HashSet<String>();
	private String subject;
	private String body;

	@Override
	public void send() {
		validateRequiredInfo();
		validateAddresses();
		sendMail();
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

	protected void sendMail() {
		try {
			sendMailUsingJavaMailAPI();
		} catch (Exception e) {
			throw new EmailTransportException("Email could not be sent: "
					+ e.getMessage(), e);
		}
	}

	protected void sendMailUsingJavaMailAPI() throws AddressException,
			MessagingException {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", emailTransportConfig.getSmtpServer());
		properties.put("mail.smtp.auth", emailTransportConfig
				.isAuthenticationRequired());

		Session session = Session.getInstance(properties);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAddress));

		for (String to : toAddresses) {
			message.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(to));
		}

		message.setSubject(subject);
		message.setText(body);
		message.setHeader("X-Mailer", "Fluent Mail API");
		message.setSentDate(Calendar.getInstance().getTime());

		String protocol = "smtp";
		if (emailTransportConfig.useSecureSmtp()) {
			protocol = "smtps";
		}

		SMTPTransport smtpTransport = (SMTPTransport) session
				.getTransport(protocol);
		if (emailTransportConfig.isAuthenticationRequired()) {
			smtpTransport.connect(emailTransportConfig.getSmtpServer(),
					emailTransportConfig.getUsername(), emailTransportConfig
							.getPassword());
		} else {
			smtpTransport.connect();
		}
		smtpTransport.sendMessage(message, message.getAllRecipients());
		smtpTransport.close();
	}

	@Override
	public Email from(String address) {
		this.fromAddress = address;
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

	protected Email validateAddresses() {
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
