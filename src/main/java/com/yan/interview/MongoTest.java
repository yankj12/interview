package com.yan.interview;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


/**
 * http://mongodb.github.io/mongo-java-driver/3.5/driver/getting-started/quick-start/
 * @author Yan
 *
 */
public class MongoTest {

	public static void main(String[] args) {
		//You can instantiate a MongoClient object without any parameters to connect to a MongoDB instance running on localhost on port 27017
		MongoClient mongoClient = new MongoClient();
		
		//Once you have a MongoClient instance connected to a MongoDB deployment, use the MongoClient.getDatabase() method to access a database.
		//Specify the name of the database to the getDatabase() method. If a database does not exist, MongoDB creates the database when you first store data for that database.
		MongoDatabase database = mongoClient.getDatabase("manage");
		
		MongoCollection<Document> collection = database.getCollection("TeamUser");
		
		//To return the first document in the collection, use the find() method without any parameters and chain to find() method the first() method.
		Document myDoc = collection.find().first();
		System.out.println(myDoc.toJson());
		
		
	}

}
