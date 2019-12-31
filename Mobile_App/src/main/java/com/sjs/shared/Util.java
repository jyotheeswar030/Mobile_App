package com.sjs.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Util {
	private final Random RAMDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public String generateUserID(int length) {
		return generateRandomString(length);
	}

	private String generateRandomString(int length) {
		StringBuffer retunValue = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			retunValue.append(ALPHABET.charAt(RAMDOM.nextInt(ALPHABET.length())));
		}
		return new String(retunValue);
	}
}
