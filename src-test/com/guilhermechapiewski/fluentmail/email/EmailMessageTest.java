package com.guilhermechapiewski.fluentmail.email;

import static org.junit.Assert.*;

import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import com.guilhermechapiewski.fluentmail.email.EmailBuilder;
import com.guilhermechapiewski.fluentmail.email.EmailMessage;
import com.guilhermechapiewski.fluentmail.validation.IncompleteEmailException;

public class EmailMessageTest {

	Mockery context = new Mockery();

	@Test
	public void should_send_mail_when_parameters_are_correct() {

		final EmailBuilder email = context.mock(EmailBuilder.class);

		context.checking(new Expectations() {
			{
				one(email).from("email@from.com");
				will(returnValue(email));

				one(email).to("email@to.com");
				will(returnValue(email));

				one(email).withSubject("subject");
				will(returnValue(email));

				one(email).withBody("body");
				will(returnValue(email));

				one(email).send();
			}
		});

		email.from("email@from.com").to("email@to.com").withSubject("subject")
				.withBody("body").send();
	}

	@Test
	public void should_allow_many_tos() {
		EmailMessage email = (EmailMessage) new EmailMessage().to("a@a.com")
				.to("b@b.com").to("c@c.com");

		Set<String> addresses = email.getToAddresses();

		assertNotNull("Addresses should not be null", addresses);
		assertEquals("Should have the correct froms quantity", 3, addresses
				.size());
		assertTrue("Should contain the specified address", addresses
				.contains("a@a.com"));
		assertTrue("Should contain the specified address", addresses
				.contains("b@b.com"));
		assertTrue("Should contain the specified address", addresses
				.contains("c@c.com"));
	}

	@Test
	public void should_ignore_repeated_tos() {
		EmailMessage email = (EmailMessage) new EmailMessage().to("a@a.com")
				.to("a@a.com").to("a@a.com");

		Set<String> addresses = email.getToAddresses();

		assertNotNull("Addresses should not be null", addresses);
		assertEquals("Should have the correct froms quantity", 1, addresses
				.size());
		assertTrue("Should contain the specified address", addresses
				.contains("a@a.com"));
	}

	@Test
	public void should_require_minimum_info_to_send_message() {
		EmailMessage email = (EmailMessage) new EmailMessage();

		try_to_send_incomplete_mail(email);

		email.from("a@a.com");

		try_to_send_incomplete_mail(email);

		email.to("b@b.com");

		try_to_send_incomplete_mail(email);

		email.withSubject("test");

		try_to_send_incomplete_mail(email);

		email.withBody("test");

		try_to_send_complete_mail(email);
	}

	private void try_to_send_incomplete_mail(EmailMessage email) {
		boolean error = false;
		try {
			email.send();
		} catch (IncompleteEmailException e) {
			error = true;
		}
		assertTrue("Should throw exception", error);
	}

	private void try_to_send_complete_mail(EmailMessage email) {
		boolean error = false;
		try {
			email.send();
		} catch (Exception e) {
			error = true;
		}
		assertFalse("Should not throw any exception", error);
	}

	@Test
	public void should_send_email_using_java_mail_api() {
		EmailMessage email = (EmailMessage) new EmailMessage().from("root@gc.org").to("john@doe.com").withSubject(
				"Testing").withBody("FluentMailAPI");
		
		email.send();
		
		fail("Not implemented");
	}
}
