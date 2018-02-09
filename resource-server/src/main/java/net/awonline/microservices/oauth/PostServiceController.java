package net.awonline.microservices.oauth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/posts")
public class PostServiceController {

	@RequestMapping("/a")
	public String getComments() {
		return "dupa";
	}
}
