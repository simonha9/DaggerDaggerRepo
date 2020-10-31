package ca.utoronto.utm.mcs;

import java.io.IOException;

import javax.inject.Singleton;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.sun.net.httpserver.HttpServer;

import dagger.Module;
import dagger.Provides;

@Module
public class DaggerModule {

	private static HttpServer server;
	private static MongoClient db;

	@Provides
	@Singleton
	public HttpServer provideHttpServer() {
		if (server == null) {
			try {
				server = HttpServer.create();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return server;
	}

	@Provides
	@Singleton
	public MongoClient provideMongoClient() {
		if (db == null)
			db = MongoClients.create();
		return db;
	}

}
