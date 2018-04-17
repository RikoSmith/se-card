package com.se.cgapi.services;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CardServices {


    //Database Connection
    private final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:Zxc123654@ds247007.mlab.com:47007/se-cardgame"));
    private final MongoDatabase db = mongoClient.getDatabase("se-cardgame");
    private final Gson gson = new Gson();

    //Collections
    //private MongoCollection<Document> USERS = db.getCollection("users");
    private MongoCollection CARDS = db.getCollection("cards");
    private Logger logger = Logger.getLogger(getClass().getName());

    public String getCard(String id){
        JsonObject result = new JsonObject();

        Document query = new Document();
        query.put("id", Integer.parseInt(id));
        FindIterable res = CARDS.find(query);
        MongoCursor cursor = res.iterator();

        if (!cursor.hasNext()) {
            result.addProperty("ok", false);
            result.addProperty("err", "Card with such id does not exist.");
        } else {
            Document jo = (Document) cursor.next();
            return jo.toJson().toString();
        }

        return result.toString();

    }

    public String getAllCards(){
        Document query = new Document();
        FindIterable res = CARDS.find(query);
        MongoCursor cursor = res.iterator();

        List<String> all = new ArrayList<>();

        while (cursor.hasNext()){
            Document jo = (Document) cursor.next();
            all.add(jo.toJson());
        }

        return all.toString();

    }




}
