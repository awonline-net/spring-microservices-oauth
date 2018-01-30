package net.awonline.microservices.oauth.commentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.awonline.microservices.oauth.commentservice.model.Comment;
import net.awonline.microservices.oauth.commentservice.service.CommentService;

@RestController
@RequestMapping(value = "v1/posts/{postId}/comments")
public class CommentServiceController {

	@Autowired
	CommentService commentService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Comment> getComments(@PathVariable("postId") String postId) {
		return commentService.getComments(postId);
	}
}
