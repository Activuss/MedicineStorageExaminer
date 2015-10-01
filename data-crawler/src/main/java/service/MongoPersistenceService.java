package service;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public final class MongoPersistenceService implements PersistenceService {

	public final static String HOST_NAME = "localhost";
	public final static int PORT_NUMBER = 27017;
	public final static String DB_NAME = "medical-leftovers";
	public final static String COLLECTION_NAME = "medicals";

	@Override
	public void addMedical(String medicalJson) {
		MongoCollection<Document> collection = getMongoDBCollection(HOST_NAME, PORT_NUMBER, DB_NAME, COLLECTION_NAME);

		BasicDBObject dbObject = (BasicDBObject) JSON.parse(medicalJson);
		collection.insertOne(new Document(dbObject));
	}

	@Override
	public String getMedical(String location, String medicalName) {
		MongoCollection<Document> collection = getMongoDBCollection(HOST_NAME, PORT_NUMBER, DB_NAME, COLLECTION_NAME);

		Document foundDocument = collection.find(new BasicDBObject("location", location).append("name", medicalName))
				.first();
		return foundDocument.toJson();
	}

	@Override
	public List<String> getAllMedical() {
		MongoCollection<Document> collection = getMongoDBCollection(HOST_NAME, PORT_NUMBER, DB_NAME, COLLECTION_NAME);

		List<String> result = new ArrayList<>();
		List<Object> foundDocuments = collection.find().into(new ArrayList<>());
		for (Object object : foundDocuments) {
			result.add(((Document) object).toJson());
		}

		return result;
	}

	private MongoCollection<Document> getMongoDBCollection(String hostName, int portNumber, String dbName,
	                                                       String collectionName) {
		MongoClient mongoClient = new MongoClient(hostName, portNumber);
		MongoDatabase db = mongoClient.getDatabase(dbName);
		MongoCollection<Document> collection = db.getCollection(collectionName);
		mongoClient.close();
		return collection;
	}
}
