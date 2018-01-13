package com.se.cgapi;


import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.google.gson.Gson;
import org.bson.Document;
import org.xml.sax.helpers.DefaultHandler;


@Path("/v1")
@ApplicationPath("/api")
public class Service extends Application{

    //Gson
    private Gson gson = new Gson();

    //Database Connection
    private MongoClient mClient = new MongoClient("s247007.mlab.com", 47007);
    private MongoDatabase db = mClient.getDatabase("se-cardgame");
    private MongoCredential credential = MongoCredential.createCredential("admin", "se-cardgame", "Zxc123654".toCharArray());

    //Collections
    private MongoCollection<Document> USERS = db.getCollection("users");

    //Test operation
    @GET
    @Path("/helloWorld")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject helloWorld(){

        JsonObject result = new JsonObject();

        result.addProperty("ok", true);

        return result;
    }

    //Login
    @POST
    @Path("/login")
    public String login(@QueryParam("username") String uname, @QueryParam("pword") String password){


        return "ok";
    }

}
