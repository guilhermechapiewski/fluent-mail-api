package com.guilhermechapiewski.fluentmail.transport;

import java.io.InputStream;
import java.util.Properties;

public class EmailTransportConfiguration {

	private static final String PROPERTIES_FILE = "fluent-mail-api.properties";
	private static final String KEY_SMTP_SERVER = "smtp.server";
	private static final String KEY_AUTH_REQUIRED = "auth.required";
	private static final String KEY_USE_SECURE_SMTP = "use.secure.smtp";
	private static final String KEY_USERNAME = "smtp.username";
	private static final String KEY_PASSWORD = "smtp.password";
	private static final String KEY_STARTTTLS = "smtp.starttls";
	private static final String KEY_PORT = "smtp.port";

	private static String smtpServer = "";
	private static boolean authenticationRequired = false;
	private static boolean useSecureSmtp = false;
	private static boolean starttls = false;
	private static Integer port = 25;
	private static String username = null;
	private static String password = null;
	

	static {
		Properties properties = loadProperties();

		String smtpServer = properties.getProperty(KEY_SMTP_SERVER);
		boolean authenticationRequired = Boolean.parseBoolean(properties.getProperty(KEY_AUTH_REQUIRED));
		boolean useSecureSmtp = Boolean.parseBoolean(properties.getProperty(KEY_USE_SECURE_SMTP));
		boolean starttls = Boolean.parseBoolean(properties.getProperty(KEY_STARTTTLS));
		String portNumber = properties.getProperty(KEY_PORT);
		Integer port = ( portNumber == null || "".equals(portNumber) ) ? 25 : Integer.parseInt(portNumber);
		String username = properties.getProperty(KEY_USERNAME);
		String password = properties.getProperty(KEY_PASSWORD);

		configure(smtpServer, authenticationRequired, useSecureSmtp, starttls, port, username, password);
	}
	
	private static Properties loadProperties() {
		Properties properties = new Properties();

		InputStream inputStream = EmailTransportConfiguration.class
				.getResourceAsStream(PROPERTIES_FILE);

		if (inputStream == null) {
			inputStream = EmailTransportConfiguration.class
					.getResourceAsStream("/" + PROPERTIES_FILE);
		}

		try {
			properties.load(inputStream);
		} catch (Exception e) {
			// Properties file not found, no problem.
		}

		return properties;
	}

	/**
	 * Configure mail transport to use the specified SMTP server. Because this
	 * configuration mode does not require to inform username and password, it
	 * assumes that authentication and secure SMTP are not required.
	 * 
	 * @param smtpServer
	 *            The SMTP server to use for mail transport. To use a specific
	 *            port, user the syntax server:port.
	 */
	public static void configure(String smtpServer) {
		configure(smtpServer, false, false, false, 25, null, null);
	}

	/**
	 * @param smtpServer
	 *            The SMTP server to use for mail transport. To use a specific
	 *            port, user the syntax server:port.
	 * @param authenticationRequired
	 *            Informs if mail transport needs to authenticate to send mail
	 *            or not.
	 * @param useSecureSmtp
	 *            Use secure SMTP to send messages.
	 * @param starttls
	 * 			  Use starttls ( required from Gmail ).
	 * @param port
	 * 			  The SMTP port number.    
	 * @param username
	 *            The SMTP username.
	 * @param password
	 *            The SMTP password.
	 */
	public static void configure(String smtpServer,
			boolean authenticationRequired, boolean useSecureSmtp,
			boolean starttls, Integer port,
			String username, String password) {
		EmailTransportConfiguration.smtpServer = smtpServer;
		EmailTransportConfiguration.authenticationRequired = authenticationRequired;
		EmailTransportConfiguration.useSecureSmtp = useSecureSmtp;
		EmailTransportConfiguration.starttls = starttls;
		EmailTransportConfiguration.port = port;
		EmailTransportConfiguration.username = username;
		EmailTransportConfiguration.password = password;
	}
	
	public String getSmtpServer() {
		return smtpServer;
	}

	public boolean isAuthenticationRequired() {
		return authenticationRequired;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean useSecureSmtp() {
		return useSecureSmtp;
	}

	public boolean useStarttls() {
		return starttls;
	}

	public Integer getPort() {
		return port;
	}

}