package com.se.cgapi.services;


import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Logger;

public class CardServices {


    //Database Connection
    private final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:Zxc123654@ds247007.mlab.com:47007/se-cardgame"));
    private final MongoDatabase db = mongoClient.getDatabase("se-cardgame");
    private final Gson gson = new Gson();

    //Collections
    //private MongoCollection<Document> USERS = db.getCollection("users");
    private MongoCollection USERS = db.getCollection("cards");
    private Logger logger = Logger.getLogger(getClass().getName());

    public



}
