package examples;

import com.guilhermechapiewski.fluentmail.email.EmailMessage;
import com.guilhermechapiewski.fluentmail.transport.EmailTransportConfiguration;

public class SendEmailExample {

	public static void main(String[] args) {
		// put your e-mail address here
		final String yourAddress = "guilherme.chapiewski@gmail.com";

		// configure programatically your mail server info
		EmailTransportConfiguration.configure("smtp.server.com", true,
				false, "username", "password");

		// and go!
		new EmailMessage().from("demo@guilhermechapiewski.com").to(yourAddress)
				.withSubject("Fluent Mail API")
				.withAttachment("file_name")
				.withBody("Demo message").send();

		System.out.println("Check your inbox!");

	}
}
