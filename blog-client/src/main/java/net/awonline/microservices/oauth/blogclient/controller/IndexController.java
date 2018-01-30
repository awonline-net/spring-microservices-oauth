package net.awonline.microservices.oauth.blogclient.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.awonline.microservices.oauth.blogclient.model.Comment;
import net.awonline.microservices.oauth.blogclient.model.Post;

@Controller
public class IndexController {

	// TODO Use Zuul + Eureka
	// FIXME check Feign project
	private static final String POST_SERVICE_URL = "http://localhost:8001/v1/posts/{postId}";
	private static final String COMMENT_SERVICE_URL = "http://localhost:8002/v1/posts/{postId}/comments";

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping("/")
	public String greeting(@RequestParam(value = "postId", required = false, defaultValue = "1") String postId,
			Model model) {

		String postJson = restTemplate.getForObject(POST_SERVICE_URL, String.class, postId);
		String commentsJson = restTemplate.getForObject(COMMENT_SERVICE_URL, String.class, postId);

		Post post = getPostFromJson(postJson);
		List<Comment> comments = getCommentsFromJson(commentsJson);

		model.addAttribute("postId", postId);
		model.addAttribute("post", post);
		model.addAttribute("comments", comments);

		return "index";
	}

	private Post getPostFromJson(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Post post = null;
		try {
			post = mapper.readValue(json, Post.class);
		} catch (Exception e) {
			e.printStackTrace();
			post = new Post();
		}
		return post;
	}

	@SuppressWarnings("unchecked")
	private List<Comment> getCommentsFromJson(String json) {
		ObjectMapper mapper = new ObjectMapper();
		List<Comment> comments = new ArrayList<>();
		try {
			comments = mapper.readValue(json, List.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
}
