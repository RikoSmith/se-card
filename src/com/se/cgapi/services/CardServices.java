package com.se.cgapi.services;


import com.google.gson.Gson;
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
        Document result = new Document();

        Document query = new Document();
        query.put("id", Integer.parseInt(id));
        FindIterable res = CARDS.find(query);
        MongoCursor cursor = res.iterator();

        if (!cursor.hasNext()) {
            result.append("ok", false);
            result.append("err", "Card with such id does not exist.");
        } else {
            Document jo = (Document) cursor.next();
            result.append("ok", true);
            result.append("card", jo);

        }

        return result.toJson();

    }

    public String getAllCards(){
        Document result = new Document();

        Document query = new Document();
        FindIterable res = CARDS.find(query);
        MongoCursor cursor = res.iterator();

        List<Document> all = new ArrayList<>();

        while (cursor.hasNext()){
            Document jo = (Document) cursor.next();
            all.add(jo);
        }

        result.append("ok", true);
        result.append("data", all);

        return result.toJson();

    }




}
