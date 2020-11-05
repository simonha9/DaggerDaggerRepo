package ca.utoronto.utm.mcs.service;

import ca.utoronto.utm.mcs.dao.PostDAOComponent;
import ca.utoronto.utm.mcs.handler.PostHandler;
import dagger.Component;

@Component(modules = PostServiceModule.class, dependencies = PostDAOComponent.class)
public interface PostServiceComponent {
	void inject(PostHandler handler);
	PostService providePostService();
}
