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
import java.util.List;
import java.util.logging.Logger;


@Path("/v1")
@ApplicationPath("/api")
public class Service extends Application{

    @Context
    private HttpServletRequest httpRequest;

    @Resource
    WebServiceContext wsContext;

    private Logger logger = Logger.getLogger(getClass().getName());



    private UserServices u = new UserServices();

    //Test operation
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String helloWorld(@Context HttpServletRequest req){

        JsonObject result = new JsonObject();


        result.addProperty("ip", req.getRemoteAddr());


        return result.toString();
    }

    //Login
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login(@FormParam("username") String uname, @FormParam("pword") String password, @Context HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session.getAttribute("is_logged") != null){
            logger.info("SESSION_K: " + session.getAttribute("is_logged"));
        }else {
            session.setAttribute("is_logged", 0);
        }

        return u.login(uname, password, session);

    }

    //Login
    //Only for cookie testing, FORBIDDEN through GET
    /*@GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String login2(@QueryParam("username") String uname, @QueryParam("pword") String password, @Context HttpServletRequest req){
        HttpSession session = req.getSession();
        if(session.getAttribute("is_logged") != null){
            logger.info("SESSION_K: " + session.getAttribute("is_logged"));
        }else {
            session.setAttribute("is_logged", 0);
        }

        return u.login(uname, password, session);

    }*/

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

    @GET
    @Path("/getActiveUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public String getActUsers(){

        JsonObject result = new JsonObject();
        result.addProperty("ok", true);
        String l = u.getActiveUserList();
        result.addProperty("users", l);

        return result.toString();

    }
}
