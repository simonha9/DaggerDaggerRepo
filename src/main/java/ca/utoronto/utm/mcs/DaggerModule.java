package ca.utoronto.utm.mcs;

import java.io.IOException;
import java.net.InetSocketAddress;

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
	HttpServer provideHttpServer() {
		if (server == null) {
			try {
				server = HttpServer.create(new InetSocketAddress("0.0.0.0", App.port), 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return server;
	}

	@Provides
	MongoClient provideMongoClient() {
		if (db == null) {
			String connectionString = ("mongodb://localhost:27017");
			db = MongoClients.create(connectionString);
		}
		return db;
	}

}
