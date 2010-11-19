package com.guilhermechapiewski.fluentmail.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMultipart;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.BeforeClass;
import org.junit.Test;

import com.guilhermechapiewski.fluentmail.email.Email;

public class PostalServiceTest {

	static final EmailTransportConfiguration config = new EmailTransportConfiguration();

	static String SMTP_SERVER = "smtp.server.com";
	static boolean AUTH_REQUIRED = true;
	static boolean USE_SECURE_SMTP = false;
	static String USERNAME = "john";
	static String PASSWORD = "doe";

	Mockery context = new Mockery();

	@BeforeClass
	public static void setup() {
		EmailTransportConfiguration.configure(SMTP_SERVER, AUTH_REQUIRED,
				USE_SECURE_SMTP, USERNAME, PASSWORD);
	}

	@Test
	public void should_choose_correct_protocol_when_using_secure_smtp_or_not() {
		PostalService postalService = new PostalService();

		EmailTransportConfiguration.configure(SMTP_SERVER, AUTH_REQUIRED,
				false, USERNAME, PASSWORD);

		assertEquals("Should use SMTP protocol", "smtp", postalService
				.getProtocol());

		EmailTransportConfiguration.configure(SMTP_SERVER, AUTH_REQUIRED, true,
				USERNAME, PASSWORD);

		assertEquals("Should use Secure SMTP protocol", "smtps", postalService
				.getProtocol());
	}

	@Test
	public void should_create_message_from_email() throws Exception {
		final Email email = context.mock(Email.class);

		final String from = "from.john@doe.com";

		final String to = "to.john@doe.com";
		final Set<String> tos = new HashSet<String>();
		tos.add(to);

		final String cc = "cc.john@doe.com";
		final Set<String> ccs = new HashSet<String>();
		ccs.add(cc);

		final String bcc = "bcc.john@doe.com";
		final Set<String> bccs = new HashSet<String>();
		bccs.add(bcc);

		final String subject = "subject";

		final String body = "text";

		context.checking(new Expectations() {
			{
				one(email).getFromAddress();
				will(returnValue(from));

				one(email).getToAddresses();
				will(returnValue(tos));

				one(email).getCcAddresses();
				will(returnValue(ccs));

				one(email).getBccAddresses();
				will(returnValue(bccs));

				one(email).getSubject();
				will(returnValue(subject));
				
				one(email).getAttachments();
				will(returnValue(new HashSet<String>()));

				one(email).getBody();
				will(returnValue(body));
			}
		});

		PostalService postalService = new PostalService();
		Message message = postalService.createMessage(email);

		assertEquals(from, message.getFrom()[0].toString());
		assertEquals(to, message.getRecipients(Message.RecipientType.TO)[0]
				.toString());
		assertEquals(cc, message.getRecipients(Message.RecipientType.CC)[0]
				.toString());
		assertEquals(bcc, message.getRecipients(Message.RecipientType.BCC)[0]
				.toString());
		assertEquals(subject, message.getSubject());
		
		MimeMultipart mimeContent = (MimeMultipart)message.getContent();
		assertEquals(body, mimeContent.getBodyPart(0).getContent());
	}

	@Test
	public void should_get_session_with_correct_config() {
		EmailTransportConfiguration.configure(SMTP_SERVER, AUTH_REQUIRED,
				USE_SECURE_SMTP, USERNAME, PASSWORD);

		PostalService postalService = new PostalService();
		Session session = postalService.getSession();

		assertNotNull("Session cannot be null", session);
		assertEquals("Should get correct smtp server", SMTP_SERVER, session
				.getProperty("mail.smtp.host"));
	}
}
