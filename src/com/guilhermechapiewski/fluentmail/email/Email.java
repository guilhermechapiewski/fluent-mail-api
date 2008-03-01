package com.guilhermechapiewski.fluentmail.email;

import java.util.Set;

public interface Email {

	String getFromAddress();
	
	Set<String> getToAddresses();
	
	String getSubject();
	
	String getBody();
}
