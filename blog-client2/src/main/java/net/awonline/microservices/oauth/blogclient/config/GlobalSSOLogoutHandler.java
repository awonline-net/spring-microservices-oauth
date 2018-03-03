package net.awonline.microservices.oauth.blogclient.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class GlobalSSOLogoutHandler implements LogoutHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalSSOLogoutHandler.class);

	private static final String OAUTH_SSO_LOGOUT_ENDPOINT = "https://localhost:9001/uaa/globalLogout";

	@Autowired
	private OAuth2RestOperations restTemplate;

	@Override
	public void logout(HttpServletRequest req, HttpServletResponse res, Authentication authentication) {
		try {

			HttpEntity<String> request = new HttpEntity<String>("", new HttpHeaders());
			restTemplate.postForEntity(OAUTH_SSO_LOGOUT_ENDPOINT, request, String.class);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}