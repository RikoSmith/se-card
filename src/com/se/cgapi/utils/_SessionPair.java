package com.se.cgapi.utils;

public class _SessionPair {

    private String sess;

    private String username;

    public _SessionPair(String key, String value){
        this.sess = key;
        this.username = value;
    }

    public String getSess() {
        return sess;
    }

    public void setSess(String sess) {
        this.sess = sess;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
