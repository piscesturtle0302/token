package com.example.demo.customer.enums;

public enum Sex{

    MALE("男"),
    FEMALE("女"),
    OTHER("其他");

    private String localName;
    private Sex(String localName){
        localName = this.localName;
    }
    public String getLocalName(){
        return localName;
    }

}
