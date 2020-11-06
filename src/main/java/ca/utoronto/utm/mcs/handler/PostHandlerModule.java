package ca.utoronto.utm.mcs.handler;

import ca.utoronto.utm.mcs.service.PostService;
import dagger.Module;
import dagger.Provides;

@Module
public class PostHandlerModule {

	PostHandler postHandler;
	
	public PostHandlerModule(PostHandler postHandler) {
		this.postHandler = postHandler;
	}
	
	public PostHandlerModule() {}

	@Provides PostHandler providePostHandler(PostService postService) {
		postHandler = new PostHandler(postService);
		return postHandler;
	}
}
