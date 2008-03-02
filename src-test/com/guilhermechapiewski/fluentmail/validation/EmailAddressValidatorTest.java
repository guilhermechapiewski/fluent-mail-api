package com.guilhermechapiewski.fluentmail.validation;

import static org.junit.Assert.*;

import org.junit.Test;

import com.guilhermechapiewski.fluentmail.validation.EmailAddressValidator;

public class EmailAddressValidatorTest {

	EmailAddressValidator validator = new EmailAddressValidator();

	@Test
	public void should_validate_long_address() {
		assertTrue(validator
				.validate("guilherme.chapiewski@video.dev.wm.google.code.com"));
	}

	@Test
	public void should_validate_short_address() {
		assertTrue(validator.validate("g@g.com"));
	}

	@Test
	public void should_not_validate_invalid_addresses() {
		assertFalse(validator.validate("22 Acacia Avenue"));
		assertFalse(validator.validate("gc.com.br"));
		assertFalse(validator.validate("@test.com.br"));
		assertFalse(validator.validate(""));
	}

	@Test
	public void should_not_validate_null_address() {
		assertFalse(validator.validate(null));
	}
}
