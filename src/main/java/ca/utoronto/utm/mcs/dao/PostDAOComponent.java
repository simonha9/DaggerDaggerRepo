package ca.utoronto.utm.mcs.dao;

import ca.utoronto.utm.mcs.DaggerComponent;
import ca.utoronto.utm.mcs.service.PostService;
import dagger.Component;

@Component(modules = PostDAOModule.class, dependencies = DaggerComponent.class)
public interface PostDAOComponent {
	void inject(PostService service);
	PostDAO providePostDAO();
}
