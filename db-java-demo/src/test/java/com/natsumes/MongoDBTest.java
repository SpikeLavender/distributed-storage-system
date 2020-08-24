package com.natsumes;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import java.util.function.Consumer;

public class MongoDBTest {

    @Test
    public void testAdd() {
        MongoCredential credential = MongoCredential.createCredential("natsume", "natsumes", "123456".toCharArray());
        ServerAddress serverAddress = new ServerAddress("123.207.218.247", 27017);
        MongoClient mongoClient = new MongoClient(serverAddress, credential, MongoClientOptions.builder().build());
        MongoDatabase database = mongoClient.getDatabase("natsumes");
        MongoCollection<Document> collection = database.getCollection("user_info");
        Document document = Document.parse(
                "{name: 'testJava', city:'shanghai'}"
        );
        collection.insertOne(document);
        mongoClient.close();
    }

    @Test
    public void testQuery() {
        MongoCredential credential = MongoCredential.createCredential("natsume", "natsumes", "123456".toCharArray());
        ServerAddress serverAddress = new ServerAddress("123.207.218.247", 27017);
        MongoClient mongoClient = new MongoClient(serverAddress, credential, MongoClientOptions.builder().build());
        MongoDatabase database = mongoClient.getDatabase("natsumes");
        MongoCollection<Document> collection = database.getCollection("user_info");
        FindIterable<Document> findIterable = collection.find();
        findIterable.forEach((Consumer<? super Document>) System.out::println);
        mongoClient.close();
    }
}
