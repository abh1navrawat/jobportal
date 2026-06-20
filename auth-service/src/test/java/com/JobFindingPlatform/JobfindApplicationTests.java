package com.JobFindingPlatform;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class JobfindApplicationTests {

	@Test
	void printHash() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hash = encoder.encode("plsgod");
		System.out.println("BCRYPT_HASH_FOR_PLSGOD: " + hash);
	}

}
