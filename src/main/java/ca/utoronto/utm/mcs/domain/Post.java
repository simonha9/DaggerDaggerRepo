package ca.utoronto.utm.mcs.domain;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Post {

	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String title;
	private String author;
	private String content;
	private List<String> tags = new ArrayList<>();
	
	public Post(String title, String author, String content, List<String> tags) {
		super();
		this.title = title;
		this.author = author;
		this.content = content;
		this.tags = tags;
	}
	
	public Post() {}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	
	
	
}
