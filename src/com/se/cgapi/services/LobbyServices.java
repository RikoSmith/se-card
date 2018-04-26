package com.se.cgapi.services;

import com.se.cgapi.models.Lobby;

import javax.servlet.http.HttpSession;

public class LobbyServices {

    public LobbyServices(){

    }

    public Lobby newLobby(String username){
        Lobby lobby = new Lobby(username);
        return lobby;
    }

    /*public String joinLobby(String lcode){

    }



    public String getLobbyInfo(String lcode){

    }*/

}
