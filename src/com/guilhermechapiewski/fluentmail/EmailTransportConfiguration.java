package com.guilhermechapiewski.fluentmail;

public class EmailTransportConfiguration {

	private static String smtpServer = "";
	private static boolean authenticationRequired = false;
	private static boolean useSecureSmtp = false;
	private static String username = null;
	private static String password = null;

	static {
		// TODO: try to load config from properties
		configure("webmail.corp.globo.com", true, false, "gc", "");
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
		configure(smtpServer, false, false, null, null);
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
	 * @param username
	 *            The SMTP username.
	 * @param password
	 *            The SMTP password.
	 */
	public static void configure(String smtpServer,
			boolean authenticationRequired, boolean useSecureSmtp,
			String username, String password) {
		EmailTransportConfiguration.smtpServer = smtpServer;
		EmailTransportConfiguration.authenticationRequired = authenticationRequired;
		EmailTransportConfiguration.useSecureSmtp = useSecureSmtp;
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

}
