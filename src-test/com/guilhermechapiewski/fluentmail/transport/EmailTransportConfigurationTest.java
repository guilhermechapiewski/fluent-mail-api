package com.guilhermechapiewski.fluentmail.transport;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmailTransportConfigurationTest {

	EmailTransportConfiguration config = new EmailTransportConfiguration();

	@Test
	public void should_manage_configuration_correctly() {
		String smtpServer = "smtp.server.com";
		boolean authenticationRequired = true;
		boolean useSecureSmtp = false;
		String username = "john";
		String password = "doe";

		EmailTransportConfiguration.configure(smtpServer,
				authenticationRequired, useSecureSmtp, username, password);

		assertEquals("Should configure smtp server correctly", smtpServer,
				config.getSmtpServer());
		assertEquals("Should configure authentication correctly",
				authenticationRequired, config.isAuthenticationRequired());
		assertEquals("Should configure secure smtp correctly", useSecureSmtp,
				config.useSecureSmtp());
		assertEquals("Should configure username correctly", username, config
				.getUsername());
		assertEquals("Should configure password correctly", password, config
				.getPassword());

	}
}
