package ca.utoronto.utm.mcs.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import ca.utoronto.utm.mcs.Dagger;
import ca.utoronto.utm.mcs.DaggerComponent;
import ca.utoronto.utm.mcs.domain.Post;
import ca.utoronto.utm.mcs.exception.DocNotExistException;
import ca.utoronto.utm.mcs.exception.InvalidIdException;
import ca.utoronto.utm.mcs.exception.MissingRequiredInfoException;

public class PostDAO {

	Dagger dagger;
	MongoDatabase db = null;

	@Inject
	public PostDAO(Dagger dagger) {
		this.dagger = dagger;
		db = dagger.getDb().getDatabase(DaggerComponent.DATABASE_NAME);
	}

	public String savePost(Post post) throws MissingRequiredInfoException {
		if (post.getAuthor() == null || post.getContent() == null || post.getTitle() == null || post.getTags() == null)
			throw new MissingRequiredInfoException("Required fields are missing.");
		MongoCollection col = db.getCollection(DaggerComponent.COLLECTION_NAME);
		Document doc = new Document();
		doc.append("title", post.getTitle());
		doc.append("author", post.getAuthor());
		doc.append("content", post.getContent());
		doc.append("tags", post.getTags());
		col.insertOne(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		return id.toString();
	}

	public Post findPostById(String _id) throws MissingRequiredInfoException, DocNotExistException, InvalidIdException {
		if (_id == null)
			throw new MissingRequiredInfoException("Id is null.");
		MongoCollection col = db.getCollection(DaggerComponent.COLLECTION_NAME);
		Post post = new Post();
		FindIterable rs = col.find(Filters.eq("_id", new ObjectId(_id)));
		Document doc = (Document) rs.first();
		if (doc != null) {
			return buildPostFromDoc(doc);
		} else {
			throw new DocNotExistException("That document does not exist");
		}
	}

	private Post buildPostFromDoc(Document doc) throws InvalidIdException {
		Post post = new Post();
		post.setAuthor(doc.getString("author"));
		post.setContent(doc.getString("content"));
		ObjectId docId = doc.getObjectId("_id");
		if (doc == null)
			throw new InvalidIdException("That id is not hexadecimal");
		post.setId(doc.getObjectId("_id").toString());
		post.setTags(doc.getList("tags", String.class));
		post.setTitle(doc.getString("title"));
		return post;
	}

	public List<Post> findPostsWithTitle(String title) throws InvalidIdException, DocNotExistException {
		MongoCollection col = db.getCollection(DaggerComponent.COLLECTION_NAME);
		List<Post> posts = new ArrayList<>();
		BasicDBObject sortObj = new BasicDBObject();
		Bson sort = new BasicDBObject("title", 1);
		List<Document> docs = (List<Document>) col.find(Filters.regex("title", title)).sort(sort)
				.into(new ArrayList<Document>());
		if (!docs.isEmpty()) {
			for (Document doc : docs) {
				Post post = buildPostFromDoc(doc);
				posts.add(post);
			}
		} else {
			throw new DocNotExistException("That document does not exist");
		}
		return posts;
	}

	public void deletePostById(String postId) throws MissingRequiredInfoException, DocNotExistException {
		if (postId == null) throw new MissingRequiredInfoException("The Id cannot be null");
		MongoCollection col = db.getCollection(DaggerComponent.COLLECTION_NAME);
		ObjectId id = new ObjectId(postId);
		if (col.find(Filters.eq("_id", id)).first() != null) {
			col.deleteOne(Filters.eq("_id", new ObjectId(postId)));
		} else {
			throw new DocNotExistException("That document does not exist.");
		}
	}
}
