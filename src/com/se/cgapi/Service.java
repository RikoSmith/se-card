package com.se.cgapi;


import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;


@Path("/v1")
@ApplicationPath("/api")
public class Service extends Application{



    //Test operation
    @GET
    @Path("/helloWorld")
    @Produces(MediaType.APPLICATION_JSON)
    public String helloWorld(){

        JsonObject result = new JsonObject();
        result.addProperty("number", 99);

        return result.toString();
    }

    //Login
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@FormParam("username") String uname, @FormParam("pword") String password){
        User u = new User();
        return u.login(uname, password);
    }

}
