package com.se.cgapi;


import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;


@Path("/v1")
@ApplicationPath("/api")
public class Service extends Application{

    User u = new User();

    //Test operation
    @GET
    @Path("/helloWorld")
    @Produces(MediaType.APPLICATION_JSON)
    public String helloWorld(){

        JsonObject result = new JsonObject();
        result.addProperty("number", 123);

        return result.toString();
    }

    //Login
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@FormParam("username") String uname, @FormParam("pword") String password){

        return u.login(uname, password);
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public String signUp(@FormParam("username") String uname, @FormParam("pass") String pass, @FormParam("pass2") String pass2,
                         @FormParam("name") String name, @FormParam("lastname") String lastname, @FormParam("email") String email){
        return u.signUp(uname, pass, pass2, name, lastname, email);
    }

}
