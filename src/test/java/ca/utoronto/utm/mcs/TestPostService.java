package ca.utoronto.utm.mcs;

import java.util.ArrayList;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import ca.utoronto.utm.mcs.domain.Post;
import ca.utoronto.utm.mcs.service.PostService;

public class TestPostService {

	PostService postService;
	
	@Inject
	public TestPostService(PostService postService) {
		this.postService = postService;
	}
	
	@Test
	public void test() {
		Post post = new Post();
		post.setAuthor("author");
		post.setContent("content");
		post.setTitle("title");
		post.setTags(new ArrayList<String>());
		
		String id = postService.savePost(post);
		Assert.assertNotNull(id);
	}

}
