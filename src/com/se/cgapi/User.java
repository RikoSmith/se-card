package com.se.cgapi;

import com.google.gson.JsonObject;
import com.mongodb.*;

public class User {

    //Database Connection
    private MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:Zxc123654@ds247007.mlab.com:47007/se-cardgame"));
    private DB db = mongoClient.getDB("se-cardgame");
    //private MongoCredential credential = MongoCredential.createCredential("admin", "se-cardgame", "Zxc123654".toCharArray());

    //Collections
    //private MongoCollection<Document> USERS = db.getCollection("users");
    private DBCollection USERS = db.getCollection("users");

    public User(){

    }


    public String login(String u, String pw){
        JsonObject result = new JsonObject();

        DBObject query = new BasicDBObject("username", u);
        DBCursor cursor = USERS.find(query);

        if(cursor.count() == 0){
            result.addProperty("ok", false);
            result.addProperty("err", "User with such username does not exist.");
        }else {
            DBObject jo = cursor.one();
            String n = (String)cursor.one().get("username");
            if (n.equals(u)){
                String p = (String)cursor.one().get("pword");
                if(pw.equals(p)){
                    result.addProperty("ok", true);
                    result.addProperty("username", n);
                }else {
                    result.addProperty("ok", false);
                    result.addProperty("err", "Incorrect password.");
                }
            }
        }

        return result.toString();

    }

}
