package com.guilhermechapiewski.fluentmail.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

import com.guilhermechapiewski.fluentmail.transport.PostalService;
import com.guilhermechapiewski.fluentmail.validation.EmailAddressValidator;
import com.guilhermechapiewski.fluentmail.validation.IncompleteEmailException;

public class EmailMessageTest {

	Mockery context = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Before
	public void setup() {
		// cleaning dependencies
		EmailMessage.setEmailAddressValidator(new EmailAddressValidator());
		EmailMessage.setPostalService(new PostalService());
	}

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
	public void should_allow_many_tos_in_the_same_method() {
		EmailMessage email = (EmailMessage) new EmailMessage().to("a@a.com",
				"b@b.com", "c@c.com");

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
	public void should_allow_many_ccs() {
		EmailMessage email = (EmailMessage) new EmailMessage().cc("a@a.com")
				.cc("b@b.com").cc("c@c.com");

		Set<String> addresses = email.getCcAddresses();

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
	public void should_allow_many_ccs_in_the_same_method() {
		EmailMessage email = (EmailMessage) new EmailMessage().cc("a@a.com",
				"b@b.com", "c@c.com");

		Set<String> addresses = email.getCcAddresses();

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
	public void should_ignore_repeated_ccs() {
		EmailMessage email = (EmailMessage) new EmailMessage().cc("a@a.com")
				.cc("a@a.com").cc("a@a.com");

		Set<String> addresses = email.getCcAddresses();

		assertNotNull("Addresses should not be null", addresses);
		assertEquals("Should have the correct froms quantity", 1, addresses
				.size());
		assertTrue("Should contain the specified address", addresses
				.contains("a@a.com"));
	}

	@Test
	public void should_allow_many_bccs() {
		EmailMessage email = (EmailMessage) new EmailMessage().bcc("a@a.com")
				.bcc("b@b.com").bcc("c@c.com");

		Set<String> addresses = email.getBccAddresses();

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
	public void should_allow_many_bccs_in_the_same_method() {
		EmailMessage email = (EmailMessage) new EmailMessage().bcc("a@a.com",
				"b@b.com", "c@c.com");

		Set<String> addresses = email.getBccAddresses();

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
	public void should_ignore_repeated_bccs() {
		EmailMessage email = (EmailMessage) new EmailMessage().bcc("a@a.com")
				.bcc("a@a.com").bcc("a@a.com");

		Set<String> addresses = email.getBccAddresses();

		assertNotNull("Addresses should not be null", addresses);
		assertEquals("Should have the correct froms quantity", 1, addresses
				.size());
		assertTrue("Should contain the specified address", addresses
				.contains("a@a.com"));
	}
	
	@Test
	public void should_allow_many_attachments(){
		EmailMessage email = (EmailMessage) new EmailMessage().withAttachment("C:\\java.png")
			  .withAttachment("/home/danielbussade/teste.txt").withAttachment("C:\\teste.pdf");
		
		Set<String> attachments = email.getAttachments();
		assertNotNull("Attachments should be null",attachments);
		assertEquals("Should have the correct froms quantity", 3, attachments
				.size());
		assertTrue("Should contain the specified attachments", attachments
				.contains("C:\\java.png"));
		assertTrue("Should contain the specified attachments", attachments
				.contains("/home/danielbussade/teste.txt"));
		assertTrue("Should contain the specified attachments", attachments
				.contains("C:\\teste.pdf"));
		
	}
	
	@Test
	public void should_allow_many_attachments_in_the_same_method(){
		EmailMessage email = (EmailMessage) new EmailMessage().withAttachment("C:\\java.png",
				"/home/danielbussade/teste.txt","C:\\teste.pdf");
		
		Set<String> attachments = email.getAttachments();
		assertNotNull("Attachments should be null",attachments);
		assertEquals("Should have the correct froms quantity", 3, attachments
				.size());
		assertTrue("Should contain the specified attachments", attachments
				.contains("C:\\java.png"));
		assertTrue("Should contain the specified attachments", attachments
				.contains("/home/danielbussade/teste.txt"));
		assertTrue("Should contain the specified attachments", attachments
				.contains("C:\\teste.pdf"));
		
	}
	
	@Test
	public void should_require_minimum_info_to_send_message() throws Exception {
		final PostalService postalService = context.mock(PostalService.class);
		final EmailAddressValidator emailAddressValidator = context
				.mock(EmailAddressValidator.class);
		final EmailMessage email = new EmailMessage();

		EmailMessage.setPostalService(postalService);
		EmailMessage.setEmailAddressValidator(emailAddressValidator);

		context.checking(new Expectations() {
			{
				one(postalService).send(email);

				one(emailAddressValidator).validate("a@a.com");
				will(returnValue(true));

				one(emailAddressValidator).validate("b@b.com");
				will(returnValue(true));
			}
		});

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
	public void should_send_email_using_java_mail_api() throws Exception {
		final EmailMessage email = (EmailMessage) new EmailMessage().from(
				"root@gc.org").to("john@doe.com").withSubject("Testing")
				.withBody("FluentMailAPI");

		final PostalService postalService = context.mock(PostalService.class);

		EmailMessage.setPostalService(postalService);

		context.checking(new Expectations() {
			{
				one(postalService).send(email);
			}
		});

		email.send();
	}
}