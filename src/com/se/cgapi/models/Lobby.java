package com.se.cgapi.models;

import com.se.cgapi.utils.RandomString;

public class Lobby {

    private String p1;
    private String p2;
    private int age;
    private final String code;
    private String expectedMove;
    private boolean isFull;

    private final static RandomString gen = new RandomString();

    public Lobby(String player){
        this.code = gen.nextString();
        this.isFull = false;
        this.p1 = player;
        this.expectedMove = p1;
        this.age = 0;
    }

    public String getP1() {
        return p1;
    }

    public String getExpectedMove() {
        return expectedMove;
    }

    public void switchMove () {
        if(this.expectedMove.equals(p1)){
            this.expectedMove = this.p2;
        }else {
            this.expectedMove = this.p1;
        }
    }

    public void setP1(String p1) {
        this.p1 = p1;

    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        this.age = this.age + 1;
    }

    public String getCode() {

        return code;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }
}
