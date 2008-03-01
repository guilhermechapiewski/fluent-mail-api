package com.guilhermechapiewski.fluentmail.api;

public interface Email extends From, To, Subject, Body {
	
	Email validateAddresses();
	
	void send();
}
