package ca.utoronto.utm.mcs.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ca.utoronto.utm.mcs.dao.PostDAO;
import ca.utoronto.utm.mcs.domain.Post;
import ca.utoronto.utm.mcs.exception.DocNotExistException;
import ca.utoronto.utm.mcs.exception.InvalidIdException;
import ca.utoronto.utm.mcs.exception.MissingRequiredInfoException;

public class PostService {

	PostDAO postDAO;

	@Inject
	public PostService(PostDAO postDAO) {
		this.postDAO = postDAO;
	}

	public String savePost(Post post) throws MissingRequiredInfoException {
		return postDAO.savePost(post);
	}
	
	public Post findPostById(String _id) throws MissingRequiredInfoException, DocNotExistException, InvalidIdException {
		return postDAO.findPostById(_id);
	}
	public void deletePostById(String postId) throws MissingRequiredInfoException, DocNotExistException {
		postDAO.deletePostById(postId);
	}

	public List<Post> findPostsWithTitle(String title) throws InvalidIdException, DocNotExistException {
		return postDAO.findPostsWithTitle(title);
	}
}
