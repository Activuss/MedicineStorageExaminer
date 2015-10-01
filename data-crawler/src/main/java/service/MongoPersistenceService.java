package service;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

public final class MongoPersistenceService implements PersistenceService {

	public final static String HOST_NAME = "localhost";
	public final static int PORT_NUMBER = 27017;
	public final static String DB_NAME = "medical-leftovers";
	public final static String COLLECTION_NAME = "medicals";

	public void addMedical(String medicalJson) {
		MongoCollection<Document> collection = getMongoDBCollection(HOST_NAME, PORT_NUMBER, DB_NAME, COLLECTION_NAME);

		BasicDBObject dbObject = (BasicDBObject) JSON.parse(medicalJson);
		collection.insertOne(new Document(dbObject));		
	}

	public String getMedical(String location, String medicalName) {
		MongoCollection<Document> collection = getMongoDBCollection(HOST_NAME, PORT_NUMBER, DB_NAME, COLLECTION_NAME);
		
		Document foundDocument = collection.find(new BasicDBObject("location", location).append("name", medicalName))
				.first();
		return foundDocument.toJson();
	}
	
	private MongoCollection<Document> getMongoDBCollection(String hostName, int portNumber, String dbName,
			String collectionName) {
		MongoClient mongoClient = new MongoClient(HOST_NAME, PORT_NUMBER);
		MongoDatabase db = mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);
		mongoClient.close();
		return collection;
	}


}
