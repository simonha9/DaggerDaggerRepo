package ca.utoronto.utm.mcs.dao;

import ca.utoronto.utm.mcs.Dagger;
import ca.utoronto.utm.mcs.DaggerDaggerComponent;
import dagger.Module;
import dagger.Provides;

@Module
public class PostDAOModule {
	PostDAO postDAO;
	
	public PostDAOModule(PostDAO postDAO) {
		this.postDAO = postDAO;
	}
	
	public PostDAOModule() {}

	@Provides 
	PostDAO providePostDAO() {
		postDAO = new PostDAO(DaggerDaggerComponent.create().buildMongoHttp());
		return postDAO;
	}
}
