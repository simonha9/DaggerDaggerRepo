package ca.utoronto.utm.mcs;

import dagger.Component;
import javax.inject.Singleton;

@Component(modules = DaggerModule.class)
public interface DaggerComponent {
	
	String DATABASE_NAME = "csc301a2";
	String COLLECTION_NAME = "posts";
	
	public Dagger buildMongoHttp();
}
