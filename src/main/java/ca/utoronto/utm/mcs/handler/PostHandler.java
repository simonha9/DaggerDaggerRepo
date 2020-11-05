package ca.utoronto.utm.mcs.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ca.utoronto.utm.mcs.Utils;
import ca.utoronto.utm.mcs.domain.Post;
import ca.utoronto.utm.mcs.exception.DocNotExistException;
import ca.utoronto.utm.mcs.exception.InvalidRequestMethodException;
import ca.utoronto.utm.mcs.exception.MissingRequiredInfoException;
import ca.utoronto.utm.mcs.service.PostService;

public class PostHandler implements HttpHandler {

	PostService postService;

	@Inject
	public PostHandler(PostService postService) {
		this.postService = postService;
	}

	@Override
	public void handle(HttpExchange r) throws IOException {
		try {
			if (r.getRequestMethod().equals("GET")) {
				handleGet(r);
			} else if (r.getRequestMethod().equals("PUT")) {
				handlePut(r);
			} else if (r.getRequestMethod().equals("DELETE")) {
				handleDelete(r);
			} else {
				throw new InvalidRequestMethodException("Request is not GET, PUT or DELETE");
			}
		} catch (JSONException | IOException | MissingRequiredInfoException e) {
			r.sendResponseHeaders(400, -1);
		} catch (DocNotExistException e) {
			r.sendResponseHeaders(404, -1);
		} catch (InvalidRequestMethodException e) {
			r.sendResponseHeaders(405, -1);
		} catch (Exception e) {
			e.printStackTrace();
			r.sendResponseHeaders(500, -1);
		}
	}

	private void handleGet(HttpExchange r) throws Exception {
		Post post = getPostFromRequestBody(r);
		if (post.getId() != null) {
			Post returnedPost = postService.findPostById(post.getId());
			String response = buildResponse(returnedPost);
			r.getResponseHeaders().set("Content-Type", "appication/json");
			r.sendResponseHeaders(200, response.length());
			OutputStream os = r.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	private void handlePut(HttpExchange r) throws Exception {
		Post post = getPostFromRequestBody(r);
		String id = postService.savePost(post);
		// out id
		
		String response = buildResponse(id);
		r.getResponseHeaders().set("Content-Type", "appication/json");
		r.sendResponseHeaders(200, response.length());
		OutputStream os = r.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	private String buildResponse(String id) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.accumulate("_id", id);
		return obj.toString();
	}
	
	private String buildResponse(Post post) throws JSONException {
		JSONObject obj = new JSONObject();
		JSONObject idObj = new JSONObject();
		idObj.accumulate("$oid", post.getId());
		obj.accumulate("_id", idObj);
		obj.accumulate("title", post.getTitle());
		obj.accumulate("tags", post.getTags());
		obj.accumulate("author", post.getAuthor());
		obj.accumulate("content", post.getContent());
		JSONArray arr = new JSONArray();
		arr.put(obj);
		return arr.toString();
	}

	private void handleDelete(HttpExchange r) throws Exception {

	}

	public JSONObject convertRequestToJSON(HttpExchange r) throws IOException, JSONException {
		String body = Utils.convert(r.getRequestBody());
		JSONObject deserialized = new JSONObject(body);
		return deserialized;
	}

	private Post getPostFromRequestBody(HttpExchange r) throws IOException, JSONException {
		JSONObject deserialized = convertRequestToJSON(r);
		Post post = new Post();
		if (deserialized.has("title"))
			post.setTitle(deserialized.getString("title"));
		if (deserialized.has("author"))
			post.setAuthor(deserialized.getString("author"));
		if (deserialized.has("_id"))
			post.setId(deserialized.getString("_id"));
		if (deserialized.has("content"))
			post.setContent(deserialized.getString("content"));
		if (deserialized.has("tags")) {
			JSONArray arr = deserialized.getJSONArray("tags");
			List<String> tags = new ArrayList<>();
			for (int i = 0; i < arr.length(); i++) {
				String tag = arr.get(i).toString();
				tags.add(tag);
			}
			post.getTags().addAll(tags);
		}

		return post;
	}

}
