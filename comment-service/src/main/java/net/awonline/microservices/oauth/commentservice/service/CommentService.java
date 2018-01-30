package net.awonline.microservices.oauth.commentservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.awonline.microservices.oauth.commentservice.model.Comment;

@Service
public class CommentService {

	private static Map<String, List<Comment>> MAP = new HashMap<>();

	private static int POST_COUNTER = 0;
	private static int COMMENT_COUNTER = 0;

	static {

		MAP = new HashMap<>();

		MAP.put("" + (++POST_COUNTER), Arrays.asList(generateComment(++COMMENT_COUNTER, POST_COUNTER),
				generateComment(++COMMENT_COUNTER, POST_COUNTER), generateComment(++COMMENT_COUNTER, POST_COUNTER)));

		MAP.put("" + (++POST_COUNTER), Arrays.asList(generateComment(++COMMENT_COUNTER, POST_COUNTER)));

		MAP.put("" + (++POST_COUNTER), Arrays.asList(generateComment(++COMMENT_COUNTER, POST_COUNTER),
				generateComment(++COMMENT_COUNTER, POST_COUNTER)));
	}

	private static Comment generateComment(int commentId, int postId) {
		Comment comment = new Comment();
		comment.setId("" + commentId);
		comment.setPostId("" + postId);
		comment.setDescription("Description for: Post[" + postId + "], Comment[" + commentId + "]");
		return comment;
	}

	public List<Comment> getComments(String postId) {

		if (!MAP.containsKey(postId)) {
			return new ArrayList<>();
		}

		return MAP.get(postId);
	}
}
