package com.se.cgapi.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.se.cgapi.utils.RandomString;
import com.se.cgapi.utils._SessionPair;
import org.bson.Document;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;


public class UserServices {

    //Database Connection
    private final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:Zxc123654@ds247007.mlab.com:47007/se-cardgame"));
    private final MongoDatabase db = mongoClient.getDatabase("se-cardgame");
    private final Gson gson = new Gson();

    //Collections
    //private MongoCollection<Document> USERS = db.getCollection("users");
    private MongoCollection USERS = db.getCollection("users");
    private Logger logger = Logger.getLogger(getClass().getName());

    //List of logged users
    public static final List<_SessionPair> activeUsers = new ArrayList<>();

    //Random String generator
    private final RandomString gen;

    public UserServices(){
        gen = new RandomString(32);
    }

    //---Handles signin in
    //
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String login(String u, String pw, HttpSession sess){
        JsonObject result = new JsonObject();


        if((int)sess.getAttribute("is_logged") != 1) {
            Document query = new Document();
            query.put("username", u);
            FindIterable res = USERS.find(query);
            MongoCursor cursor = res.iterator();
            if (!cursor.hasNext()) {
                result.addProperty("ok", false);
                result.addProperty("err", "User with such username does not exist.");
            } else {
                Document jo = (Document) cursor.next();
                String n = (String) jo.get("username");
                if (n.equals(u)) {
                    String p = (String) jo.get("pword");
                    if (pw.equals(p)) {
                        result.addProperty("ok", true);
                        result.addProperty("username", n);
                        result.addProperty("lastname", (String) jo.get("lastname"));
                        result.addProperty("email", (String) jo.get("email"));
                        String key = this.addActiveUser(n);
                        logger.info(activeUsers.toString());
                        result.addProperty("sessKey", key);
                        sess.setAttribute("is_logged", 1);
                    } else {
                        result.addProperty("ok", false);
                        result.addProperty("err", "Incorrect password.");
                    }
                }
            }
        }else {
            result.addProperty("ok", false);
            result.addProperty("err", "User is alredy logged in.");
        }

        return result.toString();

    }

    //---Handles sign up
    //  Google OAuth 2.0 credentials:
    //  Client ID: 754560524206-n4vo5fteruh5kiuaf09sth4ghevj2aau.apps.googleusercontent.com
    //  Secret ID: 8hkJqWQIgENmtpxdgKBhJ1PH
    //
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String signUp(String uname, String pass, String name, String lastname, String email){

        JsonObject result = new JsonObject();


        //pass = pass.decodeFromCache

        Document newUser = new Document();
        newUser.append("username", uname);
        newUser.append("pword", pass);
        newUser.append("name", name);
        newUser.append("lastname", lastname);
        newUser.append("email", email);

        try{
            USERS.insertOne(newUser);

            result.addProperty("ok", true);
            result.addProperty("newuser", uname);
        }catch (Exception e){
            result.addProperty("ok", false);
            result.addProperty("err", e.toString());
        }

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


    public String addActiveUser(String user){
        String key = gen.nextString();
        logger.info("ADDED/ Added new active user user");
        activeUsers.add(new _SessionPair(key, user));
        return key.toString();
    }

    public boolean removeActiveUser(String user, String key){
        return activeUsers.remove(new _SessionPair(key, user));
    }

    public String getActiveUserList(){
        List<String> list = new ArrayList<>();
        logger.info("getAct" + activeUsers.toString());
        for(_SessionPair s : activeUsers){
            logger.info("getAct" + activeUsers.toString());
            list.add(s.getUsername());
        }
        return gson.toJson(list);
    }



}
