package com.contactura.contactura;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassowrdEncoder {

	public static void main(String[] args) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		System.out.println(passwordEncoder.encode("maracuja"));
	}
}
