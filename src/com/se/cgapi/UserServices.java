package com.se.cgapi;

import com.google.gson.JsonObject;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.BSONObject;
import org.bson.BsonDocument;
import org.bson.Document;

public class UserServices {

    //Database Connection
    private MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:Zxc123654@ds247007.mlab.com:47007/se-cardgame"));
    private MongoDatabase db = mongoClient.getDatabase("se-cardgame");
    //private MongoCredential credential = MongoCredential.createCredential("admin", "se-cardgame", "Zxc123654".toCharArray());

    //Collections
    //private MongoCollection<Document> USERS = db.getCollection("users");
    private MongoCollection USERS = db.getCollection("users");

    public UserServices(){

    }

    //---Handles signin in
    //
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String login(String u, String pw){
        JsonObject result = new JsonObject();

        Document query = new Document();
        query.put("email", u);
        FindIterable res = USERS.find(query);
        MongoCursor cursor = res.iterator();
        if(!cursor.hasNext()){
            result.addProperty("ok", false);
            result.addProperty("err", "User with such username does not exist.");
        }else {
            Document jo = (Document)cursor.next();
            String n = (String)jo.get("username");
            if (n.equals(u)){
                String p = (String)jo.get("pword");
                if(pw.equals(p)){
                    result.addProperty("ok", true);
                    result.addProperty("username", n);
                    result.addProperty("name", (String)jo.get("name"));
                    result.addProperty("lastname", (String)jo.get("lastname"));
                    result.addProperty("email", (String)jo.get("email"));
                    result.addProperty("password", (String)jo.get("pword"));
                }else {
                    result.addProperty("ok", false);
                    result.addProperty("err", "Incorrect password.");
                }
            }
        }

        return result.toString();

    }

    //---Handles sign up
    //
    //
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String signUp(String uname, String pass, String pass2, String name, String lastname, String email){
        JsonObject result = new JsonObject();

        DBObject newUser = new BasicDBObject();




        //WriteResult res = USERS.insertOne();

        return result.toString();
    }

    //---Checks if there is a user with the same email registered.
    // Returns a uname (ok status true) if it is else empty reponse (ok status false) /
    //
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String searchEmail(String email){
        JsonObject result = new JsonObject();

        Document query = new Document();
        query.put("email", email);
        FindIterable res = USERS.find(query);
        MongoCursor cursor = res.iterator();

        if(!cursor.hasNext()){
            result.addProperty("ok", false);
            result.addProperty("err", "No user with such email");
        }else {
            result.addProperty("ok", true);
            Document jo = (Document)cursor.next();
            String user = (String)jo.get("username");
            result.addProperty("username", user);
        }

        return result.toString();
    }

    //---Checks if there is a user with the same username registered.
    // Returns a uname (ok status true) if there is else empty response (ok status false) /
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String searchUsername(String uname){
        JsonObject result = new JsonObject();

        Document query = new Document();
        query.put("username", uname);
        
        FindIterable res = USERS.find(query);
        MongoCursor cursor = res.iterator();

        if(!cursor.hasNext()){
            result.addProperty("ok", false);
            result.addProperty("err", "No user with such username");
        }else {
            result.addProperty("ok", true);
            Document jo = (Document)cursor.next();
            String em = (String)jo.get("email");
            result.addProperty("email", em);
        }

        return result.toString();
    }

}
