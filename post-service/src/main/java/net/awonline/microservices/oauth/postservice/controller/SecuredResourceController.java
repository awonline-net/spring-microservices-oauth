package net.awonline.microservices.oauth.postservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "v1/posts")
public class SecuredResourceController {

	@RequestMapping("/secured")
	@ResponseBody
	public String getSecuredResource() {
		return "Secured resource from Post Resource Server";
	}
}
