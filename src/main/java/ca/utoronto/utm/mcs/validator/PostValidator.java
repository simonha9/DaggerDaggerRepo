package ca.utoronto.utm.mcs.validator;

import java.util.ArrayList;
import java.util.List;

import ca.utoronto.utm.mcs.exception.ValidationFailedException;

public class PostValidator {

	private String title;
	private String author;
	private String content;
	private List<String> tags = new ArrayList<>();
	private String id;
	
	public PostValidator(String title, String author, String content, List<String> tags) {
		super();
		this.title = title;
		this.author = author;
		this.content = content;
		this.tags = tags;
	}

	public PostValidator(String title, String author, String content, List<String> tags, String id) {
		super();
		this.title = title;
		this.author = author;
		this.content = content;
		this.tags = tags;
		this.id = id;
	}
	
	public void validate() throws ValidationFailedException {
		if (title == null || author == null || content == null || tags == null) 
			throw new ValidationFailedException("Request is missing required information");
	}
	
	

	
	
}
