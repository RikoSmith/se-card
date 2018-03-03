package com.se.cgapi;


import com.google.gson.JsonObject;
import com.sun.deploy.net.HttpRequest;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Path("/v1")
@ApplicationPath("/api")
public class Service extends Application{

    @Context
    private HttpServletRequest httpRequest;




    private UserServices u = new UserServices();

    //Test operation
    @GET
    @Path("/helloWorld")
    @Produces(MediaType.APPLICATION_JSON)
    public String helloWorld(){

        JsonObject result = new JsonObject();
        HttpSession sess = httpRequest.getSession();
        if (sess == null)
            throw new WebServiceException("No HTTP Session found");
        Integer times = (Integer) sess.getAttribute("times");
        if(times != null){
            result.addProperty("times", times);
            sess.setAttribute("times", times+1);
        }else {
            result.addProperty("times", 1);
            sess.setAttribute("times", 1);
            sess.setAttribute("num", 43);
        }





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
    public String signUpA(@FormParam("username") String uname, @FormParam("pass") String pass,
                         @FormParam("name   ") String name, @FormParam("lastname") String lastname, @FormParam("email") String email){

        return u.signUp(uname, pass, name, lastname, email);

    }

    @GET
    @Path("/searchEmail")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchEmail(@QueryParam("email") String email){

        return u.searchEmail(email);

    }

    @GET
    @Path("/searchUsername")
    @Produces(MediaType.APPLICATION_JSON)
    public String searchUsername(@QueryParam("uname") String uname){

        return u.searchUsername(uname);

    }

}
