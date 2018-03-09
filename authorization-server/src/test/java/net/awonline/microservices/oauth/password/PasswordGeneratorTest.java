package net.awonline.microservices.oauth.password;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import net.awonline.microservices.oauth.AuthorizationServerApplicationTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordGeneratorTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationServerApplicationTests.class);

	@Autowired
	PasswordEncoder passwordEncoder;

	@Test
	public void generateBCryptPasswords() {

		String password1 = "blog-web-client-secret";
		String password2 = "readonly-blog-web-client-secret";

		LOGGER.info(passwordEncoder.encode(password1));
		LOGGER.info(passwordEncoder.encode(password2));
	}
}
