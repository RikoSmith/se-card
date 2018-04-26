package com.se.cgapi;


import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.se.cgapi.models.Lobby;
import com.se.cgapi.services.CardServices;
import com.se.cgapi.services.LobbyServices;
import com.se.cgapi.services.UserServices;
import com.se.cgapi.utils.RandomString;
import org.bson.Document;

import javax.annotation.Resource;
import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceContext;
import java.util.logging.Logger;


@Path("/v1")
@ApplicationPath("/api")
public class Service extends Application {

    @Context
    private HttpServletRequest httpRequest;

    @Resource
    WebServiceContext wsContext;

    private Logger logger = Logger.getLogger(getClass().getName());

    private UserServices u = new UserServices();
    private CardServices c = new CardServices();
    private LobbyServices l = new LobbyServices();

    private final static RandomString gen = new RandomString();

    private final MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:Zxc123654@ds247007.mlab.com:47007/se-cardgame"));
    private final MongoDatabase db = mongoClient.getDatabase("se-cardgame");
    private MongoCollection LOBBIES = db.getCollection("lobby");

    //Test operation
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String helloWorld(@Context HttpServletRequest req) {

        try {
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Hahahahahahahahahahahahaha!";
    }

    ////////--USER SERVICES--////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Login
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String login(@FormParam("username") String uname, @FormParam("pword") String password, @Context HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("is_logged") != null) {
            logger.info("SESSION_K: " + session.getAttribute("is_logged"));
        } else {
            session.setAttribute("is_logged", 0);
        }

        return u.login(uname, password, session);

    }

    //Login
    //Only for cookie testing, FORBIDDEN through GET
    /*@GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login2(@QueryParam("username") String uname, @QueryParam("pword") String password, @Context HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("is_logged") != null) {
            logger.info("SESSION_K: " + session.getAttribute("is_logged"));
        } else {
            session.setAttribute("is_logged", 0);
        }

        return u.login(uname, password, session);

    }*/


    @GET
    @Path("/goauth")
    @Produces(MediaType.APPLICATION_JSON)
    public String googleAuth(@Context HttpServletRequest req, @Context HttpServletResponse res) {


        ServiceBuilder builder = new ServiceBuilder("");

        final OAuth20Service service = new ServiceBuilder("33969162692-4555s1e38op24e285gqecob95lj76rhn.apps.googleusercontent.com")
                .apiSecret("1wJ1kaOadFpbqY0FcJGZCJyk").callback("http://localhost:3000/oauth2callback").build(GoogleApi20.instance());

        HttpSession sess = req.getSession();

        sess.setAttribute("goauth", service);
        String url = service.getAuthorizationUrl();


        try {
            res.sendRedirect(url);
        } catch (Exception e) {
            return e.getMessage();
        }

        return "Hi";
    }


    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public String signUpA(@FormParam("username") String uname, @FormParam("pass") String pass,
                          @FormParam("name   ") String name, @FormParam("lastname") String lastname, @FormParam("email") String email) {

        return u.signUp(uname, pass, name, lastname, email);

    }

    @GET
    @Path("/searchEmail")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchEmail(@QueryParam("email") String email) {

        return u.searchEmail(email);

    }

    @GET
    @Path("/searchUsername")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchUsername(@QueryParam("uname") String uname) {

        return u.searchUsername(uname);

    }

    @GET
    @Path("/getActiveUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public String getActUsers() {

        JsonObject result = new JsonObject();
        result.addProperty("ok", true);
        String l = u.getActiveUserList();
        result.addProperty("users", l);

        return result.toString();

    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public String logout(@Context HttpServletRequest req) {
        HttpSession session = req.getSession();

        session.invalidate();

        return "{\"ok\": true, \"message\": \"You successfully logged out\"}";

    }


    ////////--CARD SERVICES--////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GET
    @Path("/getCard")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCard(@QueryParam("id") String id) {
        return c.getCard(id);
    }


    @GET
    @Path("/getAllCards")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCard() {
        return c.getAllCards();
    }


    ////////--LOBBY SERVICES--////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GET
    @Path("/newLobby")
    @Produces(MediaType.APPLICATION_JSON)
    public String createLobby(@Context HttpServletRequest req) {
        HttpSession sess = req.getSession();
        if (sess.getAttribute("username") != null) {
            Document lobby;
            if (sess.getAttribute("lobby") != null) {
                lobby = (Document) sess.getAttribute("lobby");
            } else {
                lobby = new Document();
                lobby.append("p1", sess.getAttribute("username"));
                lobby.append("p2", null);
                lobby.append("age", 0);
                lobby.append("isFull", false);
                lobby.append("expectedMove", sess.getAttribute("username"));
                lobby.append("lastdata", null);
                lobby.append("code", gen.nextString());
                LOBBIES.insertOne(lobby);
            }
            return lobby.toJson();
        } else {
            return "{\"ok\": false, \"err\":\"You are not looged in\"}";
        }
    }

    @GET
    @Path("/joinLobby")
    @Produces(MediaType.APPLICATION_JSON)
    public String createLobby(@QueryParam("key") String key, @Context HttpServletRequest req) {
        HttpSession sess = req.getSession();
        Document query = new Document();
        Document ress = new Document();
        query.put("code", key);
        FindIterable res = LOBBIES.find(query);
        MongoCursor cursor = res.iterator();

        if (!cursor.hasNext()) {
            Document a = new Document();
            a.append("ok", false);
            a.append("err", "No such lobby!");
            ress =  a;
        } else {
            if (sess.getAttribute("username") != null) {
                Document lobby = (Document)cursor.next();
                lobby.put("p2", (String)sess.getAttribute("username"));
                lobby.put("isFull", true);
                LOBBIES.replaceOne(query, lobby);
                logger.info("JOINED_LOBBY: " + lobby.toJson().toString());
                ress.append("ok", true);
                ress.append("message", "You've successfully joined the lobby");
            }else {
                return "{\"ok\": false, \"err\":\"You are not looged in\"}";
            }
        }

        return ress.toJson();
    }

    @GET
    @Path("/checkLobby")
    @Produces(MediaType.APPLICATION_JSON)
    public String checkLobby(@QueryParam("key") String key, @Context HttpServletRequest req) {
        HttpSession sess = req.getSession();
        Document query = new Document();
        Document ress = new Document();
        query.put("code", key);
        FindIterable res = LOBBIES.find(query);
        MongoCursor cursor = res.iterator();

        if (!cursor.hasNext()) {
            Document a = new Document();
            a.append("ok", false);
            a.append("err", "No such lobby!");
            ress =  a;
        } else {
            if (sess.getAttribute("username") != null) {
                Document lobby = (Document)cursor.next();
                String reqName = (String)sess.getAttribute("username");
                if(lobby.getString("p1").equals(reqName) || lobby.getString("p2").equals(reqName)){
                    if(lobby.getString("p2") != null){
                        ress = new Document();
                        ress.append("ok", true);
                        ress.append("lobby", lobby);
                    }else{
                        ress.append("ok", true);
                        ress.append("lobby", lobby);
                    }
                }else {
                    ress = new Document();
                    ress.append("ok", false);
                    ress.append("err", "You are not in this lobby!");
                }
            }else {
                return "{\"ok\": false, \"err\":\"You are not looged in\"}";
            }
        }
        return ress.toJson();
    }



    @POST
    @Path("/turn")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String postState(@FormParam("key") String key, @FormParam("data") String data, @Context HttpServletRequest req) {
        HttpSession sess = req.getSession();
        Document query = new Document();
        Document ress = new Document();
        query.put("code", key);
        FindIterable res = LOBBIES.find(query);
        MongoCursor cursor = res.iterator();

        if (!cursor.hasNext()) {
            Document a = new Document();
            a.append("ok", false);
            a.append("err", "No such lobby!");
            ress =  a;
        } else {
            if (sess.getAttribute("username") != null) {
                Document lobby = (Document)cursor.next();
                String reqName = (String)sess.getAttribute("username");

                if(lobby.getString("p1").equals(reqName) || lobby.getString("p2").equals(reqName)){
                    if(reqName.equals(lobby.getString("expectedMove"))){
                        lobby.put("lastdata", data);
                        String nextMove;
                        if(lobby.getString("p1").equals(reqName)){
                            nextMove = lobby.getString("p2");
                        }else {
                            nextMove = lobby.getString("p1");
                        }
                        lobby.put("expectedMove", nextMove);
                        LOBBIES.replaceOne(query, lobby);
                        ress = new Document();
                        ress.append("ok", true);
                        ress.append("message", "Successful turn!");
                    }else{
                        ress = new Document();
                        ress.append("ok", false);
                        ress.append("err", "It's not your turn!");
                    }
                }else {
                    ress = new Document();
                    ress.append("ok", false);
                    ress.append("err", "You are not in this lobby!");
                }


            }else {
                return "{\"ok\": false, \"err\":\"You are not looged in\"}";
            }

        }

        return ress.toJson();
    }


}