package net.awonline.microservices.oauth.blogclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/restricted")
	public String restricted() {
		return "restricted";
	}
}
