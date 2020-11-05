package ca.utoronto.utm.mcs;

import java.io.IOException;

import javax.inject.Inject;

import ca.utoronto.utm.mcs.dao.DaggerPostDAOComponent;
import ca.utoronto.utm.mcs.dao.PostDAO;
import ca.utoronto.utm.mcs.dao.PostDAOComponent;
import ca.utoronto.utm.mcs.dao.PostDAOModule;
import ca.utoronto.utm.mcs.handler.DaggerPostHandlerComponent;
import ca.utoronto.utm.mcs.handler.PostHandler;
import ca.utoronto.utm.mcs.handler.PostHandlerComponent;
import ca.utoronto.utm.mcs.handler.PostHandlerModule;
import ca.utoronto.utm.mcs.service.DaggerPostServiceComponent;
import ca.utoronto.utm.mcs.service.PostService;
import ca.utoronto.utm.mcs.service.PostServiceComponent;
import ca.utoronto.utm.mcs.service.PostServiceModule;

public class App {
	static int port = 8080;

	@Inject
	PostHandler postHandler;
	@Inject
	Dagger service;

	private App() {
		DaggerComponent component = DaggerDaggerComponent.create();
		service = component.buildMongoHttp();
		postHandler = DaggerPostHandlerComponent.builder()
				.daggerComponent(component).build().getHandler();
				
//				DaggerPostHandlerComponent.builder()
//				.postHandlerModule(new PostHandlerModule())
//				.postServiceComponent(DaggerPostServiceComponent.builder()
//						.postDAOComponent(DaggerPostDAOComponent.builder()
//								.postDAOModule(new PostDAOModule(dagger)).build())
//						.postServiceModule(new PostServiceModule()).build()).build().providePostHandler();
	}

	public static void main(String[] args) throws IOException {
		App app = new App();
		PostHandler handler = app.postHandler;
		Dagger service = app.service;
		// Create your server context here
		service.getServer().start();
		service.getServer().createContext("/api/v1/post", handler);
		System.out.printf("Server started on port %d", port);
	}
}
