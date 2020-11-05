package ca.utoronto.utm.mcs.handler;

import ca.utoronto.utm.mcs.DaggerComponent;
import dagger.Component;

@Component(modules = PostHandlerModule.class, dependencies = DaggerComponent.class)
public interface PostHandlerComponent {
	public PostHandler getHandler();
}
