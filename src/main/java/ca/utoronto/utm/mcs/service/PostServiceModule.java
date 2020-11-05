package ca.utoronto.utm.mcs.service;

import ca.utoronto.utm.mcs.dao.PostDAO;
import dagger.Module;
import dagger.Provides;

@Module
public class PostServiceModule {

	PostService postService;
	
	public PostServiceModule(PostService postService) {
		this.postService = postService;
	}
	
	public PostServiceModule() {}
	
	@Provides PostService providePostService(PostDAO postDAO) {
		postService = new PostService(postDAO);
		return postService;
	}
}
