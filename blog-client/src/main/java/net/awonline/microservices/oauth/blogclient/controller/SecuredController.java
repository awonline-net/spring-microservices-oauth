package net.awonline.microservices.oauth.blogclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecuredController {

	// TODO Use Zuul + Eureka
	// TODO Use Feign
	private static final String POST_SERVICE_SECURED_SERVICE_URL = "http://localhost:8001/post-service/v1/posts/secured";

	@Autowired
	private OAuth2RestOperations restTemplate;

	@RequestMapping(name = "/secured")
	@ResponseBody
	public String getSecuredResource() {
		return restTemplate.getForObject(POST_SERVICE_SECURED_SERVICE_URL, String.class);
	}
}
